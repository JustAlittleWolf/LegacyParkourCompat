# 1.8.9 shared Block-bounds lifecycle callers

Snapshot: 2026-10-29. Source-only inventory for the exact 1.8.9 Feather Gen2 build 2 export. Canonical root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/`. The export has 1,612 Java files; its ready marker binds the source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaeca92284187c6fe9410d8004`, and the artifact-manifest SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`. Relevant files are bound in `source-hashes.json` and checked against that canonical source manifest.

## Decision

The old per-block bounds are mutable fields on each `Block` instance: `Block.minX/minY/minZ/maxX/maxY/maxZ` are instance fields (`Block.java:95-103`), the static `Blocks` registry fields hold registered instances (`Blocks.java:7-20`; `Block.java:31-34`), and `Block.setShape` writes all six fields (`Block.java:292-298`). Inference: worlds loaded in the same JVM (including integrated client/server worlds) can reference those same static block objects. `Block.getCollisionShape` and `getOutlineShape` read those fields; the generic `addCollisions` path calls `getCollisionShape` (`Block.java:335-351`). `Block.resetShape` is empty by default (`Block.java:654`), so there is no general automatic restoration after a writer.

The camera DDA is only one writer path. Other source callsites include player item interactions, camera collision/outline rendering, block-model and weather rendering, particles, projectile collision, moving-piston collision, AI line-of-sight/pathing, and explosion exposure rays. Several are outside movement behavior themselves, yet their writes target the same registered `Block` instances that later player collision calls query. The source does not expose one lifecycle boundary that observes all of these calls and all six-field writes. A camera-pick-only observer therefore cannot establish cache validity for ordinary player movement.

The confirmed ordinary local-player reader is `Entity.move` calling `World.getCollisions` (`Entity.java:439,469`), which scans blocks through `World.getCollisions` → virtual `Block.addCollisions` (`World.java:891-923`) → generic `Block.getCollisionShape` when not overridden/refreshed. Accepted cache-consumer evidence establishes Anvil 145, Chest 54 and Trapped Chest 146 as stale-bound movement consumers; ID 146 is supplied by the immutable correction/rebind cited below, which supersedes the original matrix's two-consumer count. This inventory does not upgrade the other provider families from the writer matrix's open consumer status.

## Call paths that can write shared Block bounds

`World.rayTrace` (`World.java:633-761`) calls a block's virtual `rayTrace` for eligible cells. The accepted old provider report binds the visitor flags, per-cell eligibility, virtual dispatch, custom overrides and source order; this audit reuses that evidence instead of repeating its math. Whole-source search of the 1,612 Java files finds direct `World.rayTrace` entry points captured in `ray-call-sites.txt`:

| Caller | Source behavior and lifecycle meaning |
|---|---|
| `GameRenderer.pick` → `Entity.rayTrace` (`GameRenderer.java:311-327`; `Entity.java:1029-1034`) | Ordinary camera pick; the entity method calls `World.rayTrace(from, to, false, false, true)`. |
| `GameRenderer` camera transform (`GameRenderer.java:521-536`) | Up to eight world rays for third-person camera obstruction; the two-argument overload uses `false,false,false`. |
| `GameRenderer.renderBlockOutline` (`WorldRenderer.java:1679-1690`) | Not a ray call: directly calls `block.updateShape` then reads `getOutlineShape`, so it bypasses a wrapper around `World.rayTrace`. |
| Item target acquisition (`Item.java:259-275`, used by `BucketItem`, `EnderEyeItem`, `GlassBottleItem`, `LilyPadItem`, `SpawnEggItem`) | Player item-use paths; `getUseTarget` calls `World.rayTrace(start, end, allowLiquids, !allowLiquids, false)`. The `ignoreBlocksWithoutCollision` flag varies with liquid policy. |
| `BoatItem.useOnBlock` (`BoatItem.java:40`) | Player placement target ray, via the three-argument overload with liquids enabled. |
| `LivingEntity` visibility (`LivingEntity.java:1539`) | Entity-to-entity line-of-sight ray; not player movement, but it dispatches the same world/block providers. |
| `ProjectileEntity`, `ThrownEntity`, `ArrowEntity`, `FishingBobberEntity` tick paths | Non-player ray queries; `ArrowEntity` and `WaterPathNavigation` use `ignoreBlocksWithoutCollision=true`. Their provider writes are relevant to the global cache even though their own physics is out of scope. |
| `WaterPathNavigation` (`WaterPathNavigation.java:55`) | Mob path query with flags `false,true,false`. |
| `World.getBlockDensity` (`World.java:1594-1614`) via `Explosion.damageEntities` (`Explosion.java:121-137`) | Samples many default-flag world rays and can write providers in succession. The ordinary `World.explode` path calls both `damageEntities` and `damageBlocks` (`World.java:1583-1590`). The client packet handler's `handleExplosion` (`ClientPlayNetworkHandler.java:820-828`) invokes only `damageBlocks`, not `damageEntities`; caller reachability depends on which explosion path is used. |

For calls with `ignoreBlocksWithoutCollision=true`, `World.rayTrace` calls `getCollisionShape` before `canRayTrace`/`rayTrace` for both the start cell and later visited cells (`World.java:648,745-747`). That prefilter and its ordering are part of those callers' cache history; it is not used by the ordinary `Entity.rayTrace` camera flags above. The accepted per-cell contract already records this generic-caller distinction.

Direct `updateShape`/`resetShape` callsites were searched across the complete export and are recorded in `shape-refresh-call-sites.txt`. Besides the `Block.rayTrace` provider, confirmed external paths include:

- `BlockModelRenderer.render(WorldView world, BakedModel model, BlockState state, BlockPos pos, BufferBuilder bufferBuilder)` calls `block.updateShape(world,pos)` before rendering (`BlockModelRenderer.java:25-29`); `BlockRenderDispatcher` invokes this overload for block model rendering (`BlockRenderDispatcher.java:64`). Another `BlockModelRenderer` overload calls `block.resetShape()` (`BlockModelRenderer.java:280-285`), but this is a separate overload/path and `Block.resetShape` itself is a no-op. It is not a general post-write reset guarantee.
- `WorldRenderer.renderBlockOutline` updates and reads shape (`WorldRenderer.java:1687-1690`); camera precipitation in `GameRenderer` updates shape before reading `getMaxY` (`GameRenderer.java:1252-1264`).
- `RainSplashParticle` updates a block shape before reading its max bound (`RainSplashParticle.java:52-60`). `LiquidRenderer.render` calls `LiquidBlock.updateShape` (`LiquidRenderer.java:31-34`), but `LiquidBlock` inherits the no-op `Block.updateShape` (`Block.java:596`); this particular call is not a bounds write.
- `DyeItem` updates the target block before reading a bound for particle placement (`DyeItem.java:105-115`).
- `ArrowEntity` updates a hit block then queries its collision shape (`ArrowEntity.java:161-162`).
- `MovingBlock.updateShape` (`MovingBlock.java:133-159`) calls `movedState.getBlock().updateShape(world,pos)` and copies its bounds into the moving-block singleton; renderer/updateShape callers can therefore dispatch a writer on the moved block singleton outside the camera ray path. Separately, `MovingBlock.getCollisionShape` (`:118-130`) calls the moved state's collision-shape getter and does not itself call `updateShape`.
- Individual collision getters also call `updateShape` before returning a bound-derived box (for example Door, Trapdoor, Ladder, Cocoa, Wall and PistonBase); these movement/query-time refreshes are in the accepted writer/provider closures and are not assumed to be reset calls.

The complete callsite scan includes false-positive `updateShape` methods on block entities (e.g. chest model/neighbor geometry) and unrelated shape types; the report's list above is restricted to calls that write `Block`'s six bound fields or dispatch its block provider. The raw scan aids rechecking and is not a claim that every same-name method is a Block-bounds writer.

## Readers and cache-use boundaries

- `World.getCollisions` (`World.java:891-923`) iterates the collision cells in x/z/y order and dispatches `block.addCollisions(...)`. The inherited implementation at `Block.java:339-343` asks `getCollisionShape`; the inherited getter at `Block.java:346-351` constructs a box from the shared min/max fields. `Entity.move` uses this route for axis clipping (`Entity.java:439-459`) and again in the step-up candidate path (`Entity.java:469`). This is the source-defined ordinary local-player movement consumer path.
- `World.getBlockCollisions` (`World.java:967-992`) also dispatches `addCollisions`. It feeds `Entity.pushAwayFrom` (`Entity.java:1520-1552`) and a `LivingEntity` collision-space check (`LivingEntity.java:1067`). These are non-player/general entity readers. `World.isFullCube` (`World.java:1720-1724`) reads `getCollisionShape` too, but it is reached from the base `Entity.pushAwayFrom` only.
- Important local-player override: `LocalClientPlayerEntity` calls `pushAwayFrom` four times during its tick (`LocalClientPlayerEntity.java:546-549`) but overrides the base method (`:292-343`). That override uses `canSurvive`/`Block.isSolid()` and does not call `World.getBlockCollisions` or `World.isFullCube`. Therefore the base push-away reader is **not** direct evidence of a cache consumer in the local-player path. The local-player movement cache consumer remains `Entity.move` → `World.getCollisions`.
- Other non-player and server callers of `World.getCollisions` are listed in `collision-read-call-sites.txt`; they are not proof of player use, but they must be inventoried if an unobserved write can persist until a later player query. `World.getBlockDensity` and generic ray paths likewise have no cache reset at method exit.

`BlockModelRenderer.resetShape`, selected dynamic getters, or a later query can refresh some classes. None provides a universal reset: the base reset is empty, resets are class-specific, and later queries can leave any block's instance bounds in the last state produced by their operation. There is no evidence here for a lifecycle-wide invalidation listener or a complete replay that re-seeds all registered blocks.

## Result and remaining gate

This inventory closes the existence and order of source-visible categories, but not their runtime reachability schedule in every client/server configuration, nor which open writer families can persist into the generic movement getter without a class-specific refresh. The confirmed writer list is a lower bound, not a proof that all unknown callers are harmless. The cache mirror cannot be called lifecycle-valid from the camera pick alone. To support a bounded old operation, the adapter needs a source-closed policy that observes or invalidates after every relevant non-camera writer (including rendering, item, entity and integrated-server routes) and a complete way to establish validity again. No such observation/reset boundary is established by this source inventory; whole-session native fallback after an unobserved writer is the only safe disposition under the accepted domain contract.

## Evidence identities

- Exact 1.8.9 source hashes and source-manifest checks: `source-hashes.json`.
- Whole-export static callsite inventories: `ray-call-sites.txt`, `shape-refresh-call-sites.txt`, `collision-read-call-sites.txt`, `block-instance-writers.txt`.
- Accepted provider contract: `.native-source/20261028/collision-next/dda-legacy-provider-closure/provider-and-hit-contract.md`, SHA-256 `20904785D6D067658FC433842146D364851822CAF7C7B8565CA5C8B761894A29`.
- Accepted writer matrix: `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/report.md`, SHA-256 `F9905E2F93B0C53C330EDCEA37742576BF370F00F4452CA1F0FDAC1CDD1507C2`. For the corrected movement-consumer count, bind `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/trapped-chest-146-correction.md` SHA-256 `2368CAF3DAF8B95EB35AFB3F3107C16C9DBFEC6D7F60DEFB6737724F4215290A` and accepted `.native-source/20261028/collision-next/dda-writer-coverage-matrix-review/id146-rebind-review.md` SHA-256 `A78F0CE756DF1B6DB2AAE1726B741013EB555798FCEDC22E51440B9C21656464`.


No product files, backlog, test, build, game, or runtime were read or run. No canonical sources were written.
