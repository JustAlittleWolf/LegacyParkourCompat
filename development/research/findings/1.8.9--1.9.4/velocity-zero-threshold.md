# per-axis velocity cutoff is lower

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void mobTick() {`, lines 1385-1458.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void mobTick() {`, lines 1645-1716.

Difference: Before jump and travel, both versions independently test the absolute value of `velocityX`, `velocityY`, and `velocityZ` and replace a component with zero only when it is strictly below the threshold. 1.8.9 uses `0.005`; 1.9.4 uses `0.003`.

For a component whose absolute value is at least `0.003` and below `0.005`, 1.9.4 retains the value while 1.8.9 zeros it.

Equality at either boundary is not zeroed by that component's strict comparison.

Movement meaning: The cutoffs are in `LivingEntity.mobTick`, before the AI/jump/travel sections and before `moveRelative(FF)V`.

The compared fields can carry prior or externally supplied velocity.

All velocity writers and later branch consumers remain in the open `INV-STATE` and `INV-EXTERNAL` inventories.

The source proves a changed pre-travel velocity state for the specified component interval.

It does not establish any observed position or trajectory.
