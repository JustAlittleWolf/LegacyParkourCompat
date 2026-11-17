# Direct position-correction receiver: early representatives

Scope: exact source facts for the named releases only. The inspected receiver applies a server-sent position/rotation correction to the local player. This report records (1) riding/vehicle gate or detach and (2) whether each position-relative flag preserves that velocity component. No release behavior is inferred from adjacent samples.

## Exact source identity and receiver ranges

For every row, the listed SHA-256 is the actual handler source file's byte hash, and it matched that source file's entry in the local provenance/source manifest. For ready-marker trees, the ready marker's recorded source-manifest SHA-256 also matched the manifest file. For legacy trees, the embedded `.decompile-provenance.json` supplied both the source hash map and exact-release client/mapping input URLs. Mappings are the local directory/provenance mapping identity.

| Release | Local mapping; receiver source file | Correction method signature and lines | Handler source SHA-256 | Local source identity evidence |
|---|---|---|---|---|
| 1.8 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 525–568 | `1cca0ded9f1112d407b149931c1f067a104d2beed74f1e7070200e8c035a102d` | Embedded provenance exact 1.8 inputs; source-map hash matches |
| 1.8.4 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 517–565 | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | Embedded provenance exact 1.8.4 inputs; source-map hash matches |
| 1.8.8 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 517–565 | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | Embedded provenance exact 1.8.8 inputs; source-map hash matches |
| 1.9.2 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 545–594 | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` | Embedded provenance exact 1.9.2 inputs; source-map hash matches |
| 1.10 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 552–601 | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | Embedded provenance exact 1.10 inputs; source-map hash matches |
| 1.10.2 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 552–601 | `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` | Ready marker id/map 1.10.2/Ornithe Feather; manifest SHA and source entry match |
| 1.11 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 565–614 | `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` | Ready marker id/map 1.11/Ornithe Feather; manifest SHA and source entry match |
| 1.11.1 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 565–614 | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | Ready marker id/map 1.11.1/Ornithe Feather; manifest SHA and source entry match |
| 1.11.2 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 565–614 | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | Ready marker id/map 1.11.2/Ornithe Feather; manifest SHA and source entry match |
| 1.13 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 613–662 | `623510536225f3839ea1e217466982285220fdf38e8c049a1635d93d2a6889a3` | Ready marker id/map 1.13/Ornithe Feather; manifest SHA and source entry match |
| 1.14 | Ornithe Feather; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `handlePlayerMove(PlayerMoveS2CPacket packet)`, 650–710 | `d5c63c25b086b370588810ea999cab06a538fbf8a01a6d357b59bd6fd608ba5c` | Ready marker id/map 1.14/Ornithe Feather; manifest SHA and source entry match |
| 1.14.1 | Yarn; `net/minecraft/client/network/ClientPlayNetworkHandler.java` | `onPlayerPositionLook(PlayerPositionLookS2CPacket arg)`, 642–697 | `13db0a2baa0a2c986c795a2990eab64f3d9a4dc121e360dbd04c2ea84310ddd7` | Legacy embedded provenance exact 1.14.1 client/Yarn inputs; source-map hash matches; no ready marker/source-manifest sidecar present |
| 1.14.4 | Mojmap; `net/minecraft/client/multiplayer/ClientPacketListener.java` | `handleMovePlayer(ClientboundPlayerPositionPacket clientboundPlayerPositionPacket)`, 659–714 | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` | Ready marker id/map 1.14.4/Mojmap; manifest SHA and source entry match |
| 1.15.1 | Mojmap; `net/minecraft/client/multiplayer/ClientPacketListener.java` | `handleMovePlayer(ClientboundPlayerPositionPacket clientboundPlayerPositionPacket)`, 669–734 | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | Ready marker id/map 1.15.1/Mojmap; manifest SHA and source entry match |
| 1.15.2 | Mojmap; `net/minecraft/client/multiplayer/ClientPacketListener.java` | `handleMovePlayer(ClientboundPlayerPositionPacket clientboundPlayerPositionPacket)`, 669–734 | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | Ready marker id/map 1.15.2/Mojmap; manifest SHA and source entry match |
| 1.16.1 | Mojmap; `net/minecraft/client/multiplayer/ClientPacketListener.java` | `handleMovePlayer(ClientboundPlayerPositionPacket clientboundPlayerPositionPacket)`, 683–752 | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` | Ready marker id/map 1.16.1/Mojmap; manifest SHA and source entry match |

## Classification

| Releases sampled | Local-player riding/vehicle behavior | Per-axis position-relative / velocity rule |
|---|---|---|
| Each individually listed sample from 1.8, 1.8.4, 1.8.8, 1.9.2, 1.10, 1.10.2, 1.11, 1.11.1, 1.11.2, 1.13, 1.14, 1.14.1, 1.14.4, 1.15.1, and 1.15.2 | Receiver has no riding/vehicle predicate and performs no detach. | For X, Y, and Z independently: relative position adds to the current coordinate and retains that axis's existing velocity; absolute position uses the packet coordinate and zeros only that axis's velocity. |
| 1.16.1 | New gate: after coordinate/velocity components are derived, `if (player.tickCount > 0 && player.getVehicle() != null) player.removeVehicle();` detaches the local player before position/velocity writes. | Same sampled per-axis rule: relative position retains that axis's old delta movement; absolute position sets that axis's component to `0.0`. |

## Method evidence at the observed behavior change

`1.15.2 ClientPacketListener.java:669–716`: per-axis flags retain `vec3.x()/y()/z()` on relative axes; else choose `0.0`; the method writes the resulting vector via `player.setDeltaMovement(d, h, l)`. There is no vehicle check in the receiver body (669–734).

`1.16.1 ClientPacketListener.java:683–734`: the same per-axis selection is at 692–724, then lines 726–728 detach only under `player.tickCount > 0 && player.getVehicle() != null`; position and velocity are written at 730–734. Thus the sampled detach gate first appears in this set at 1.16.1; no intermediate boundary is inferred.

## Evidence limitation

This is a local source/provenance consistency check. It establishes that each examined decompiled file matches its local exact-release provenance or ready/source manifest and records the mapping identity supplied there. It does not independently re-fetch or externally authenticate upstream artifacts. The 1.14.1 legacy tree has no ready marker or separate source-manifest sidecar; its embedded provenance source map and exact input records are the available local evidence.