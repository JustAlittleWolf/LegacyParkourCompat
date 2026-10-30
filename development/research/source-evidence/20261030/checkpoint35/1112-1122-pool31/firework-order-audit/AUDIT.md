# Independent source audit: firework attachment and same-dimension respawn

## Audited artifact

The report was hashed before inspection and preserved byte-for-byte as a read-only copy:

- Original: `.native-source/20261030/1112-1122-pool31/firework-order/report.md`
- SHA-256: `2FD34F31578AC1325F1B8998DC80C2998F2FB329D01A51CF3E9BDF5F18927FEB`
- Preserved copy: `report.2FD34F31578AC1325F1B8998DC80C2998F2FB329D01A51CF3E9BDF5F18927FEB.md` (same SHA-256, read-only)

## Source identity

The assigned `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` source volume was mounted and readable. I independently checked the source manifests, ready markers, verification summaries, provenance JSON, and each cited core source hash against its version manifest.

| Version | Manifest SHA-256 | Provenance | Verification summary |
|---|---|---|---|
| 1.11.2 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | Ornithe Feather Gen2 build 2; Vineflower 1.12.0 | 1,921 source files, zero source differences |
| 1.12.2 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | Ornithe Feather Gen2 build 2; Vineflower 1.12.0 | 2,050 source files, zero source differences |

The aggregate hashes above match `ornithe-feather.sources.sha256`, the `ready.json` source-manifest hash, and `verification.json`. Every source file hash cited in the submitted report matched its manifest entry. Additional server/removal-path hashes checked against the per-file manifests:

| Source | 1.11.2 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/server/EntityMap.java` | `59d9595da8b74449b9fe5516e2c62b262151d6e8ad397ae1453ad481cbfcfdbf` | same |
| `net/minecraft/server/TrackedEntity.java` | `1ba9b26e88e9fc278c667b4311105bbfe45d1b58d6ffa015e02a56ad1c3e2cdc` | same |
| `net/minecraft/server/PlayerManager.java` | `5f92907db38432cc50b65efe23d125a56502a95be3edd0e31b361dd56d664163` | `882937cb6794bc34366dd67a0b43f2c8122b54608ed822b5bd67880af503d53d` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197` | `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9` |

## Verdict: revise

The submitted report correctly describes the client-side respawn replacement, copied synced flags, temporary yaw/headYaw mismatch, attached-firework lookup, `getLookVector` delta, queued-task drain, and normal entity-list insertion/tick behavior. Its decisive order claim omits the server's respawn tracking teardown and restart, however. In the normal tracked-firework route, the active pre-respawn client firework does not survive to write into the replacement player, and the re-tracked firework is inserted after the replacement player.

The decisive exact-source trace is:

1. `PlayerManager.respawn` begins by calling `EntityMap.onPlayerRespawn(oldPlayer)` (1.11.2 lines 388–393; 1.12.2 lines 407–412). `EntityMap.onPlayerRespawn` visits each tracker (both versions lines 249–253). `TrackedEntity.onPlayerRespawn` removes the old viewer and sends a remove-entity packet for every entity still tracked by that player (both versions lines 542–547). A nearby, tracked Fireworks entity therefore receives a client removal before the respawn packet.
2. The client handles that removal through `ClientPlayNetworkHandler.handleRemoveEntities` → `ClientWorld.removeEntity(id)` (1.11.2 lines 556–561; 1.12.2 lines 569–574; `ClientWorld.removeEntity` lines 226–234 in both). The earlier rocket is removed/marked removed; it is not a surviving active entity for the post-respawn tick.
3. The server sends `PlayerRespawnS2CPacket` and then `networkHandler.teleport(...)` before adding the replacement server player to the world (1.11.2 `PlayerManager.java` lines 435–446 and 451–453; 1.12.2 lines 454–465 and 470–472). The replacement's world insertion triggers `EntityMap.onEntityAdded`; for a server player it calls `updatePlayer` on existing trackers (both `EntityMap.java` lines 75–84). `TrackedEntity.updatePlayer` sends the add-entity packet and, immediately after, synced data (both `TrackedEntity.java` lines 316–325; `FireworksEntity` is encoded as type 76 at lines 484–486).
4. Thus any initial spawn/metadata already queued before the old-player removal is followed by removal before the client tick. The fresh tracking spawn/metadata is after the client respawn/correction handlers. The client respawn handler appends the replacement local player to the existing world during `Minecraft.respawnPlayer` (1.11.2 lines 1918–1928; 1.12.2 lines 1977–1992). The later firework spawn handler calls `forceEntity`, which appends the new Firework to the entity list (client network handler type-76 branch at 1.11.2 lines 329–330 / 1.12.2 lines 342–343 and `ClientWorld.forceEntity` lines 205–218). Therefore the replacement player precedes this new Firework in the next ordinary entity iteration.
5. The client correction writes yaw and pitch but does not write `headYaw` (`ClientPlayNetworkHandler.handlePlayerMove`, 1.11.2 lines 565–600 / 1.12.2 lines 578–612; `Entity.updatePositionAndAngles`, 1.11.2 lines 1070–1093 / 1.12.2 lines 1093–1116). That mismatch can exist until the replacement's first living tick, but the living entity is ticked before its later-inserted Firework. `LivingEntity.tick` invokes `serverTickAi` for locally controlled entities (1.11.2 lines 1770–1782; 1.12.2 lines 1814–1826); `LocalClientPlayerEntity.isLocallyControlled()` is true (1.11.2 lines 468–470; 1.12.2 lines 485–487); and `PlayerEntity.serverTickAi` assigns `headYaw = yaw` (1.11.2 lines 410–414; 1.12.2 lines 406–410). The re-tracked Firework consequently sees the repaired angles.

The original report's client-only observation that `ClientWorld.unloadEntities()` removes only entities already marked removed is accurate (1.11.2 lines 305–345; 1.12.2 lines 308–348), but it does not cover the explicit `RemoveEntities` packet emitted by the server respawn tracker. The initial packet sequence is consequently not “spawn + metadata, respawn, first rocket tick” for a still-active rocket: it is “old entity removal, respawn + correction, replacement player insertion, fresh rocket spawn + metadata.” If the rocket is not re-tracked near the replacement, it is absent instead.

The `getLookVector` implementation difference is real in isolation: 1.11.2 `LivingEntity.getLookVector()` routes through the override using `headYaw` at lines 1915–1928, whereas 1.12.2 inherits `Entity.getLookVector()` using `yaw` at lines 1690–1692. `FireworksEntity.tick()` reaches its attached velocity write at lines 95–110 in both, but the source-reachable respawn ordering above repairs the local player's angles before a re-tracked rocket reaches that write. The report therefore does not establish a meaningful local-player velocity difference for same-dimension respawn.

No source-generation, build, test, or game launch was performed. This is a bounded source-order audit, not a pair-freeze or complete movement-coverage claim.
