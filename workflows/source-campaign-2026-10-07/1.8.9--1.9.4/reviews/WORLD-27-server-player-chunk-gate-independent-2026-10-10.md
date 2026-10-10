# WORLD-27-SERVER independent snapshot review — 2026-10-10

- Decision: **revision required**.
- Reviewer: independent blind source reviewer `/root/review_world27`, 2026-10-10 Europe/Vienna.
- Finding: `../findings/WORLD-27-server-player-chunk-gate.md`.
- Author commit: `aeb260ff0b3bfa7d9c4c8a906648ca3dcf91e483`.
- Exact finding Git blob: `89b553b6aa32345a08935686b2b48e53e17d43a6`.
- SHA-256 of raw Git blob bytes: `d4f9cd88afbc7086976381dab8897916bacf16c4d3d744a68f17c9120daed7d6`.
- Endpoints: exact 1.8.9 and 1.9.4, aligned Ornithe Feather gen2 build 2.
- Implementation handoff: **blocked**, no accepted snapshot from this review.
- First changed release: unknown within `(1.8.9, 1.9.4]`; no intervening exact release reviewed.
- Pair discovery: partial; this is not a full-pair coverage audit or freeze.
- Runtime validation: not performed.

## Identity and isolation

Review started from the exact author commit on `fix/review-world27-2026-10-10`, isolated checkout `.task-worktrees/review-world27-2026-10-10`. Only this review file is changed. The reviewer read the author-tree AGENTS, README, discovery workflow and campaign protocol, the exact finding, its WORLD-27 client source-only dependency and relevant pair-run evidence references. No implementation, either wiki lane, mixed coordination report, main diff/merge, decompilation, cache write, build, test, gameplay, server, Docker or push was used. No worker process remains running. Git blob bytes were hashed through the binary `git cat-file` stream, independently of Windows checkout newline conversion.

All evidence paths below are relative to the repository root. Canonical source prefix is `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather/`. Each source hash listed below was recomputed from the current read-only source file and matched its row in the exact version's published `ornithe-feather.sources.sha256`. Source manifests, artifact manifests, readiness and provenance records were read and hashed; revised immutable mapped JARs and revision records were independently hashed. This bounded verification does not re-audit unrelated source files or prove equivalence to an unavailable original mapped JAR.

## Artifact binding

The finding's `WORLD-27-SERVER-A` / `WORLD-27-SERVER-B` identities use revision `feather-r1-2026-10-07`. Revised artifact prefix is `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/<version>/ornithe-feather/`.

