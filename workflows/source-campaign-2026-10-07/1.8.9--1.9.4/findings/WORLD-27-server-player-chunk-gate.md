# WORLD-27-SERVER: loaded-chunk gate difference; fallback correction reachability unestablished

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: `WORLD-27-SERVER-CHUNK-GATE`, `WORLD-27`
- Classification: changed behavior
- Confidence: source-confirmed gate difference; fallback/correction witness unestablished
- Applicability: historical player applicability withheld for the fallback/correction witness
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed
- Snapshot status: revised candidate `SNAP-WORLD-27-SERVER-02`; fresh independent review required; implementation handoff blocked

## Paired evidence

### A artifact identity

- Evidence artifact record ID: `WORLD-27-SERVER-A` in the pair run's Artifact evidence identities.
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.8.9/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.8.9/ornithe-feather/revision.json` / `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.8.9/artifacts.sha256` / `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`
- Original derived-artifact availability and expected hash: unavailable; recorded expected SHA-256 `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09`.
- Source/raw-input hash relation and verification reference: source tree and raw inputs are recorded identical in `revision.json`; source manifest `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; the cited source-file rows match.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable.
- Provenance limitations: this finding is based on the verified, hash-matched decompiled source files. It makes no claim that the revised mapped JAR is byte-equivalent to the unavailable original.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`; `net.minecraft.server.network.handler.ServerPlayNetworkHandler#handlePlayerMove(PlayerMoveC2SPacket)V`, lines 190-338; SHA-256 `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b`. Gate/provider paths: `net/minecraft/world/World.java` `World.isChunkLoaded(BlockPos,boolean)` lines 175-176 and inherited `World.isChunkLoadedAt(int,int,boolean)` lines 226-228, SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`; `net/minecraft/server/world/chunk/ServerChunkCache.java` `ServerChunkCache.hasChunk(int,int)` line 47, SHA-256 `f1a1c89800430f53390d5a919c7991085935bb41ddd8f7d3a056265b89297cd5`.

### B artifact identity

- Evidence artifact record ID: `WORLD-27-SERVER-B` in the pair run's Artifact evidence identities.
- Publication status: revised-derived
- Revision ID: `feather-r1-2026-10-07`
- Immutable evidence path: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.9.4/ornithe-feather/client-ornithe-feather.jar`
- Evidence artifact SHA-256: `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.9.4/ornithe-feather/revision.json` / `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.9.4/artifacts.sha256` / `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`
- Original derived-artifact availability and expected hash: unavailable; recorded expected SHA-256 `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`.
- Source/raw-input hash relation and verification reference: source tree and raw inputs are recorded identical in `revision.json`; source manifest `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather.sources.sha256` SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; the cited source-file rows match.
- Revised-to-original derived-artifact equivalence and evidence reference: unverified; the original mapped JAR is unavailable.
- Provenance limitations: this finding is based on the verified, hash-matched decompiled source files. It makes no claim that the revised mapped JAR is byte-equivalent to the unavailable original.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash: `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`; `net.minecraft.server.network.handler.ServerPlayNetworkHandler#handlePlayerMove(PlayerMoveC2SPacket)V`, lines 367-466; SHA-256 `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b`. Gate/provider paths: `net/minecraft/world/World.java` `World.isChunkLoaded(BlockPos,boolean)` lines 186-187 and abstract `World.isChunkLoadedAt(int,int,boolean)` line 237, SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`; `net/minecraft/server/world/ServerWorld.java` `ServerWorld.isChunkLoadedAt(int,int,boolean)` lines 292-294, SHA-256 `69f2fd977001f5c392ea55550087048e884492542f46cc22f6bba797d4a4f88f`; `net/minecraft/server/world/chunk/ServerChunkCache.java` `ServerChunkCache.isChunkLoaded(int,int)` line 221, SHA-256 `cb53198eaff56342c5e747a44d66d4ed83f2ad574fde59266719a2d9479794a6`.

For both sides, `World.getCollisions` is the paired block-state gate and collision-list consumer: A lines 891-945, SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`; B lines 899-960, SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`. The handler's player call to `move` reaches the paired `Entity.move` implementations; A SHA-256 `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`, B SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.

## Source-level difference

A's `World.isChunkLoaded(BlockPos,boolean)` first rejects positions outside `World.contains`, including X/Z outside `[-30,000,000,30,000,000)`. B removes that containment check. A `ServerWorld` inherits `World.isChunkLoadedAt`, which checks `chunkSource.hasChunk`; B `ServerWorld.isChunkLoadedAt` delegates to `ServerChunkCache.isChunkLoaded`. The collision scan reaches these methods through the one-argument overload at `(x,64,z)`, which passes `allowEmpty=true`. A's server cache tests loaded-position membership in `chunksByPos`; B's tests membership in `chunks`. Actual membership is an external provider input. Under the finding's loaded-cell precondition, A rejects X=30,000,000 before consulting it, while B admits the cell.

