# F-002: Elytra lift coefficient uses double cosine instead of the float lookup table

- **1.17.1**, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2077–2081.
- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2085–2086.

## Difference

When an entity is fall-flying, 1.17.1 obtains the pitch cosine from the float lookup table, then rounds the derived coefficient back to float before using it. 1.18.2 computes the pitch cosine using `Math.cos` in double precision and keeps the squared coefficient as double.

Thus the source uses different trigonometric approximations, precision and rounding points in the coefficient that controls gliding vertical acceleration and dive pull.

Other inputs and adjacent formulas are outside this finding.

## Movement meaning

`LivingEntity.travel()` enters the branch when `isFallFlying()` is true.

Local player elytra startup in `LocalPlayer.aiStep()` sets up fall flying, so the branch is player-reachable.

The outputs update delta movement; later movement resolution is separate.

The coefficient can differ because the implementations have different approximations and precision.

This predicts a potentially different vertical/horizontal velocity update under fall-flying, but no particular numeric sample or trajectory is asserted.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.17.1--1.18.2/findings/F-002-elytra-lift-trig-precision.md).
