# 1.9.4 → 1.10.2: client player response and network-consumer slice

## Evidence identity

Read-only exact source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4\ornithe-feather\` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.10.2\ornithe-feather\`. Both release-parent ready/provenance records identify exact version ids `1.9.4` and `1.10.2`, Ornithe Feather Gen2 mappings, respectively `feather-gen2-1.9.4+build.2` and `feather-gen2-1.10.2+build.2`. The ready-bound source manifest digests are `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` and `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. Exact input and mapping artifact hashes and tool provenance are retained in the release-parent metadata. Previously checked paired identities in `.native-source/20261028/frontier-c/194-1102-tick-boundary/report.md` were reused only as source identity context; the response-specific slices below were retraced.

SHA-256 of files read for this slice (1.9.4 / 1.10.2):

- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`: `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` / `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656`.
- `net/minecraft/entity/Entity.java`: `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` / `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.
- `net/minecraft/entity/living/LivingEntity.java`: `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea520be4e5` / `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`.
- `net/minecraft/entity/living/player/PlayerEntity.java`: `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` / `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` / `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.

## Examined response paths

Source members and concrete ranges are paired by member; line ranges are from the exact roots above.

- Server correction to local player: `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)` (1.9.4 552–600; 1.10.2 558–606). Relative X/Y/Z add to the current position; absolute coordinates zero the corresponding velocity; relative pitch/yaw add current angles; both call `updatePositionAndAngles`, acknowledge teleport id, and send the resulting player position/angles. Predicate, arithmetic, write order and gates are unchanged.
- Entity velocity packet, including local player when the packet id resolves to that entity: `handleEntityVelocity(EntityVelocityS2CPacket)` (438–445 / 441–448). Both apply `lerpVelocity(packet components / 8000.0)` to a found entity. Unchanged.
- Explosion response: `handleExplosion(ExplosionS2CPacket)` (893–902 / 900–909). Both run the same client explosion construction/block-damage call, then add packet player-velocity components to local velocity in X/Y/Z order. Unchanged.
- Entity spawn velocity, including player-like living entity creation: `handleAddMob(AddMobS2CPacket)` (755–784 / 762–791). Position/angles then packet velocity divided by `8000.0F` in X/Y/Z order; no local-player-specific response difference. Newer source changes multipart id iteration spelling and yaw assignment duplication, unrelated to local player response.
- Entity passengers and mount attachment: `handleEntityPassengers(EntityPassengersS2CPacket)` (803–827 / 810–834) and `handleAttachEntity(AttachEntityS2CPacket)` (829–841 / 836–848). Passenger packet starts riding via `entity2.startRiding(entity, true)`; the attachment packet adjusts leash state only. Direct player start-riding gate and call are unchanged. Text/logging changes are not movement behavior.
- Server vehicle correction: `handleVehicleMove(VehicleMoveS2CPacket)` (1493–1501 / 1499–1507). Both resolve the local player's vehicle and, when it is not the player and `isLogicalSideForUpdatingMovement()` is true, update vehicle position/angles and send a vehicle move packet. The player's mounted positioning path was not changed here.
- Abilities cache: `handlePlayerAbilities(PlayerAbilitiesS2CPacket)` (1314–1323 / 1320–1329). Both copy flying, creative, invulnerable, allow-flight and fly/walk speeds to player ability state in identical order.
- Effect cache used by later movement decisions: `handleEntityStatusEffect` (1188–1202 / 1195–1209) resolves the entity and effect id, constructs duration/amplifier/ambient/particle state, sets permanence and adds it for a living entity. `handleEntityRemoveStatusEffect` (1266–1274 / 1273–1281) removes the effect. These bodies are equivalent; packet line shifts only. No new or changed effect receipt gate/order found in the paired client handler.
- Local player spawning and position seed: `handleAddPlayer` (456–480 / 463–487) uses the same creation, network identity, update-position and initial previous/last-coordinate initialization; 1.10.2 expands chained assignments into separate assignments with the same X/Y/Z values and order.
- `PlayerEntity.push(Entity)` (1.9.4 1893–1900; 1.10.2 1905–1912) skips push while sleeping and otherwise delegates to `Entity.push`; same behavior. `Entity.push` and `LivingEntity.applyKnockback` were inspected at the method level; no changed player-response method was found. The broad containing files differ for other reasons, including the 1.10.2 no-gravity feature and unrelated combat/attribute changes.
- `LocalClientPlayerEntity` direct mount/vehicle positioning callers and `PlayerEntity` mount positioning were checked at the response boundary; no version-specific movement response was identified in this bounded slice.

## Finding result

No independently demonstrated 1.9.4-to-1.10.2 change was found in the assigned direct player response consumers. Teleport correction, received velocity, explosion impulse, passenger start-riding, mounted vehicle correction, ability receipt, and status-effect cache updates preserve their gates, values, and operation order. No canonical finding draft is warranted for this slice.

## Bounded terminal inventory and limitations

Examined: the client play handler's direct position/velocity/mount/ability/effect consumers listed above; the linked entity/player velocity and push/knockback methods listed above. Partially examined: player respawn (handler call and lifecycle boundary only), entity spawn velocity (non-player creation path only), and knockback entry conditions (no damage-resolution audit). Unexamined: source packet serialization/producers, server-side movement responses, all block/world event callbacks including pistons, local tick/travel, collision-response details inside `Entity.move`, collision providers/shapes, full respawn initialization, other effects/attribute updates beyond the paired receipt paths, and any runtime trajectories. This is a bounded response lane report only: it makes no pair-freeze, full source coverage, acceptance, implementation, or runtime-parity claim. Unknown intermediate patch boundaries remain open within 1.9.4–1.10.2.
