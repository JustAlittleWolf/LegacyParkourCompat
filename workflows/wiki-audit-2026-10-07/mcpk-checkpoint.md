# MCPK audit resumable checkpoint — 2026-10-08

**State: PARTIAL, cleanly committed.** This is a bounded MCPK wiki and integrity-consumer checkpoint, not a frozen full release-pair audit. No Minecraft Wiki pages, implementation sources, or other audit-track findings were consulted. No tests, builds, games, servers, or Docker actions were run.

## Closed in this checkpoint

- Inspected and recorded the MCPK pages listed in [mcpk-wiki.md](mcpk-wiki.md); the wiki itself says its Version Differences page is no longer maintained and stops at 1.18.
- Published the exact Feather consumer evidence snapshot [mcpk-feather-r1-finding-snapshot.md](mcpk-feather-r1-finding-snapshot.md), keyed by revision `feather-r1-2026-10-07`.
- Consumer-verified all six immutable Feather JAR snapshots against their sidecars and `revision.json`; verified all 11,958 source files and 222 original artifact-manifest inputs other than the unavailable original derived JAR, with zero hash mismatches. The source owner reported that the independent operations audit passed.
- Recorded the Feather-backed findings and exact endpoint evidence in [mcpk-source-adjudication.md](mcpk-source-adjudication.md) and [mcpk-1.8.9-1.9.4.md](mcpk-1.8.9-1.9.4.md).
- Published bounded, revision-independent snapshots for 1.15 Soul Sand, 1.15 Honey, 1.17 Powder Snow, 1.17 Big Dripleaf, the 1.17 swimming-entry gate, 1.15 slipperiness sample examples, and the 1.16.1→1.16.2 sneak edge-backoff predicate. Four r2 snapshots are preserved; edge-backoff now has a corrected r3 successor.
- Preserved clean acceptance of edge-backoff r3 and the corrected slipperiness identity in commit `bdda49ac76ce8b6e136917349e8b59a61d2fabe4` (cherry-picked as `dfceacf` into this lane); Big Dripleaf and swimming remain accepted from their earlier clean review.
- The Big Dripleaf r2 snapshot records the 10-tick first-shape delay discrepancy and scopes `entityInside` to a player-relevant, not player-exclusive, path. The swimming r2 snapshot preserves first-entry versus continuation behavior and adds the unchanged flying-player override and tick call order. The slipperiness r2 snapshot corrects the exact-case `SoulsandBlock.java` path and records the coordinate-flooring sources.
- The original derived mapped JARs remain unavailable. Their equivalence to the revised snapshots is **UNPROVEN**; the old cache mismatch is not waived.

## Still open

