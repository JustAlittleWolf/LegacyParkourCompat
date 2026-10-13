# matching open trapdoor above a ladder is climbable

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isClimbing() {`, lines 794-800.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isClimbing() {`, lines 905-917.

Difference: 1.8.9 returns climbing only when the block at the player's floored position is a ladder or vine, and excludes spectators. 1.9.4 keeps that spectator exclusion and adds a trapdoor branch: the block must be a `TrapdoorBlock`, its `OPEN` property must be true, the block directly below must be a ladder, and the ladder and trapdoor `FACING` values must match.

The ladder and trapdoor state properties and their registrations exist on both sides; the new rule therefore changes movement for an arrangement of existing blocks rather than assigning historical behavior to a modern-only block.

Movement meaning: `LivingEntity.moveRelative(FF)V` consults `isClimbing()` before movement and applies the climbing horizontal clamp, fall-distance reset, downward-velocity clamp, and player-sneak descent rule. 1.8.9's consumer is at lines 1150-1162; 1.9.4's corresponding consumer is at lines 1376-1393 in the same paired `LivingEntity.java` files.

The new predicate also reads the block state directly below and compares its facing.

When all 1.9.4 predicate conditions hold, 1.9.4 selects the climbing travel branch; 1.8.9 does not.

The resulting player velocity follows the existing climbing clamp branch.
