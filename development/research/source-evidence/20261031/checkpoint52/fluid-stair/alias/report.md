# Fluid stair predicate alias: 1.13.1 and 1.13.2

**Result: 1.13.1 is an exact source alias of 1.13.2 for the old fluid stair-face rejection and the complete vanilla stair registration scope.** This extends the old-predicate endpoint evidence to 1.13.1. It does not independently extend the player fluid-impulse caller or its body-fluid query to 1.13.1.

## Source identity and integrity

Both read-only roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather`. Their ready records identify exact Minecraft versions 1.13.1 and 1.13.2 and `ornithe-feather`; 1.13.1 provenance identifies Feather Gen2 `1.13.1+build.2`, and the 1.13.2 ready record identifies the corresponding `1.13.2+build.2` mapping artifacts. Each version's source manifest hashes match its ready record, and each manifest binds every compared source file below.

| Identity | 1.13.1 | 1.13.2 |
|---|---|---|
| Ready-record SHA-256 | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` |
| Provenance SHA-256 | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |
| Source-manifest SHA-256 | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

| Relative source file | SHA-256 in both versions |
|---|---|
| `net/minecraft/fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| `net/minecraft/block/StairsBlock.java` | `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |

## Predicate and registered domain

In both versions, `net/minecraft/fluid/FlowingFluid.java`, `getFlow` at lines 54–99 calls `isSolidFace` for each horizontal neighbor and the block above it when `FALLING` is true. `isSolidFace` at lines 105–123 has the same ordered gates: reject same-fluid neighbors, accept `UP`, reject ICE, then set `bl = Block.isExceptionForAttachment(block) || block instanceof StairsBlock`; it returns a solid face only if `!bl` and the queried face shape is `FaceShape.SOLID`. The class import and all source bytes match across the two versions, so the stair rejection is categorical and is independent of individual stair-state face shapes.

`net/minecraft/block/Block.java` registers exactly these 17 stair IDs as `new StairsBlock(...)` in each version, and `net/minecraft/block/Blocks.java` has matching aliases for all 17: `oak_stairs`, `cobblestone_stairs`, `brick_stairs`, `stone_brick_stairs`, `nether_brick_stairs`, `sandstone_stairs`, `spruce_stairs`, `birch_stairs`, `jungle_stairs`, `quartz_stairs`, `acacia_stairs`, `dark_oak_stairs`, `prismarine_stairs`, `prismarine_brick_stairs`, `dark_prismarine_stairs`, `red_sandstone_stairs`, and `purpur_stairs`. The two files are byte-identical between the versions, and a recursive source search finds no other `StairsBlock` constructor sites. The registry-level vanilla stair domain is therefore equal at these endpoints; every listed key uses the excluded class. `StairsBlock.java` is also byte-identical, preserving the same state schema and face-shape behavior.

## Boundary and review

For the assigned old-side question, 1.13.1 and 1.13.2 are aliases, not a boundary: both reject every registered stair before face-shape testing. Together with the independently reviewed 1.13.2→1.14 result in `../1132-1144-finish46/fluid-face-boundary/result.md`, this supports the old predicate at both 1.13.1 and 1.13.2 and the new predicate beginning at 1.14.0 (with 1.14.1–1.14.4 retention verified by that result). The independent review at `../1132-1144-finish46/fluid-impulse-independent-review/review.md` has SHA-256 `e32d3f8d661bb668e8d536a52c94715dbe8883cd646c62d9151d0063b83144ab` and accepts the cited 1.13.2→1.14.4 impulse candidate revision.

This audit establishes predicate and stair-registration aliasing only. It does not establish the 1.13.1 entity fluid scan, player velocity write, state-producer history, or any trajectory.
