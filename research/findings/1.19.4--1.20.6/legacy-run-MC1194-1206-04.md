# MC1194-1206-04: Jump Boost power is rounded to float before velocity assignment

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 1947-1967.
- **1.20.6**, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`; lines 2057-2080.

## Difference

1.19.4 promotes the float base jump power and the double-typed effect contribution to double before adding, then assigns the double result to vertical velocity. 1.20.6 evaluates the base, Jump Boost contribution and their sum as floats, rounding their sum to float before promotion at the `setDeltaMovement` call.

With Jump Boost amplifier 0 and block jump factor 1.0, 1.19.4 sums the binary float values for `0.42F` and `0.1F` in double precision; 1.20.6 rounds their sum back to float.

The assigned vertical values therefore differ by float-rounding precision under that reachable precondition. 1.20.6 also sources its base from a syncable jump-strength attribute whose default matches `0.42F`.

## Movement meaning

Grounded player jump processing reaches `LivingEntity.jumpFromGround()` through the Player override; `LocalPlayer.aiStep()` can also reach it on the flight-activation path documented in finding `MC1194-1206-01`.

Jump Boost is the direct effect dependency.

The base jump-strength attribute is server-synchronized in 1.20.6; any nondefault server value is an external input and is not assumed here.

Block jump-factor behavior remains a Stage 5 dependency.

Source-proven: under the stated Jump Boost condition, the arithmetic path rounds at different precision before writing vertical velocity.

The magnitude is a small numeric delta and its later trajectory has not been tested.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.19.4--1.20.6/findings/MC1194-1206-04.md).
