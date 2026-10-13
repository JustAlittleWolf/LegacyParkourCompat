# Water sprint gravity gate

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: 1.12.2 applies the water vertical adjustment even while sprinting. 1.13.2 ordinary water gravity is guarded by !isSprinting(), in addition to the recorded no-gravity and vertical-clamp conditions.

Movement meaning: A sprinting player can skip a vertical velocity update. This is independent of the horizontal drag coefficient and of the changed non-sprinting gravity magnitude.
