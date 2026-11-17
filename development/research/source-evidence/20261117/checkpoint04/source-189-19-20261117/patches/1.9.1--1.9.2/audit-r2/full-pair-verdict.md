# Independent full-pair source coverage audit — 1.9.1 → 1.9.2

**Verdict: ACCEPT — complete full-pair source coverage; no in-scope movement difference found.**

## Bound inputs

| Input | SHA-256 |
|---|---|
| `coverage.md` (ordered r1 coverage) | `645AC96B881BFA148A9B17BB6708A2C0FA506F150BB009E47F903DBADBEE05C5` |
| `coverage-r2.md` (packet and save/load supplement) | `BC5E124E8522EA86A68589728C523E160E76FDCC53E8A3AD13CCAED193B57919` |
| `audit-r2/verdict.md` (bounded supplement acceptance) | `DF9B4D3BA22AF758B7141531A8CAB78B3AEB5B39568371BE24E6F0F4B9662701` |
| 1.9.1 Feather provenance/source manifest | `E9FB0DA6511597E2DE953A143F9570B82243E5B9504603686E557F06271DA64A` |
| 1.9.2 Feather provenance/source manifest | `9928B467DEB35C7E7F891B4FEE1627A68FEECDF233C2076909FD4834B0380510` |

The manifest inputs identify 1.9.1 Feather Gen2 `1.9.1+build.2` (artifact SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`) and 1.9.2 Feather Gen2 `1.9.2+build.2` (artifact SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`). The decompiled source trees were treated as read-only. I verified the manifest SHA-256 values and, independently, every listed Java source hash and exact source path inventory on disk: each side has 1,819 manifest rows and 1,819 Java files, with no missing, extra, or hash-mismatched path. The inventories have identical path sets. Hash comparison yields 21 changed source files.

## Full tick and dependency retrace

- **Client dispatch and input:** `client/entity/living/player/LocalClientPlayerEntity.java` gates on the loaded chunk, calls its superclass tick and selects the mounted packet branch or `sendMovementToServer`; `ClientPlayerEntity.java`, `Input.java`, and `KeyboardInput.java` cover player input, camera/local-player packet selection, and movement actions. All four files have identical source hashes across the pair. The client version/protocol edits in `Minecraft.java` do not change client tick scheduling or input dispatch.
- **Player/living tick and state:** `entity/living/player/PlayerEntity.java` supplies player state, predicates, jump and direct player responses; `entity/living/LivingEntity.java` contains the living tick, jump, fall handling, relative movement, and travel branches. `LivingEntity.java` is byte-identical. A full line-delta review of `PlayerEntity.java` finds only the saved `DataVersion` literal changing 175→176. No player tick, dimension, jump, equipment/effect predicate, or collision-response body changes.
- **Entity movement and collision state:** `entity/Entity.java` supplies base tick and movement state, movement resolution/step handling, velocity, liquid/ground/collision predicates, shape/position updates, and entity push/riding state. `util/math/Box.java` supplies the bounds operations used by these paths. Both sources are byte-identical across the pair.
- **World and block/fluid providers:** `world/World.java` supplies collision queries, liquid drag/material queries, and state lookups; `block/state/BlockState.java`, `block/Block.java`, `block/Blocks.java`, and `block/FlowingLiquidBlock.java` cover dispatch, provider registration, shapes, and liquid behavior. These sources are byte-identical. The complete 21-file source delta contains no block, block-state, world, entity, or provider source. Direct block/fluid contact callbacks and player responses consequently have no changed source dependency in this pair. Previously frozen pane/piston slices remain excluded and are not reopened by this audit.
- **Packet-originated passenger attachment:** `network/PacketByteBuf.java` changes no-argument `readIntArray()` from a `readableBytes()/4` maximum length to `readableBytes()`; the decoder error label also changes from “IntArray” to “VarIntArray.” A source-wide search finds this is the sole no-argument call, in `network/packet/s2c/play/EntityPassengersS2CPacket.java`, whose body is identical. Identical `client/network/handler/ClientPlayNetworkHandler.java` processes passenger IDs with `startRiding(entity, true)`, including the local player. The packet delta can therefore change passenger attachment and select `LocalClientPlayerEntity.tick`'s mounted input/send path. That resulting mounted/vehicle path is outside the project movement scope; it does not change the on-foot local-player travel procedure or introduce an in-scope response. This closes the prior r1 packet omission and agrees with the bounded r2 acceptance.
- **Save/load and data-version edge:** 1.9.2 changes `PlayerEntity.writeCustomNbt`'s `DataVersion` to 176, `DataFixes.createDataFixerUpper`'s current version to 176, and `DataFixerUpper.run`'s threshold to 176. It also updates `WorldData` version metadata, the data-version tag written by `AnvilChunkStorage`, and `WorldSaveInfo.isOutdated`'s threshold. The complete source delta shows these are version literals/thresholds, with no new data-fix registration or changed schema body. The player schema runs list migrations only for `Inventory` and `EnderItems`; `Entity.readNbt`/`writeNbt` continue to read/write `Pos`, `Motion`, and `Rotation` unchanged. The 175→176 change can run the existing fixer path for a 175-tagged save, but adds no movement-state transformation and changes no fixed-profile tick path. `PlayerDataStorage.java` is identical across the pair.
- **Other changed sources:** The remaining changed lines are version display strings, protocol 108→109 in login/status handshakes and protocol compatibility labels, and server/world version metadata. They do not alter the local player movement tick, collision providers, or any client-side direct player response. No server-side physics is counted as player movement coverage.

