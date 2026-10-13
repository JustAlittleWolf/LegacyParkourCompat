# TICK-01: local sprint timeout removed

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: local sprint lifetime; `TICK-01`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity#setSprinting(Z)V`, lines 349-352, and `#mobTick()V`, lines 488-493; SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity#setSprinting(Z)V`, lines 406-409, and `#mobTick()V`, lines 605-606; SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.
- State transmission: A `tick()` invokes `sendMovementToServer()` after the superclass/local movement tick when not riding; that sender emits START/STOP_SPRINTING when `isSprinting()` changes (lines 105-127). B has the same comparison and action packet at lines 143-169. Both server `handlePlayerMovementAction()` handlers apply those actions with `player.setSprinting(true/false)` (A `ServerPlayNetworkHandler.java` lines 667-683; B lines 805-821; paired file hashes `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` / `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b`).

## Source-level difference

In A, entering sprint sets `sprintTimer` to 600 and local `mobTick` decrements it; when it reaches zero, that method calls `setSprinting(false)`. In B, `setSprinting` sets the timer to zero and `mobTick` increments it. A source-wide reference search found no B read of the incremented field or timeout consumer. Therefore an otherwise valid sprint can remain active beyond 600 ticks in B; A forcibly ends it at that point.

For corresponding local-player states with no newer off-hand-use capability, the normal sprint-initiation and stop gates have the same conditions in both versions: on-ground double-tap or held sprint key, forward input, collision, food-or-flight permission, main-hand item-use and blindness. B's off-hand item use has no A counterpart and is outside this historical comparison. A timeout-driven `setSprinting(false)` is sent through the same client action packet and applied by the same server START/STOP handler in each endpoint. Health/food producers and combat are excluded.

## Reachability and dependencies

The local player's `mobTick` runs before `LivingEntity.mobTick` travel. `setSprinting` also updates the state consumed by sprint movement speed and sprint-jump paths. The 600-tick stop occurs in A before the next travel call; B has no corresponding timer read. The source manifest's `LocalClientPlayerEntity.java` hash covers the entire cited body.

## Consequence and uncertainty

The source proves only the extra persistence of the sprint flag beyond A's 600-tick deadline, provided no normal sprint gate clears it first. When the timeout is the clearing condition, A's local state change reaches the same STOP_SPRINTING packet consumer that B uses for other stop conditions; B emits no timeout stop. It does not establish a measured trajectory. The exact first release that removed the timer is unknown inside the compared interval.

## Handoff

Independent delta: local sprint auto-stop timer. Related to other sprint gates, but those are not part of this finding. Runtime validation is deferred.
