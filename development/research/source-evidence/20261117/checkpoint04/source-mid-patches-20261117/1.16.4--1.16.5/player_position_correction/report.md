# 1.16.4 → 1.16.5 player position-correction route

Status: **bounded source slice; no version difference found in the examined producer, packet, or client-application route.** This report does not freeze the exact pair or claim runtime parity.

## Exact source identities

Both endpoints are Mojmap. The canonical source input contract is `build/source-inputs-115-116-20261117/README.md`.

- **1.16.4:** `decompiled_minecraft/1.16.4/mojmap/`; complete source map is the `sources` object in the mapping-local hidden `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json`, whose SHA-256 is `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. The source map binds the listed Java paths. The official client mapping artifact SHA-256 is `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.
- **1.16.5:** `decompiled_minecraft/1.16.5/mojmap/`; complete root source map `decompiled_minecraft/1.16.5/mojmap.sources.sha256` SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; provenance `decompiled_minecraft/1.16.5/mojmap.provenance.json` SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`. Official client mapping artifact SHA-256: `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`.

The 1.16.4 mapping-local hidden source map form was read directly. Each selected file's disk SHA-256 was checked against that endpoint map; the corresponding 1.16.5 file was checked against its root manifest. All seven selected files have the same bytes and manifest-bound SHA at both endpoints:

| Mojmap path | 1.16.4 SHA-256 | 1.16.5 SHA-256 |
|---|---|---|
| `net/minecraft/server/level/ServerPlayer.java` | `9fad233370c04ab17f973bc12476466d824e3c1ba59a400542b073380bf550c2` | same |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `3db41202adf5f9ef3626c63843eda39b9476c65ce86a2c7c5c6044fdb519ea80` | same |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | same |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | same |
| `net/minecraft/client/Minecraft.java` | `3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc` | same |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | same |
| `net/minecraft/network/protocol/game/ClientGamePacketListener.java` | `91bf58b911d19a874770359823797c6f9e03d62ddc4c337e88bd4e6025356c0c` | same |

## Bounded call path and behavior

- `ServerPlayer.teleportTo(double, double, double)` delegates to `this.connection.teleport(...)` (`ServerPlayer.java:1183–1185`). `ServerGamePacketListenerImpl.teleport(double, double, double, float, float)` delegates to its relative-argument overload (`ServerGamePacketListenerImpl.java:966–968`). The overload sets the pending acknowledged position and sends `ClientboundPlayerPositionPacket` with position/rotation values adjusted for the relative flags (`:970–984`). The connection method is also used by server position correction paths in `handleMovePlayer`; this report does not model server physics.
- `ClientboundPlayerPositionPacket.handle(ClientGamePacketListener)` dispatches to `handleMovePlayer(this)` (`ClientboundPlayerPositionPacket.java:53–55`). `ClientPacketListener.handleMovePlayer` obtains `this.minecraft.player` (`:671–674`); `Minecraft.player` is a `LocalPlayer` (`Minecraft.java:307`). It resolves each relative position component against current player state and each absolute component against the packet value, updates the old-position fields, detaches an existing vehicle when `tickCount > 0`, writes raw position and position history, and sets each delta-motion component to its prior value only when that component is relative (otherwise zero) (`ClientPacketListener.java:675–723`). It then resolves relative rotations, calls `absMoveTo`, acknowledges the teleport, and sends a position/rotation packet (`:723–735`).
- These are direct client-side writes to the controlled player's position, velocity and orientation when a position correction packet arrives. Their operation order and gates are identical in this exact adjacent pair because the entire listed producer, packet and consumer source files are byte-identical.

## Disposition and limits

**No 1.16.4→1.16.5 source delta was found in the examined player correction producer/receiver route.** The bounded result only establishes that the selected source path is unchanged. Server correction generation is not historical client movement simulation; no server-side historical physics is proposed. No packet was sent and no runtime behavior was tested. Other pair coverage remains partial, including fluid contact/travel, shapes/providers/registrations, tag/resource membership, other player-response callbacks and complete tick/dependency inventory. No pair freeze or exact patch boundary follows from this report.