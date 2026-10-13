# F-003: Stable sprinting uses a one-ULP lower air-speed value in 1.19.4

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/player/Player.java`; lines 508–513.
- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`; lines 2112–2117.

## Difference

While sprinting with stable sprint state in ordinary airborne movement, 1.18.2 forms its speed through two binary32 operations: assign `0.02F`, then add `0.006F`. 1.19.4 returns the literal `0.025999999F`.

Under Java float rounding, 1.18.2's sum is binary32 `0x3cd4fdf4` (approximately `0.026000000536441803`); 1.19.4's literal is `0x3cd4fdf3` (approximately `0.025999998673796654`). 1.19.4 is one binary32 ULP lower.

When not sprinting both return/use `0.02F`.

The creative-flight branch is separately coded and is outside this finding.

## Movement meaning

`LocalPlayer.aiStep()` sets the sprint state before delegating to `Player.aiStep()` and `LivingEntity.aiStep()`.

The standard travel path calls `handleRelativeFrictionAndCalculateMovement()`, whose air branch supplies this value to `moveRelative()`.

Water, lava, fall-flying and grounded branches use other speed expressions and are excluded here.

For the same normalized movement vector and surrounding state, 1.19.4 supplies a one-ULP lower acceleration scale to airborne sprinting.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.18.2--1.19.4/findings/F-003-air-sprint-speed-rounding.md).
