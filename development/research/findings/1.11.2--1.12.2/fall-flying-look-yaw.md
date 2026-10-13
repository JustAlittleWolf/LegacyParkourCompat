# Fall-flying direction uses body yaw instead of head yaw

1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public Vec3d getLookVector() {`, lines 1916-1918.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public Vec3d getLookVector() {`, lines 1664-1666.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public Vec3d getRotationVector(float tickDelta) {`, lines 1921-1929.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public Vec3d getLookVector() {`, lines 1690-1692.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public Vec3d getRotationVector(float tickDelta) {`, lines 1960-1968.

Difference: For 1.11.2, `LivingEntity.getLookVector()` calls the virtual `getRotationVector(1.0F)`, so the `LivingEntity` override supplies pitch and head yaw at the tick endpoint.

In 1.12.2 the `LivingEntity.getLookVector()` override is absent; the inherited `Entity` implementation calls the final two-float rotation-vector helper directly with pitch and body yaw.

Although the 1.12.2 `LivingEntity.getRotationVector(float)` still uses head yaw for its own callers, `Entity.getLookVector()` bypasses that override.

Movement meaning: The client player's inherited `LivingEntity.moveRelative` selects its fall-flying branch while `isFallFlying()` is true and uses `getLookVector()` in the glide acceleration calculations.
