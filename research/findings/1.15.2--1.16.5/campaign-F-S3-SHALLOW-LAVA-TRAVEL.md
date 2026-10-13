# F-S3-SHALLOW-LAVA-TRAVEL: shallow lava uses a different vertical adjustment

- **1.15.2**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 1913-1926.
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S3-SHALLOW-LAVA-TRAVEL.md); lines 1959-1978.

## Difference

The branch before movement is shared in outline: add relative input and move once.

When 1.16.5's lava height is at or below the eye-height-derived threshold, it uses vertical multiplier `0.8F` instead of 1.15.2's `0.5` and applies the falling-adjustment helper before common gravity.

The helper subtracts `d / 16.0` from y unless gravity is disabled or sprinting; those latter cases return the input vector unchanged.

At greater lava height 1.16.5 follows the 1.15.2-style `scale(0.5)` path, preserving the branch distinction.

## Movement meaning

For a non-flying Player in lava, 1.16.5's `Player.isAffectedByFluids()` returns true and `LivingEntity.canStandOnFluid()` returns false.

The fluid scan measures the intersecting height relative to the deflated bounding box and stores it under `FluidTags.LAVA`; `isInLava()` is true for positive height after first tick. 1.15.2 shallow positive fluid height no greater than the threshold reaches 1.16.5's special branch. 1.15.2's lava branch is selected by its lava-contact state and has no height split.

This finding does not derive player pose height beyond the explicit threshold method.

The source proves different y-velocity arithmetic in the shallow-lava branch; the x/z multiplier and later gravity addition are identified separately.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S3-SHALLOW-LAVA-TRAVEL.md).
