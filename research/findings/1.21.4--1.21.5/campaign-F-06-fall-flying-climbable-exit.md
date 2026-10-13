# F-06: Fall-flying uses ordinary air travel when the player is on a climbable

- **1.21.4**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 2189-2199.
- **1.21.5**, mojmap: `LivingEntity#travel(Vec3)`; lines 2213-2222.

## Difference

When a non-fluid travel tick enters the fall-flying branch while `onClimbable()` is true, 1.21.4 still applies the glide velocity math and moves with the resulting velocity. 1.21.5 instead runs regular `travelInAir(input)` and calls `stopFallFlying`.

For the same input and state, 1.21.5 therefore selects ground/air friction and gravity travel in place of gliding and clears the fall-flying flag through the helper.

## Movement meaning

LocalPlayer jump input can request fall flying through `tryToStartFallFlying`; the resulting living-player state reaches `LivingEntity.aiStep` and `travel(Vec3)`.

The source proves different travel branch selection and the fall-flying stop call for climbable contact. 1.21.5's resulting position and velocity depend on ordinary air-travel inputs, friction, gravity and collision.

The precise observable meaning of the transient flag toggle is not inferred beyond the method calls.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-06-fall-flying-climbable-exit.md).
