# Independent source audit: respawn and local position closure

## Verdict

**Accepted for the bounded source closure, with a scope qualification.** I independently traced the cited local-client handlers and server packet producers in the canonical read-only Mojmap trees for 1.18.2 and 1.19. The closure's conclusion holds for direct local-player movement writes in the assigned slices: the respawn reset, direct player correction, ability writes, portal tick, controlled-entity guards and producer order show no release-to-release change that supplies a historical local movement delta.

The effect update path is not literally state-identical: 1.19 resolves the effect directly from the packet and constructs `MobEffectInstance` with optional `FactorData`; 1.18.2 resolves by numeric effect ID and uses the older constructor. The closure records this difference. Its no-delta conclusion is supported for direct writes to position, velocity, rotation and pose, but should not be broadened to assert that effect-instance state is entirely unchanged or that all effect mechanics have been audited.

## Source identity and selected hashes

Canonical source root (read-only): `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

The audited closure's SHA-256 is `7d7125967f278e43a9de78174ccaea0eea452a171b0feab8b8e5a1bf24915009`, matching the supplied identity. Ready markers both report `status=ready`, `mapping=mojmap`, and exact `versionId` 1.18.2 / 1.19. Their declared manifest hashes match the actual manifest files:

- 1.18.2 manifest: `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; client artifact: `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`.
- 1.19 manifest: `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`; client artifact: `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764`.

All nine selected source hashes below were independently recalculated from the canonical files and matched their per-version manifest entries and the closure's table:

| Source file | 1.18.2 SHA-256 | 1.19 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `e718016022c2ae86a2c354af2d34fe4de7d6e36dc8792d2e2c1f08abb6b77b7b` | `a67c7880c967e93322de0f2e30af0eb34e24a8b7df21171e2a37032b27a21671` |
| `net/minecraft/client/player/LocalPlayer.java` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| `net/minecraft/world/entity/Entity.java` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| `net/minecraft/server/level/ServerPlayer.java` | `9ed753c5012b32ea550e664db88f2e352eb9b599c935092a119ad1a309c1f680` | `d711ff046f29fc1f135fc15afbb540d7c05e51c2953750a6cc528491c3e9b7f1` |
| `net/minecraft/server/players/PlayerList.java` | `393b36c4d4dbcdf6c164192cac8cd6053b2fcb71458d3ee34bf638c983ac1ea6` | `5d0d1dd1f61a961437b7d489c328764dd949c9408e2443a816ce8cdc2f63725f` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `c979dd50b8d80d2d00190e08360a4fc689693715a84e248503099f340a8546c9` | `81d6d0d92c0e902f2618e82fc6a261d58b7b468cbe8b501047975a902d4e18a2` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0` | `ecdfc5706756b92c6ab6de7d0cf467f42ce4952c9ca02237ca96d7c1053745d0` |
| `net/minecraft/network/protocol/game/ClientboundRespawnPacket.java` | `b731143868a20df4c338f99939e78a70560b77d60ae51c42356a0e4b5345f627` | `4e2e903725e441d010529fd4b11a9b5086aecf26a73983c5fa2d9486eb8025b7` |
| `net/minecraft/network/protocol/game/ClientboundTeleportEntityPacket.java` | `9ba51291ad2e2881febe3e6fdd2838677155e6a6ca38d2ad5a8e3363958d15e7` | `6049f96694fcccc1e395dbab27d8fa2596b144b6506893598aa2982890c136d4` |

## Independently checked claims

- `ClientPacketListener.handleRespawn`: 1.18.2 lines 910–969; 1.19 lines 948–1008. Both create/install a replacement `LocalPlayer`, retain the entity ID, copy entity data, conditionally copy attributes, call `resetPos`, set yaw to `-180.0F`, install input, adjust abilities, and set local mode. The only movement-adjacent extra client write is 1.19 `setLastDeathLocation` (line 1002), which is metadata. Dimension type lookup differs as expected from registry API evolution.
- `LocalPlayer.resetPos`: 1.18.2 lines 623–639; 1.19 lines 706–722. The standing-pose write, upward collision search from current Y, zero velocity, zero X rotation, health reset and death-time reset match in order. The handler's subsequent yaw write matches.
- `ClientPacketListener.handleMovePlayer`: 1.18.2 lines 541–606; 1.19 lines 549–614. The local player target, dismount gate, relative-axis coordinate/velocity handling, old/current position writes, relative rotation and `absMoveTo`, then teleport acknowledgment and position/rotation response match operation-for-operation.
- `handleSetEntityMotion` (1.18.2 lines 441–447; 1.19 lines 443–449) has no local-control check and calls `lerpMotion` with packet components divided by `8000.0` in both. `Entity.lerpMotion` is the corresponding velocity setter in both trees. This packet can write velocity to the local entity if its ID matches; the unchanged path is worth retaining in the closure.
- `handleTeleportEntity` and `handleMoveEntity`: the local-control predicate gates interpolation, rotation and on-ground writes in both releases (1.18.2 `ClientPacketListener` lines 476–522; 1.19 lines 483–530). The unconditional teleport bookkeeping changes from `setPacketCoordinates` to `syncPacketPositionCodec`; relative move decoding changes to the codec in 1.19. `Entity.isControlledByLocalInstance` at 1.18.2 lines 2685–2688 and 1.19 lines 2713–2716 retains the same controlling-passenger/local-player predicate. A locally controlled entity therefore skips both relative movement application and teleport interpolation/application. This conclusion is limited to these client handlers and their local-control predicate.
- `handlePlayerAbilities`: 1.18.2 lines 1541–1550; 1.19 lines 1615–1624. All six ability/speed writes and their order match. Effect update: 1.18.2 lines 1329–1342; 1.19 lines 1365–1384. Both resolve a living target and force-add a packet-derived effect, with the 1.19 `FactorData` addition noted above. Effect removal is the same `removeEffectNoUpdate` call (1.18.2 lines 1490–1496; 1.19 lines 1564–1570).
- `LocalPlayer.handleNetherPortalClient`: 1.18.2 lines 818–860; 1.19 lines 902–944. The progress, confusion-duration gate, portal flag, UI/sound and cooldown logic match. It does not write position, velocity, rotation or pose, and does not itself transfer dimensions.
- Server provenance: `PlayerList.respawn` sends `ClientboundRespawnPacket` then invokes `connection.teleport(...)` in both versions (1.18.2 lines 481–495; 1.19 lines 482–498). The call order is confirmed at source; the teleport method emits the correction packet. In `ServerPlayer.changeDimension`, the server sends the respawn packet before applying `PortalInfo` rotation/position and then sends abilities and active effects (1.18.2 lines 721–762; 1.19 lines 728–770). These are producer-side facts and do not imply client-side physics execution or wider network scheduling.

## Discrepancies and limits

No material error was found in the bounded closure. The stated effect-handler difference is real, and the conclusion should remain limited to direct movement-state writes: the effect instance representation has a 1.19 `FactorData` field. The audited sources do not by themselves prove how packets are scheduled or delivered, any player trajectory, the precise intermediate patch where a behavior first changed, complete movement coverage, pair freeze, implementation correctness or runtime parity. No product implementation, product history, wiki/backlog, other lane report, runtime/gameplay, build or test evidence was consulted or produced.
