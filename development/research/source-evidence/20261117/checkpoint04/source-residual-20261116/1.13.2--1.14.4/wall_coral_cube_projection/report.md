# Coral block wall-neighbor predicate: 1.13.2 → 1.14.4

## Scope and identity

This packet checks only the five exact common `minecraft:*_coral_block` rows identified by the source-only unexamined projection. It compares each release's wall-neighbor predicate for the registered singleton state and all four horizontal directions. It does not claim movement trajectory, full provider inventory, or pair completion.

Canonical endpoints are Ornithe Feather Gen2 build 2. The 1.13.2 ready marker pins source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 pins `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. The ready markers and file source manifests were checked. The full registry crosswalk's unexamined projection TSV SHA-256 is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`.

Relevant canonical source-file SHA-256 values:

| Release | File | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/CoralBlock.java` | `1da2beb5dba501839639d1a33b79f7f3cb5524865e5c9990a1690ce10c5322a2` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/CoralBlock.java` | `817099b02117b7fc53695abae56dc7427b740dadd3a5bc41031b3e1201e1e61a` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/tag/BlockTags.java` | `dc59d07204b25e5d852d0384e849975367590a3cbc2b9a293396d0bf5637e52a` |

## Exact keys and result

The source crosswalk rows identify these five keys as `CoralBlock` with `Block` face-shape owner at both endpoints. Each has only the singleton default block state; neither `CoralBlock` source adds state properties.

| Key | Old bit (all horizontal directions) | Target bit (all horizontal directions) |
|---|---:|---:|
| `minecraft:tube_coral_block` | true | true |
| `minecraft:brain_coral_block` | true | true |
| `minecraft:bubble_coral_block` | true | true |
| `minecraft:fire_coral_block` | true | true |
| `minecraft:horn_coral_block` | true | true |

## Predicate evidence

In 1.13.2, `WallBlock.shouldConnectTo` accepts a neighbor when it is not `isExceptionForConnection` and its directional face is `FaceShape.SOLID` (apart from the separate fence-gate pole branch). `Block.getFaceShape` returns `SOLID`. The old `isExceptionForConnection` list does not include coral blocks. Each old registry registration creates a `CoralBlock` with `Block.Properties.of(Material.STONE, MapColor.*)` and no collision-disabling property. `CoralBlock` adds no face-shape or geometry override; inherited `Block.getShape` returns the full block shape.

In 1.14.4, `WallBlock`'s corresponding predicate accepts a full collision face when the block is not `Block.isExceptionForAttachment`, or accepts the separate `WALLS`/directional fence-gate branch. `Block.isExceptionForAttachment` lists leaves, barrier, pumpkins, and melon, not coral. The five target registrations construct `CoralBlock` with stone properties and do not call `.noCollision()`. `Block.Properties.hasCollision` defaults to true; inherited `Block.getShape` returns `VoxelShapes.block()`, and inherited `Block.getCollisionShape` returns the state's shape when `hasCollision` is true. Thus all four collision faces are full. None of the coral keys appears in the exact 1.14.4 `data/minecraft/tags/blocks/walls.json` values (payload SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`); so the `WALLS` branch does not change the result. CoralBlock is not a fence gate.

The resulting neighbor bit is true in every checked old and target case. This is a bounded no-difference result for these five provider/predicate rows only. It does not establish every update/placement context, other block classes, player collision effects, or pair freeze.

## Crosswalk limitation

The current r2 crosswalk is the assigned exact-key input. Preserve its historical audit limitation: the prior r1 TSV was overwritten, so the crosswalk correction audit could not byte-diff all unchanged rows from r1. This packet verifies the five named rows directly; it does not claim that limitation is repaired or re-audit the full crosswalk.
