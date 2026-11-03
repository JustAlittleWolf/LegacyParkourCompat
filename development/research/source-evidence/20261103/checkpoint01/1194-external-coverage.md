# 1.19.4 → 1.20.4 player-response terminal inventory

## Inputs and boundary

Read-only exact Mojmap trees: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\mojmap` for 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3 and 1.20.4. Source identities and class/source hashes are documented by the permitted `.native-source/20261103/1194-1204-pool54/full-player-graph-closure/closure-report.md` and `direct-player-response-closure/closure.md`; those are source-only inputs. Local read-only scans were repeated for every release's `ClientPacketListener.java` and `LocalPlayer.java`, covering `setPos`, `setDeltaMovement`, `lerpMotion`, `lerpTo`, `setBoundingBox`, `refreshDimensions`, `absMoveTo`, `resetPos`, `teleportRelative`, `setOnGround`, and pose setters. This is a residual closure, not full-pair acceptance.

## Ordinary LocalPlayer tick / pose / input terminals

The reachable ordinary on-foot path is `ClientLevel.tickEntities → tickNonPassenger → virtual Entity.tick → LocalPlayer.tick → Player.tick → LivingEntity.tick → virtual LocalPlayer.aiStep → Player.aiStep → LivingEntity.aiStep → virtual jump/travel → Entity.move`. `LivingEntity.aiStep` supplies jump and travel decisions; `Entity.move` writes position/AABB and velocity/collision results; `Player.tick` invokes `updatePlayerPose` after the inherited tick returns. `LocalPlayer.aiStep` consumes stored `Input`, updates crouch/sprint/slow-input decisions and advances into `super.aiStep`. `LocalPlayer.serverAiStep` copies input impulses to `xxa`/`zza` only when `isControlledCamera()`. Exact call authority and passenger exclusions are detailed in the permitted `full-player-graph-closure/closure-report.md` and `localplayer-tick-authority/authority-closure.md`.

The source graphs confirm `Input.java` and `KeyboardInput.java` are byte-identical across all six releases. `Player.updatePlayerPose` remains a per-tick pose/size path; 1.20.2's `canPlayerFitWithinBlocksAndEntitiesWhen` substitution uses matching pose dimensions/box deflation for a non-passenger. The newly added `!isPassenger()` crouch guard is passenger-only and does not alter ordinary on-foot crouching. `LocalPlayer` has direct velocity writes in `moveTowardsClosestSpace` (suffocation escape), vertical flying input in `aiStep`, and `resetPos` (lifecycle). These are included in the API scan; the main graph method matrix separately lists all LocalPlayer method bodies. Do not infer whole-class closure from the API scan alone.

## Packet and callback position / velocity / AABB inventory

### Direct local-player movement writers

- `ClientPacketListener.handleMovePlayer` (all six): the local player is selected directly; packet-relative X/Y/Z bits retain the corresponding current velocity component and offset current/current-old positions, while absolute axes set velocity to `0.0` and set position/old-position from packet coordinates. It then calls `setPos` and `setDeltaMovement`, applies relative-or-absolute rotations, acknowledges the teleport and reports the resulting position. This authoritative correction path is present in each release; the prior closure's normalized body hash is identical across all six. No branch other than packet relative-axis flags controls these writes.
- `ClientPacketListener.handleExplosion` (all six): directly adds packet knockback vector to `minecraft.player` delta movement. Its knockback expression is unchanged. In 1.20.3 the handler adds explosion interaction/particle/sound fields; existing block-interaction response ordering and the explicit player velocity addition are retained. The new `TRIGGER_BLOCK` mode is modern-only.
- `ClientPacketListener.handlePlayerInfoUpdate` → `LocalPlayer.onGameModeChanged` (1.20 through 1.20.4): added response only. The callback requires target mode `SPECTATOR` and replaces Y velocity with zero while preserving X/Z. Caller gates: prior cached mode differs, local player is non-null, and packet profile UUID equals the local player's UUID. Absent in 1.19.4. The separate `CHANGE_GAME_MODE` game-event path does not call this callback.
- `ClientPacketListener.handleSetEntityMotion` (all six): resolves entity ID, then calls `lerpMotion(packet components / 8000.0)` if entity exists. For the local player this can update the velocity interpolation target. Handler and helper operation are identical across releases; existence of entity is the only handler gate.

### Entity-indexed writes and control gates

- `handleTeleportEntity` and `handleMoveEntity` resolve a general entity. Position/rotation/on-ground updates are gated by `!entity.isControlledByLocalInstance()`. `LocalPlayer.isEffectiveAi()` is true, and `Entity.isControlledByLocalInstance()` falls back to that when there is no controlling passenger, so these remote-entity interpolation writes do not apply to the ordinary local player. `handleTeleportEntity` does still update the packet position codec before the gate; that helper is not the current player position/AABB writer. The 1.20.2 interpolation API rewrite therefore does not open a local-player position delta.
- `handleSetEntityData` assigns synchronized entity data for a resolved entity. This can invoke entity callbacks, including dimensions/pose refresh, so it is an indirect AABB terminal rather than a direct setter. The assigned callback chain was checked in the prior direct-response closure: LocalPlayer item-use flags/Elytra sound are unchanged; pose refresh uses unchanged local-player gates; Player is excluded from Entity's non-player free-position adjustment. No cross-release direct local-player position/velocity/AABB response was identified here. Reopen this if a particular synchronized field or player-specific callback is discovered outside that inventory.
- `handleSetPassengers` calls start/eject riding and, on a newly attached LocalPlayer to a Boat, writes player yaw/head yaw from boat yaw. It does not set player position, velocity or bounding box. Passenger/vehicle simulation is outside this ordinary on-foot inventory; the passenger-local input/crouch effect has a separate bounded source note.
- `handleMoveVehicle` may call `absMoveTo` only on `player.getRootVehicle()` when that root is not the player and is controlled locally. This is a vehicle position write and excluded non-player/vehicle physics.

### Lifecycle and other callback paths

- `handleLogin`, `handleRespawn`, and `LocalPlayer.resetPos` create/reinitialize local-player position, pose and velocity. These are lifecycle/spawn placement paths, not ordinary tick responses; no historical fixed-profile movement candidate is taken from them. `handleSetSpawn` only changes the world default spawn position.
- `handleEntityEvent` dispatches to entity callbacks; LocalPlayer's event override does not add a direct player position/velocity write in the inventoried cases. `handleDamageEvent` and `handleSetHealth` route through damage/health state; health, damage calculation and death/respawn are excluded.
- `handlePlayerAbilities`, attribute, effect, and equipment synchronization are inputs to movement state rather than direct position/velocity/box writes; the prior packet closure records same handler writes and the bounded effect/attribute conclusions.
- Block/section update handlers write vanilla world state, which can change later movement collision/fluid queries; they are world-state producers, not a packet direct-player writer. Provider and state-dependent shape/collision closure remains a separate lane.

### LocalPlayer-owned direct writes outside inherited tick

A text scan found `LocalPlayer.moveTowardsClosestSpace` (1.19.4 lines 412–439; 1.20/1.20.1 lines 415–442; 1.20.2–1.20.4 lines 405–432), called four times from `LocalPlayer.aiStep` (1.19.4 lines 675–678; 1.20/1.20.1 lines 681–684; 1.20.2–1.20.4 lines 680–683) under `!noPhysics`. The helper selects the nearest horizontal edge among neighboring collision shapes and writes 0.1 along the selected horizontal axis only when the candidate is closer and `!suffocatesAt(adjacentPos)`. This direct velocity correction and gate are present in all six releases; the adjacent call and helper behavior show no pair delta. `LocalPlayer.aiStep` also writes vertical velocity from shift/jump input only when abilities.flying and `isControlledCamera()`; this is the stored-input flight response, not a new external callback. Lifecycle `resetPos` writes position/pose/zero velocity on initialization or respawn (1.19.4 lines 626–640; 1.20/1.20.1 lines 629–643; 1.20.2–1.20.4 lines 627–641). The newly added `LocalPlayer.onGameModeChanged` spectator callback (1.20/1.20.1 lines 1053–1056; 1.20.2–1.20.4 lines 1052–1055) is the sole additional LocalPlayer direct velocity setter in 1.20–1.20.4. No LocalPlayer direct `setBoundingBox`, `absMoveTo`, or `teleportRelative` call was found. Its inherited `Entity.setPos`/`refreshDimensions`/pose paths remain the AABB writers; these are already represented in the tick graph and synchronized-data callback path.

## Adjacent release deltas from this residual

- 1.19.4→1.20: local spectator Y-velocity clear is the only additional direct packet-triggered player velocity response identified in the scanned writer surface. Existing local correction and explosion response operations persist.
- 1.20→1.20.1: no new direct local-player position/velocity/AABB writer identified.
- 1.20.1→1.20.2: remote-entity movement interpolation changes are behind the local-control gate. No new direct local-player position/velocity/AABB writer identified in the listener sweep.
- 1.20.2→1.20.3: explosion packet shape expands for modern block interaction and visual payload; existing-player knockback and eligible old block interaction behavior are unchanged. No new direct local-player position/velocity/AABB writer identified.
- 1.20.3→1.20.4: no new direct local-player position/velocity/AABB writer identified in the listener sweep.

## Limit

This closes the direct network listener writer inventory and named local-player callbacks in this bounded slice, not every external movement source. It does not close arbitrary world tick callbacks, block/fluid/provider state dependencies, all dynamic callback overrides, or a separate independent blind audit of the whole graph. The movement-core and provider gaps below remain open; this report does not declare pair freeze.


