# Wall trapdoor and shulker face closure

## Scope and source identity

Bounded source-only closure of the trapdoor and shulker-box rows in `wall_generic_neighbor_inventory/report.md`. Compared exact Minecraft 1.13.2 and 1.14.4 Ornithe Feather Gen2 build 2 sources, read-only. The 1.13.2 `ornithe-feather.sources.sha256` file hashes to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; its ready marker identifies version 1.13.2 and that digest. The 1.14.4 manifest hashes to `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; its ready marker identifies version 1.14.4 and that digest. Each source-file hash below matches its manifest row and current bytes.

## Compared wall gates

- Old 1.13.2 `net/minecraft/block/WallBlock.java:51–65`: `shouldConnectTo` accepts a solid `FaceShape` only when `isExceptionForConnection(block)` is false. That exception includes `Block.isExceptionForAttachment`; `net/minecraft/block/Block.java:241–256` includes every `ShulkerBoxBlock` and every block in `BlockTags.TRAPDOORS`. `net/minecraft/data/tag/BlockTagsProvider.java:261–263` builds that tag from the six wooden trapdoors and iron trapdoor. Thus both families are rejected on the solid-face arm for every queried horizontal direction. Their returned `FaceShape` is never a pole shape, so the secondary arm does not restore a connection.
- Target 1.14.4 `net/minecraft/block/WallBlock.java:48–52, 67–70`: the generic arm is `!isExceptionForAttachment(block) && stateFaceSolid`; the other arm is `BlockTags.WALLS` or a fence gate. Target `net/minecraft/block/Block.java:260–267` no longer lists shulker boxes or trapdoors as attachment exceptions. Neither family is among the 14 entries in the previously extracted target `walls.json` payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`). Both therefore reach the target directional collision-face predicate.
- Target 1.14.4 `BlockState.java:197–202, 326–331` dispatches face solidity through `Block.isFaceSolid` when a state has no cached face result. `Block.java:402–408` requires a non-leaves state and a full collision-shape face. Both families are absent from the six-entry target leaves payload already recorded in `wall_resource_membership/extraction-record.md`.

## Trapdoors: concrete state-dependent difference

Common registered keys are `oak_trapdoor`, `spruce_trapdoor`, `birch_trapdoor`, `jungle_trapdoor`, `acacia_trapdoor`, `dark_oak_trapdoor`, and `iron_trapdoor`. Old 1.13.2 `Block.java:1156–1161, 1382` registers each as `TrapdoorBlock`; target 1.14.4 `Blocks.java:656–672, 1073–1074` registers the same keys with the same class. Target 1.14.4 has no added trapdoor registration in this class beyond those seven.

Both `TrapdoorBlock` classes define the same six shapes and the same state dimensions (`FACING`, `OPEN`, `HALF`, `POWERED`, `WATERLOGGED`; old lines 30–40, 152–160; target lines 32–42, 145–150). Old `getFaceShape` (1.13.2 lines 157–160) returns `SOLID` only for the upward face of a closed top-half trapdoor or downward face of a closed bottom-half trapdoor. Every horizontal direction is `UNDEFINED`, for all four horizontal facings and either open value. Old wall bits are therefore false independently of the attachment exception.

Target collision-shape dispatch uses the same six slab shapes (target `TrapdoorBlock.java:32–61`); the class does not override `getCollisionShape`, so `Block.java:376–377` delegates to the state shape. For a closed trapdoor, the 3/16-high horizontal plate (`TOP_SHAPE` or `BOTTOM_SHAPE`) has no full horizontal face. For an open trapdoor, the vertical 3/16 slab has exactly one full horizontal face, at the block boundary it touches:

| State `FACING` when `OPEN=true` | Target full horizontal face | Old wall bit | Target wall bit |
|---|---|---:|---:|
| `NORTH` | `SOUTH` | false | true |
| `SOUTH` | `NORTH` | false | true |
| `WEST` | `EAST` | false | true |
| `EAST` | `WEST` | false | true |

For the other three horizontal faces of each open state, and all horizontal faces of each closed state, the target full-face predicate is false. `HALF`, `POWERED`, and `WATERLOGGED` do not change these horizontal collision faces. The target face calculation is the outer-face slice/full-block check in `VoxelShape.java:170–200` and `Block.java:406–408`; the 3/16 slab reaches the listed block boundary and spans the complete other two face axes. These results are common to all seven keys because registration uses the same class and shape constants.

