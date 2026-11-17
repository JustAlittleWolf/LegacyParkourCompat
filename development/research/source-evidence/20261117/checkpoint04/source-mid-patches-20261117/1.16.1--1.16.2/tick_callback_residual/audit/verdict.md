# Independent source audit — direct player response and movement-packet routes

## Verdict

**ACCEPT** the bounded source report `build/source-mid-patches-20261117/1.16.1--1.16.2/tick_callback_residual/direct-player-response-routes.md`, SHA-256 `E5B4AA5D1FF7C09F2E9B59DD00C952560A8029DBC6860B9DFF6C78EE280CF730`.

This verdict accepts only the listed client movement-packet sender, server movement packet consumer/correction writer, client correction consumer, and hurt-marked velocity response route. It makes no full-pair, historical server-physics, world-state, or runtime-parity claim.

## Identity and source binding

The source report's base inventory binding is exact: `ordinary_player/coverage-partial.md` SHA-256 `A542F16666AED2E489296B8AF886D7717092A4809EADC516DAEC79F11D57967A`; its independent partial-source verdict is `ordinary_player/audit/verdict.md` SHA-256 `5203F7819178EB5EE399CD8DE31DFD5482B82B5657A4277923B9790BD3FED57E`.

Both endpoints are exact Mojmap publications. The 1.16.1 source manifest, provenance and ready sidecars hash to `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`, `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`, and `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`. The 1.16.2 equivalents hash to `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`, `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`, and `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`. Declared release IDs and mappings are 1.16.1/Mojmap and 1.16.2/Mojmap.

All seven file hashes listed in the report were recomputed from disk and matched their respective `mojmap.sources.sha256` manifest entries at both endpoints. These files are `LocalPlayer.java`, `ClientPacketListener.java`, `ServerGamePacketListenerImpl.java`, `ServerEntity.java`, `ServerboundMovePlayerPacket.java`, `ClientboundPlayerPositionPacket.java`, and `ClientboundSetEntityMotionPacket`.

## Bounded route review

The following paired method bodies are identical across endpoints: `LocalPlayer.tick` and `sendPosition`; server `handleMovePlayer`, both relevant `teleport` overloads, and the `isPlayerCollidingWithAnythingNew` helper; client `handleMovePlayer` and `handleSetEntityMotion`. The latter methods were extracted from the exact class files and compared. `ServerEntity.java` and the three packet classes are byte-identical by their manifest-bound hashes.

The packet dispatch routes are direct: `ServerboundMovePlayerPacket.handle` calls `ServerGamePacketListener.handleMovePlayer`; `ClientboundPlayerPositionPacket.handle` calls `ClientGamePacketListener.handleMovePlayer`; `ClientboundSetEntityMotionPacket.handle` calls `ClientGamePacketListener.handleSetEntityMotion`. The server correction writer resolves relative coordinates/rotations, stores the pending position/id, updates the server-side position and sends `ClientboundPlayerPositionPacket`. The client consumer applies relative axes to local position/velocity and acknowledges the teleport. The velocity packet consumer resolves an entity ID and passes its fixed-point components divided by 8000.0 to `lerpMotion`. `ServerEntity.sendChanges` emits the motion packet when `hurtMarked` is set and clears the flag; its private `broadcastAndSend` helper also sends to the connection when the entity is a `ServerPlayer`.

The report correctly limits this server route to vanilla packet/correction behavior and does not claim historical server physics. No difference was found in these bounded routes.

## Remaining coverage

The report keeps the following outside its accepted slice and open: the complete local-player input/tick/travel/landing/callback graph, changed block/provider/state registrations, other direct position/velocity writers, and the airborne edge-backoff witness. Portal/respawn transition writers are explicitly outside this ordinary packet-response slice. These limits are consistent with the report; this audit does not freeze the pair.

No backlog, product, implementation, wiki, or Git-history material was read. No build, test, or runtime was run, and no canonical source was modified.