Both `World.getCollisions` bodies retain the initial stone state only when the candidate is outside the **effective** border and the player is within its one-block margin. The default full-cube provider and B's non-corner scan guard remain source evidence, but `setSize` alone cannot establish effective bounds of +/-30,000,000. The cap producer dependency below prevents the old fallback witness from proving historical-player applicability.

## Reachability and dependencies

The server player route is exact on both sides: `ServerPlayNetworkHandler.handlePlayerMove` has an awake, non-riding packet branch; it queries `serverWorld.getCollisions` for the contracted player shape, calls `player.move` for the packet displacement, sets the requested position, and then queries collisions again. A uses lines 310-315 and 332-338; B uses lines 431-439 and 460-466. `ServerPlayerEntity` extends `PlayerEntity`, which extends `LivingEntity`, which extends `Entity`; none of the intervening classes declares `move`, so the call resolves to the paired `Entity.move(DDD)V` (A line 371, B line 441). The class/source hashes are A `ServerPlayerEntity.java` `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81`, `PlayerEntity.java` `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`, and `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; B `ServerPlayerEntity.java` `8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd`, `PlayerEntity.java` `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`, and `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. As an unestablished hypothetical only, when the initial query is empty but B's final query sees the fallback box, the existing `bl && (bl2 || !bl3)` guard sends a teleport correction to the pre-move position. In A the out-of-bound candidate is skipped, so the same queries stay empty and that correction guard does not fire.

State input: server chunk-cache loaded membership -> `ServerWorld.isChunkLoadedAt` -> `World.getCollisions` candidate-cell gate. The loaded state is supplied by the server chunk provider; this finding does not emulate or claim a change in chunk loading/unloading. The player packet position is an input; the effective border bound must be derived from its cap writer, not assumed from diameter. The handler-to-collision call route is proven; the stated fallback response reachability is not.

## Effective-border cap producer and disposition

`DEP-WORLD-27-SERVER-EFFECTIVE-BORDER-CAP`: **open for an eligible fallback witness**. Its bounded available-source writer/caller census is finished, with no supporting route found.

All paths here are relative to `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather/`. Both `WorldBorder` sources are byte-identical: field `maxSize=29999984` at line 18; `getMinX/getMinZ/getMaxX/getMaxZ` at 61-95 clamp against that field; `setSize(double)` at 135-144 and timed overload at 146-152 write size/lerp state, not the cap; `setMaxSize(int)` at 165-167 is the cap setter. Paired `ServerWorld` constructors call `getWorldBorder().setMaxSize(server.getMaxWorldSize())` at A 123 / B 117. `MinecraftServer.getMaxWorldSize()` returns 29999984 at A 1090-1092 / B 1088-1090. Neither available `IntegratedServer` overrides it. Paired `WorldBorderCommand` set/add branches at A 43-100 / B 45-102 call `setSize`; their border accessor uses `server.worlds[0]` at A 171-173 / B 174-176. These command paths cannot raise the cap.

A full paired `net/minecraft` search for `setMaxSize|getMaxWorldSize` finds only the declarations, server constructor call, and `WorldBorderS2CPacket.apply` INITIALIZE branch at 124-134 (cap assignment at 132). The packet's `handle(ClientPlayPacketHandler)` at 103-105 dispatches to the client handler; paired `ClientPlayNetworkHandler.handleWorldBorder` A 1181-1184 / B 1224-1227 applies it to `this.world.getWorldBorder()`. This is a client synchronization consumer, not a server cap writer. No dedicated-server class exists in these published client-JAR trees. An exact-version dedicated-server artifact would be needed to assess any dedicated override/configuration path; no such path, external override, modified server, or cap above 29999984 is inferred here.

With the installed cap and old start X=29,999,999.4, `World.isWithinBorder` A 947-964 / B 963-980 returns false: the expanded positive margin is at most 29,999,985. Thus B `World.getCollisions` 929-930 reads the loaded chunk's actual block state through the `!bl2` branch instead of retaining fallback stone (A analogous branch 917-918 is behind its rejected candidate gate). The candidate chunk contents and a reachable loading route are not established by this finding. The cap therefore defeats the original fallback/correction proof; it does not erase the independent gate difference. World/chunk loading production is outside this bounded movement-consumer comparison. No replacement actual-block collision witness is asserted.

### New paired source hashes

Each hash was recomputed from read-only bytes and matched its published source-manifest row. Earlier World/handler/cache/Entity identities above remain unchanged.

