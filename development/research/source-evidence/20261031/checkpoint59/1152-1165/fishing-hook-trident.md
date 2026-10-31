# Fishing-hook local-player response and trident origin follow-up

Source-only closure of the FishingHook exact-case route correction and TridentItem server-origin question for Mojmap 1.15.2 → 1.16.5. This does not constitute full-pair coverage or freeze.

## Source identity and hashes

Read-only roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`. Existing corrected direct manifests and exact-case path inventory are the source identity basis; the independent path audit verified all rows/hashes against these roots. Hashes recomputed for files used here:

- 1.15.2 `net/minecraft/world/entity/fishing/FishingHook.java`: `231742105948328a5b094ec1f56ad02862d6c2dc2d37f1a2a142cad63f2ba7e0`
- 1.16.5 `net/minecraft/world/entity/projectile/FishingHook.java`: `9fa44d46d12cb71de62e4315c5a23627f3df8153d71229567dd17f3d7869840b`
- 1.15.2 `net/minecraft/client/multiplayer/ClientPacketListener.java`: `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30`
- 1.16.5 `net/minecraft/client/multiplayer/ClientPacketListener.java`: `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e`
- 1.15.2 `net/minecraft/world/item/TridentItem.java`: `9dfe4ec0e5159359285f1b7c50379795fac78f0a404eced1b1ed561b321531fb`
- 1.16.5 `net/minecraft/world/item/TridentItem.java`: `dc4c0926ac143060fd4d83e77729563c3c50bb284b39777fcb01edb0de831490`

## FishingHook route and behavior

The ordinal router classified the removed 1.15.2 `world/entity/fishing/FishingHook.java` and added 1.16.5 `world/entity/projectile/FishingHook.java` as no-movement-dependency. That disposition is incorrect: both expose an in-scope direct local-player velocity response.

In both versions `FishingHook.handleEntityEvent(byte)` checks event 31, `level.isClientSide`, `hookedIn instanceof Player`, and `isLocalPlayer()`, then calls `bringInHookedEntity()`. In both `bringInHookedEntity` gets the owner, returns if absent, constructs a `Vec3` from owner position minus hook position, scales it by `0.1`, and adds it to the hooked player's existing delta movement. The 1.15.2 body uses `this.owner` directly; 1.16.5 calls `this.getOwner()`. The event gate and arithmetic order/constant are otherwise the same. Thus these two path rows must be routed movement-reachable, but the inspected local-player response itself yields no endpoint behavioral difference.

The caller is real: old and new `ClientPacketListener.handleEntityEvent(ClientboundEntityEventPacket)` resolve the event entity, special-case only IDs 21 and 35, and otherwise invoke `entity.handleEntityEvent(eventId)` on the client thread. In both FishingHook classes, `DATA_HOOKED_ENTITY` is a synced integer defaulting to 0; `onSyncedDataUpdated` resolves positive IDs to `level.getEntity(id - 1)` and nonpositive to null. The source-level synchronized target and callback form therefore provides the same data flow into the local response. The newer hook class adds other projectile/fishing state and reorganizes hit handling; those changes do not alter the exact response bodies above. No packet-side blocker was found for the paired behavior checked here. This is a direct source response, not an assertion about event production parity or simulated trajectory.

Exact source locations: old `FishingHook.java:427–439`, `onSyncedDataUpdated:109–116`, `setHookedEntity:269–271`; new `FishingHook.java:480–494`, `onSyncedDataUpdated:112–125`, `setHookedEntity:277–279`; old/new `ClientPacketListener.java:1002–1018` / `1015–1032`.

## TridentItem disposition

Both versions gate all item-release/Riptide work under `if (!level.isClientSide)` at old `TridentItem.releaseUsing:51–91` and new `:55–95`. The in-gate response calls `player.push(h,l,m)`, then calls `player.move(SELF, (0, 1.1999999F, 0))` when grounded. The only movement predicate difference in this paired slice is old direct `player.onGround` versus new `player.isOnGround()`; the downstream call is unchanged. This source operation is server-only, so it is not historical client physics. No server-emulated movement claim is made. Whether a resulting server-produced synchronization packet differs is outside this changed source route: the predicate is only an API access substitution here, not a changed source gate/math, and network movement synchronization requires no additional TridentItem delta finding from this slice.

## Remaining scope

This closes only the FishingHook route classification and the cited TridentItem decision. It does not close all inbound packet responses, other changed entity-origin effects, fishing-hook source introduction boundary, or remaining pair dependencies. No resource/tag input was needed for these paths. No code, canonical findings, backlog, tests, builds, decompilation, or runtime checks were performed.
