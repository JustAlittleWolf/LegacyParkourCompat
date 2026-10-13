# F-ELYTRA-GLIDE-MATH — 1.13.2 regroups vertical glide acceleration

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: 1.13.2 regroups the arithmetic and introduces a shared gravity factor that can be affected by Slow Falling.

Preserve these source expressions and intermediate types/order; don't simplify them to a real-number identity.

Movement meaning: - Reachability and dependencies: inherited LivingEntity movement dispatch is called by PlayerEntity through the local player's travel tick while fall-flying.

Elytra state/equipment eligibility and camera pitch are separate dependencies.

Slow Falling itself is a modern-only effect for this historical one-way scope.