| Relative path | A SHA-256 | B SHA-256 |
|---|---|---|
| `net/minecraft/world/border/WorldBorder.java` | `1f0052bc10592684ea845aa36b99b4dc78d1f4368df21625349fdd887b561f95` | `1f0052bc10592684ea845aa36b99b4dc78d1f4368df21625349fdd887b561f95` |
| `net/minecraft/server/world/ServerWorld.java` | `6de0ad8b28e72a7b77c01dbf149a71e1e118182ce1fda22883651d4f2616c7c7` | `69f2fd977001f5c392ea55550087048e884492542f46cc22f6bba797d4a4f88f` |
| `net/minecraft/server/MinecraftServer.java` | `08659c96cdee110d432fd2f928b626cc18aad318d7fb754eda90443c4a20a8c3` | `012afcd8953d96c81704176646ce821718a213fcec6cb12cc0cc88f3a5a04524` |
| `net/minecraft/server/integrated/IntegratedServer.java` | `7daf4901e8984f6eaccf36fa95e2b3b78aa701e911be28bea808f5ecdfc355fb` | `0595802676c68e946921d5ec3e4cc03a55ffafb48866df3ea0d5186e9100f5df` |
| `net/minecraft/server/command/WorldBorderCommand.java` | `a7549e4351d2f30ecc12dd1e743cc4e5e85c325b8418280460f3568dcc95e85e` | `9a734755c73d89bfd928f011f95c07f1d75e1e99b6c997b71a646ce5f9ea64be` |
| `net/minecraft/network/packet/s2c/play/WorldBorderS2CPacket.java` | `b454249ddfd8350ce71be7a2f4699557ae0ce87af82e18053dc7602e336bbdf6` | `b454249ddfd8350ce71be7a2f4699557ae0ce87af82e18053dc7602e336bbdf6` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` |

## Consequence and uncertainty

Retained **unestablished hypothetical**, superseding the old claimed source witness: if an eligible source route were to establish effective bounds +/-30,000,000, the candidate chunk were loaded, and no other collision intervened, an awake non-riding non-noClip airborne player starting at X=29,999,999.4 and requesting X=29,999,999.8 could reach the stated fallback and correction consumer. Shapes must come from vanilla position setters: paired `Entity` default width/height are `0.6F`/`1.8F` (A 143-144 / B 167-168), and position-bound reconstruction uses float half-width and height (A 230-231 / B 281-282). X bounds near [29,999,999.1,29,999,999.7], Y near [10,11.8] and Z near [0.2,0.8] are explanatory approximations, not literal exact vanilla endpoints. Under those hypothetical bounds the positive X clip is approximately 0.3 and post-query residual approximately 0.1, squared approximately 0.01 < 0.0625. No tick trajectory or runtime result is claimed.

A's `<0.25` comparison at handler line 207 is the teleport-acknowledgement gate, not the movement speed limit. The movement gate at 303 compares displacement squared minus velocity squared against 100. The old packet delta is approximately 0.4, so it is small under the ordinary speed gate; accepted acknowledgement and A's `tickPlayer` prefix at 290/reset at 291 must still preserve ordinary state. B requires settled teleport/baselines and passes its packet-count speed gate for this small displacement. These handler facts do not establish the missing effective border.

The original snapshot's world-border-size premise was insufficient. Source-confirmed historical-player applicability of its fallback/correction consequence is withheld. Loaded membership is a separate external precondition and does not close the cap dependency. Original derived mapped-JAR equivalence remains unverified; this revision uses the hash-matched decompiled source files only.

## Handoff and supersession

- Superseded original candidate: author commit `aeb260ff0b3bfa7d9c4c8a906648ca3dcf91e483`, finding Git blob `89b553b6aa32345a08935686b2b48e53e17d43a6`, raw Git-blob SHA-256 `d4f9cd88afbc7086976381dab8897916bacf16c4d3d744a68f17c9120daed7d6`. The original evidence and claim remain available at that immutable commit.
- Prior independent verdict retained: **revision required**, review commit `0fab496bfde7f9cea9307ee9b3739787ac5f801e`, path `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/reviews/WORLD-27-server-player-chunk-gate-independent-2026-10-10.md`. No acceptance is implied by this revision.
- Replacement candidate ID: `SNAP-WORLD-27-SERVER-02`, 2026-10-10 Europe/Vienna. Gate difference preserved; effective-cap witness open; fresh independent review required. Implementation blocked separately by unaccepted applicability and unknown first changed release within (1.8.9,1.9.4].
- Next action: independently review this reduced claim and disposition; obtain exact paired dedicated-server evidence only if a source-supported cap route is to be investigated. Route the shared cap dependency to the client WORLD-27 owner; this server revision changes no client finding snapshot or its decision.
- Pair discovery partial; full inventories, full-pair freeze and independent coverage audit remain open. Runtime validation not performed.