| Record | 1.8.9 SHA-256 | 1.9.4 SHA-256 |
|---|---|---|
| Revised `client-ornithe-feather.jar` | `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` | `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3` |
| Revised `revision.json` | `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d` | `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915` |
| Original `ready/<version>/artifacts.sha256` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` |
| `ornithe-feather.sources.sha256` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| `ornithe-feather.ready.json` | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` |
| `ornithe-feather.provenance.json` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` |
| Raw `artifacts/<version>/client.jar` | `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` | `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` |
| Raw `artifacts/<version>/version.json` | `341af5f47a6b5bc137bfe681f4f3533e081041290aabbf9d7ebeae9881de768f` | `4b21379203f0d87df8cc2ab4dd3c3689f139772f1cdb37b40688252547e2d5be` |
| Raw `artifacts/yarn/feather-gen2-<version>+build.2-mergedv2.jar` | `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` | `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` |
| Raw corresponding `.tiny` | `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63` | `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027` |

Raw client, metadata and mapping bytes match the original artifact manifest rows. Readiness identifies requested/resolved releases 1.8.9 and 1.9.4 exactly. Revision records declare identical source trees and raw inputs, while the original mapped JARs are unavailable at their expected identities: A `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09`, B `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`. Revised-to-original equivalence remains **unverified**. No claim in this review relies on proving that equivalence.

## Verified source and conditional behavior

1. A `World.isChunkLoaded(BlockPos,boolean)` 175–176 rejects a position through `contains` 162–164 before the chunk provider. X=30,000,000 fails the exclusive positive bound. The one-argument overload 171–173 passes `allowEmpty=true`; inherited `isChunkLoadedAt` 226–228 calls `hasChunk`. A `ServerWorld` has no override of that member; `ServerChunkCache.hasChunk` 47–48 tests `chunksByPos` membership.
2. B `World.isChunkLoaded(BlockPos,boolean)` 186–187 omits that containment guard. Its overload 182–184 passes `true`; `ServerWorld.isChunkLoadedAt` 292–294 calls `ServerChunkCache.isChunkLoaded` 221–222, testing `chunks` membership. A loaded cache entry for chunk X=1,875,000, Z=0 is therefore a genuine external precondition for B admitting the candidate. This review does not establish ordinary loading of that chunk or emulate its producer.
3. Paired `World.getCollisions` A 891–945 / B 899–960 retain the fallback stone state only when the candidate is outside the **effective** border and `isWithinBorder` is true. With an actually effective maxX=30,000,000 and outside-border flag false, the stated start/target player X is inside the one-block margin. B's non-corner scan edge and interior Y rows admit relevant stone cells; the widened scan does not itself defeat the conditional witness.
4. Stone uses the full cube: A `Block` constructor bounds / `addCollisions` / `getCollisionShape`; B `Block.FULL_BLOCK_SHAPE`, `getShape`, `getCollisionShape`, `addCollision`; paired `StoneBlock` constructors and lack of collision override. Both default registration paths construct `StoneBlock`.
5. The inheritance chain `ServerPlayerEntity -> PlayerEntity -> LivingEntity -> Entity` is verified in both sources, with no intervening `move(DDD)` override. Paired ordinary `Entity.move` A 371–460 / B 441–533 query expanded shape, clip Y then X then Z, and require ground/downward clipping before stepping. For the hypothetical specified shape, positive X movement to the full cube is clipped to approximately 0.3 rather than 0.4; zero Y and airborne state exclude the step gate.
6. Packet reachability: A handler 190–338 rejects nonfinite input, checks End/riding/sleep/acknowledgement gates and an absolute X/Z target bound; target 29,999,999.8 passes. The `<0.25` check at 207 is a teleport-acknowledgement check, **not** the movement speed limit (303 uses `z - velocitySquared > 100`). B 367–466 requires no pending teleport and uses first/last-good positions plus packet count. Finite small displacement passes its speed gate. In A, `tickPlayer` 290 precedes reset to the stored start at 291; the exact witness must preserve the required ordinary state through that prefix.
7. With the effective-bound hypothesis, initial contracted query is empty, movement clips, and the handler then sets the requested position. The final shape's maxX is approximately 30,000,000.1; contraction by 0.0625 still leaves approximately 30,000,000.0375, strictly intersecting the cube. Residual squared error is approximately 0.01, below 0.0625. Thus B's `bl && (bl2 || !bl3)` branch sends correction to `d,e,f` at 464–466; A skips the out-of-range candidate. These are conditional source calculations, not runtime observations or exact decimal bit patterns. Entity position setters regenerate bounds using float width/height; a future exact witness must retain those casts instead of treating the decimal shape endpoints as literal vanilla dimensions.

The loaded-gate difference and the handler's conditional consumer path are retained as source evidence. They do not establish the assumed effective border or close this snapshot's reachability dependency.

## Concrete unresolved dependency: effective border bound

**DEP-WORLD-27-SERVER-EFFECTIVE-BORDER-CAP** — unresolved; blocks acceptance of the stated fallback/correction witness.

Paired `WorldBorder.java` is byte-identical. Field `maxSize` defaults to **29,999,984** at line 18. `getMinX` 61–68 and `getMinZ` 70–77 clamp the lower bounds against `-maxSize`; `getMaxX` 79–86 and `getMaxZ` 88–95 clamp the upper bounds against `maxSize`. Although `setSize(double)` 135–144 stores the requested diameter directly, it does not change this cap. The cap setter is `setMaxSize(int)` 165–167.

Both server constructors explicitly install the cap: A `ServerWorld` line 123 and B line 117 call `getWorldBorder().setMaxSize(server.getMaxWorldSize())`. Paired `MinecraftServer.getMaxWorldSize` A 1090–1092 / B 1088–1090 returns **29,999,984**. Available `IntegratedServer` sources do not override it. Paired `WorldBorderCommand` set/add paths A 43–100 / B 45–102 call `setSize`, not `setMaxSize`. A bounded search of both published `net/minecraft` source trees finds only the setter declaration, server constructor and `WorldBorderS2CPacket` apply call as `setMaxSize` occurrences; the packet application is not evidence of a server-side border-cap producer. A dedicated-server class is absent from these published client-JAR source trees, so this review makes no claim about an unavailable dedicated artifact's override/configuration path.

At the stated player X=29,999,999.4 with the installed cap, the effective maximum is 29,999,984. Even the expanded one-block margin ends at 29,999,985; `World.isWithinBorder` A 947–964 / B 963–980 is false. B `World.getCollisions` therefore executes the `!bl2` block-state lookup at 929–930 instead of retaining the fallback stone. A still skips the out-of-hard-limit candidate. B's block state then depends on the actual loaded chunk contents (`World.getBlockState` 621–627), and no fallback-stone correction follows from the finding's stated evidence. This is an omitted producer/guard dependency, not a denial of the separate loaded-gate change.

The finding assumes minX/maxX=-/+30,000,000 and describes an unusual border configuration, but its cited size setter does not establish those effective bounds. Do not infer an external cap override, a modified server, or an implementation workaround to fill that gap.

## Verified source hashes

Paths are under the canonical source prefix declared above. All rows matched both bytes and published manifest entries.

| Relative source path | 1.8.9 SHA-256 | 1.9.4 SHA-256 |
|---|---|---|
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| `net/minecraft/world/border/WorldBorder.java` | `1f0052bc10592684ea845aa36b99b4dc78d1f4368df21625349fdd887b561f95` | `1f0052bc10592684ea845aa36b99b4dc78d1f4368df21625349fdd887b561f95` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` | `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b` |
| `net/minecraft/server/world/ServerWorld.java` | `6de0ad8b28e72a7b77c01dbf149a71e1e118182ce1fda22883651d4f2616c7c7` | `69f2fd977001f5c392ea55550087048e884492542f46cc22f6bba797d4a4f88f` |
| `net/minecraft/server/world/chunk/ServerChunkCache.java` | `f1a1c89800430f53390d5a919c7991085935bb41ddd8f7d3a056265b89297cd5` | `cb53198eaff56342c5e747a44d66d4ed83f2ad574fde59266719a2d9479794a6` |
| `net/minecraft/server/MinecraftServer.java` | `08659c96cdee110d432fd2f928b626cc18aad318d7fb754eda90443c4a20a8c3` | `012afcd8953d96c81704176646ce821718a213fcec6cb12cc0cc88f3a5a04524` |
| `net/minecraft/server/integrated/IntegratedServer.java` | `7daf4901e8984f6eaccf36fa95e2b3b78aa701e911be28bea808f5ecdfc355fb` | `0595802676c68e946921d5ec3e4cc03a55ffafb48866df3ea0d5186e9100f5df` |
| `net/minecraft/server/command/WorldBorderCommand.java` | `a7549e4351d2f30ecc12dd1e743cc4e5e85c325b8418280460f3568dcc95e85e` | `9a734755c73d89bfd928f011f95c07f1d75e1e99b6c997b71a646ce5f9ea64be` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81` | `8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/util/math/Box.java` | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` | `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198` |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| `net/minecraft/block/StoneBlock.java` | `ca15180da4a946055afe68f8c6c578070fae700d13b50b4015272d772ddeed23` | `3a5fe9daed1a7cf39f2c15c14a2c9f8852a2ec9e195a3937ce5825293cc84967` |

