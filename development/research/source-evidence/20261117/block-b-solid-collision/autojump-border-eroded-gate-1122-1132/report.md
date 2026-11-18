# 1.12.2–1.13.2 AutoJump border witness

## Scope and evidence identity

Source-only verification of one bounded Core AutoJump candidate. No product implementation, product history, wiki inventory, or backlog was consulted. The sources were read in the primary checkout at `decompiled_minecraft/<version>/ornithe-feather`; no source was generated or modified.

| Release | Mapping/source identity | Source manifest SHA-256 | Exact client SHA-256 | Mapping inputs |
|---|---|---|---|---|
| 1.12.2 | Ornithe Feather, `feather-gen2-1.12.2+build.2` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `feather-gen2-1.12.2+build.2-mergedv2.jar` and `.tiny` |
| 1.13.2 | Ornithe Feather, `feather-gen2-1.13.2+build.2` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `feather-gen2-1.13.2+build.2-mergedv2.jar` and `.tiny` |

Both provenance/ready records identify Java 25, Vineflower 1.12.0, ASM 9.10.1, MappingIO 0.9.1, Gson 2.14.0, Tiny Remapper 0.14.1 and Gradle 9.7.1. Ready-marker manifest digests equal the SHA-256 of each local source manifest. For all source files named below, the file SHA-256 was checked against its source manifest entry.

Relevant source file hashes (all match their corresponding source-manifest entries):

