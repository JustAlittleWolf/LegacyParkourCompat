# F-1.20.2-REMOTE-PLAYER-LERP-ARITHMETIC: remote player interpolation changes arithmetic order

- **1.20.1**, mojmap: `1.20.1/mojmap/net/minecraft/client/player/RemotePlayer.java`; lines 44-54.
- **1.20.2**, mojmap: `1.20.2/mojmap/net/minecraft/client/player/RemotePlayer.java`; lines 44-48.

## Difference

1.20.1 computes each interpolated position component as `current + (target - current) / steps`.

It computes yaw as `currentYaw + (float)wrapDegrees(targetYaw-currentYaw) / steps`, and pitch as `currentPitch + (float)(targetPitch-currentPitch) / steps`; the angle difference is converted to float before division.

The decrement occurs before the position/rotation setters.

1.20.2 computes `inverseSteps = 1.0 / steps` once, then calls `Mth.lerp(inverseSteps,current,target)` for XYZ and `Mth.rotLerp` / `Mth.lerp` for yaw/pitch, casting the resulting angle after interpolation.

The helper sets position and rotation; `RemotePlayer.aiStep` decrements the counter immediately after the helper returns.

These formulas are mathematically equivalent over real numbers, but use different IEEE-754 operation order and angle precision.

For example, for `currentX = Math.PI`, `targetX = 123456.789`, `steps = 3`, 1.20.1's expression gives `41154.3573951024`, while the 1.20.2 `Mth.lerp` expression gives `41154.357395102394` in double precision.

## Movement meaning

This source difference can alter the rounded position/angle written during remote-player interpolation.

It does not run for the local controlled player: `LivingEntity.aiStep` clears that player's `lerpSteps` before its ordinary interpolation branch, and LocalPlayer's own path is separately covered by S1.

Source-proven: the per-tick position/rotation calculation and setter/decrement order changed for remote players with pending interpolation.

Predicted: intermediate client-side remote-player positions/rotations can differ at floating-point precision.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.20.1--1.20.2/findings/F-1.20.2-REMOTE-PLAYER-LERP-ARITHMETIC.md).
