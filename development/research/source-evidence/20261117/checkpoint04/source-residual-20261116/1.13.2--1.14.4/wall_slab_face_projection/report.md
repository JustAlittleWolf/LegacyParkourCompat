# Slab wall face projection closure

## Scope and exact sources

Closes only the common slab row in `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`). Compared Minecraft 1.13.2 and 1.14.4 Ornithe Feather Gen2 build 2. The endpoint source-manifest bytes hash to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`, respectively; both ready markers identify the expected version and mapping, and each cited source-file digest agrees with its manifest entry and local source bytes.

| Version | Source file | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/SlabBlock.java` | `2e7ff3fcfa5faa83aa4af7ea9b68f07af8d8fe7047e08ee3a9d7d37b78c7128f` |
| 1.14.4 | `net/minecraft/block/SlabBlock.java` | `91f2ebc592e5cb07d5ce8a6faa123ec0d4b7bf10aa6323d6aceb343bc0f73a53` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.13.2 | `net/minecraft/block/Block.java` registration body | same `Block.java` digest above |
| 1.14.4 | `net/minecraft/block/Blocks.java` registration body | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/shape/VoxelShape.java` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |

Registration sources identify all four requested common keys (`stone_slab`, `brick_slab`, `quartz_slab`, `purpur_slab`) as `SlabBlock` in both endpoints: old `Block.java:1555–1564`, target `Blocks.java:1323–1353`. These keys are absent from the target `WALLS` resource payload (the 14 values in the reused `wall_resource_membership/payloads/data/minecraft/tags/blocks/walls.json`, SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`). None is an attachment exception, so the generic face arm is the applicable wall predicate in both endpoints.

For geometry only, reuse `stair_shape_provider/coverage.md` (SHA-256 `7c45510fc88f2c49d003c040619bdaf74a9a878fba460a33163c793450859753`): it establishes matching slab collision boxes in the two endpoints—DOUBLE is the unit cube; TOP and BOTTOM are full-X/Z half-height boxes. This assignment independently projects those already-established boxes through the wall predicates and does not broaden the geometry claim.

## Predicate and state-space comparison

Old 1.13.2 `SlabBlock.getFaceShape` (`SlabBlock.java:69–78`) returns `SOLID` for every face only when `HALF=DOUBLE`; for TOP it returns `SOLID` only for UP, and for BOTTOM only for DOWN. Old `WallBlock.shouldConnectTo` (`WallBlock.java:51–65`) accepts this generic arm when the block is not excepted and the returned face is `SOLID`. Thus all horizontal wall directions are true for DOUBLE and false for TOP/BOTTOM.

Both endpoints define slab state with `HALF` and Boolean `WATERLOGGED` (`SlabBlock.java:34–44` old; `:20–34` target); all six combinations in this two-property state domain are covered, including DOUBLE with either waterlogged value. Old face-shape selection and both endpoints' collision-shape selection do not read `WATERLOGGED`. Target 1.14.4 `WallBlock` (`WallBlock.java:48–52`) accepts the generic face arm when `Block.isFaceSolid` succeeds. The target check is `!state.is(BlockTags.LEAVES) && isFaceFull(state.getCollisionShape(...), face)` (`Block.java:402–408`). These slab keys are not leaves. The full cube for DOUBLE passes in every horizontal direction. For TOP and BOTTOM, the face slice normal to any horizontal axis spans only half of the vertical interval, so it is not a full-block face; `VoxelShape.getFaceShape` constructs that boundary slice (`VoxelShape.java:170–200`).

Each table cell is `old → target`. The result is identical for each of the four common keys and either `WATERLOGGED=false` or `true`:

| `HALF` | NORTH | EAST | SOUTH | WEST |
|---|---|---|---|---|
| `DOUBLE` | true → true | true → true | true → true | true → true |
| `TOP` | false → false | false → false | false → false | false → false |
| `BOTTOM` | false → false | false → false | false → false | false → false |

Therefore the complete requested projection is closed for 4 keys × 3 slab types × 2 `WATERLOGGED` values × 4 horizontal directions, with no old/target wall-bit difference and no unknown state in this slice. This is a wall directional-bit comparison only; it does not claim wall-inventory completion, pair freeze, or measured player-trajectory parity.
