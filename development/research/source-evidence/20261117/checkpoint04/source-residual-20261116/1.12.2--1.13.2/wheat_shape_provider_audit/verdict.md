# Independent audit: wheat collision shape provider

## Verdict

**ACCEPT** for the bounded registered `minecraft:wheat` provider claim in `wheat_shape_provider/wheat-shape-provider.md` (SHA-256 `81d3d16b76253fe2d7d5bd976d451b8b6a7b5dbfcf009fa7ed536096a0c07eee`). No corrections are required. The report correctly finds equivalent AGE 0–7 outline geometry, but an empty collision shape for wheat in both releases; therefore this provider slice establishes no ordinary player collision delta. Pair coverage remains partial.

## Identity and registration checks

- 1.12.2 ready JSON and `ornithe-feather.sources.sha256` both bind manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 ready JSON and source sidecar both bind manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- All eleven Java file hashes in the audited report were recomputed and matched both the source bytes and the relevant manifest entries.
- The accepted registry inventory/crosswalk hashes match: old `registry-collision-1122.md` `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`; new `registry-collision-1132.md` `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`; crosswalk `registry-collision-crosswalk.md` `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`. The exact key `minecraft:wheat` joins to `WheatBlock` in both, and the crosswalk marks it `EXACT_KEY_ONLY`.
- The report also cites the accepted provider owner roster SHA `524e27cf0618ea8942a961f27835fabf7091248a0629b9f03f4bd4d39c05a979`; the roster is owner inventory only and is not used as geometry evidence.

## Provider and reachability checks

1.12.2 `WheatBlock.AGE` has inclusive range 0–7 and indexes eight boxes with unit X/Z bounds and Y maxima from 1/8 through 1.0 in 1/8 increments. `PlantBlock.getCollisionShape` returns `EMPTY_BLOCK_SHAPE` for all states; `BlockState` dispatches to that override, and base `Block.addCollisions` receives the resulting empty box.

1.13.2 uses `Properties.AGE_7` and eight `Block.block` shapes with Y maxima 2, 4, 6, 8, 10, 12, 14, and 16. The audited `Block.block` source divides coordinates by 16, producing the same outline bounds. `WheatBlock` is registered with `.noCollision()`, which sets `hasCollision=false`; base `Block.getCollisionShape` therefore returns `VoxelShapes.empty()` without returning the AGE outline. `WorldView.getBlockCollisions` obtains this collision shape and filters empty shapes.

The report’s player-reachability route is correct: old `Entity.move` queries `World.getCollisions`, whose block loop dispatches through `BlockState.addCollisions`; target `Entity.move` queries the world collision stream, whose block enumeration obtains each state’s collision shape. `PlayerEntity` extends `LivingEntity`, which extends `Entity`, so player movement uses these routes. The state and collision gates establish that wheat cannot constrain ordinary player movement in either endpoint.

This audit accepts no crop growth, harvesting, food, rendering, non-player, or pair-wide claim.
