# WORLD-27-SERVER: loaded-chunk gate changes server player collision correction

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: `WORLD-27-SERVER-CHUNK-GATE`, `WORLD-27`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

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

Both `World.getCollisions` bodies retain the initial stone state when the candidate block is outside the configured world border and the player is within the one-block border margin. The paired border source stores a configured size directly, and the default stone collision provider is a full cube; see the paired source evidence in `WORLD-27-client-limit-collision-fallback.md`. The candidate lies on a non-corner scan edge that passes B's `q != 2` guard.

## Reachability and dependencies

The server player route is exact on both sides: `ServerPlayNetworkHandler.handlePlayerMove` has an awake, non-riding packet branch; it queries `serverWorld.getCollisions` for the contracted player shape, calls `player.move` for the packet displacement, sets the requested position, and then queries collisions again. A uses lines 310-315 and 332-338; B uses lines 431-439 and 460-466. `ServerPlayerEntity` extends `PlayerEntity`, which extends `LivingEntity`, which extends `Entity`; none of the intervening classes declares `move`, so the call resolves to the paired `Entity.move(DDD)V` (A line 371, B line 441). The class/source hashes are A `ServerPlayerEntity.java` `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81`, `PlayerEntity.java` `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`, and `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; B `ServerPlayerEntity.java` `8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd`, `PlayerEntity.java` `d658a0d95452d12bb7e347bfd802240eeeecaf7f938e10dcd43e2640434387f85`, and `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`. When the initial query is empty but B's final query sees the fallback box, the existing `bl && (bl2 || !bl3)` guard sends a teleport correction to the pre-move position. In A the out-of-bound candidate is skipped, so the same queries stay empty and that correction guard does not fire.

State input: server chunk-cache loaded membership -> `ServerWorld.isChunkLoadedAt` -> `World.getCollisions` candidate-cell gate. The loaded state is supplied by the server chunk provider; this finding does not emulate or claim a change in chunk loading/unloading. The configured world-border bound and player packet position are inputs. Reachability proceeds through server player movement and the collision response, not through a client-only inference.

## Consequence and uncertainty

Conditional source-level witness: configure the world border to `minX=-30,000,000`, `maxX=30,000,000`; require the server provider to report candidate cell X=30,000,000 loaded; start an awake, non-riding, non-`noClip` player at X=29,999,999.4 with shape X `[29,999,999.1,29,999,999.7]`, Y `[10,11.8]`, Z `[0.2,0.8]`, `onGround=false`, and the collision-relevant outside-border flag false. Assume the accepted-movement baselines match that starting position (A's teleport acknowledgement is settled; B has no pending requested teleport and its first/last-good positions match). Submit a position packet to X=29,999,999.8 with no Y/Z displacement and `onGround=false`. Assume no other collision box changes the query. A's requested delta has squared length `0.16 < 0.25`, passes the movement limit, and A rejects the candidate through `World.contains`; movement reaches the requested X and the post-query is empty. B's delta passes its packet-rate speed gate; B admits the cell and `Entity.move` clips positive X at 0.3 against the fallback full cube. After the handler sets the requested position, the post-query finds the fallback collision, while the 0.1 residual has squared length `0.01 < 0.0625` and does not independently set `bl2`. The handler's `bl && (bl2 || !bl3)` gate therefore teleports the server player to the pre-move location. The packet target is below A's absolute 30,000,000 limit. This is a source-level conditional consequence, not an observed trajectory.

The required loaded chunk beyond the hard horizontal world limit is an external condition; this finding does not claim that ordinary server chunk loading produces it. The world-border configuration is also unusual and not the default. The source proves the gate and player response only under these stated inputs. The original derived mapped JAR equivalence remains unverified, while the cited source files match the published source manifests.

## Handoff

Independent delta: server player movement at the intersection of the configured world-border maximum and A's exclusive horizontal coordinate bound, when B's server chunk provider reports the candidate loaded. Related finding: `WORLD-27` client world-limit fallback, which remains a separate client path. Applicability is conditional and the first changed release is unknown within this endpoint interval. Implementation and runtime validation are deferred.
