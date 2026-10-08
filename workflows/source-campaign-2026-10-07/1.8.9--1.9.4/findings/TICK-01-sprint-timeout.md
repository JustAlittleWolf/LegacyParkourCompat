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

## Source-level difference

In A, entering sprint sets `sprintTimer` to 600 and local `mobTick` decrements it; when it reaches zero, that method calls `setSprinting(false)`. In B, `setSprinting` sets the timer to zero and `mobTick` increments it. A source-wide reference search found no B read of the incremented field or timeout consumer. Therefore an otherwise valid sprint can remain active beyond 600 ticks in B; A forcibly ends it at that point.

This finding is limited to the timeout. Forward input, collision, item-use and blindness gates are separate parts of the local sprint path. Health/food producers and combat are excluded.

## Reachability and dependencies

The local player's `mobTick` runs before `LivingEntity.mobTick` travel. `setSprinting` also updates the state consumed by sprint movement speed and sprint-jump paths. The 600-tick stop occurs in A before the next travel call; B has no corresponding timer read. The source manifest's `LocalClientPlayerEntity.java` hash covers the entire cited body.

## Consequence and uncertainty

The source proves only the extra persistence of the sprint flag beyond A's 600-tick deadline, provided no other sprint gate clears it first. It does not establish a measured trajectory. The exact first release that removed the timer is unknown inside the compared interval.

## Handoff

Independent delta: local sprint auto-stop timer. Related to other sprint gates, but those are not part of this finding. Runtime validation is deferred.