## Exact source hash anchors

The manifest hashes above bind the complete source inventories. Key paired source-file SHA-256 values independently checked during this retrace:

| Source | 1.9.1 SHA-256 | 1.9.2 SHA-256 |
|---|---|---|
| `LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | same |
| `ClientPlayerEntity.java` | `36cbc6f0e7e2d47f4207d15130976860eac60b4ae6fac2ef2e8f5a9f719370d7` | same |
| `Input.java` | `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009` | same |
| `KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | same |
| `LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | same |
| `Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | same |
| `World.java` | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | same |
| `Box.java` | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` | same |
| `Block.java` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` | same |
| `BlockState.java` | `9168e488472aa91bf30b01c4a6dce912cbd5ee312cfda65a89f753748b199476` | same |
| `Blocks.java` | `ae6e28482364703673c89e33d885ef30d7c7421ab5a0e0a0c2d4e656600bb352` | same |
| `FlowingLiquidBlock.java` | `66dce05969a86a163965a3e473e3e00b14e0da7dacffb4c75064c13605e3511b` | same |
| `EntityPassengersS2CPacket.java` | `c49ec4c182b096f73033bd12ea25d727fe9d68481beb219ea4387457ebaccb80` | same |
| `ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` | same |
| `PacketByteBuf.java` | `b12848a6453b72d8a916bcc028a396e3faff72bd2cb0a6e8dfab4e24429bc472` | `4511dafe34b6e7628920422025fda4beeec29daa7ab466b3a5638e5a5c73d696` |
| `PlayerEntity.java` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` |
| `DataFixes.java` | `6e9b9b36768dc5a3793bbf89831bca487f4e5c3a429e8848803162b5c6eda34d` | `54c93c81ab1d8b304d99d0226ab0e93e95ea258b95ec0ca39950cb9dd6b5f008` |
| `DataFixerUpper.java` | `89509823a5743c1b026f3400e6e53bb771249a13438362ff5e3c46c00eb181b2` | `663bfb40e48d6ff7f3967fea9eb8f36cefa00cd26f6be334b1dc66587f75b75f` |
| `PlayerDataStorage.java` | `b9e2dc13df3d5b9e6492fff89e6693cfe8b70d59e8c57fcf35c2574dfe5938e7` | same |

The remaining version-metadata/chunk-save source deltas are bound transitively by both complete manifest hashes and were line-delta reviewed; their path inventory is the 21-file delta summarized above. No source path was partial or unexamined in the in-scope movement graph. The explicit pane/piston exclusion is retained as stated.

## Closure

The independently retraced input→tick→travel→entity move/collision/provider/callback graph is complete, its paired dependencies are closed, and the r2 packet and save/load edges are reconciled. I find no source-confirmed 1.9.1→1.9.2 difference affecting in-scope local on-foot player movement. No residual full-pair source-coverage gate remains. The exact patch introduction boundary within this release interval remains unknown; implementation and runtime parity are outside this source audit.
