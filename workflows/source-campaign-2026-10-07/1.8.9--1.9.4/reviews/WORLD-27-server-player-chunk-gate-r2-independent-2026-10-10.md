# WORLD-27-SERVER revision 2 independent review — 2026-10-10

- Reviewer: independent blind source reviewer `/root/review_world27`, 2026-10-10 Europe/Vienna.
- Revised candidate: `SNAP-WORLD-27-SERVER-02`.
- Documentation verdict: **accepted as accurate partial source documentation**.
- Eligible finding-snapshot verdict: **not accepted for implementation handoff**; applicability/dependency closure remains unresolved. This documentation decision is not an accepted eligible movement snapshot.
- Implementation handoff: **blocked** by the effective-cap/reachability dependency and unknown first changed release.
- Pair discovery: partial; no full-pair freeze or coverage-audit acceptance.
- Runtime validation: not performed.

## Exact immutable review input

The finding input is author commit `dc90ce4356878099c872fe52a05d493aafc365a2`, path `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-27-server-player-chunk-gate.md`, Git blob `cc566e5dc5fa1b95d05ebc71b706c92305d65b90`, raw Git-blob SHA-256 `56580a7ceb6e4b1f58b4babeb9d43658f1ae4a009e6556fbc8d6996187706b31`. Blob identity and raw binary-stream SHA were independently verified. The source-only ledger checkpoint is `20b697dee1a71a7d3fd7aa608b2425830c3a1103`, pair `run.md` blob `e145b960ca6e20ae8a81a7c3f50eb4a722f22def`, independently verified by Git. Relevant ledger excerpts preserve the blocked server slice and distinguish the closed immediate gate/dispatch dependencies from the open effective-cap witness.

This append-only review continues the same isolated checkout `.task-worktrees/review-world27-2026-10-10`, branch `fix/review-world27-2026-10-10`, from clean HEAD `0fab496bfde7f9cea9307ee9b3739787ac5f801e`. The original review [WORLD-27-server-player-chunk-gate-independent-2026-10-10.md](WORLD-27-server-player-chunk-gate-independent-2026-10-10.md) remains unchanged. Revised input was read with `git show`, without checkout/import of the author tree, main merge/diff, implementation or wiki access. Only this new review is writable. No cache write, decompilation, build, test, runtime, server, Docker or push occurred; no owned worker process remains.

## Artifact/evidence binding

Exact endpoints remain 1.8.9 and 1.9.4 in Ornithe Feather gen2 build 2. Canonical source prefix is `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather/`, read-only. Revised artifact identity remains `WORLD-27-SERVER-A/B`, revision `feather-r1-2026-10-07`; unchanged full artifact/raw-input verification and hashes are recorded in the original independent review linked above. That independent verification is retained; this re-review did not need to rehash unrelated publication bytes.

