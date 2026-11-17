# Independent source audit: 1.16.4 → 1.16.5 position-correction route

**Verdict: ACCEPT** for the bounded producer/packet/client-application route in report SHA-256 `dbea9ab382396c0d7ab638a1f2eca4974c6b5910a9a462c83963d8d2b6d885ba`.

## Exact source identity and citation binding

The exact Mojmap input contract is `build/source-inputs-115-116-20261117/README.md`. For 1.16.4, the mapping-local hidden source map at `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json#sources` is bound by whole provenance SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. For 1.16.5, `decompiled_minecraft/1.16.5/mojmap.sources.sha256` has SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, and provenance `decompiled_minecraft/1.16.5/mojmap.provenance.json` has SHA-256 `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`. The provenance declares `versionId=1.16.5`, `namespace=mojmap`; the input contract records 1.16.4 as exact Mojmap 1.16.4 and its official client mapping identity.

I checked each of the seven report-listed Java files on disk against the appropriate exact source map at both endpoints. All fourteen bindings match; each old/new file pair has identical bytes and identical manifest-bound SHA-256:

| Source path | SHA-256 at both endpoints |
| --- | --- |
| `net/minecraft/server/level/ServerPlayer.java` | `9fad233370c04ab17f973bc12476466d824e3c1ba59a400542b073380bf550c2` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `3db41202adf5f9ef3626c63843eda39b9476c65ce86a2c7c5c6044fdb519ea80` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` |
| `net/minecraft/client/Minecraft.java` | `3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/network/protocol/game/ClientGamePacketListener.java` | `91bf58b911d19a874770359823797c6f9e03d62ddc4c337e88bd4e6025356c0c` |

## Bounded route review

The cited route is present in the exact sources. `ServerPlayer.teleportTo(double, double, double)` delegates to its connection teleport method (1.16.4 line 1183 onward). `ServerGamePacketListenerImpl.teleport` delegates to the relative-argument overload, updates the pending position/teleport acknowledgment fields, applies the server-side absolute position, and sends `ClientboundPlayerPositionPacket` (lines 966–984). Its `handleMovePlayer` method also calls the correction method in its server correction branches. `ClientboundPlayerPositionPacket.handle` dispatches to `ClientGamePacketListener.handleMovePlayer` (lines 53–55).

On the client, `ClientPacketListener.handleMovePlayer` obtains `this.minecraft.player`; `Minecraft.player` is typed `LocalPlayer`. The handler resolves each position component as relative or absolute and updates the old-position fields, removes a vehicle under its tick-count gate, writes raw position plus position-history fields and delta movement, resolves relative rotations, calls `absMoveTo`, then acknowledges the correction and sends the current position/rotation (lines 671–735). For an absolute motion component the resulting delta is zero; a relative component preserves its prior delta. These are direct writes to the controlled player's position, velocity, and orientation when the correction packet arrives.

## Acceptance boundary

The bounded call-path and no-delta conclusion are supported: the selected producer, packet, and receiver files are byte-identical across this exact adjacent pair, so their source behavior does not change here. This does not establish that all position-correction producers or player response callbacks have been inventoried; the report expressly leaves other pair coverage open. The server-side correction producer is not treated as historical movement simulation. No packet/runtime behavior, ordinary-world reachability, full tick/dependency closure, or pair freeze is accepted by this verdict.