- 1.12.2 `LocalClientPlayerEntity.java`: `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`
- 1.12.2 `World.java`: `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`
- 1.12.2 `WorldBorder.java`: `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257`
- 1.12.2 `StatusEffect.java`: `b7953929609d29d36fb4714013870f9d3b6362b71682f3c06b597536de3eb2db`
- 1.13.2 `LocalClientPlayerEntity.java`: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`
- 1.13.2 `WorldView.java`: `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`
- 1.13.2 `WorldBorder.java`: `7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718`
- 1.13.2 `StatusEffect.java`: `60cc53e7233d28413d3c93292fd216babe8ea9edeb70774141b68101c515c639`
- 1.13.2 `IntVoxelShape.java`: `e563f5530907a5cd0ecc07bd79473d378c8c1aeeaba1dba4ac6f703de7d7b3ca`
- 1.13.2 `VoxelSet.java`: `fe39ea60ac27e595280627a4d15ea93a69292da5870b5ef34a1d7d7283a267c6`

## Bounded witness

Set a square world border with east maximum x=10. Use a loaded chunk covering x=10, vanilla AIR at x=10 for y=64 through 66, and only a support floor under the player (top y=64). Player center is z=0.5 and moves east from x=8.89 to x=8.99 with accepted horizontal delta dx=0.10, dz=0. Keep the player ordinary-sized and supported at y=64; AutoJump enabled, countdown initially 0, non-sneaking, not riding, nonzero forward input, yaw -90 degrees, no Jump Boost. The stated dx is accepted because the swept player box remains east of the synthetic wall (its x extent ends before 10); this is a specified reachable tick input/state, not a claim that every ordinary input produces exactly 0.10 displacement.

The 1.12.2 and 1.13.2 border-center predicate is strict and uses the border maximum eroded by 1 when `outsideWorldBorder` is true, or expanded by 1 otherwise. At post-move x=8.99, both tests say inside: `8.99 < 9` in the eroded case and `8.99 < 11` in the expanded case. The same holds at pre-move x=8.89. Thus the collision call sees `collidesWithBorder`/`isWithinBorder` true for either initial flag. Both versions update the flag only when the old and newly computed booleans are equal; starting true resets it false, while starting false leaves it false. Historic initial flag cannot change this witness's border branch.

The local-client `move` wrapper captures old x/z, performs superclass movement, and calls `autoJump((float)(newX-oldX), (float)(newZ-oldZ))`. Here actual horizontal displacement squared is 0.01 (> 0.001), so the input/yaw fallback is not used. With Speed II, the speed effect has a +0.2 movement-speed modifier at operation 2, and a player's base movement speed is 0.1, giving `f = 0.14`. AutoJump computes `p = max(f*7, 1/l)` where `l` is the fast inverse square root of the actual displacement squared; this yields approximately `max(0.98, 0.10) = 0.98`. Its direction is east, aligned with yaw -90 (dot product is approximately +1, passing the `m < -0.15` rejection gate). The ray endpoint is based on the already-updated position plus actual displacement plus lookahead: `8.99 + 0.10 + 0.98 = 10.07`. The autojump query box is expanded by player width 0.6 in X/Z and contains x=10. At current x=8.99, the two cells checked over the player's head are x=8, y=65 and y=66; they are AIR. The horizontal ray pair, offset upward by 0.51 and separated laterally by half-width 0.3, reaches the x=10 artificial border wall. The player and headroom gates pass; jump-height allowance `o` remains 1.2 without Jump Boost.

For x=10, `WorldBorder.contains(BlockPos)` is false at maxX=10 (`pos.getX() < getMaxX()`), even though that chunk is loaded. The block-collision scan includes its one-block vertical padding, so it visits border cells x=10 at y=63, 64, 65, and 66; the AutoJump query itself overlaps y=64 and the lower part of y=65. Physical AIR does not remove the border collision: the 1.12.2 world collision path substitutes STONE for cells outside the border when its entity is considered inside.

### Different first-hit height and timer result

- **1.12.2:** `World.getCollisions(entity, box)` iterates candidate block positions, substitutes STONE for outside-border block positions, then appends their collision boxes individually. `LocalClientPlayerEntity.autoJump` loops those boxes in order and stops on its first box intersecting either horizontal ray. The first x=10/y=64 wall cube has maxY=65, so `s=65`; relative to feet at y=64, `u=1`. With `u > 0.5` and `u <= 1.2`, it writes `ticksToNextAutojump = 1`.
- **1.13.2:** `WorldView.getBlockCollisions` accumulates all outside-border full-block voxels into one `BitSetVoxelSet`, then emits an `IntVoxelShape` for that accumulated shape. The padded candidate cells y=63..66 become a contiguous full-height box when `VoxelShape.getBoundingBoxes()` decomposes the aggregate; its maxY is 67. Although the AutoJump query only overlaps its lower portion, the returned box retains that full maxY. AutoJump's stream `flatMap(shape -> shape.getBoundingBoxes())` therefore sees `s=67`; `u=3` exceeds 1.2, so it returns before writing the countdown.

Difference: the old individual wall block boxes let AutoJump use the first intersected cube's top; the newer border-collision voxel aggregation presents the combined wall height as the first intersecting box. For this reachable supported step, the 1.12.2 client sets the one-tick AutoJump delay while 1.13.2 does not. This is a Core AutoJump source difference caused by border collision shape aggregation; vanilla block states remain AIR in both cases.

## Gates, limits, next step

The tested source gates are the local client wrapper/AutoJump preconditions, movement magnitude fallback, view alignment, two-block headroom, lookahead calculation, border-state predicate and border-cell synthesis. The witness math is internally consistent and the candidate is not rejected by the reach or `isWithinBorder` thresholds. It has not been run in a game, and no runtime parity claim is made. If continued beyond this assigned witness, independently check the collision voxel box decomposition and exact accepted-delta construction against a second reviewer before treating it as a frozen finding.

Primary source locations (path and method; source lines are exact decompiler output):

- 1.12.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `move` line 846, `autoJump` line 857 (lookahead line 896, first-hit/timer lines 946–948).
- 1.12.2 `net/minecraft/world/World.java`, collision collection line 964, `isWithinBorder` line 1042.
- 1.12.2 `net/minecraft/world/border/WorldBorder.java`, `contains(BlockPos)` line 24.
- 1.12.2 `net/minecraft/entity/living/effect/StatusEffect.java`, Speed modifier line 217; `PlayerEntity.java`, base speed line 157 and `getSpeed` line 1407.
- 1.13.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `move` line 871, `autoJump` line 882 (lookahead line 921, voxel-box iteration line 935, timer lines 969–971).
- 1.13.2 `net/minecraft/world/WorldView.java`, `getBlockCollisions` line 112, entity collision routing line 170, `collidesWithBorder` line 180.
- 1.13.2 `net/minecraft/world/border/WorldBorder.java`, `contains(BlockPos)` line 22.
- 1.13.2 `net/minecraft/block/shape/VoxelShape.java`, `getBoundingBoxes` line 109; `IntVoxelShape.java`, class line 6; `VoxelSet.java`, bounds and box decomposition; `BitSetVoxelSet.java`, occupancy and max-bound accumulation.
- 1.13.2 `net/minecraft/entity/living/effect/StatusEffect.java`, Speed modifier line 213; `PlayerEntity.java`, base speed line 159 and `getSpeed` line 1488.

