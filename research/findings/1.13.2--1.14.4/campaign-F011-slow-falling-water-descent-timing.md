# F011: Slow-falling water descent predicate timing

- **1.13.2**, ornithe-feather: `net/minecraft/entity/living/LivingEntity.java`; lines 1480-1484.
- **1.14.4**, ornithe-feather: `net/minecraft/entity/living/LivingEntity.java`; lines 1786-1793.

## Difference

Both use `d=0.01` when slow falling applies at the start of movement. 1.13.2's special water descent condition reads current `velocityY` after movement and water damping. 1.14.4 stores `bl = initialVelocityY <= 0.0` before travel and later gates the special descent assignment on that stored boolean.

The remaining tolerance expressions are structurally similar.

## Movement meaning
If the player enters this branch with positive vertical velocity and movement/collision changes it to a nonpositive value before the descent check, 1.13.2 may take the special near-zero descent branch while 1.14.4's captured `bl` remains false.

This is a source-derived precondition, not a reproduced trajectory.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F011-slow-falling-water-descent-timing.md).
