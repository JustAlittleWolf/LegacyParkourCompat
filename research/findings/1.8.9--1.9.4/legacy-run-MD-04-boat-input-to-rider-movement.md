# MD-04: Boat controls change the local rider's movement

- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/Input.java`; lines 470-485.
- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/Input.java`; lines 762-770.

## Difference

1.8.9 boat's direct horizontal propulsion reads the ridden living entity's float movement values.

If the player holds left only, `forwardSpeed` is zero, so this control expression contributes no horizontal velocity. 1.9.4 routes discrete direction booleans to the boat instead.

With a passenger and left-only input, 1.9.4 turns the boat and adds a `0.005F` horizontal acceleration scale even though forward input is absent; the boat then moves using the updated velocity.

Sneak scaling applies to 1.8.9's float inputs but not to 1.9.4's direction booleans, so the control channels also differ while sneaking.

## Movement meaning

The world dispatches a mounted local player through `rideTick()`.

In 1.8.9 the inherited rider tick runs `LocalClientPlayerEntity.tick()`, whose input update copies float movement to the player fields read by `BoatEntity.tick()`.

In 1.9.4 the local player's override samples the same tick then passes direction booleans to the boat. 1.9.4's controlling passenger makes the client the logical side that updates boat movement.

The base rider-tick path in both versions updates the passenger's position from the vehicle after the rider tick, so changes in boat movement feed into the player's world position.

The analysis is limited to this player-on-boat interaction; it does not catalog general boat physics.

Source proves the control inputs and horizontal acceleration differ.

For a stationary, locally controlled boat with a player passenger pressing left only in water, the 1.8.9 rider-speed expression adds no horizontal acceleration while 1.9.4's paddle control adds `0.005F` in the boat's yaw-relative direction before movement.

The predicted consequence is different boat displacement and, through the rider-position update, different player position.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/findings/MD-04-boat-input-to-rider-movement.md).
