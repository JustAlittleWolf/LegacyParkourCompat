# WORLD-02: Boat passenger position and orientation writes change

- Older version A: Java 1.8.9
- Newer version B: Java 1.9.4
- Mechanic / coverage slice IDs: direct player passenger transform; WORLD-02, TICK-04
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player interaction with another entity
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/`; `net/minecraft/world/World.java`, `World#tickEntities()V`, lines 1341-1346, SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`; `net/minecraft/entity/Entity.java`, `Entity#rideTick()V`, lines 1275-1286 and 1288-1327, SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; `net/minecraft/entity/vehicle/BoatEntity.java`, `BoatEntity#updateRiderPositon()V`, lines 366-372, SHA-256 `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2`.
- B manifest artifact: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/`; `net/minecraft/world/World.java`, `World#tickEntities()V`, lines 1406-1413, SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`; `net/minecraft/entity/Entity.java`, `Entity#rideTick()V`, lines 1432-1444, SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; `net/minecraft/entity/vehicle/BoatEntity.java`, `BoatEntity#updateRiderPositon(Entity)V` and `copyEntityData(Entity)V`, lines 558-600, SHA-256 `3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57`.

## Source-level difference

World dispatches a mounted entity to `rideTick()` in both versions. A's base method ticks the rider, then calls the boat's no-argument position update. The A boat sets rider position to `x + cos(yaw) * 0.4`, `y + mountHeight + rideHeight`, and `z + sin(yaw) * 0.4`; it does not write rider orientation. A's base `rideTick()` accumulates and reduces wrapped yaw/pitch deltas but does not assign rider yaw, pitch, or head yaw, and the source-tree reference search found no other consumers of those accumulators.

B's base method ticks the rider, then calls `updateRiderPositon(this)`. With one passenger, the B boat's horizontal offset `f` remains zero, so it places the rider at the boat center. The method then adds the boat's `yawVelocity` to rider yaw and head yaw and calls `copyEntityData`, which sets body yaw to boat yaw and clamps the wrapped rider-to-boat yaw difference to `[-105, 105]` degrees while adjusting `lastYaw` and head yaw.

## Reachability and dependencies

The local mounted-player path is `World.tickEntities()` -> `Entity.rideTick()` -> virtual `this.tick()` -> player/living tick -> the vehicle-specific passenger update after the rider tick. LocalPlayer/rideable dispatch and the boat override are identified in both source trees. The exact local tick uses and boat input transfer are documented in the run's TICK-04 row. Boat velocity/trajectory inputs are excluded; only the boat's direct player position/orientation writes are claimed here.

## Consequence and uncertainty

Source proves that the callback writes different passenger coordinates for a one-passenger boat and that B directly writes rider yaw/head yaw while A does not. It does not prove a resulting world trajectory, camera response, or the prior boat state that caused a particular `yawVelocity`. Multiple-passenger seating and removed-boat handling have additional B branches and are not generalized by this one-passenger comparison. No runtime behavior was tested.

## Handoff

This is a direct player passenger transform delta in the boat callback. It does not include boat physics, vehicle displacement, or other mount types. Related slice: TICK-04 records the changed input transport but excludes its vehicle-only consumers. First changed release remains unknown within the paired endpoints.
