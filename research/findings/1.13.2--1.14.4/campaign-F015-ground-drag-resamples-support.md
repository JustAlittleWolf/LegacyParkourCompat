# F015: Ground drag resamples support after movement

- **1.13.2**, ornithe-feather: `LivingEntity.java`; lines 1538-1558.
- **1.14.4**, ornithe-feather: `LivingEntity.java`; lines 1840-1866.

## Difference

In 1.13.2's ordinary non-fluid, non-fall-flying branch, the block below the player's shape is sampled before movement to compute the ground acceleration.

After `move`, 1.13.2 resets the drag coefficient from `0.91F`, rechecks `onGround`, and, if still grounded, reads slipperiness at the player's new position before multiplying horizontal velocity by the resulting value. 1.14.4 captures `onGround ? slipperiness * 0.91F : 0.91F` before movement in `w`, then uses that same `w` after movement. 1.14.4 therefore does not resample support state or the block below the resulting position for this drag step.

## Movement meaning

The difference is reachable in ordinary land travel when the player crosses a support boundary or moves onto a block with a different slipperiness during the move.

It can also differ if collision/movement changes `onGround` between branch entry and the post-move drag.

Source proves that 1.13.2's post-move horizontal damping may use a different coefficient from 1.14.4's pre-move coefficient.

No specific block transition, numeric delta, or trajectory is claimed.

F006 separately records the acceleration-formula arithmetic; this finding covers coefficient sampling time and location.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F015-ground-drag-resamples-support.md).
