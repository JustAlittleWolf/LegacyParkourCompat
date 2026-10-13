# MD-06: Sprinting doubles the flight movement input factor

- **1.7.10**, ornithe-feather: `1.7.10/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 1236-1250.
- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; lines 1278-1292.

## Difference

Precondition: a player is airborne, has flight enabled, is not riding, and is sprinting. 1.7.10 applies the ability fly speed as the input factor. 1.8.9 applies twice the ability fly speed.

Both restore the original `speedInAir` after the superclass movement call and multiply vertical velocity by `0.6` within this flight override.

## Movement meaning

Local player input permits the held sprint-key branch while airborne when forward input and other sprint guards pass; flight permission satisfies the hunger-or-flight gate. `PlayerEntity.moveRelative` then selects the flying branch, and `LivingEntity.moveRelative` consumes the temporary factor in its airborne movement branch.

The fly-speed value is stored in `PlayerAbilities` and may be externally set or synchronized; both releases use a `0.05F` default.

Source-proven: 1.8.9 doubles the horizontal movement input factor while flying and sprinting under the stated conditions.

Inference: the same airborne input can add more horizontal velocity in 1.8.9.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/findings/MD-06-sprint-flying-horizontal-acceleration.md).