- Independent reviewer acceptance of the exact `mcpk-feather-r1-finding-snapshot.md` and its dependencies. Until then, affected source/wiki pairs remain partial and no pair freeze is claimed.
- No acceptance is pending for the four named snapshots. Their original files remain immutable. Exact commit:path, Git blob, and content SHA-256 identities are recorded in [the re-review candidate register](mcpk-clean-rereview-candidates-2026-10-08.md) and the [independent follow-up](mcpk-independent-followup-2026-10-08.md).
- Continue the remaining MCPK claim checks listed under “Still unresolved” in [mcpk-wiki.md](mcpk-wiki.md) and the full inventory in [mcpk-full-catalog-2026-10-08.md](mcpk-full-catalog-2026-10-08.md): exact 1.11.1 wall-height interval; full 1.13 water math/flow and remaining shapes; 1.14 movement edge cases; Y=256 water-exit cause/fix boundary; player-affecting effects/enchantments; and 1.17 powder-snow tick timing.
- Bounded browser/source follow-ups are in [frozen r1](mcpk-independent-followup-2026-10-08.md), [corrected r2](mcpk-independent-followup-2026-10-08-r2.md), [expanded r3](mcpk-independent-followup-2026-10-08-r3.md), [expanded r4](mcpk-independent-followup-2026-10-08-r4.md), and [corrected r5](mcpk-independent-followup-2026-10-08-r5.md). The r4 re-review's two bounded requests are addressed in r5: the 1.13.2 jump method is `LivingEntity.mobTick()` with player/tick and water-depth producer edges; Blip revision 3476's 1.15+ fall-damage claim is explicitly routed as unverified and open. Prior exact filename and stuck-response citations remain preserved.
- Immutable identity for the frozen initial follow-up r1: `44e5177bd840ec8c5da91e39354f639485bd80a8:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08.md`, Git blob `a2628811a67b1f30af79ba803f838831a298e572`, content SHA-256 `7582fc092c79090211a4ce07cb2ac793aa252add8ae86acf6935bab78080760f`.
- Independent review requested two evidence corrections only; corrected successor r2 applies them without changing the bounded conclusions. Immutable r2 identity: `39ce7c1c2d0c86c963a1d770750f760e64e40469:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r2.md`, Git blob `17ed4dcb39df0a78649a3ad0776c64f4e5b09ec0`, raw SHA-256 `0d280dc278c076a6fbf78389bdc4bd36ae1b2e2cbb9b8e233bddc921d3747012`.
- Expanded r3 immutable identity: `25dc88a28570cf13f6723d5f3d1f248db2c41605:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r3.md`, Git blob `a0932897c6c3065e2c207665c4b63268f67de172`, raw SHA-256 `090b2b19b46fac732d0e2c5577302ef3fca743ee20b676fdc9855ee5524d2f38`.
- Expanded r4 immutable identity: `20894469259abb7761cab1356882cf5e113814da:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r4.md`, Git blob `e8b5b4151a3e032258837933fdb591db128bafe6`, raw SHA-256 `8c169122cfd57cb44a6100c9daa6ec74125313112b362c015e13a6a845a885bc`.
- Corrected r5 immutable identity: `fe37af704b2213ec58fcdd049af1fadfae016ee0:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r5.md`, Git blob `b5d6bd29e4a2e919c0c67f888728d862dfd8bcd5`, raw SHA-256 `c126e2be6e79b73fe3ae60b5e1093e11a787a68bde2e9c4cd54bbf18e0a9948d`.
- Do not treat the wiki's TODO-only 1.18 section or its lack of later sections as evidence for 1.19+ or 26.x behavior.
- Fresh normal-browser page revision IDs and source endpoint comparisons are in r1–r5 and the full catalog. The Y=256 consumer is the player-reachable 1.13.2 `LivingEntity.mobTick()` fluid-depth jump branch. Exact 1.16.0 Mojmap is now ready and checked; its targeted `Entity.java`/`LivingEntity.java` match 1.16.1 byte-for-byte, while the 1.16.2 jump consumer is structurally unchanged. The source-specific initiating cause/fix boundary remains unresolved; the respawn-position `<256` loop is not connected to this consumer.
- MCPK Blip revision 3476 was read in a normal browser at 2026-10-08 16:00 UTC. It reports 1.14+ blip-up/wall-blip patches and normal blips continuing; source endpoint consumers are recorded in r4, but trajectory-specific behavior and patch introduction remain unresolved because 1.14.0 is absent.
- The r4 independent review is pinned at `d514d2444f44709e8f6e4007b92f9a3bb4e6ebfe:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-review-r4-2026-10-08.md`; r5 addresses both requests. It retains unknown 1.14.0/1.16.0 boundaries and unverified trajectories.

## Resume protocol

1. Resume this worktree and branch, then confirm the clean state:

   ```powershell
   Set-Location 'D:\Javastuff\LegacyParkourCompat\.task-worktrees\mcpk-clean-rereview-continuation'
   git status --short --branch
   git log --oneline -5
   ```

2. Edge-backoff r3, slipperiness r2 identity, Big Dripleaf, and swimming are clean-review accepted. MCPK research fetches still fail with HTTP 403, but fresh page reads were recorded in a normal browser with exact page revisions in the independent follow-up. Check Feather snapshot/dependency acceptance before any pair-freeze claim.
3. Keep each claim tied to exact endpoint identity, source hashes, and—where affected—revision `feather-r1-2026-10-07`.
4. Do not rewrite the original source markers or manifests, do not claim the revised JARs equal the unavailable originals, and do not run tests/builds/games/servers/Docker without explicit authorization/admission.

## Git checkpoint

