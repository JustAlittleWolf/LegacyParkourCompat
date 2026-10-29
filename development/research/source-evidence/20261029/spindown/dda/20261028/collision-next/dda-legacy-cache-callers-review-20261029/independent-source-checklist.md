# Independent 1.8.9 shared-bounds caller checklist

Snapshot: 2026-10-29. This is an independent source-only call-site inventory for review of `dda-legacy-cache-callers-20261029`. It does not inspect product code or make implementation/parity claims. Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.9\ornithe-feather`. The source hash manifest `ornithe-feather.sources.sha256` is SHA-256 `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`; provenance JSON is `BA066FC8F1C7CEB9819D2106338D9E9F2C2B96EB5A8329EB40E171B1F9889185`.

## Shared instance and movement reader

`net/minecraft/block/Block.java` SHA-256 `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528`: fields `minX/minY/minZ/maxX/maxY/maxZ` are mutable instance bounds; constructor invokes `setShape(0,0,0,1,1,1)` (line 201), and protected-final `setShape` writes all six (292–298). Public min/max getters read those same fields (599–619). `rayTrace` calls virtual `updateShape(world,pos)` then clips using the fields (456 onward). Base `getCollisionShape` and `getOutlineShape` snapshot the fields into a `Box` (outline 325 onward; collision 346 onward); base `addCollisions` reads `getCollisionShape` and appends its result when intersecting (339–343). Therefore writes made by any world, render, particle, projectile, or item caller affect later reads of that globally registered Block instance.

`net/minecraft/world/World.java` SHA-256 `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE`: `getCollisions(Entity,Box)` enumerates loaded blocks and dynamically calls `block.addCollisions(...)` (891–950); entity movement calls this for the primary and step-up collision scans (`Entity.java`, lines 439 and 469; see hash below). `getBlockCollisions(Box)` similarly invokes `addCollisions(..., null)` (967–1004), used by `Entity.pushAwayFrom` (Entity.java line 1525). These are movement/entity collision reader roots; subclass callbacks may refresh mutable bounds as well as read them. `World.isUnobstructed(Box,Entity)` (1402 onward) checks intersecting entities only, so it is not a block-bounds read despite its name.

Accepted writer matrix: `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/report.md`, SHA-256 `F9905E2F93B0C53C330EDCEA37742576BF370F00F4452CA1F0FDAC1CDD1507C2`; accepted ID146 correction and rebind must accompany any claim that the known direct movement consumers are 145, 54, and 146. The matrix is a bounded provider index, not a complete caller inventory.

## Direct `World.rayTrace` roots found by full source search

The complete `rg "\.rayTrace\\("` search under 1.8.9 `net/minecraft` found these call families. `World.rayTrace` has overload defaults at lines 625–630 and the five-argument implementation at 633; visited blocks call virtual `Block.rayTrace` at lines 649 and 747. The exact caller flags matter because the implementation may call `getCollisionShape` before ray eligibility when `ignoreBlocksWithoutCollision=true`.

| Caller | Source and bounded role |
|---|---|
| Client camera pick | `client/render/GameRenderer.java:315–330` calls `Minecraft.getCamera().rayTrace`; `entity/Entity.java:1029–1033` calls `world.rayTrace(..., false, false, true)`. The camera normally is the local player, but camera identity is not itself proof of player identity. |
| Third-person camera obstruction | `client/render/GameRenderer.java:~520–535` performs eight direct `world.rayTrace` calls with default `(false,false,false)` semantics to shorten the camera arm. It runs on client render path and mutates the same Block singletons. |
| Item use/placement | `item/Item.java:259–276`, `getUseTarget`, calls `world.rayTrace(..., allowLiquids, !allowLiquids, false)`. Callers found: `BucketItem.java:27`, `EnderEyeItem.java:114`, `GlassBottleItem.java:17`, `LilyPadItem.java:21`, and `SpawnEggItem.java:103`. These player interaction paths vary `allowLiquids`; the conditional ignore-collision gate differs from camera pick. |
| Boat placement | `item/BoatItem.java:~30–42` calls `world.rayTrace(..., true)` (default other flags), then may query `World.getCollisions` to validate the new boat. |
| Mob visibility | `entity/living/LivingEntity.java:1538–1540`, `canSee`, uses default flags from mob AI and may occur between player movement reads. |
| Water navigation | `entity/ai/pathing/WaterPathNavigation.java:55` calls `(false,true,false)` for a mob path probe. |
| Projectiles | `entity/projectile/ProjectileEntity.java:106`, `ThrownEntity.java:150`, `FishingBobberEntity.java:236` call the default overload during entity ticks. `ArrowEntity.java:191` calls `(false,true,false)`; additionally its `tick` directly invokes `block.updateShape(world,pos)` at 161 before reading `getCollisionShape` at 162, including its in-ground check. These are non-player writers, but can leave shared state for a later player read. |
| Explosion visibility | `world/World.java:1594–1620` samples `getBlockDensity` via repeated default `rayTrace`; `world/explosion/Explosion.java:131` invokes it. Damage/explosion physics is outside player-movement implementation scope, but these calls can update shared bounds. |
| Block placement item | `item/BoatItem.java` is the direct item `rayTrace` root in the sources found. `Item.getUseTarget` is the common block-target root; follow its interaction callers for timing. |

The search also found delegating `Block.rayTrace` overrides in `AbstractRailBlock`, `DoorBlock`, `TrapdoorBlock`, `TorchBlock`, and per-subbox `StairsBlock`; `MovingBlock.rayTrace` returns null before base clipping. Exact flag/caller distinctions and custom overrides are already in the accepted provider/hit contract and must be cited rather than rederived here.

## Direct `updateShape` callers outside the DDA ray visitor

An independent whole-tree search for non-declaration calls to `updateShape(` found additional mutation paths that a lifecycle census must explicitly classify:

| Caller | Source and effect |
|---|---|
| Client block model rendering | `client/render/block/BlockModelRenderer.java:25–29` invokes `block.updateShape(world,pos)` before tessellation. This runs across rendered blocks, not just the targeted block. |
| Client liquid rendering | `client/render/block/LiquidRenderer.java:31–35` invokes `LiquidBlock.updateShape` before the liquid mesh. |
| Target block outline | `client/render/world/WorldRenderer.java:1687` refreshes the targeted block then reads its outline. |
| Weather precipitation | `client/render/GameRenderer.java:1252` refreshes each candidate ground block and reads `getMaxY` for rain/drop positions. |
| Rain splash particles | `client/entity/particle/RainSplashParticle.java:48–58` refreshes the block under each particle and reads maxY for solid material. |
| World-event particle burst | `client/render/world/WorldRenderer.java:2088` calls `DyeItem.spawnParticles`; `item/DyeItem.java:98–111` refreshes the block then reads maxY for particles. |
| Arrow in-ground check | `entity/projectile/ArrowEntity.java:154–165` refreshes the remembered block then reads its collision shape. |
| Moving-block delegation | `block/MovingBlock.java:133–157` may call the moved block's virtual `updateShape`; its source context must be traced separately from the `MovingBlock` camera-ray override, which returns null. |

Many concrete block methods also call their own `updateShape` from `getCollisionShape`, `addCollisions`, or ray overrides (for example Banner, Door, Cocoa, Wall, Trapdoor, Ladder, Slab, PistonBase, Skull). Those are dispatch-side writes and must be accounted for when enumerating `World.getCollisions` and `getBlockCollisions` callers. Other `updateShape` invocations in constructors or same-class state helpers (for example Carpet/Snow/PressurePlate/Rail) must be classified as constructor/default-reset/state-derived and not silently treated as external reset boundaries.

## Other mutable-bound writer roots

`ArrowEntity.tick` is a confirmed non-player direct writer outside the camera ray path. `DyeItem.spawnParticles`, renderers, and particle ticks are also client-side direct writers. The exact source has `Block.setShape` as protected and final; all writes are inside block subclasses or direct assignments in subclasses. Complete search must include both `setShape(...)` and direct assignments to the six inherited fields, not just public ray calls. `World.getCollisions` and `getBlockCollisions` are separate dispatch roots because some movement providers themselves refresh cached fields. Block state mutation/world callbacks may also lead to future refreshes and do not prove a shared-instance bounds reset.

## Review checklist for the author's report

- Enumerates all direct `World.rayTrace` roots above with flags, including the eight third-person camera rays and `World.getBlockDensity` callers.
- Treats the camera, item, explosion, projectile, AI, world-event, render and particle paths as possible global cache writers where they dispatch to mutating providers; excludes none solely because non-player physics or rendering is out of product scope.
- Includes direct non-ray `updateShape` callers (Arrow, renderers, weather, particles, DyeItem, MovingBlock delegation), plus any source-proven other callers.
- Enumerates direct writes (`setShape` and six-field assignments), distinguishing constructor/default resets, state-derived writes, and provider refreshes; does not infer that any unrelated query resets all instances.
- Lists `getCollisions` movement readers and `getBlockCollisions` entity reader, and distinguishes `isUnobstructed` (entity-only in World.java) from block collision reads.
- Checks if non-player callers can occur before later local-player movement. If tick/render order is not closed, records it as a retained lifecycle gate instead of asserting a safe phase boundary.
- Does not claim runtime order, absence of mod/plugin/external callers, or a nonempty operational adapter domain without exact evidence.

## Hashes for source roots used above

| Source | SHA-256 |
|---|---|
| `block/Block.java` | `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528` |
| `world/World.java` | `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE` |
| `entity/Entity.java` | `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B` |
| `entity/living/LivingEntity.java` | `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E` |
| `entity/living/player/PlayerEntity.java` | `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88` |
| `entity/projectile/ArrowEntity.java` | `B1B6B352655944A24143261BCE900467860B577D0E69D0C872818B4B80C4A18F` |
| `item/DyeItem.java` | `4433405AEDCB507AE5A7060B91B4619FC7B758ADC3EE00A13804F028B5DC2598` |
| `item/Item.java` | `0B79C6167DD2093713C651F615CB2324213553C343FCF918F2CD90B2C81BC922` |
| `item/BoatItem.java` | `78C08AF94FA758A321F639C0A2010C8C56B81B3D54B493F295985181E6D2AFE2` |
| `client/render/GameRenderer.java` | `69B0007074901E5BD617F35039F122F21C67402340FE08B3DAEA1B316C6E7940` |
| `client/render/world/WorldRenderer.java` | `100EAF364BF96E15D8E1B6353CC2570B5D1D5E710986118653AD0A17B6989EF0` |
| `client/render/block/BlockModelRenderer.java` | `A8FEA83DA7A867FBCA0CA0A0CAFFF185B41E73870533C3FC3CAF9CD70B612925` |
| `client/render/block/LiquidRenderer.java` | `7B3810B0059A3EAF84949AFE01057271F9BD6CEBA16910EFFECE59F58B5104F3` |
| `client/entity/particle/RainSplashParticle.java` | `E8B95B0FC843FC53D241B624246B389B02AACA5835539ED28D1AF95506429E51` |
| `world/explosion/Explosion.java` | `68D633251F2A6E604E53C2838693ED986692B87BF48E1BD390EE07C1E9C3390F` |

This checklist is not yet the final verdict; the author report remains to be compared against it.
