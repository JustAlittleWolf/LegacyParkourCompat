# Client direct player responses: 1.10.2 → 1.11

## Evidence identity

Both endpoints are the exact Ornithe Feather source roots recorded in `coverage.md`:

- 1.10.2 ready marker `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`; source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
- 1.11 ready marker `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`; source manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.

The relevant handler file hashes were recomputed and matched the sidecar manifest rows:

| Source | 1.10.2 SHA-256 | 1.11 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` |

No product implementation, Git history, or wiki lane was consulted. The already accepted moving-piston response is the separate immutable r2 slice `findings/moving-piston-client-response-r2.md` (SHA-256 `49ccaea0cc48e8bd8314d63d647c4c976febf4448e27dd8de9cb424fc556a18f`), with its audit correction and gates in `audit-r2/final-verdict.md`; this report does not repeat it.

## Direct local-player response paths

`ClientPlayNetworkHandler` is the S2C dispatch sink at both endpoints. These methods either directly write local-player movement state or can reach it through a generic entity/player operation. The packet dispatch methods call `PacketUtils.ensureOnSameThread` where applicable in both versions. Their movement-relevant bodies compare equal; line numbers shift due unrelated source changes. The called `Entity` helpers `lerpVelocity`, `lerpPositionAndAngles`, `updatePositionAndAngles`, `getControllingPassenger`, and `isLogicalSideForUpdatingMovement` also compare equal.

| Handler and packet | Local player effect and concrete gate | Pair result |
| --- | --- | --- |
| `handlePlayerMove(PlayerMoveS2CPacket)` (old 552; new 565) | Always selects `this.minecraft.player`. Relative X/Y/Z are added to current coordinates; each non-relative axis first zeros the corresponding velocity component. Relative pitch/yaw are added to current rotation. `updatePositionAndAngles` applies the result, then sends teleport acknowledgement and current position/angles. Server correction is produced by `ServerPlayNetworkHandler` when it sends `PlayerMoveS2CPacket` (old 515; new 517). | Body and axis gates match. No response delta. |
| `handleEntityVelocity(EntityVelocityS2CPacket)` (old 438; new 451) | Looks up packet entity ID in the client world; when present, calls `entity.lerpVelocity(packet velocity / 8000.0)`. Thus it reaches the local player only when the packet ID resolves to that player; the generic handler body is otherwise shared with non-player entities. | Body matches. No delta. Server tracking producers send this for tracked entity velocity (old/new `server/TrackedEntity.java`). |
| `handleExplosion(ExplosionS2CPacket)` (old 893; new 905) | After explosion block handling, adds the packet's three player-velocity components to the local player's existing velocity. Server producer is `ServerWorld`'s per-player `ExplosionS2CPacket` send (old 946; new 947). | Body and producer construction pattern match. No delta. Explosion damage calculation is excluded; the direct velocity response is covered here. |
| `handlePlayerAbilities(PlayerAbilitiesS2CPacket)` (old 1314; new 1348) | Writes local player `flying`, `creativeMode`, `invulnerable`, `canFly`, fly speed and walk speed. These are movement-relevant ability inputs; abilities arrive from `PlayerManager` / `ServerPlayerEntity` packet sends in both endpoints. | Body matches. No delta. |
| `handlePlayerRespawn(PlayerRespawnS2CPacket)` (old 870; new 882) | On dimension change, replaces client world and then calls `Minecraft.respawnPlayer`; in all cases resets the local player through that respawn path and updates game mode. This can reset position and movement state as a lifecycle response. | Handler order and gate match. No delta. |
| `handleEntityTeleport(EntityTeleportS2CPacket)` (old 482; new 495), `handleEntityMove(EntityMoveS2CPacket)` (old 513; new 526) | Resolve a generic entity and update its tracked/lerped position. Position application is under `!entity.isLogicalSideForUpdatingMovement()`. For the local player, `getControllingPassenger()` returns null and the player is in a client world, so `isLogicalSideForUpdatingMovement()` is false and this branch does apply if the packet ID resolves to the local player. The dedicated server correction path is `handlePlayerMove`; these generic entity packets are also locally player-eligible by ID. | Bodies and applicability gates match. No local-player delta. |
| `handleEntityData(EntityDataS2CPacket)` (old 447; new 460) | For a present entity and non-null entry list, forwards synced metadata to that entity. This is the generic route for replicated entity flags (including player sprint/sneak flags), not a bespoke position writer. | Handler body and `SyncedData.update` value-copy/callback path match. The 1.11 packet constructor adds `markClean()` after a full metadata snapshot, which changes server dirty bookkeeping only; it does not change the received values. `LivingEntity.onDataValueChanged` changes empty-stack bookkeeping for item-use state only. No movement-flag delta. |
| `handlePlayerSleep(PlayerSleepS2CPacket)` (old 749; new 752); `handleEntityEvent` action 2 | Sleep packet resolves the target player and calls `trySleep`; entity event action 2 calls `wakeUp(false,false,false)` on a `PlayerEntity`. These can alter sleeping/pose or position lifecycle state, but the sleep/bed-entry path is an explicit project exclusion. | Sleep method is unchanged. The event's new branch is unrelated to movement; see changed-body disposition below. |
| `handleEntityPassengers(EntityPassengersS2CPacket)` (old 803; new 810) | Replaces passenger list and calls `startRiding(entity,true)` for each resolved passenger. If the local player is among them, its vehicle/passenger relationship changes and may affect subsequent vehicle-follow positioning; vehicle physics are excluded. | Recognized passenger behavior is the same. 1.11 only changes unknown-passenger handling from warning to silently skip; no local-player movement delta. |

## Changed handler bodies and local applicability

The client play handler has 13 changed method bodies by exact method extraction: `handleAddEntity`, `handleAddMob`, `handleBlockEntityUpdate`, `handleCommandSuggestions`, `handleCustomPayload`, `handleEntityEvent`, `handleEntityPassengers`, `handleEntityPickup`, `handleGameEvent`, `handleInventoryMenuSlotContent`, `handleMapData`, `handleResourcePack`, and `handleTitles`. This list is based on paired method bodies, not whole-file diff interpretation.

- `handleEntityEvent` adds event 35 for the 1.11 Totem of Undying: particles, sound, and an item overlay if the affected entity is the local player. It does not write position, velocity, dimensions, pose, or movement flags. The old/new fallback to `entity.doEvent` and the existing player wake action remain. The new event is a visual response to a new mechanic, not a 1.10.2-profile movement change.
- `handleEntityPassengers` changes the response to an unknown passenger from a warning to skipping it; resolved passenger IDs still use the same `startRiding(entity, true)` call and overlay gate. Its only changed branch is reached when the passenger ID cannot resolve; that branch does not write local-player movement state.
- `handleAddEntity`/`handleAddMob` add or alter spawning of non-player entities and guards around unknown entity types. New entity types (for example llama spit and evoker fangs) do not instantiate or write the local player. Entity creation here is tracked-world setup, not a direct local-player response.
- Remaining changed handlers concern block-entity payloads, pickup inventory, chat/command text, resource packs, map/title UI, or custom payload handling. Their changed operations do not directly write the listed local player movement state. An unrelated method-name/file change alone is not evidence of movement.

## Disposition and remaining gate

No changed client packet/event handler in this checked slice has a player-eligible direct movement-state delta. The applicable local-player response handlers above were checked as paired method bodies, and the changed handlers were dispositioned by their actual branch and target. Damage/fall-damage calculation, bed-entry eligibility, vehicle physics, and new 1.11 entity movement remain excluded. The separate moving-piston response is only the accepted conditional observation in the immutable slice cited above; its moved-state shapes, final clipping and net trajectory gates remain open there. This report closes only the external client packet-handler direct-response slice and does not freeze the pair or imply runtime parity.


