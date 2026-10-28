# Respawn, dimension transition, portal, and local-player position closure

## Scope and verdict

Compared the exact Mojmap client sources for 1.18.2 and 1.19 at the assigned canonical source root. In the bounded respawn/transition paths, there is no changed client write to a locally controlled player's position, velocity, rotation, or pose/dimensions that produces a historical movement delta. The locally controlled correction and respawn reset procedures are operation-for-operation the same. One 1.19 addition copies last-death-location metadata; it does not provide position or movement state.

## Exact client paths

- `ClientPacketListener.handleRespawn(ClientboundRespawnPacket)` (1.18.2 lines 910–969; 1.19 lines 948–1008): when the dimension key differs, the handler creates and installs a new `ClientLevel`. On every respawn packet, including a same-dimension packet, it creates a new `LocalPlayer`, preserves the old entity ID, assigns it to `minecraft.player`, copies entity data and conditionally copies attributes (`shouldKeepAllPlayerData`), then calls `resetPos()`. Afterward it assigns server brand, adds the new player to the level, sets yaw to `-180.0F`, installs keyboard input, adjusts abilities from local game mode, restores reduced-debug/death-screen state, and applies the packet's game mode. 1.19 additionally calls `setLastDeathLocation(packet.getLastDeathLocation())` before closing the death screen; this is metadata, not a coordinate writer.
- `LocalPlayer.resetPos()` is unchanged (1.18.2 lines 623–639; 1.19 lines 706–722): sets `Pose.STANDING`; from the new player's current Y, steps upward by `1.0` while within build bounds until `level.noCollision(this)`; then sets velocity to `Vec3.ZERO` and X rotation to `0.0F`; finally restores health and clears death time. The handler's subsequent yaw write is also unchanged. No respawn packet coordinates or dimensions are consumed here; standing pose is the only direct pose/dimension-related reset.
- `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` is operation-for-operation identical (1.18.2 lines 541–605; 1.19 lines 549–614). It directly targets `minecraft.player`; optionally dismounts; resolves each relative position component against current position and preserves only the corresponding velocity component for relative axes (otherwise uses `0.0`); updates old/current position fields; sets velocity; resolves relative pitch/yaw; calls `absMoveTo`; and sends teleport acknowledgment followed by a position/rotation response. This is the server's direct local-player position correction, and both releases use the same path.
- `handleSetEntityMotion` (1.18.2 lines 441–447; 1.19 lines 443–449) looks up the packet entity ID and calls `lerpMotion(x/8000.0, y/8000.0, z/8000.0)` when present. `Entity.lerpMotion` calls `setDeltaMovement` identically in both versions. The handler has no local-control guard, so a matching local entity can receive this velocity write; no delta was found.
- `handleTeleportEntity` differs in bookkeeping: 1.18.2 calls `setPacketCoordinates(x,y,z)`; 1.19 calls `syncPacketPositionCodec(x,y,z)`. Both then interpolate position/rotation and set on-ground only under `!entity.isControlledByLocalInstance()`. `Entity.isControlledByLocalInstance()` is unchanged: its controlling passenger being a `Player` delegates to `Player.isLocalPlayer()`. Thus a locally controlled player or locally controlled vehicle gets packet-position bookkeeping only from this handler, with no direct position/rotation/ground write. The changed codec baseline is not consumed by the local controlled movement branch: `handleMoveEntity` also skips locally controlled entities. For other entities, teleport/move handlers perform interpolation; `handleAddPlayer` explicitly constructs `RemotePlayer`.
- `LocalPlayer.handleNetherPortalClient()` (called from the local player tick) is unchanged (1.18.2 lines 818–860; 1.19 lines 902–944). It updates portal progress, clears the portal flag, handles portal screen/sound behavior, and processes cooldown. It does not move or rotate the player or change dimensions. The portal transfer itself is server-produced.
- `handlePlayerAbilities` writes flying, instabuild, invulnerability, may-fly and walking/flying speeds from the packet in both releases (1.18.2 lines 1541–1550; 1.19 lines 1615–1624). The respawn path also calls unchanged `MultiPlayerGameMode.adjustPlayer` and `setLocalMode`, which derive ability state from the selected game mode. `handleUpdateMobEffect`/`handleRemoveMobEffect` update/remove effects on the packet's living entity in both releases. 1.19's update constructor additionally carries `FactorData`; these handlers do not write position, velocity, rotation, or pose. No changed direct movement writer was found in these effect/ability paths.

