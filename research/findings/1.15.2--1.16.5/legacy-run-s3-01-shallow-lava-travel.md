# S3-01: Shallow-lava travel uses a different velocity adjustment

- **1.15.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s3-01-shallow-lava-travel.md); lines 1932–1943.
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s3-01-shallow-lava-travel.md); lines 1959–1971.

## Difference

1.16.5 adds a shallow-lava branch. `getFluidJumpThreshold()` is 0.4 when eye height is at least 0.4 and 0 otherwise. 1.15.2 has no corresponding branch in its lava travel code.

## Movement meaning

Player travel dispatches through `LivingEntity.travel`. 1.15.2 player affected by fluids and standing in lava whose measured height is at or below the threshold reaches 1.16.5's special adjustment.

Player flight bypasses fluid travel in both versions through their respective guards.

The vertical velocity multiplier and gravity adjustment differ in this shallow-lava state, while the horizontal multiplier remains 0.5.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s3-01-shallow-lava-travel.md).
