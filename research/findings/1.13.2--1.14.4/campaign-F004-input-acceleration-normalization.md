# F004: Input acceleration cutoff and vector arithmetic

- **1.13.2**, ornithe-feather: `Entity.java`; lines 1057-1075.
- **1.14.4**, ornithe-feather: `Entity.java`; lines 1062-1077.

## Difference

1.13.2 computes squared input magnitude in float, returns below `1.0E-4F`, square-roots and normalizes using float operations, then adds rotated components to double velocity. 1.14.4 computes Vec3d squared magnitude in double, returns below `1.0E-7`, conditionally normalizes in double and scales before yaw rotation and vector addition.

Threshold, numeric type and operation sequence differ.

## Movement meaning

LivingEntity travel calls the helper for ordinary movement and water/lava paths; player overrides may customize `moveRelative`.

The output feeds velocity consumed by Entity.move.

Source proves changed arithmetic and cutoff.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F004-input-acceleration-normalization.md).