| Record | A 1.8.9 SHA-256 | B 1.9.4 SHA-256 |
|---|---|---|
| Source manifest `ready/<version>/ornithe-feather.sources.sha256`, recomputed again | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| Original artifact manifest `ready/<version>/artifacts.sha256`, recomputed again | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` |
| Revised immutable mapped JAR identity, retained prior independent verification | `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` | `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3` |
| Revision manifest identity, retained prior independent verification | `95e2dc4aa3edba2d287f2bab092c61c0f66874f790af1b8c5d98196c980e105d` | `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915` |
| New `net/minecraft/network/packet/s2c/play/WorldBorderS2CPacket.java`, recomputed and source-manifest matched | `b454249ddfd8350ce71be7a2f4699557ae0ce87af82e18053dc7602e336bbdf6` | `b454249ddfd8350ce71be7a2f4699557ae0ce87af82e18053dc7602e336bbdf6` |
| New `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, recomputed and source-manifest matched | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` |

The twelve reused source-file pairs were rehashed and matched the unchanged source-manifest entries: World, ServerWorld, ServerChunkCache, ServerPlayNetworkHandler, WorldBorder, MinecraftServer, IntegratedServer, WorldBorderCommand, Entity, LivingEntity, PlayerEntity and ServerPlayerEntity. Their exact per-file SHA values remain those in the original review and revised finding. Original mapped JAR availability/equivalence remains unverified at the original expected identities; no new equivalence claim is made.

## Bounded revised-claim verification

- **Gate difference preserved accurately.** A `World.isChunkLoaded(BlockPos,boolean)` 175–176 first uses `contains` 162–164; B 186–187 omits it. At X=30,000,000, Y=64, Z=0, A rejects before provider lookup. B delegates through `ServerWorld.isChunkLoadedAt` 292–294 to cache membership 221–222. Both collision-scan one-argument overloads pass `allowEmpty=true`; A inherits its base provider 226–228, using cache `hasChunk` 47–48. B admission remains conditional on the external loaded entry. This is a method-gate difference, not an established reachable player collision response.
- **Dispatch closure is limited correctly.** Paired `handlePlayerMove` A 310–338 / B 431–466 reaches World collision checks and inherited `Entity.move`. The revision explicitly calls fallback/correction reachability an unestablished hypothetical. It no longer equates that call route with a proved historical-player consequence. The revised acknowledgement explanation correctly distinguishes A line 207's `<0.25` check from line 303's speed gate. Float-derived dimensions and explanatory approximate coordinates replace the former literal-decimal geometry claim.
- **Cap producer closure is accurate within available sources.** Identical `WorldBorder` field 18 defaults to 29999984; bounds 61–95 clamp against it; size/lerp setters 135–152 do not raise it; cap setter is 165–167. ServerWorld A 123 / B 117 installs `MinecraftServer.getMaxWorldSize()`, A 1090–1092 / B 1088–1090 returning 29999984. Available IntegratedServer has no override. WorldBorderCommand set/add paths and A 171–173 / B 174–176 world accessor operate on the server border without changing its cap.
- **Packet caller closure is accurate.** Repeating the paired `net/minecraft` census for `setMaxSize|getMaxWorldSize` finds the named declarations, constructor and packet application. WorldBorderS2CPacket `handle(ClientPlayPacketHandler)` 103–105 dispatches to the client handler; `apply` INITIALIZE 124–134 writes maxSize at 132. ClientPlayNetworkHandler A 1181–1184 / B 1224–1227 applies it to the client world's border. This synchronization consumer does not establish a server override. No dedicated-server class was found in either published client-JAR source tree; absence here does not prove absence from an unavailable dedicated artifact.
- **Withheld applicability is justified.** With cap 29999984, old start X=29999999.4 lies beyond even the expanded border maximum 29999985. Paired `isWithinBorder` A 947–964 / B 963–980 is false. B's `!bl2` branch 929–930 reads actual chunk contents instead of keeping fallback stone. The revised finding asserts neither a replacement actual-block witness nor an external cap override. It preserves uncertainty rather than inferring one.

The revision therefore addresses the prior documentation overclaim. It does **not** resolve `DEP-WORLD-27-SERVER-EFFECTIVE-BORDER-CAP`: only the bounded available-source writer/caller census is closed. The eligible fallback witness remains open. External server loaded membership is a separate unestablished precondition; first changed release remains unknown within `(1.8.9,1.9.4]`. These conditions prevent acceptance under the campaign's eligible finding-to-implementation gate.

## Next action and checkpoint

Record this as verification of the reduced partial documentation, without publishing an accepted eligible movement snapshot or dispatching implementation. Keep the cap dependency open unless an eligible exact-source effective-bound route is established; investigate an exact paired dedicated-server artifact only if that source-only route is pursued. Do not infer a configuration override from its current absence. Route the shared client cap premise to its source owner independently. Continue broader pair discovery according to its own queue; this review neither closes nor audits those inventories.

If applicability evidence changes, bind the changed finding to a new immutable commit/blob/raw SHA and obtain fresh independent review; resolve the exact-release implementation boundary separately before dispatch. Prior revision-required history remains intact. Static checks verify only this new report changed, whitespace passes and the original review remains byte-identical. No default-branch merge is permitted by this source-blind assignment. Stop after committing this checkpoint.