## Required revision and next action

Return this dependency to the source owner. Preserve the proven paired chunk-gate difference, but revise the finding's reachability and scope disposition. Establish an eligible exact-source route to the effective border bound, including the `maxSize` producer and any unavailable version-matched artifact needed to verify it, or retain the fallback/correction scenario explicitly as an unestablished hypothetical and withhold source-confirmed historical-player applicability for that witness. Keep external loaded membership separate from the missing border-cap producer. Record the acknowledgement threshold correctly and use float-derived dimensions/approximate explanatory decimals in a revised witness. Bind any changed finding to a new author commit/blob/raw SHA and obtain fresh independent review.

Even if that dependency is resolved, the first changed release remains unknown and implementation dispatch remains blocked until the required exact-release boundary is established. Health/food production, attack/damage resolution, world/chunk loading emulation and non-player/vehicle physics remain outside this bounded player-collision consumer review. The related client WORLD-27 report shares the effective-border assumption; route the cap dependency there for owner verification without treating this server review as acceptance or rejection of its separate immutable snapshot. Full-pair inventories, broad provider coverage and independent coverage audit remain open.

Static handoff checks: only this assigned report is staged/committed; original finding blob remains unchanged; whitespace check passes. No default-branch merge is performed because the source-blind assignment explicitly forbids whole-main import. This report is the durable review checkpoint; source-cache ownership remains unchanged.
