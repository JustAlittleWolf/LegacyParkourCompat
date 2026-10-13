# F-012: sprint air-control coefficient rounds one float step lower in 1.17.1

- **1.17.1**, mojmap: `Player.java`; lines 504-510.
- **1.18.2**, mojmap: `Player.java`; lines 506-512.

## Difference

- 1.17.1 `Player#aiStep`, lines 504-510, sets `flyingSpeed = 0.02F` and, while sprinting, assigns `(float)(this.flyingSpeed + 0.005999999865889549)`. `Player.java`
- 1.18.2 `Player#aiStep`, lines 506-512, sets the same `0.02F` and, while sprinting, uses compound float addition `this.flyingSpeed += 0.006F`. `Player.java`
- For the fixed base value, 1.17.1's double-literal expression rounds to float `0.025999998673796654` (bits `0x3cd4fdf3`); 1.18.2's float-literal addition yields `0.026000000536441803` (bits `0x3cd4fdf4`).

This is a one-ULP difference.
- 1.17.1 `LivingEntity#handleRelativeFrictionAndCalculateMovement/#getFrictionInfluencedSpeed`, lines 2155-2201, uses `flyingSpeed` as the friction-influenced speed when `onGround` is false; `moveRelative` applies the movement input with that coefficient. `LivingEntity.java`
- 1.18.2 same methods, lines 2161-2207, retain that consumer formula. `LivingEntity.java`
- The writer runs after `super.aiStep()` in Player.

The LocalPlayer tick invokes the Player movement tick; the value set at that end-of-tick writer is available to the next airborne, non-fluid travel before the next writer runs.

## Movement meaning

When a player is sprinting during `Player#aiStep`, the two versions store adjacent float values.

If the following movement tick is airborne, uses ordinary non-fluid/non-fall-flying travel, and has nonzero input, `getFrictionInfluencedSpeed` supplies the differing value to `moveRelative`; the acceleration and resulting requested movement vector can therefore differ.

This is a source-proven coefficient and consumer delta, not an observed displacement.

Collision resolution and later accumulated movement are not simulated here.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-012-sprint-air-control-float.md).
