# DDA provider closure: 1.8.9 Ender Chest (block ID 130)

## Result and scope

This is a source-only closure for the old 1.8.9 Ender Chest registry alias, metadata-to-state decoder, collision box and block-instance bounds used by collision/ray-trace methods, compared with the 26.2 `minecraft:ender_chest` state and collision-shape provider. It does not close old-vs-modern ray visitation, establish camera reachability of a DDA side effect, or claim runtime parity.

The canonical 1.8.9 source root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`, mapping `ornithe-feather`; the newer root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`, mapping `unobfuscated`. Both ready markers report status `ready`, exact version and mapping; their source-manifest digests match the on-disk manifests. Every selected Java source hash also matches the corresponding canonical source manifest. See `sources.sha256`.

## Old registry, metadata and provider

The 1.8.9 `Block.register` table assigns numeric ID 130 to key `ender_chest` (`Block.java:1112-1117`). `Blocks.ENDER_CHEST` resolves that same key (`Blocks.java:153,361`). The block constructor sets the only Ender Chest instance bounds to min `(1/16,0,1/16)` and max `(15/16,14/16,15/16)` (`EnderChestBlock.java:23-29`). A full-file scan finds no other `setShape` call in `EnderChestBlock`.

The old metadata decoder is `Direction.byId(metadata)`, followed by replacing any vertical direction with `NORTH` (`EnderChestBlock.java:115-122`). `Direction.byId` selects `ALL[Math.abs(id % 6)]`; enum IDs are `DOWN=0, UP=1, NORTH=2, SOUTH=3, WEST=4, EAST=5` (`Direction.java:11-18,168-169`). For the 4-bit metadata range 0–15, every input therefore decodes to a permitted horizontal state:

| Metadata | Old `FACING` |
|---|---|
| 0, 1, 2, 6, 7, 8, 12, 13, 14 | NORTH |
| 3, 9, 15 | SOUTH |
| 4, 10 | WEST |
| 5, 11 | EAST |

The old state schema contains only horizontal `FACING` (`EnderChestBlock.java:23,130-132`); its default is `NORTH`. No metadata value in 0–15 fails this decoder/property gate. This statement is limited to the state returned by the source method for those metadata inputs.

Old movement collision uses the base `Block.addCollisions`/`getCollisionShape` path: it builds a world-space box from the block instance's `minX/minY/minZ/maxX/maxY/maxZ` fields, then retains it on strict shape intersection (`Block.java:340-350`). The inherited `Block.rayTrace` first calls `updateShape(world,pos)` and then reads the same six fields (`Block.java:456-462`). `EnderChestBlock` does not override `updateShape`; the inherited implementation is empty (`Block.java:596-598`). Thus this block's shape is established once by its constructor and is not changed by the old per-position collision or ray-trace calls. The base fields are mutable in `Block`, but no state-dependent Ender Chest bounds writer is present in this exact class.

## 26.2 alias, state and geometry

The 26.2 `Blocks.ENDER_CHEST` registration uses `BlockItemIds.ENDER_CHEST` (`Blocks.java:2658-2663`); that ID is created from `"ender_chest"` (`references/BlockItemIds.java`, `BlockItemId.java:18-20`, which applies the default namespace). This matches the old registry key one-to-one for this block.

The modern block exposes `FACING = HorizontalDirectionalBlock.FACING` and an additional `WATERLOGGED` property; its default state is `NORTH, false` (`EnderChestBlock.java:44-56`). Its `getShape` returns one static `SHAPE` for every state, level, position and collision context (`EnderChestBlock.java:67-69`). `BlockBehaviour.getCollisionShape` returns the state's shape when `hasCollision` is true (`BlockBehaviour.java:327-328`). The Ender Chest registration does not call `noCollision`, and `Properties` defaults `hasCollision` to true (`Blocks.java:2658-2663`; `BlockBehaviour.java:985`). Waterlogging and facing do not affect this provider result.

The static shape is `Block.column(14.0, 0.0, 14.0)` (`EnderChestBlock.java:46`). The three-argument `column(sizeXZ,minY,maxY)` centers the width in X/Z, and `Block.box` divides coordinates by 16 (`Block.java:160-162,177-185`). The resulting bounds are exactly `(1/16,0,1/16)` through `(15/16,14/16,15/16)`, matching the old constructor bounds. All values are exact multiples of 1/16, so this bounded box comparison has no decimal-rounding discrepancy.

## Closed and open claims

Closed by this source evidence:

- Old numeric ID 130 and registry key `ender_chest` map to the modern `minecraft:ender_chest` registration.
- All 16 old metadata values decode to one of the four horizontal facing values as tabulated; default facing is north.
- Old collision geometry is fixed per block instance at construction and independent of facing; old rayTrace calls a no-op shape updater for this block and reads those same fields.
- The modern facing/waterlogged states all use one state-independent collision shape, its default collision gate is enabled, and its box exactly matches the old fixed box.

Not established here:

- Whether a particular historical player camera/tick actually queries this provider in an order that changes an emulated DDA result.
- Whether old `World.rayTrace` and modern `BlockGetter.traverseBlocks` visit the same provider sequence or stop at equivalent hits.
- Whether any mutable state retained on shared old `Block` instances affects a specific observer before a writer runs. For Ender Chest, the block source shows no per-query bounds writer.
- Whole-block-catalog conversion, block-entity behavior, or any runtime witness.

## Exact source references

- 1.8.9 `net/minecraft/block/Block.java`: ID registration 1112-1117; constructor bounds fields/setter 292-299; collision box creation/filter 340-350; ray-trace field read 456-462; base no-op `updateShape` 596-598.
- 1.8.9 `net/minecraft/block/Blocks.java`: `ENDER_CHEST` field/key 153, 361.
- 1.8.9 `net/minecraft/block/EnderChestBlock.java`: horizontal property/default/box 23-29; state decoder and metadata encoder 115-127; state schema 130-132.
- 1.8.9 `net/minecraft/block/state/property/DirectionProperty.java`: horizontal value-set construction.
- 1.8.9 `net/minecraft/util/math/Direction.java`: enum IDs 11-18; modulo lookup 168-169.
- 26.2 `net/minecraft/world/level/block/Blocks.java`: Ender Chest registration 2658-2663.
- 26.2 `net/minecraft/references/BlockItemIds.java`: Ender Chest resource key.
- 26.2 `net/minecraft/references/BlockItemId.java`: default namespace applied by `create(String)`.
- 26.2 `net/minecraft/world/level/block/EnderChestBlock.java`: properties/default 44-56; static shape/state-independent provider 46, 67-69.
- 26.2 `net/minecraft/world/level/block/Block.java`: box and centered column construction 160-162, 177-185.
- 26.2 `net/minecraft/world/level/block/state/BlockBehaviour.java`: default collision behavior 327-328; `hasCollision` default 985.
- 26.2 `net/minecraft/world/level/block/HorizontalDirectionalBlock.java`: shared horizontal facing property.
- 26.2 `net/minecraft/world/level/block/state/properties/BlockStateProperties.java`: horizontal facing and waterlogged properties.
- 26.2 `net/minecraft/core/Direction.java`: horizontal direction values used by the modern property.
