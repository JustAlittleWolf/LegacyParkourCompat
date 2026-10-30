# Vertical glide acceleration changes operation grouping in 1.13

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

1.13, Ornithe Feather Gen2 build 2: `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1463–1481. Exact 1.13.1 and 1.13.2 retain the new expression. Exact source review of all fourteen stable releases from 1.9 through 1.12.2 retains the old expression and float-narrowed lift factor.

Difference: Old movement adds `-0.08 + m * 0.06` to double vertical velocity, where `m` is the narrowed float lift factor. Starting with 1.13, it adds `d * (-1.0 + n * 0.75)`, with a float lift factor `n` promoted to double and ordinary gravity `d == 0.08`. Both forms use double arithmetic after the float narrowing. Their algebraic equality does not preserve Java operation order or rounding. The acceleration precedes dive conversion, pull-up conversion, horizontal steering and drag.

Preserve these source expressions and intermediate types/order; don't simplify them to a real-number identity.

Movement meaning: Inherited LivingEntity movement dispatch is called by PlayerEntity through the local player's travel tick while fall-flying. A static pitch `30F`, yaw `0F`, zero vertical velocity and ordinary-gravity witness gives float lift `0.7500276565551758F`; the old term is `-0.03499834060668946`, and the grouped term is `-0.03499834060668945`. This one-ULP acceleration difference does not establish a complete trajectory or runtime parity.

Elytra state/equipment eligibility and camera pitch are separate dependencies.

Slow Falling itself is a modern-only effect for this historical one-way scope.
