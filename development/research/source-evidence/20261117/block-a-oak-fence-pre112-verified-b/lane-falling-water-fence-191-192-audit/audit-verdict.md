# Independent source audit verdict — 1.9.1 → 1.9.2

## Immutable inputs

- Frozen independent source/provenance roster: `source-roster.md`, SHA-256 `175d92328a221e084043bbdc727f5e1211a579c444df083f304fdf7e7405a9ee`.
- Author comparison reviewed after freeze: `lane-falling-water-fence-191-192/roster-comparison.md`, expected and observed SHA-256 `7cd4e5b1e04072e826d94d22182400f5c2df8ec014929740347f630f7e4a5ff7`.
- Exact source identities and manifest hashes are bound in the frozen roster.

## Dispositions by slice

1. **Dry ordinary oak fence as falling-water side/above cue — PASS, no 1.9.1→1.9.2 movement delta.** `Block.java` registers ordinary oak fence ID 85 with `Material.WOOD` (line 875). `FenceBlock` is non-cube and non-solid-render, but neither predicate controls the cue. `LiquidBlock.getFlow` checks each horizontal neighbor and the cell one block above that neighbor (line 160). Its virtual `isFaceSolid` call dispatches to `LiquidBlock.isFaceSolid`: same-liquid neighbors are false; for the horizontal directions used by this loop, ordinary wood falls through to `Block.isFaceSolid`, which returns the target state's `material.isSolid()`. Thus an ordinary dry oak fence at either checked side position triggers the falling-liquid branch when the current water state's level is at least 8. The branch normalizes the current flow vector, adds `(0,-6,0)`, and the method normalizes again before returning. The report's source-level conclusion is correct; the LiquidBlock override is a necessary part of the call chain and was checked independently.

   Evidence in both releases: `LiquidBlock.java` lines 80–88, 128–168; `Block.java` lines 305–307, 875–882; `FenceBlock.java` lines 105–111; `Material.java` lines 9, 62–72. All files have identical manifest-recorded hashes across the pair.

2. **Normalized water flow to current player velocity — PASS, no release delta.** `LiquidBlock.applyMaterialDrag` adds each block's `getFlow` result to the accumulator. `World.applyLiquidDrag` scans intersecting water cells in nested X/Y/Z loops, tests `l >= d`, and accumulates contributions; if the final vector has positive length and `entity.hasLiquidCollision()` is true, it normalizes the accumulated vector and adds `0.014` times its components to entity velocity. `Vec3d.normalize` uses `sqrt(x*x + y*y + z*z)` and returns the shared zero vector when the length is below `1e-4`; this arithmetic is identical across the pair. The report is accurate on the behavior and no-difference result.

   Evidence in both releases: `World.java` lines 1554–1595; `LiquidBlock.java` lines 128–173; `Vec3d.java` lines 35–37; `Entity.java` lines 845–857. All have identical manifest-recorded hashes.

3. **LocalClientPlayer loaded gate and player nonflying gate — PASS, no release delta; supplemental source slice closes an omission in the comparison report.** `LocalClientPlayerEntity.tick` calls `super.tick()` only when the chunk at `BlockPos(x, 0.0, z)` is loaded (`LocalClientPlayerEntity.java:142–155`); that inherited tick reaches `Entity.checkWaterCollisions`. Independently, `World.applyLiquidDrag` returns false before scanning when the entity's entire expanded/contracted bounds area is not loaded (`World.java:1561–1562`), causing `checkWaterCollisions` to set `inWater=false`. Finally, `PlayerEntity.hasLiquidCollision` returns `!abilities.flying` (`PlayerEntity.java:1710–1712`), so flying suppresses the velocity-current application while liquid contact checking still occurs. `LocalClientPlayerEntity.isLocal()` is true and is used by the logical-movement-side predicate, but that ownership predicate is not a current-flow gate. These loaded and flying behaviors are unchanged across the pair.

   The author comparison covered the loaded liquid-area test and the nonflying predicate, but did not identify the distinct LocalClient tick-level chunk gate in its stated terminal inventory. This audit checked it against both exact sources and closes that coverage gap for this bounded slice.

## Overall bounded verdict

All three assigned slices are source-confirmed, their source dependencies are closed, and no falling-water/fence/current-application movement difference exists between these exact releases. No finding draft is warranted. This is a narrow slice verdict, not full-pair movement coverage or runtime parity.