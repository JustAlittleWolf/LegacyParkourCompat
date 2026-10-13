# WORLD-09: boat-passenger water travel branch

- **1.8.9**, ornithe-feather: `Entity.java`; line 300.
- **1.9.4**, ornithe-feather: `Entity.java`; line 361.

## Difference

For a non-flying boat passenger overlapping water, 1.8.9 queries water and can select water travel. 1.9.4 forces inWater=false and selects land travel.

## Movement meaning

The mounted local player delegates travel to LivingEntity. Its water-state flag selects water travel in 1.8.9 and land travel in 1.9.4 for the recorded boat/water precondition.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-09-boat-water-travel-branch.md).
