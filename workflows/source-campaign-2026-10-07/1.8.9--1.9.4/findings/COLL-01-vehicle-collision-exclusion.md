# COLL-01: Mounted player collision query includes its boat in A

- Older version A: Java 1.8.9
- Newer version B: Java 1.9.4
- Mechanic / coverage slice IDs: same-vehicle collision candidate exclusion; COLL-01-VEHICLE, INV-COLLISION, INV-STATE, INV-EXTERNAL
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player interaction with another entity
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/`; `net/minecraft/world/World.java`, `World#getCollisions(Entity,Box)`, lines 927-942, SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`. The source tests `entity.rider != list2 && entity.vehicle != list2` before reading candidate `list2.get(r)` at 930-932. Bytecode for the same method in the immutable revised mapped artifact confirms both entity fields are compared to the list reference (offsets 300-321); revised artifact SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`.
- A boat provider: `net/minecraft/entity/vehicle/BoatEntity.java`, `BoatEntity#getCollisionShape()Box`, lines 58-60, SHA-256 `77ef72c28024b754ff76f2c67e14dd61832394bac2bd7b62b29708560e588da2`; `Entity#getCollisionAgainstShape(Entity)Box` returns null at lines 1271-1273, `Entity.java` SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`.
- B manifest artifact: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/`; `net/minecraft/world/World.java`, `World#getCollisions(@Nullable Entity,Box)`, lines 941-957, SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`. It skips candidates for which `entity.hasSameVehicle(entity2)` is true. `Entity#getVehicle()Entity` and `hasSameVehicle(Entity)Z` lines 2226-2238 compare root vehicle identity; `Entity.java` SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`. `BoatEntity#getCollisionShape()Box` returns its shape at lines 104-108, BoatEntity.java SHA-256 `3d41b9df0ef0d36158105235c92b35dac4b51070476e7ef688520961d1d56e57`; base `Entity#getCollisionAgainstShape(Entity)Box` returns null at lines 1427-1430. Revised mapped artifact bytecode confirms the `hasSameVehicle` guard at offsets 419-431; revised artifact SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`.
- Player reachability: `World#tickEntities()V` dispatches mounted entities to `rideTick()` (A lines 1341-1346; B 1406-1413); `LivingEntity#mobTick()V` calls `moveRelative()` and the ordinary movement path calls `Entity#move(DDD)V` (A `LivingEntity.java` lines 1446-1451 and `Entity.java` 439-444; B `LivingEntity.java` lines 1705-1712 and `Entity.java` 509-518). Their hashes are recorded in the run's TICK/STATE rows and artifact manifest. Both sources retain this route while the player is riding.

## Source-level difference

A's comparisons use the whole collision-query result list where the loop needs to test a candidate entity. The mapped bytecode confirms the decompiled source expression. Since the player rider and its boat are not the result-list object, the predicate admits the boat candidate; when its boat shape intersects the player's collision query box, A adds that shape. The player's base opposing-shape callback returns null, so the boat's own collision shape is the relevant added shape.

B checks each candidate's root vehicle. A mounted player and its boat resolve to the same root vehicle, so B skips the boat candidate before reading either collision shape. Both versions otherwise query the same entity candidates for this bounded comparison.

## Reachability and dependencies

The local passenger reaches `LivingEntity#moveRelative(FF)V` through `World#tickEntities()` -> `Entity#rideTick()` -> local player tick -> `LivingEntity#mobTick()`. The travel method calls `Entity#move(DDD)V`, which calls `World#getCollisions(Entity,Box)`. A boat exposes its current box as a collision shape in both versions. B's root-vehicle helper follows the passenger-to-boat mount link; A's base player collision-against-shape method supplies no second shape.

## Consequence and uncertainty

Source proves that A's mounted-player collision query can include the intersecting boat box and B omits that same-vehicle candidate. The supplied shape can affect later axis clipping when requested movement intersects it, but no particular clipped displacement or trajectory is claimed. The original derived mapped JARs are unavailable; the immutable revised mapped artifacts corroborate the source expressions, but their equivalence to the originals is unproven under the run's recorded artifact-identity limitation.

## Handoff

This finding covers only the entity-candidate exclusion feeding a mounted player's collision query. Axis clipping, candidate ordering, and resulting displacement remain open under COLL-01. It does not include vehicle physics or boat movement. First changed release remains unknown within the endpoints.
