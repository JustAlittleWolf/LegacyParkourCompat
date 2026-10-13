# F-004: careful player steps directly activate sculk sensors in 1.19.2

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`; lines 543-674.
- **1.19.2**, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`; lines 547-679.

## Difference

Sculk Sensor blocks exist in both endpoints. 1.18.2's `Entity.move()` skips all `stepOn()` callbacks when `isSteppingCarefully()` is true, and 1.18.2's SculkSensorBlock inherits the no-op base callback. 1.19.2 removes the careful-step guard from the grounded `stepOn()` dispatch and adds a SculkSensorBlock override that activates an inactive sensor for any non-Warden entity on the server.

That callback does not check `isSteppingCarefully()`.

## Movement meaning

Local player grounded movement -> `Entity.move()` resolves the support block and, when grounded, calls `stepOn()` -> 1.19.2's SculkSensorBlock callback checks server side, inactive phase and non-Warden entity -> sensor enters ACTIVE phase with power 15 and a `STEP` vibration frequency.

Under those same conditions, 1.18.2 does not invoke the callback for a careful step and its SculkSensorBlock has no direct step activation.

Source-proven: on a grounded careful step over an inactive Sculk Sensor, 1.19.2 invokes a player-facing block callback that can change the sensor to ACTIVE and power 15; 1.18.2 skips the callback.

The callback does not directly write player position or velocity.

Predicted consequence: the changed sensor/redstone state can alter later world interactions.

No world layout, redstone response or trajectory was simulated.

The separate `GameEvent.STEP` event-dispatch change is F-003 and may interact with listener behavior, but is not needed for this direct callback path.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.18.2--1.19.2/findings/F-004.md).
