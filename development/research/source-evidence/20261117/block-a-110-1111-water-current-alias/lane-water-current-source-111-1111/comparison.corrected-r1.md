# 1.11 to 1.11.1 ordinary water-current source comparison

## Scope and verdict

Compare only the vanilla ordinary-water-current path for an on-foot, nonflying, nonfalling `LocalClientPlayerEntity` on a loaded client chunk. The examined operation is the `Entity.checkWaterCollisions` -> `World.applyLiquidDrag` -> `LiquidBlock.applyMaterialDrag` / `getFlow` path, including its caller AABB, loaded-region gate, flow accumulation and velocity addition, plus the inherited client tick path that reaches it.

**Verdict: no source difference in this bounded operation between 1.11 and 1.11.1.** Every listed operation and directly used helper below was compared by extracted method text and was identical. This does not establish whole-pair coverage, an intermediate patch boundary, or behavior for other fluid checks, eyes, collision shapes, other releases, or unrelated movement paths.

## Exact source identities

The canonical publisher files are `ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, and `ornithe-feather.sources.sha256` beside each version tree. The hidden `.decompile-provenance.json` is absent in both mapping directories, so identity is bound to the publisher sidecars. Each ready marker resolves to the exact requested release and Ornithe Feather mapping. All source-manifest entries were SHA-256 checked against the current source bytes: 1,918/1,918 files for 1.11 and 1,921/1,921 for 1.11.1; no mismatches. The roster contains the sidecar and relevant source hashes.

## Compared behavior

### Client tick and caller

- `LocalClientPlayerEntity.tick` tests `world.isChunkLoaded(new BlockPos(x, 0.0, z))`, then runs its superclass chain when true. `World.isChunkLoaded(BlockPos)` delegates to the overload with `allowEmpty=true`; `ClientWorld.isChunkLoadedAt` returns `allowEmpty || !chunk.isEmpty()`, so that client-side predicate short-circuits to true even when the chunk is empty. The inherited `PlayerEntity.tick`, `LivingEntity.tick`, and `Entity.tick` call chain is identical for this pair. `Entity.tick` calls `baseTick`; `Entity.baseTick` calls `checkWaterCollisions` every time it runs.
- For an unmounted player, `Entity.checkWaterCollisions` passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` to `world.applyLiquidDrag(..., Material.WATER, this)`. Its alternate branch is the boat-mount check. This caller has no `onGround`, fall-distance, flying, or logical-side condition around the water-current scan. The local-player loaded-chunk gate is the client tick gate examined here.
- `LivingEntity.checkFallDamage` has a secondary `checkWaterCollisions` call only when `!isInWater()`. That does not change the already-in-water ordinary current scan described here.

### Bounds and loaded selector

- `World.applyLiquidDrag` derives `i/k/m = floor(bounds.minX/minY/minZ)` and `j/l/n = ceil(bounds.maxX/maxY/maxZ)`. It first requires `isAreaLoaded(i, k, m, j, l, n, true)`; failure returns `false` before scanning or applying current.
- `World.isAreaLoaded` rejects a vertical range unless `maxY >= 0 && minY < 256`, then checks every intersected X/Z chunk using shifted integer bounds and inclusive chunk loops. It calls `isChunkLoadedAt` with `allowEmpty=true`; `ClientWorld.isChunkLoadedAt` short-circuits true for that flag, so the client chunk check does not veto this scan. The vertical-range test can still return false.
- After the loaded check, the block scan uses X/Y/Z loops with lower bounds included and ceil maxima excluded. It selects `blockState.getMaterial() == material`, computes `d = p + 1 - LiquidBlock.getHeightLoss(LEVEL)`, and accepts the cell for the current accumulator when `l >= d`.

### Per-cell flow and aggregate velocity

