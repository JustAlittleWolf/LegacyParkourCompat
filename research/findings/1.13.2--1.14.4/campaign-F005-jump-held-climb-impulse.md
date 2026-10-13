# F005: Jump-held climb impulse

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1575–1578.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1846–1850.

Difference: After moving, 1.13.2 sets vertical velocity to 0.2 only if `collidingHorizontally && isClimbing()`. 1.14.4 sets it if `(collidingHorizontally || jumping) && isClimbing()`. Thus holding jump while climbing can trigger the 1.14.4 assignment even without horizontal collision.

Movement meaning: The ordinary travel branch follows non-water/non-lava and non-fall-flying dispatch. `jumping` and `isClimbing()` are player/entity state inputs; exact climbing block registrations and their historical applicability remain to be inventoried.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F005-jump-held-climb-impulse.md).
