# 1.13.2 changes water travel drag while sprinting

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1555-1584.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/water-sprint-travel.md); lines 1617-1655.

## Difference

For a sprinting player in water, with no Depth Strider or Dolphin's Grace and gravity enabled, 1.12.2 damps horizontal velocity by `0.8F` and subtracts `0.02` from vertical velocity. 1.13.2 damps horizontal velocity by `0.9F` and skips that vertical gravity update.

The shared `0.8F` fallback is returned by `LivingEntity.getBaseMovementSpeedMultiplier()` in both versions.

This finding assumes the sprint flag is already active at water travel.

## Movement meaning

The local sprint flag is an input-controlled player state; it feeds the inherited water branch. 1.13.2's submerged-sprint gate and swim flag are documented separately.

Depth Strider changes `f` and gravity state/tags can affect surrounding movement; their data and aggregation remain in stages 5-6.

The finding excludes Dolphin's Grace so that its separate coefficient override is not conflated.

Source proves different post-move horizontal damping and a skipped vertical adjustment under the stated precondition.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/water-sprint-travel.md).