- Worktree: `C:\Users\Wolfi\.codex\worktrees\mcpk-clean-rereview\LegacyParkourCompat`
- Branch: `fix/mcpk-clean-rereview-dispositions`
- The clean-review disposition updates were committed at `10d4937470804e6ffd75b35930a9c3d62044b9be` (edge-backoff r3) and `59b6d55a88d724a91026553fff52e4027831d2f1` (registry/catalog/checkpoint updates).
- Merged default branch tip `1ea23480755a2574abd5b5855827ded5d5f50702` in merge commit `7b221a6909c23d4e6423cd41023e5bca122607de`, then merged current `main` tip `d4c4f154a0c2487dd6dd7d20d92eb57b3d8ab1ae` in merge commit `6473cb0f6f442e4c300f8b8eb70904e8791fee30`.
- The latest merge had no Git conflicts and did not touch the `workflows/wiki-audit-2026-10-07/` MCPK audit files. It brought implementation and reconciliation paths from `main`; those implementation sources were not inspected as part of this MCPK-only lane. The MCPK audit remains independent and partial.
- No full pair or wiki-lane acceptance is claimed.
- The clean acceptance commit `bdda49ac76ce8b6e136917349e8b59a61d2fabe4` was cherry-picked into this lane as `dfceacf`; the independent follow-up is a bounded source/page record only.
- Continuation commits `39ce7c1c`, `ae5f090a`, `25dc88a2`, `98b3e726`, `20894469`, and `e80f47e8` corrected and extended the immutable follow-up evidence through r4. Current branch tip is merge commit `985bc71a509abbdd644369e38510cc3541d8c157`, which merges `main` tip `61f278e268c3ed2bcc4d95e8f7bb6e9c543cfa82`; Git reported no conflicts. The merge changed no MCPK audit files. The merged implementation/source campaign files were not inspected to preserve this MCPK-only lane boundary; no MCPK file overlaps those changes.
- The r4 re-review requests were addressed in r5 commit `fe37af704b2213ec58fcdd049af1fadfae016ee0`; its Git blob is `b5d6bd29e4a2e919c0c67f888728d862dfd8bcd5`, raw SHA-256 `c126e2be6e79b73fe3ae60b5e1093e11a787a68bde2e9c4cd54bbf18e0a9948d`. The current merge commit is `567e20ed4e362920755e7b40b724cadb57377f66`, merging primary local `main` tip `3a60fe735560e478bf0aa0d05f5e306c74800f6a`; Git reported no conflicts and the merge changed no MCPK audit files. The MCPK lane did not inspect merged implementation/source-campaign findings. Exact 1.14.0 source/cutovers, Y=256 initiation/fix cause, and Blip trajectory/fall-distance producer evidence remain open. Exact 1.16.0 Mojmap is now ready and checked in the 2026-10-08 catalog/source-adjudication update; availability is closed, while the Y=256 claim is not.
- The full MCPK article/claim inventory, every published Version Differences release row, revision IDs, scope dispositions and updated exact 1.16.0 checks are in [mcpk-full-catalog-2026-10-08.md](mcpk-full-catalog-2026-10-08.md). It remains a partial audit: no 1.19+ claims are inferred from the Version Differences endpoint, and X/Z Facing was resolved as a redlink with no page history.
- Catalog checkpoint: 38 MCPK article/navigation entries and all 11 published Version Differences rows (1.8–1.18) have claim-cluster dispositions. The apparent X/Z Facing page was resolved as a redlink with no edit history; its related axis claims are under Collisions. Catalog content binding: commit `db6306297b22e8a870371e88c98ae80befc74560`, blob `ba408f1a8e4beef7ef31c070938a1792c476001b`, working-file SHA-256 `ed26fa1053ba42599e20b8c67e5f598e8191153d683dd98a1f3783b7bc76b2be`.
- The branch merged latest primary-local `main` tip `d8f3956602da94bf0cf67753cc0a9f4665397729` with no conflicts at merge commit `8eca6eaa0a7cc940dcd0276372afdedf82a874f6`. Git's merge output showed no edits to MCPK audit files from `main`; the branch's MCPK documents remain intact. The current local default branch must be rechecked before any later handoff.
- Overall audit status remains **PARTIAL**, not pair-complete: exact 1.14.0 remains unavailable; the Y=256 initiating transition/fix cause, Blip trajectory and any movement-path fallDistance producer, unverified water boundaries, numerous page claims/effect cutovers, and post-1.18 MCPK coverage remain open or explicitly outside scope. No tests, builds, runtime clients, TAS, servers, Gym or Docker were run.
