# F-WATER-GRAVITY — ordinary 1.13.2 water travel uses a smaller downward adjustment

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: under those conditions 1.12.2's adjustment is `-0.02`; 1.13.2's ordinary adjustment is `-0.005`, subject to the explicit clamp branch.

Movement meaning: under those conditions 1.12.2's adjustment is `-0.02`; 1.13.2's ordinary adjustment is `-0.005`, subject to the explicit clamp branch.
