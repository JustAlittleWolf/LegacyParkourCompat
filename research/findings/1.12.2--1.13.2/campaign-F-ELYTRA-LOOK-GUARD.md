# F-ELYTRA-LOOK-GUARD — 1.13.2 guards glide pitch correction against zero horizontal look

- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; lines 1434-1455.
- **1.13.2**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-ELYTRA-LOOK-GUARD.md); lines 1495-1518.

## Difference

1.12.2 executes the pitch-up correction when the pitch-derived value is negative; 1.13.2 additionally requires positive horizontal look length before dividing by it.

The look-vector formulas themselves changed between versions, so the guard must be considered with the exact `MathHelper` float/trig source and camera pitch endpoint, not as an isolated algebraic simplification.

## Movement meaning

- Reachability and dependencies: PlayerEntity camera pitch is clamped to [-90, 90] in both sources (1.12.2 Entity lines 307-314; 1.13.2 lines 318-326); the local player's inherited fall-flying moveRelative reaches this branch when the Elytra movement state is active.

Other gliding terms are F-ELYTRA-GLIDE-MATH.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-ELYTRA-LOOK-GUARD.md).
