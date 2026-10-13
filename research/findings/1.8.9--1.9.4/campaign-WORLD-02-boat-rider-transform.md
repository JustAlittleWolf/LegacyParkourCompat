# WORLD-02: Boat passenger position and orientation writes change

- **1.8.9**, ornithe-feather: `net/minecraft/world/World.java`; lines 1341-1346.
- **1.9.4**, ornithe-feather: `net/minecraft/world/World.java`; lines 1406-1413.

## Difference

World dispatches a mounted entity to `rideTick()` in both versions. 1.8.9's base method ticks the rider, then calls the boat's no-argument position update.

The 1.8.9 boat sets rider position to `x + cos(yaw) * 0.4`, `y + mountHeight + rideHeight`, and `z + sin(yaw) * 0.4`; it does not write rider orientation. 1.8.9's base `rideTick()` accumulates and reduces wrapped yaw/pitch deltas but does not assign rider yaw, pitch, or head yaw, and the source-tree reference search found no other consumers of those accumulators.

1.9.4's base method ticks the rider, then calls `updateRiderPositon(this)`.

With one passenger, the 1.9.4 boat's horizontal offset `f` remains zero, so it places the rider at the boat center.

The method then adds the boat's `yawVelocity` to rider yaw and head yaw and calls `copyEntityData`, which sets body yaw to boat yaw and clamps the wrapped rider-to-boat yaw difference to `[-105, 105]` degrees while adjusting `lastYaw` and head yaw.

## Movement meaning

The local mounted-player path is `World.tickEntities()` -> `Entity.rideTick()` -> virtual `this.tick()` -> player/living tick -> the vehicle-specific passenger update after the rider tick.

LocalPlayer/rideable dispatch and the boat override are identified in both source trees.

The exact local tick uses and boat input transfer are documented in the run's TICK-04 row.

Boat velocity/trajectory inputs are excluded; only the boat's direct player position/orientation writes are claimed here.

Source proves that the callback writes different passenger coordinates for a one-passenger boat and that 1.9.4 directly writes rider yaw/head yaw while 1.8.9 does not.

Multiple-passenger seating and removed-boat handling have additional 1.9.4 branches and are not generalized by this one-passenger comparison.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-02-boat-rider-transform.md).