- The world passes the current aggregate vector through matching cells in deterministic nested X, then Y, then Z order. `LiquidBlock.applyMaterialDrag` returns that aggregate plus this cell's `getFlow` result.
- For one cell, `getFlow` starts three doubles at zero and visits `Direction.Plane.HORIZONTAL`. `getLiquidDepth` returns `-1` for another material and maps same-material levels `>= 8` to depth `0`. A same-material neighbor contributes `(neighborDepth - centerDepth)` times each direction offset. If the neighbor is not liquid and does not block movement, the cell directly below is sampled; a nonnegative below depth contributes `belowDepth - (centerDepth - 8)` times the direction offsets.
- If the current state level is `>= 8`, the routine tests each horizontal neighbor and the cell above it for a solid face. On the first match it replaces the current cell vector with `normalize(vector) + (0, -6, 0)` and breaks. It then normalizes the per-cell vector before returning it.
- After all cells, the world checks `aggregate.length() > 0` and `entity.hasLiquidCollision()`. If both hold, it normalizes the aggregate once and adds `0.014 * x`, `0.014 * y`, and `0.014 * z` to the entity velocity components, in that order. For a player, virtual dispatch selects `PlayerEntity.hasLiquidCollision`, which returns `!abilities.flying`; it is true for the assigned nonflying case and false when flying. The `Entity` default implementation returns true but is overridden here.

## Exact method evidence

All extracted method bodies/annotations below are identical across the pair:

- `LocalClientPlayerEntity.tick`: 1.11 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:159-174`; 1.11.1 same path `:159-174`.
- `PlayerEntity.tick`: `net/minecraft/entity/living/player/PlayerEntity.java:174-251` in both.
- `LivingEntity.tick`: 1.11 `net/minecraft/entity/living/LivingEntity.java:1579-1709`; 1.11.1 same path `:1581-1711`.
- `Entity.tick`: 1.11 `net/minecraft/entity/Entity.java:313-319`; 1.11.1 same path `:317-323`.
- `Entity.baseTick`: 1.11 `net/minecraft/entity/Entity.java:321-408`; 1.11.1 same path `:325-412`.
- `Entity.checkWaterCollisions`: 1.11 `net/minecraft/entity/Entity.java:884-900`; 1.11.1 same path `:934-950`.
- `LivingEntity.checkFallDamage`: `net/minecraft/entity/living/LivingEntity.java:193-209` in both.
- `World.applyLiquidDrag`: 1.11 `net/minecraft/world/World.java:1592-1634`; 1.11.1 same path `:1571-1613`.
- `World.isAreaLoaded(int, int, int, int, int, int, boolean)`: `net/minecraft/world/World.java:216-235` in both.
- `LiquidBlock.getHeightLoss`, `getLiquidState`, `getLiquidDepth`, `isFaceSolid`, `getFlow`, and `applyMaterialDrag`: `net/minecraft/block/LiquidBlock.java:50-53`, `:58-60`, `:62-65`, `:83-93`, `:131-172`, and `:175-178` in both.
- `Block.isFaceSolid`: `net/minecraft/block/Block.java:343-345` in both.
- `World.isChunkLoaded(BlockPos)` and its overload: `net/minecraft/world/World.java:182-188` in both. `ClientWorld.isChunkLoadedAt`: `net/minecraft/client/world/ClientWorld.java:105-107` in both; its source file SHA-256 is `996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f` in both versions.
- `PlayerEntity.hasLiquidCollision`: 1.11 `net/minecraft/entity/living/player/PlayerEntity.java:1729-1731`; 1.11.1 same path `:1736-1738`. Both extracted methods return `!this.abilities.flying`. The relevant `PlayerEntity.java` and `World.java` file hashes are listed in the unchanged `source-roster.json`.
- `Entity.hasLiquidCollision` default: 1.11 `net/minecraft/entity/Entity.java:2069-2071`; 1.11.1 same path `:2128-2130`; the player override controls the assigned path.

The source hashes for every cited file are in `source-roster.json`. No runtime execution or product implementation was used.