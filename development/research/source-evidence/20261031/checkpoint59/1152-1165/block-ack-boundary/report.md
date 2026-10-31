# Block-break acknowledgement correction boundary — Mojmap 1.15.2 through 1.16.5

## Scope

Exact-source boundary audit of `MultiPlayerGameMode.handleBlockBreakAck(ClientLevel, BlockPos, BlockState, Action, boolean)`, its saved-state capture, and its direct `Minecraft.player.absMoveTo` restore. Checked releases: 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, and 1.16.5. The accepted endpoint route evidence is in `../block-ack-predicate-closure/report.md`; this audit makes no claim about producer frequency or timing, pair freeze, or runtime parity.

## Exact source identities and integrity

For 1.15.2, 1.16, 1.16.1, 1.16.2, and 1.16.5, root `mojmap.ready.json` identifies the exact release, `mojmap`, and `ready`; the root `mojmap.sources.sha256` hash matches the ready record. Each cited `MultiPlayerGameMode.java`, `ClientPacketListener.java`, `Minecraft.java`, and `Entity.java` file matches its entry in that release's own source manifest.

| Release | Ready identity | SHA-256 of root `mojmap.sources.sha256` |
|---|---|---|
| 1.15.2 | `1.15.2 / mojmap / ready / 3332 files` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16 | `1.16 / mojmap / ready / 3572 files` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | `1.16.1 / mojmap / ready / 3572 files` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | `1.16.2 / mojmap / ready / 3516 files` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | `1.16.5 / mojmap / ready / 3523 files` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |

For 1.16.3 and 1.16.4, the exact Mojmap roots carry their authoritative embedded `mojmap/.decompile-provenance.json` source maps. Provenance hashes are `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`, respectively. The exact metadata, client, and published client-mapping input hashes are:

- 1.16.3: metadata `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; client `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; mapping `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4: metadata `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`; client `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; mapping `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.

All four cited files for each of 1.16.3 and 1.16.4 were rehashed against that release's own embedded `sources` map. Relevant whole-file SHA-256 fingerprints, grouped where identical:

| File | 1.15.2 | 1.16 and 1.16.1 | 1.16.2 and 1.16.3 | 1.16.4 and 1.16.5 |
|---|---|---|---|---|
| `client/multiplayer/MultiPlayerGameMode.java` | `d69f96ab32445bdc5a2cfa1f7ca718d9213d107b7690a15799b660cd3548a4b4` | `6be55796cb04dc2a1c33ddd1aca2c0004070a80555ea2115b73306b83990fec7` | `80d1b28891b33de967cbc26e13acec097fb94091bbc226f92ad5ba2750015b66` | same as 1.16.2 and 1.16.3 |
| `client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` |
| `client/Minecraft.java` | `1d0cac30a36e2ff6cd5665bc6a08c29c3729317b66a892be85e6cff1016dbb11` | `1cf59770fd804795123c32b2e2efdf05db4b7c479018c91955c9d7c16582922e` | `117334920893b9dfabe16797d3a1094ffb1a445003c13c4b34f6e660729b59bb` | `3fc48eb93d2dc001496c11b66071db939cdaa3f1ff7c4e5b2b391e25a715facc` |
| `world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | same as 1.16.2 and 1.16.3 |

## Predicate boundary

The newer outer correction predicate first appears in 1.16.0 and persists through 1.16.5. Its saved value and restore call remain rotation-bearing through 1.16.1.

- **1.15.2:** `handleBlockBreakAck` has the unchanged public signature at `MultiPlayerGameMode.java:424–434`. The saved map at `:61` stores `PosAndRot`; `sendBlockAction` (`:418–421`) captures `new PosAndRot(localPlayer.position(), localPlayer.xRot, localPlayer.yRot)`. The handler removes the matching `(BlockPos, Action)` value, then enters reconciliation under `saved == null || !allGood || action != START_DESTROY_BLOCK && clientLevel.getBlockState(pos) != acknowledgedState` (`:425–428`). For a present value, it calls five-argument `Minecraft.player.absMoveTo(x, y, z, yRot, xRot)` (`:430–432`).
- **1.16 and 1.16.1:** The same method signature persists (`MultiPlayerGameMode.java:435–445`); the map still stores `PosAndRot` (`:62`) and `sendBlockAction` still captures position, pitch, and yaw (`:429–432`). The handler now stores current state in `blockState2` and gates reconciliation with `(saved == null || !allGood || action != START_DESTROY_BLOCK && blockState2 != blockState) && blockState2 != blockState` (`:436–440`). This explicit final identity-difference conjunct is the first audited occurrence in 1.16.0. For a saved value the response still invokes five-argument `absMoveTo`, restoring position and rotation (`:441–443`).
- **1.16.2 through 1.16.5:** The same handler signature and correction predicate continue (`MultiPlayerGameMode.java:434–445`). The saved map changes to `Vec3` (`:61`), and `sendBlockAction` stores only `localPlayer.position()` (`:428–431`). After the state correction, position restoration requires a nonnull saved vector, `clientLevel == player.level`, and `player.isColliding(blockPos, blockState)` (`:441`). It then calls three-argument `player.absMoveTo(vec3.x, vec3.y, vec3.z)` (`:442`), restoring position without saved yaw or pitch. These saved-data and position-only forms remain byte-identical in the checked .2 through .5 sources.

Thus the added `&& blockState2 != blockState` predicate starts in 1.16.0; position-only saved state and restoration start in 1.16.2. The complete newer saved-position/position-only response path is present from 1.16.2 through 1.16.5.

## Direct client route and restore target

`ClientPacketListener.handleBlockBreakAck` calls `PacketUtils.ensureRunningOnSameThread`, then forwards `this.level` and the packet position, state, action, and `allGood` value into `minecraft.gameMode.handleBlockBreakAck`. This route is present at 1.15.2 `ClientPacketListener.java:2354–2365`, 1.16/.1 `:2403–2414`, 1.16.2/.3 `:2378–2389`, and 1.16.4/.5 `:2380–2391`. `Minecraft.player` is declared as `LocalPlayer` in each audited release (`Minecraft.java:269`, `:300`, `:298`, or `:307` by release group).

In 1.15.2 and 1.16/.1, the callback calls inherited five-argument `Entity.absMoveTo`, which also writes yaw and pitch. From 1.16.2 the callback calls the three-coordinate overload, which only supplies restored position; both overloads are present on `Entity` from that release onward (`Entity.java:1106` and `:1114` in 1.16.2/.3, `:1106` and `:1114` in .4/.5). The pending entry is removed by the exact `(BlockPos, Action)` key in every release. The response claim does not require proving how often an acknowledgement is produced or when a producer sends it.

## Conclusion

The correction predicate's final current-state identity comparison begins in 1.16.0. Position capture then narrows from `PosAndRot` to `Vec3`, and the restore call from position-plus-rotation to position-only, in 1.16.2. Both newer forms persist through 1.16.5. These are exact method-bound results only; they do not establish broader pair coverage or runtime parity.
