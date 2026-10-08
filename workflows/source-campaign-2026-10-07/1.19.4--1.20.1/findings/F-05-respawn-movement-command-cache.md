# F-05: Respawn movement-command cache can suppress first sneak/sprint update

- Pair: 1.19.4 (A) -> 1.20.1 (B), Mojmap.
- Status: source-confirmed conditional client command and server player-state writer difference; trajectory not established.
- Slice: S7-05. Related bounded emission body: S1-05.

## Evidence

When `ClientboundRespawnPacket.shouldKeep((byte)2)` is false, A's `ClientPacketListener#handleRespawn` still calls the five-argument `createPlayer` with the old local player's `isShiftKeyDown()` and `isSprinting()` values. B instead calls the three-argument `createPlayer`; that overload delegates with `false,false`. `LocalPlayer` stores those arguments in its `wasShiftKeyDown` and `wasSprinting` command caches on both sides. A and B then use the same `sendPosition`/`sendIsSprintingIfNeeded` comparisons to decide whether to emit the respective command.

A held sneak key can therefore match A's inherited cache and suppress PRESS_SHIFT_KEY, while B starts with a false cache and emits it. Likewise, if the fresh player becomes sprinting before the first command comparison and the old player was sprinting, A can suppress START_SPRINTING while B emits it. Both server handlers directly map the commands to `setShiftKeyDown` or `setSprinting`. This establishes the conditional command and state-writer difference only; it does not establish a trajectory or the outcome for every server respawn state.

## Source locations and hashes

- A `ClientPacketListener#handleRespawn`, lines 1060-1129; SHA-256 `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715`.
- B `ClientPacketListener#handleRespawn`, lines 1065-1143; SHA-256 `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b`.
- A `LocalPlayer` constructor, lines 134-141, and command emission, lines 218-279; SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`.
- B `LocalPlayer` constructor, lines 136-143, and command emission, lines 220-281; SHA-256 `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2`.
- A `MultiPlayerGameMode#createPlayer` three-argument overload, lines 377-378; SHA-256 `a2d6b02ef8849cc036bbc54e81327c2ab20603ae9d350c37b65b1de8a8342c98`.
- B `MultiPlayerGameMode#createPlayer` three-argument overload, lines 370-371; SHA-256 `20cf40ac52fab10eb29f6a8181dfdcdcb5feb268ee10b5a851f6fc8aa2a2747e`.
- A `ServerGamePacketListenerImpl#handlePlayerCommand`, lines 1372-1386; SHA-256 `1e1b8dc23031bd5dfc4a3dd5453ec1e6ad3211a534a810927c8fa725fc271dc9`.
- B `ServerGamePacketListenerImpl#handlePlayerCommand`, lines 1376-1390; SHA-256 `5d790f4ade510baa163cd17e622851261a3eaebbbe8ad82ac31a9bca3c251364`.

## Reachability limits

This path requires a respawn with keep-entity-data false. The conditional sneak case also requires the sneak input to remain active when the new LocalPlayer samples input. The conditional sprint case requires sprinting to be true at the first emission check and the old player's sprint state to have seeded A's cache true. The receiving handler is source-confirmed, but no runtime packet trace, server-state trace, or player trajectory was collected. Other respawn state preservation, abilities, cooldowns, and mount paths remain open.
