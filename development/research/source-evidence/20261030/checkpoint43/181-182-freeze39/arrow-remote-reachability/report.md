# Remote arrow movement reachability: 1.8.1 → 1.8.2

Pair: Minecraft 1.8.1 → 1.8.2; Feather `feather-gen2` `1.8.1+build.2` → `1.8.2+build.2`. Read-only roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.8.1,1.8.2}\ornithe-feather`.

## Evidence identity

For each compared source file below, recomputed SHA-256 matched its side's `.decompile-provenance.json` `sources` entry. The manifests identify the Feather mapping artifacts as 1.8.1 build.2 (`dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7`) and 1.8.2 build.2 (`8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37`). The source manifest check covered both sides of `entity/projectile/ArrowEntity.java`, `entity/Entity.java`, `entity/living/LivingEntity.java`, `entity/living/player/PlayerEntity.java`, `world/World.java`, `client/network/handler/ClientPlayNetworkHandler.java`, and `entity/vehicle/{BoatEntity,MinecartEntity}.java` (8 paired Java files; every present file matched). `client/entity/living/player/RemoteClientPlayerEntity.java` was also checked and matched on both sides while ruling out confusing remote-player interpolation with arrow interpolation. No source was modified.

## Trace and disposition

The assigned server-producer report identifies the adjacent delta: when an arrow's tracked displacement is in range and neither position nor angle threshold is met, 1.8.2 sends position+angles where 1.8.1 may send no movement packet. The client handler (`client/network/handler/ClientPlayNetworkHandler.handleEntityMove`, 1.8.1 lines 488–494; 1.8.2 lines 480–494) resolves the entity and calls `lerpPositionAndAngles`. An `ArrowEntity` overrides this method on both sides (`entity/projectile/ArrowEntity.java`, lines 127–131): it immediately calls `setPosition` and `setRotation`, rather than scheduling interpolation. Its `lerpVelocity` override (lines 133–146) writes velocity; its ordinary `tick` (line 149 onward) then performs projectile ray tracing, updates the arrow's position, and applies drag/gravity. The paired ArrowEntity update/interpolation and tick logic has no relevant behavioral difference; the position packet delta can therefore alter the remote arrow's client position/trajectory and ray hits.

That remote trajectory has no reachable fixed-profile local-player movement output in these sources:

- **Player move collision resolution:** `World.getCollisions` (1.8.1 lines 873–918; 1.8.2 lines 891–936) combines block collision boxes with nearby entity `getCollisionShape()` results, adding an entity box only if it is non-null and intersects. `Entity.getCollisionShape()` returns null in both versions (`Entity.java`, 720–722 / 716–718), and `ArrowEntity` does not override it. The only subclass overrides in the paired entity sources are `BoatEntity.getCollisionShape()` and `MinecartEntity.getCollisionShape()`; their candidate boxes and behavior are independent of arrow position. Thus an arrow can enter or leave the nearby entity query as its replicated position changes, but contributes no clipping box to a local player's `move` collision list.
- **Player entity callback:** `PlayerEntity.tick` (1.8.1 lines 425–490; 1.8.2 lines 431–497) queries nearby entities and invokes each entity's `onPlayerCollision`. `ArrowEntity.onPlayerCollision` (1.8.1 lines 428–438; 1.8.2 lines 427–437) is gated by `!world.isClient`; it only handles pickup of a grounded, unshaken arrow on the logical server. It does not change player position or velocity on the logical client.
- **Arrow ray hit against local player:** The arrow's paired tick raycasts candidate entity shapes and calls `takeDamage` on a hit. `LivingEntity.takeDamage` returns false immediately when `world.isClient` (1.8.1 lines 618–625; 1.8.2 lines 616–623). Consequently the successful-hit branch containing the arrow's `punchLevel` `addVelocity` call cannot run against the local player on the logical client. Damage/health effects are outside scope in any case. Server-side damage, pickup, and knockback are server outcomes, not historical local-player movement replay.

The tracked packet delta can change remote-arrow presentation and client-side projectile state, and can change whether an arrow's own raycast sees a player. The paired collision and client-response gates close the dependency to local-player movement: no local-player position, velocity, collision state, or movement predicate result can differ through this arrow route. No direct player response remains to route as a movement finding.

## Compared source slices

All references are relative to the respective `ornithe-feather` root. The same named members were compared on both sides.

- `entity/projectile/ArrowEntity.java`: `lerpPositionAndAngles` 127–131; `lerpVelocity` 133–146; `tick` 149–380 (including entity-ray hit and `addVelocity` branch); `onPlayerCollision` 427/428–437/438.
- `client/network/handler/ClientPlayNetworkHandler.java`: `handleEntityMove` 488–494 / 480–494.
- `entity/Entity.java`: `getCollisionShape` 720–722 / 716–718; `onPlayerCollision` 960–961 / 944–945.
- `world/World.java`: `getCollisions` 873–918 / 891–936; entity candidate query `getEntities` 2114–2135 / 2134–2155.
- `entity/living/player/PlayerEntity.java`: `tick` and nearby-entity callback 425–490 / 431–497.
- `entity/living/LivingEntity.java`: client rejection in `takeDamage` 618–625 / 616–623.
- `entity/vehicle/BoatEntity.java`: `getCollisionShape` 58 onward; `entity/vehicle/MinecartEntity.java`: `getCollisionShape` 101 / 102 onward. These are the only entity subclass collision-shape overrides found by paired source search.
- `client/entity/living/player/RemoteClientPlayerEntity.java`: `mobTick` interpolation 69 onward on both sides; this is for remote players and is not ArrowEntity's immediate packet application.

Result: close the remote-arrow-to-local-player-movement reachability question as no movement effect. The 1.8.2 tracked arrow packet delta remains a non-player synchronization change, not a local-player movement finding.
