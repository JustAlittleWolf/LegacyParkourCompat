# Jump-held climb impulse

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, ordinary travel branch of `moveRelative`, lines 1537–1578.
1.14, ornithe-feather: `1.14/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, ordinary travel branch of `moveRelative`, lines 1795–1843.

Difference: After `move`, 1.13.2 sets vertical velocity to `0.2` only when `collidingHorizontally && isClimbing()`. In 1.14 it replaces the moved velocity's vertical component with `0.2` when `(collidingHorizontally || jumping) && isClimbing()`. The added `jumping` condition therefore triggers the assignment for a jumping climber without horizontal collision; the write remains after movement and before the later levitation/gravity adjustment.

Movement meaning: In the ordinary non-water, non-lava, non-fall-flying travel branch, a player holding jump while on a climbable surface can receive the `0.2` vertical replacement even without a horizontal collision. In both the 1.10 and 1.10.2 `ornithe-feather` profiles, `LivingEntity.isClimbing` returns true for ladder or vine blocks and for qualifying trapdoors (unless the player is a spectator), establishing reachable player climbing inputs in both profiles.

Applicability evidence: 1.10, `1.10/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `isClimbing`, lines 928–939; 1.10.2, `1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `isClimbing`, lines 928–939.
