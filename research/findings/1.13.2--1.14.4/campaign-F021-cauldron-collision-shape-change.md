# F021: Cauldron lower collision geometry changes

- **1.13.2**, ornithe-feather: `net/minecraft/block/CauldronBlock.java`; lines 32-45.
- **1.14.4**, ornithe-feather: `net/minecraft/block/CauldronBlock.java`; lines 33-52.

## Difference

Both versions compute the collision shape as the full-block shape minus a second shape, using the same `ONLY_FIRST` Boolean operation and the same upper interior box from y=4/16 to16/16. 1.13.2's second shape contains only that interior box, leaving a solid full-footprint floor from y=0 to4/16. 1.14.4 adds three lower boxes spanning y=0 to3/16: a full-width strip across the middle in each horizontal axis and a central square.

Their union removes the lower interior and side-cross regions, leaving four corner L-shaped supports through y=3/16; the 3/16-to4/16 layer remains a continuous plate.

The registrations and collision-enabled defaults match.

## Movement meaning

Player `Entity.move` collision resolution (S007-S009) -> world block-collision enumeration (S020) -> `BlockState.getCollisionShape` -> inherited `Block.getCollisionShape` -> `CauldronBlock.getShape`.

The shape is independent of `LEVEL`; once a cauldron block is in the queried bounds, it reaches this provider.

Source proves that the lower 3/16 footprint differs: 1.13.2 has a continuous solid base, while 1.14.4 removes the center and cross areas and retains corner supports. 1.13.2 player AABB intersecting those removed regions can receive a different collision shape and potentially a different clipped displacement.

The pair establishes a difference between 1.13.2 and 1.14.4, not the first release that introduced it.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F021-cauldron-collision-shape-change.md).
