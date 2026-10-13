# F-S5-PISTON-HEAD-THRESHOLD: retracting moving-piston head uses a different short-shape threshold

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`, `getCollisionShape`, lines 333–361.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`, `getCollisionShape`, lines 335–363.

Difference: Java evaluates the relational comparison before boolean `!=`. At progress 0.5F, `1.0F - progress` is 0.5F. 1.15.2's `< 4.0F` comparison is true, so a retracting source piston (`extending == false`) sets `SHORT` true. 1.16.5's `< 0.25F` comparison is false, so the same state sets `SHORT` false. The two versions therefore select different short/long piston-head collision geometry at that progress. This is a bounded source-state difference; it does not assert a specific player displacement.

Movement meaning: `MovingPistonBlock#getCollisionShape` dispatches into this block entity method.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-PISTON-HEAD-THRESHOLD.md).
