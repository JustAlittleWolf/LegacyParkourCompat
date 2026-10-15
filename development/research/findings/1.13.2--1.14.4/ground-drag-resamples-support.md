# Ground drag repeats a support sample before movement

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1537–1580; `Entity.updateVelocity`, lines 1057–1075.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1840–1866.

Difference: In 1.13.2's ordinary non-fluid, non-fall-flying branch, the block below the player's shape is sampled to compute ground acceleration. After `updateVelocity`, but before climbable clamping and `move`, the method resets the drag coefficient from `0.91F`, rechecks `onGround`, and reads slipperiness again at the unchanged position. The subsequent `move` occurs after that second sample. `Entity.updateVelocity` only changes velocity, and PlayerEntity does not override it. 1.14.4 captures the corresponding coefficient before movement and retains it for drag. The earlier claim that the second 1.13.2 sample occurs after movement was incorrect.

Movement meaning: Crossing a support boundary during `move` does not change the already sampled drag coefficient on either endpoint. The repeated older read establishes a source-order difference, but no different position, grounded state or block state is established between the two reads in the ordinary local-player path. This observation does not justify a post-movement drag-resampling patch.
