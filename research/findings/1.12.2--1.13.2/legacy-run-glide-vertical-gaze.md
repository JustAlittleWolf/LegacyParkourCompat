# 1.13.2 suppresses the vertical-gaze glide correction

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 1239-1244.
- **1.13.2**, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`; lines 1251-1257.

## Difference

Under fall-flying at yaw `0.0F`, pitch `-90.0F`, and nonzero horizontal speed, 1.12.2's nonzero residual horizontal look vector satisfies its pitch-only dive guard and applies its dive lift and horizontal correction. 1.13.2's look vector has zero horizontal length and the added `k > 0.0` guard skips those terms.

The 1.13.2 condition also prevents division by a zero horizontal length.

This endpoint comparison does not identify when either change first appeared.

## Movement meaning

`LivingEntity.mobTick()` dispatches virtual `moveRelative()` on the player; `LivingEntity.moveRelative()` selects glide integration when `isFallFlying()` is true.

The local player's accepted camera pitch reaches `Entity.getRotationVector()`.

Fall-flying activation and equipment eligibility are not needed for the numeric method difference beyond the state precondition; server acceptance and Elytra application conditions remain external context.

Source and the sine-table expressions prove the different horizontal look length at the stated angles and the 1.12.2/1.13.2 branch decision. 1.12.2 applies a pitch dive term while 1.13.2 skips it, changing vertical and horizontal velocity before later drag and collision.

The normal-pitch glide gravity expression was also regrouped in 1.13.2 and remains a separate queued arithmetic candidate pending exact accumulated-velocity analysis.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.12.2--1.13.2/findings/glide-vertical-gaze.md).
