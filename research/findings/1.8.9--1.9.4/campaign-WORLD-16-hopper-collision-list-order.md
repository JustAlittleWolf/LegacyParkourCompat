# WORLD-16: hopper collision-list order

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/block/HopperBlock.java`.
- **1.9.4**, ornithe-feather: `HopperBlock.java`.

## Difference

- 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/HopperBlock.java` `addCollisions()` dispatches the bottom, west, north, east, south boxes in that order (49-60).
- 1.9.4 source: paired `HopperBlock.java` `addCollisions()` dispatches bottom, west, east, north, south (56-62; box constants 38-42).

## Movement meaning

- Disposition: the source list order differs but the five-box candidate set is identical.

Given the same other collision candidates, the changed north/east append order cannot change player clipping or step selection because the axis clamps are order-independent; emptiness predicates are also order-independent.

This is a no-movement-difference disposition for this provider-order delta, not a claim that the full collision candidate list is equivalent.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-16-hopper-collision-list-order.md).