## Shulker boxes: concrete all-horizontal-face difference

Common registered keys are `shulker_box` and all 16 color keys: `white_shulker_box`, `orange_shulker_box`, `magenta_shulker_box`, `light_blue_shulker_box`, `yellow_shulker_box`, `lime_shulker_box`, `pink_shulker_box`, `gray_shulker_box`, `light_gray_shulker_box`, `cyan_shulker_box`, `purple_shulker_box`, `blue_shulker_box`, `brown_shulker_box`, `green_shulker_box`, `red_shulker_box`, and `black_shulker_box`. Old 1.13.2 `Block.java:1615–1642` constructs all 17 as `ShulkerBoxBlock`; target 1.14.4 `Blocks.java:1458–1511` does the same. Each target registration sets `dynamicShape()`.

Old 1.13.2 `ShulkerBoxBlock.java:311–317` reports `SOLID` while its block entity stage is `CLOSED`; it reports `UNDEFINED` during `OPENING`/`CLOSING`, and when `OPENED` on the facing axis and its opposite. Despite those shape results, the old wall attachment exception rejects every state/direction; old wall bits are always false. The old face query reads and casts the position's `ShulkerBoxBlockEntity`, so that call has a valid-block-entity dependency even though the later wall exception suppresses its returned face.

Target 1.14.4 `ShulkerBoxBlock.java:226–229` supplies the block entity's dynamic shape (or a full-block fallback); `ShulkerBoxBlockEntity.java:93–99` constructs it by expanding the unit cube by up to 0.5 block along `FACING`. The unit cube is retained for every legal `FACING` and animation progress, so the target collision shape has a full boundary face on all six directions. Its dynamic shape avoids the ordinary per-state cached-face path (`BlockState.java:65–67, 197–202, 326–331`). Consequently each of the 17 common keys has target wall bit true for all four horizontal neighbor directions, for each of the six facing states and the closed/opening/opened/closing animation stages; old bit is false for all of them. The target fallback also yields a full face if the entity is not a `ShulkerBoxBlockEntity`; that invalid/transient-state case is not needed for the legal-state comparison.

## Result and remaining gate

Both assigned families are closed at the source level: trapdoors differ only for open states and the one slab-boundary face shown above; shulker boxes differ for every common legal facing/stage on every horizontal side. These are wall directional-bit/state-production differences reachable through `WallBlock` placement or neighbor updates (old call sites: `WallBlock.java:69–116`; target: `WallBlock.java:55–104`), not a measured player trajectory.

No unresolved comparison remains within these two families and common keys. This does not close the broader wall-neighbor inventory or claim pair freeze: the remaining exception families and the full common registered-block / target wall-tag crosswalk from `wall_generic_neighbor_inventory/report.md` were not part of this assignment.

## Source file identities

Hashes below are the exact UTF-8 source bytes declared by each endpoint's source manifest.

| Version | File | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/data/tag/BlockTagsProvider.java` | `db7e0aa20a1e9429380328b64812bdff9d2d4b3b9d2016888cb7348d0ebafc7e` |
| 1.13.2 | `net/minecraft/block/TrapdoorBlock.java` | `26dd6911517029daef779cef046e943953356644a76372f1d47b1323054629da` |
| 1.13.2 | `net/minecraft/block/ShulkerBoxBlock.java` | `9d2652c2944cc8faf1a277349fbf2015170a4799ad882f876d24ee1d7f344fb8` |
| 1.13.2 | `net/minecraft/block/entity/ShulkerBoxBlockEntity.java` | `d123b08d4d3562ccb972a4433ec5a743cdd6877c59c5b6aa20d06af2063fa5ed` |
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/TrapdoorBlock.java` | `129b1c0eda5e1431fe3845ef129524dd96c4eb018b686e6d0e38b093e198b3a5` |
| 1.14.4 | `net/minecraft/block/ShulkerBoxBlock.java` | `016487146bc14aba8de02272e0bf5c76b4dff16c276abf30bf92cfa3061593df` |
| 1.14.4 | `net/minecraft/block/entity/ShulkerBoxBlockEntity.java` | `58b5e9f68831f6e3a3d293ae29c8c0c55628fc5a8d941e18286eb10ec7c0ba73` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.14.4 | `net/minecraft/block/shape/VoxelShape.java` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
