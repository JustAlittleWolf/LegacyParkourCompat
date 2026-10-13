# F-SWIM-LOOK — 1.13.2 swimming adds pitch-directed vertical velocity control

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1386-1404.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1442-1468.

Difference: 1.13.2 applies a look-directed vertical adjustment absent from 1.12.2's player override.

It is separate from the local sneak-water descent impulse and water-height jump selection.

Movement meaning: Exact fluid query, pose, and collision effects remain separate dependencies.

Original evidence: [F-SWIM-LOOK.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-SWIM-LOOK.md).
