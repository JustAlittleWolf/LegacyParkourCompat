# Bounded liquid state projection and legacy provider facts

## Scope and evidence identity

Source comparison: Minecraft **1.8.9 / Ornithe Feather** to **26.2 / published unobfuscated**. Canonical read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Source SHA-256 values are in `canonical-source-hashes.sha256`; hashes for every permitted prior input are in `allowed-input-hashes.sha256`. The supplied source identity records old provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` and current provenance SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` (`source-manifest.json`). Exact mapping labels are as above; the supplied manifest does not expose the mapping artifact/build coordinate beyond those labels and provenance digests.

## 64-row serialized-state projection

`fluid-state-projection.tsv` enumerates every `(legacy ID 8–11, metadata 0–15)` pair. All 64 old decodes are valid. The old decoder is `Block.deserialize`: low 12 bits select `byId(i)`, bits 12–15 select metadata, then `getStateFromMetadata(j)` (`1.8.9 Block.java:131–135`). `LiquidBlock.getStateFromMetadata` stores the metadata as `LEVEL` directly (`LiquidBlock.java:299–301`); its state property is inclusive 0–15, default 0 (`:22, 24–26`). Thus the old tuple is `LEVEL=n`, retaining the exact registered block instance:

| ID | Old registry key | Registered instance | Old tuple for each metadata `n` |
|---:|---|---|---|
| 8 | `flowing_water` | `FlowingLiquidBlock(Material.WATER)` | `LEVEL=n` |
| 9 | `water` | `LiquidSourceBlock(Material.WATER)` | `LEVEL=n` |
| 10 | `flowing_lava` | `FlowingLiquidBlock(Material.LAVA)` | `LEVEL=n` |
| 11 | `lava` | `LiquidSourceBlock(Material.LAVA)` | `LEVEL=n` |

The four registrations are distinct entries and instances in `Block.java:851–854`. The same registration statements set the old internal keys to `water` or `lava`, so those internal keys do not erase the distinct numeric IDs/classes.

For the direct 26.2 chunk-palette numeric projection, row index is `((id << 4) | metadata)` (128–191 here). `BlockStateData` registers exact rows and fills only absent rows from a per-block default (`BlockStateData.java:686–705`); each row in this slice is an explicit mapping and the ledger gives its `BlockStateData` source line. The direct tuples are:

- IDs 8 and 9: `minecraft:water {level=n}`.
- IDs 10 and 11: `minecraft:lava {level=n}`.

The table also records the legacy name aliases attached to each numeric target: `flowing_water` for ID 8, `water` for ID 9, `flowing_lava` for ID 10, and `lava` for ID 11, each with `{level=n}`. This numeric state mapping preserves the level tuple but merges the old source-versus-flowing block identity into one current block key per fluid. It does **not** prove the old block objects or movement providers equivalent.

The 26.2 `DataFixers.java:585` `Flowing fixer` is registered as ordered fixer 2 (after the colorless shulker item fixer, before coral block renames) and renames `flowing_water → water` and `flowing_lava → lava`. Per-row rename trace is empty because the numeric `BlockStateData` target is already `water` or `lava`; this is separate from the fixer’s name-based alias route. `BlockNameFlatteningFix` handles `BLOCK_NAME` choices and is not evidence for the numeric `BLOCK_STATE` tuple. For all 64 rows the recorded chunk filter is false, both chunk-storage recalculation flags are false, and no listed later state fix applies. The ledger explicitly retains the per-row rename and later-fix traces.

In 26.2, `Blocks.java:296–318` registers one `LiquidBlock` per fluid; current `LiquidBlock` has `LEVEL` with default 0 (`LiquidBlock.java:53,77`) and `BlockStateProperties.LEVEL` accepts 0–15. Each ledger target is registered, has exactly `level=n`, and is accepted by the current property codec. The codec route is `BlockState.CODEC` (`BlockState.java:9`) plus the state/property codec (`StateHolder.java:145–156`); the ledger records the resolved key and concrete property tuple on every row. These source facts establish the exact direct tuple projection and the name merge, not complete world/chunk migration behavior or parity of fluid physics/context.

## Old liquid movement providers and ray gate

`LiquidBlock.getCollisionShape` returns `null` for every state (`LiquidBlock.java:110–112`). Inherited `Block.addCollisions` adds a box only if the provider returns non-null and it intersects (`Block.java:340–344`), so IDs 8–11 contribute no AABB via this old block-collision provider at any level. This says nothing about fluid drag, buoyancy, or other fluid mechanics.

The liquid constructor stores full-unit bounds with `setShape(0,0,0,1,1,1)` (`LiquidBlock.java:24–28`). Neither `FlowingLiquidBlock` nor `LiquidSourceBlock` overrides the inherited ray path. `Block.rayTrace` calls `updateShape(world,pos)` then intersects against the instance’s stored six bounds (`Block.java:456 onward`); inherited `updateShape` is empty (`:596–597`). Consequently the legacy direct provider has a full-cube ray box and no per-query bound update from these classes.

`LiquidBlock.canRayTrace(state, allowLiquids)` is exactly `allowLiquids && LEVEL == 0` (`LiquidBlock.java:69–70`). `World.rayTrace` passes its `allowLiquids` argument to this predicate for the start cell and traversed cells (`World.java:626–650, 746–747`); its overload without a liquid argument supplies false (`:621–624`), while the overload with one forwards the caller value (`:626–627`). A separate earlier gate is `!ignoreBlocksWithoutCollision || getCollisionShape(...) != null` (`World.java:648` and traversed-cell check near `:745`); because liquid collision shape is null, `ignoreBlocksWithoutCollision=true` skips the liquid before `canRayTrace`. This establishes the old provider gates only; it does not identify a particular movement-relevant caller’s chosen flags or claim traversal/first-hit parity.

## Bounded modern context

26.2 `LiquidBlock.getCollisionShape` is context-sensitive despite `.noCollision()` registration: an `alwaysCollideWithFluid` context yields a full block shape; otherwise nonzero levels are empty and level 0 may yield a living entity’s liquid collision shape when the context is above and `canStandOnFluid` accepts it (`LiquidBlock.java:81–95`). Ordinary entity collision context sets `alwaysCollideWithFluid=false` (`EntityCollisionContext.java:39–45,56–65`); `getShape` is a separate empty outline provider (`LiquidBlock.java:145–147`). These are provider/context facts only. No ordinary-player parity, standing reachability, or collision equivalence is claimed.

## Limits and open gates

This bounded artifact covers only serialized IDs 8–11, metadata 0–15 and the generic old liquid collision/ray provider facts above. It does not establish full chunk repair output outside the row-local transforms, exact migration-path execution for an actual save, current fluid movement parity, a caller-specific movement-ray flag, ray cell visitation or hit selection, or broad DDA behavior. The mapping artifact/build coordinates are not present in the allowed provenance projection, so they remain an evidence-identity gate beyond the named mapping families and recorded provenance digests.
