# 1.16.3 → 1.16.4 player position correction route (bounded source packet)

Status: **NO DIFFERENCE in the examined producer/receiver route.** This is a narrow exact-source comparison only. It does not freeze the pair, establish all player position writers, or make a runtime claim.

## Exact source identities

Both endpoints use the canonical Mojmap source roots:

- 1.16.3: `decompiled_minecraft/1.16.3/mojmap/`; source map is `decompiled_minecraft/1.16.3/mojmap/.decompile-provenance.json#sources`, digest `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. The bound metadata input is `1.16.3.json` SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; Mojmap identity SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4: `decompiled_minecraft/1.16.4/mojmap/`; source map is `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json#sources`, digest `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. The bound metadata input is `1.16.4.json` SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`; Mojmap identity SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.

The mapping-local hidden provenance forms are present at those exact paths; the source map digests above match the supplied exact-source input contract. The following full-file hashes were checked against each endpoint's `sources` object and disk bytes:

| Source path | 1.16.3 SHA-256 | 1.16.4 SHA-256 | Whole-file result |
|---|---|---|---|
| `net/minecraft/server/level/ServerPlayer.java` | `bead85f57f7726d66040a9d3075cef2f28fe136bfe4bc098698f2bbea70255a9` | `9fad233370c04ab17f973bc12476466d824e3c1ba59a400542b073380bf550c2` | changed elsewhere |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `1837e3179a34c4905462e8bc3b881b2795b43cc945369225b64c48f5a2aabf05` | `3db41202adf5f9ef3626c63843eda39b9476c65ce86a2c7c5c6044fdb519ea80` | changed elsewhere |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | changed elsewhere |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | same | byte-identical |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | same | byte-identical |

## Paired route and result

1. `ServerGamePacketListenerImpl.handleMovePlayer(ServerboundMovePlayerPacket)` contains the server movement correction call sites. In both versions the handler sends the player back through the same teleport route for its existing awaited-position timeout, sleeping movement, excessive movement and rejected/colliding move paths. The extracted method text is identical across endpoints (body-text SHA-256 `cc6b274fbd71db862e77ac821e2ce95eaf60447a004be472bf4fcfcab62e86c6`). This packet is handled by logical-server code; it is not evidence that historical client movement runs on the server.
2. `ServerGamePacketListenerImpl.teleport(double, double, double, float, float, Set<RelativeArgument>)` computes the packet's relative offsets from the selected player coordinates/rotations, stores `awaitingPositionFromClient`, wraps the incremented teleport id at `Integer.MAX_VALUE`, stores the teleport time, applies `player.absMoveTo(...)` on the server object, and sends `ClientboundPlayerPositionPacket`. Its extracted method text is identical (body-text SHA-256 `5b415c3ad9f2b58f6cd629d8c9de098da86dafed7ac981657f8dcc44022834f8`).
3. `ServerPlayer.teleportTo(double, double, double)` delegates to `connection.teleport` with current rotations in both releases. `ServerPlayer.teleportTo(ServerLevel, ...)` uses that correction path for same-level moves and the existing respawn path for dimension changes; both extracted methods are identical across endpoints (body-text SHA-256 values `a52913f2ec948f9184b023d3b965fdfc6e41a710653ecd3bee2c1dc1017e20a0` and `99ed1879ddd84d2dd8863415c12fc1994b1b5c3b752e5c3ec1ecbff5f20adb25`).
4. `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` receives the correction. It preserves delta movement on relative axes and zeros the other axes; derives position/rotation from absolute or relative packet fields; writes raw position and old/previous positions; calls `absMoveTo`; then acknowledges the teleport and sends a positional rotation update. The extracted method text is identical (body-text SHA-256 `93685d72b1bec126554f85eba4d41f24832d57120a8afe61315b3e8efe798d16`). `LocalPlayer` contains no changed receive-side implementation in this pair; the listener applies directly to the current player object.
5. The packet class and `LocalPlayer` are whole-file byte-identical. Thus, while this route can directly write the client player's position/velocity state when a vanilla correction occurs, this examined producer/packet/receiver route has no source delta between the endpoints.

## Boundary

This closes only the cited server movement-correction and client position-packet route. It does not disposition every changed server-side position writer, callback, resource/tag/provider, other changed source body, or full ordinary-player tick dependency. No trajectory simulation or runtime parity was checked; no exact intermediate patch onset is inferred. The exact pair remains **PARTIAL** and requires the independent whole-pair coverage audit before freeze.