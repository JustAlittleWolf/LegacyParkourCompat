# TICK-09: relative movement input angle conversion changes

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `public void updateVelocity(float sideways, float forwards, float scale) {`, lines 844-860.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`, `public void updateVelocity(float sideways, float forwards, float scale) {`, lines 947-963.

Difference: 1.8.9 multiplies yaw by float-cast PI and then divides by `180.0F`. 1.9.4 multiplies yaw by float-cast `(Math.PI / 180.0)`.

The remaining threshold, length normalization, scalar multiplication order, trigonometric function calls and X/Z arithmetic are the same.

Because the angle arithmetic differs, the source does not establish bit-identical trigonometric inputs or the resulting acceleration values.

No position trajectory is inferred.

Movement meaning: Source finding only.

Original evidence: [TICK-09-relative-input-angle-conversion.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-09-relative-input-angle-conversion.md).
