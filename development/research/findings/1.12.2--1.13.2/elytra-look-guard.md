# F-ELYTRA-LOOK-GUARD — 1.13.2 guards glide pitch correction against zero horizontal look

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public Vec3d getRotationVector(float tickDelta) {`, lines 1229-1237.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `protected final Vec3d getRotationVector(float pitch, float yaw) {`, lines 1239-1245.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public final Vec3d getRotationVector(float tickDelta) {`, lines 1239-1241.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `protected final Vec3d getRotationVector(float pitch, float yaw) {`, lines 1251-1259.

Difference: 1.12.2 executes the pitch-up correction when the pitch-derived value is negative; 1.13.2 additionally requires positive horizontal look length before dividing by it.

The look-vector formulas themselves changed between versions, so the guard must be considered with the exact `MathHelper` float/trig source and camera pitch endpoint, not as an isolated algebraic simplification.

Movement meaning: - Reachability and dependencies: PlayerEntity camera pitch is clamped to [-90, 90] in both sources (1.12.2 Entity lines 307-314; 1.13.2 lines 318-326); the local player's inherited fall-flying moveRelative reaches this branch when the Elytra movement state is active.

Other gliding terms are F-ELYTRA-GLIDE-MATH.