## Server provenance and call order

These are server-originated packets consumed directly by the client handlers above; their producers do not execute client movement physics.

- `PlayerList.respawn` creates a replacement server player, restores state, selects/clears spawn collision by incrementing Y, sends `ClientboundRespawnPacket`, then calls `ServerGamePacketListenerImpl.teleport(x,y,z,yaw,pitch)`. The latter emits `ClientboundPlayerPositionPacket`. This establishes the source call order at the producer; no broader transport-order claim is needed. The client respawn packet itself does not supply the respawn coordinates.
- `ServerPlayer.changeDimension` sends `ClientboundRespawnPacket` before placing the player at the `PortalInfo` position/rotation, then sends abilities and active-effect packets. The client-side portal tick does not perform that transfer.
- In 1.19, the respawn packet producer also supplies last-death-location data. This is the same metadata addition observed in the client respawn handler and does not alter the position/velocity reset or correction.

## Source identity and hashes

Canonical read-only source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

Both ready markers identify exact `versionId` and `mapping` (`1.18.2`/`mojmap`, `1.19`/`mojmap`). The source-manifest SHA-256 in each ready marker matched the actual `mojmap.sources.sha256`; every source hash below matched both its manifest entry and the file at the canonical path. Client artifact SHA-256 values are copied from the ready markers.

| Source file | 1.18.2 SHA-256 | 1.19 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b` | `a67c7880c967e93322de0f2e30af0eb34e24a8b7df21171e2a37032b27a21671` |
| `net/minecraft/client/player/LocalPlayer.java` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| `net/minecraft/world/entity/Entity.java` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| `net/minecraft/server/level/ServerPlayer.java` | `9ed753c5012b32ea550e664db88f2e352eb9b599c935092a119ad1a309c1f680` | `d711ff046f29fc1f135fc15afbb540d7c05e51c2953750a6cc528491c3e9b7f1` |
| `net/minecraft/server/players/PlayerList.java` | `393b36c4d4dbcdf6c164192cac8cd6053b2fcb71458d3ee34bf638c983ac1ea6` | `5d0d1dd1f61a961437b7d489c328764dd949c9408e2443a816ce8cdc2f63725f` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `c979dd50b8d80d2d00190e08360a4fc689693715a84e248503099f340a8546c9` | `81d6d0d92c0e902f2618e82fc6a261d58b7b468cbe8b501047975a902d4e18a2` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0` | `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0` |
| `net/minecraft/network/protocol/game/ClientboundRespawnPacket.java` | `b731143868a20df4c338f99939e78a70560b77d60ae51c42356a0e4b5345f627` | `4e2e903725e441d010529fd4b11a9b5086aecf26a73983c5fa2d9486eb8025b7` |
| `net/minecraft/network/protocol/game/ClientboundTeleportEntityPacket.java` | `9ba51291ad2e2881febe3e6fdd2838677155e6a6ca38d2ad5a8e3363958d15e7` | `6049f96694fcccc1e395dbab27d8fa2596b144b6506893598aa2982890c136d4` |

Ready-marker identity fields:

- 1.18.2: source manifest `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; client artifact `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`.
- 1.19: source manifest `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`; client artifact `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764`.

## Bounded closure

Examined paired slices: local-player construction, respawn handling, `resetPos`, local position correction, entity velocity, entity teleport and relative-move handling, player abilities/effects, local portal tick, server respawn/dimension producers, and the server teleport packet producer. No changed reachable local-player movement write was identified in these slices. This closes only the assigned respawn/transition/portal/local replacement slice; it does not assert pair freeze, full movement coverage, acceptance, implementation, or runtime parity.
