# MD-02: Sneaking input is restored during creative flight

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`; lines 13-41.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`; lines 13-48.

## Difference

For a camera-controlled player with flight enabled who sneaks, 1.8.9 passes the keyboard's sneaking-scaled horizontal input onward. 1.9.4 additionally divides each scaled input by the double literal `0.3` and casts the result back to `float` before copying it to movement speed fields.

The multiply/cast and divide/cast sequence is preserved as decompiled; it is not treated as exact algebraic cancellation.

## Movement meaning

`KeyboardInput.tick()` writes the current input. `LocalClientPlayerEntity.mobTick()` calls it before the flight/sneak branch.

The locally controlled/camera path copies the resulting input into `sidewaysSpeed` and `forwardSpeed`; `LivingEntity.mobTick()` later dispatches to `PlayerEntity.moveRelative()`, whose flight path uses the movement inputs and fly speed.

The 1.8.9 and 1.9.4 input producers otherwise retain the same sneak multiplier.

Item-use slowdown is applied before the 1.9.4 division, so the two controls interact; the division does not erase an earlier `0.2F` item-use scaling.

Source proves that 1.9.4 supplies different horizontal input values to movement for this precondition.

The predicted consequence is increased horizontal control while sneaking in flight.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/findings/MD-02-flight-sneak-input-rescaling.md).
