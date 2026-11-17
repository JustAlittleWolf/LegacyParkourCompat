# 1.16.1 → 1.16.2 direct player response and movement-packet source slice

Status: bounded source comparison only. The examined vanilla client/server packet-response route is byte-identical in its relevant methods; this does not close the pair or establish historical server physics/runtime parity.

## Exact source identity

Both endpoints are Mojmap publications. Roots: `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`. The exact ready markers and root provenance/source manifests are the ones bound by the accepted base source inventory `ordinary_player/coverage-partial.md` (SHA-256 `A542F16666AED2E489296B8AF886D7717092A4809EADC516DAEC79F11D57967A`; independent audit `ordinary_player/audit/verdict.md`, SHA-256 `5203F7819178EB5EE399CD8DE31DFD5482B82B5657A4277923B9790BD3FED57E`). The base report gives source manifest/provenance/ready file hashes and verified exact 3,572 / 3,516 Java-file inventories.

The following path files were checked against their endpoint source manifests and current bytes:

| Source | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `client/multiplayer/ClientPacketListener.java` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` |
| `server/network/ServerGamePacketListenerImpl.java` | `7962d9207b139f8f794061c79bf06454e44f2fe7be3549e513500ef3bccde6e6` | `1837e3179a34c4905462e8bc3b881b2795b43cc945369225b64c48f5a2aabf05` |
| `server/level/ServerEntity.java` | `be366b94da3225bc71859138ab702b1f3b2cb4322d241e67737693487c3c81de` | `be366b94da3225bc71859138ab702b1f3b2cb4322d241e67737693487c3c81de` |
| `network/protocol/game/ServerboundMovePlayerPacket.java` | `d507e0f95601bd5ec9c180b58e456891dbcaa3b186ec8099e9654c5797c220d1` | `d507e0f95601bd5ec9c180b58e456891dbcaa3b186ec8099e9654c5797c220d1` |
| `network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |
| `network/protocol/game/ClientboundSetEntityMotionPacket.java` | `753fb60e5ea296a64cfdf96a58bbc254f056b6d8dbcb19619ffaec2a83848762` | `753fb60e5ea296a64cfdf96a58bbc254f056b6d8dbcb19619ffaec2a83848762` |

## Bounded route inventory

1. `LocalPlayer.tick()` calls `sendPosition()` at the end of its client tick. The relevant `sendPosition()` body sends sprint/sneak state transitions and then chooses a `ServerboundMovePlayerPacket` position/rotation variant using displacement/rotation thresholds, a 20-tick reminder, passenger state, and `onGround`. This sender body is unchanged between endpoints. The exact source path has no position/velocity write itself; it reports local state to the server.
2. `ServerGamePacketListenerImpl.handleMovePlayer(ServerboundMovePlayerPacket)` receives that input. The unchanged method handles invalid packet values and pending teleport acknowledgment, writes/validates player coordinates, performs `Player.jumpFromGround()` for the server-side positive-Y/on-ground case, calls `player.move(MoverType.PLAYER, ...)`, and either stores accepted coordinates or calls the listener's `teleport(...)` correction path on rejection. The surrounding `ServerGamePacketListenerImpl` file changes elsewhere for recipe book, structure-block and advancement/interact handling; the diff has no changed hunk in this movement method.
3. `ServerGamePacketListenerImpl.teleport(...)` resolves relative arguments, stores `awaitingPositionFromClient`, updates server position through `absMoveTo`, and sends `ClientboundPlayerPositionPacket` with the pending teleport id. Its relevant overloads have no endpoint code delta. This records a vanilla correction producer only; it is not evidence about server-side historical physics or server-check eligibility policy.
4. `ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)` resolves relative axes against the current local-player position/velocity, calls `setPosRaw`, retains only relative velocity components, calls `absMoveTo`, and acknowledges the teleport before sending a fresh movement packet. The method body is unchanged across endpoints. `ClientboundPlayerPositionPacket` is byte-identical.
5. `ServerEntity.sendChanges()` emits `ClientboundSetEntityMotionPacket` when `entity.hurtMarked` is true, then clears the flag. Its private `broadcastAndSend` broadcasts and, for a `ServerPlayer`, also sends to that player connection. `ClientPacketListener.handleSetEntityMotion` resolves the entity id and calls `lerpMotion` with each packet component divided by `8000.0`. `ServerEntity` and both motion-packet source files are byte-identical; the client receiver body is unchanged across endpoints.

## Result and remaining boundaries

No source difference was found in these bounded local-player input sender, server movement-correction writer, local correction consumer, or hurt-marked motion response paths. This closes only these packet route inventories, reusing the base source report's explicit open-scope language; it is not a movement finding.

The accepted base report still leaves the complete `LocalPlayer` input → sprint/jump/crouch → travel/landing/post-travel callback graph, all changed block/provider/state registrations, other direct position/velocity writers, and the airborne edge-backoff witness open. The adjacent suffocation, Soul Speed and fluid reports cover only their named branches. Server dimension/portal transfer and respawn position are transition paths outside this bounded ordinary packet-response slice; no claim is made about those paths. Vehicle physics, damage calculation and server replay are excluded by project scope. No product applicability, world reachability, or runtime result is inferred.
