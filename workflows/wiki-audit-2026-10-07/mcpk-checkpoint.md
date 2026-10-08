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
- Continue the remaining MCPK claim checks listed under “Still unresolved” in [mcpk-wiki.md](mcpk-wiki.md): exact 1.11.1 wall-height interval; full 1.13 water math/flow and remaining shapes; 1.14 movement edge cases; Y=256 water-exit boundary; player-affecting effects/enchantments; and 1.17 powder-snow tick timing.
- The bounded browser/source follow-up is in [mcpk-independent-followup-2026-10-08.md](mcpk-independent-followup-2026-10-08.md). It resolves endpoint details for wall collision versus outline shape, 1.14 axis order, selected 1.13 water calculations, slipperiness examples, and 1.17 freeze/effect timing; it explicitly leaves exact cutovers and incomplete caller/shape inventories open.
- Immutable identity for the frozen initial follow-up r1: `44e5177bd840ec8c5da91e39354f639485bd80a8:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08.md`, Git blob `a2628811a67b1f30af79ba803f838831a298e572`, content SHA-256 `7582fc092c79090211a4ce07cb2ac793aa252add8ae86acf6935bab78080760f`.
- Independent review requested two evidence corrections only; corrected successor r2 applies them without changing the bounded conclusions. Immutable r2 identity: `39ce7c1c2d0c86c963a1d770750f760e64e40469:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r2.md`, Git blob `17ed4dcb39df0a78649a3ad0776c64f4e5b09ec0`, raw SHA-256 `0d280dc278c076a6fbf78389bdc4bd36ae1b2e2cbb9b8e233bddc921d3747012`.
- Do not treat the wiki's TODO-only 1.18 section or its lack of later sections as evidence for 1.19+ or 26.x behavior.
- Fresh normal-browser page revision IDs and additional exact-source endpoint comparisons are recorded in [the independent follow-up](mcpk-independent-followup-2026-10-08.md). The Y=256 check found a `resetPos` respawn search, not the alleged water-exit movement consumer, so that claim remains unresolved.

## Resume protocol

1. Resume this worktree and branch, then confirm the clean state:

   ```powershell
   Set-Location 'C:\Users\Wolfi\.codex\worktrees\mcpk-clean-rereview\LegacyParkourCompat'
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
