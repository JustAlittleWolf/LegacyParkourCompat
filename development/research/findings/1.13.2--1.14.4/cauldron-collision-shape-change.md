# Cauldron lower collision geometry changes

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/block/CauldronBlock.java`, `class/shape initialization`, lines 32–45.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/block/CauldronBlock.java`, `class/shape initialization`, lines 33–52.

Difference: Both versions compute the collision shape as the full-block shape minus a second shape, using the same `ONLY_FIRST` Boolean operation and the same upper interior box from y=4/16 to16/16. 1.13.2's second shape contains only that interior box, leaving a solid full-footprint floor from y=0 to4/16. 1.14.4 adds three lower boxes spanning y=0 to3/16: a full-width strip across the middle in each horizontal axis and a central square. Their union removes the lower interior and side-cross regions, leaving four corner L-shaped supports through y=3/16; the 3/16-to4/16 layer remains a continuous plate. The registrations and collision-enabled defaults match.

Movement meaning: Player `Entity.move` collision resolution (S007-S009) -> world block-collision enumeration (S020) -> `BlockState.getCollisionShape` -> inherited `Block.getCollisionShape` -> `CauldronBlock.getShape`.
