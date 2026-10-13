# TICK-02: sneaking input is rescaled during creative flight

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 488-637.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `public void tick() {`, lines 13-38.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 604-760.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `public void tick() {`, lines 13-50.

Difference: Both keyboard providers multiply sideways and forward input by `0.3` when sneaking. 1.8.9's local creative-flight branch adjusts vertical velocity but leaves those scaled horizontal values as-is. 1.9.4's branch, when the player is flying, is the camera, and the current input is sneaking, divides both horizontal values by `0.3` before applying the vertical fly-speed change.

The exact sequence is multiply-and-cast in `KeyboardInput.tick`, then divide-and-cast in 1.9.4's `LocalClientPlayerEntity.mobTick`; do not algebraically replace that sequence in an implementation.

Movement meaning: Keyboard input is sampled immediately before local movement gates in `mobTick`.

Under the stated no-item-use/no-riding precondition, the camera and `abilities.flying` guard selects the flight branch; 1.9.4 removes the sneak multiplier from both horizontal arguments before the living tick later passes them through `PlayerEntity#moveRelative(float,float)` to its superclass. 1.8.9 keeps the multiplied values.

The source proves a different pair of horizontal arguments supplied to flight travel while sneaking under the stated preconditions.

Original evidence: [TICK-02-flight-sneak-input-rescaling.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-02-flight-sneak-input-rescaling.md).
