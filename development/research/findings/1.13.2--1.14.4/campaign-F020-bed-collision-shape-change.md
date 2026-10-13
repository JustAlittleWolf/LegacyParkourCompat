# F020: Bed lower collision footprint changes

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/block/BedBlock.java`, `getDropItem`, lines 38–42, 209–212.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/block/BedBlock.java`, `getShape`, lines 48–56, 180–192.

Difference: Under identical bed state and world position, 1.13.2 returns one solid box covering the full 16-by-16 footprint from y=0 to y=9/16. 1.14.4 returns a 16-by-16 mattress from y=3/16 to y=9/16 plus two 3-by-3 corner support boxes from y=0 to y=3/16. `FACING` and `PART` choose which two corners support each bed half; the adjacent head/foot half selects the other pair. The bed registrations and inherited collision-enabled default match. 1.14.4's collision context is not read by this shape method.

Movement meaning: Player `Entity.move` collision resolution (S007-S009) -> world block-collision enumeration (S020) -> `BlockState.getCollisionShape` -> inherited `Block.getCollisionShape` -> `BedBlock.getShape`.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F020-bed-collision-shape-change.md).
