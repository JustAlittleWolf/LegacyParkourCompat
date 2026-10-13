# F010: Water-travel climb impulse

- **1.13.2**, ornithe-feather: `net/minecraft/entity/living/LivingEntity.java`; lines 1617-1655.
- **1.14.4**, ornithe-feather: `net/minecraft/entity/living/LivingEntity.java`; lines 1882-1928.

## Difference

In 1.13.2's water branch, movement is followed by velocity damping and descent handling; there is no `isClimbing()` impulse in this branch. 1.14.4, after `move`, checks `collidingHorizontally && isClimbing()` and replaces the vertical component with `0.2` before applying water drag/gravity.

## Movement meaning

LivingEntity water travel -> Entity.move collision result -> `collidingHorizontally` and `isClimbing` -> vertical velocity.

Source proves 1.14.4 can assign upward velocity in the water branch under both guards.

It does not establish which exact block/fluid configurations satisfy them or the resulting trajectory.

Those are open dependencies in INV-WORLD-MOVEMENT/INV-COLLISION.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F010-water-travel-climb-impulse.md).
