# MCPK audit resumable checkpoint — 2026-10-08

**State: PARTIAL, cleanly committed.** This is a bounded MCPK wiki and integrity-consumer checkpoint, not a frozen full release-pair audit. No Minecraft Wiki pages, implementation sources, or other audit-track findings were consulted. No tests, builds, games, servers, or Docker actions were run.

## Closed in this checkpoint

- Inspected and recorded the MCPK pages listed in [mcpk-wiki.md](mcpk-wiki.md); the wiki itself says its Version Differences page is no longer maintained and stops at 1.18.
- Published the exact Feather consumer evidence snapshot [mcpk-feather-r1-finding-snapshot.md](mcpk-feather-r1-finding-snapshot.md), keyed by revision `feather-r1-2026-10-07`.
- Consumer-verified all six immutable Feather JAR snapshots against their sidecars and `revision.json`; verified all 11,958 source files and 222 original artifact-manifest inputs other than the unavailable original derived JAR, with zero hash mismatches. The source owner reported that the independent operations audit passed.
- Recorded the Feather-backed findings and exact endpoint evidence in [mcpk-source-adjudication.md](mcpk-source-adjudication.md) and [mcpk-1.8.9-1.9.4.md](mcpk-1.8.9-1.9.4.md).
- Published bounded, revision-independent snapshots for 1.15 Soul Sand, 1.15 Honey, 1.17 Powder Snow, 1.17 Big Dripleaf, the 1.17 swimming-entry gate, 1.15 slipperiness sample examples, and the 1.16.1→1.16.2 sneak edge-backoff predicate. Four r2 snapshots are preserved; edge-backoff now has a corrected r3 successor.
- The Big Dripleaf r2 snapshot records the 10-tick first-shape delay discrepancy and scopes `entityInside` to a player-relevant, not player-exclusive, path. The swimming r2 snapshot preserves first-entry versus continuation behavior and adds the unchanged flying-player override and tick call order. The slipperiness r2 snapshot corrects the exact-case `SoulsandBlock.java` path and records the coordinate-flooring sources.
- The original derived mapped JARs remain unavailable. Their equivalence to the revised snapshots is **UNPROVEN**; the old cache mismatch is not waived.

## Still open

- Independent reviewer acceptance of the exact `mcpk-feather-r1-finding-snapshot.md` and its dependencies. Until then, affected source/wiki pairs remain partial and no pair freeze is claimed.
- A follow-up review of edge-backoff r3 and post-fix confirmation of the corrected slipperiness identity remain pending. Big Dripleaf and swimming were accepted by the clean review. The r2 files remain immutable. Exact commit:path, Git blob, and content SHA-256 identities are recorded in [the re-review candidate register](mcpk-clean-rereview-candidates-2026-10-08.md).
- Continue the remaining MCPK claim checks listed under “Still unresolved” in [mcpk-wiki.md](mcpk-wiki.md): exact 1.11.1 wall-height interval; full 1.13 water math/flow and remaining shapes; 1.14 movement edge cases; Y=256 water-exit boundary; player-affecting effects/enchantments; and 1.17 powder-snow tick timing.
- Do not treat the wiki's TODO-only 1.18 section or its lack of later sections as evidence for 1.19+ or 26.x behavior.

## Resume protocol

1. Resume this worktree and branch, then confirm the clean state:

   ```powershell
   Set-Location 'C:\Users\Wolfi\.codex\worktrees\mcpk-clean-rereview\LegacyParkourCompat'
   git status --short --branch
   git log --oneline -5
   ```

2. Big Dripleaf and swimming are clean-review accepted. Keep edge-backoff r3 pending follow-up review; confirm the corrected slipperiness blob row. The preserved MCPK fetch failed with HTTP 403, so page wording/revision attribution is catalog provenance only. Check Feather snapshot/dependency acceptance before any pair-freeze claim.
3. Keep each claim tied to exact endpoint identity, source hashes, and—where affected—revision `feather-r1-2026-10-07`.
4. Do not rewrite the original source markers or manifests, do not claim the revised JARs equal the unavailable originals, and do not run tests/builds/games/servers/Docker without explicit authorization/admission.

## Git checkpoint

- Worktree: `C:\Users\Wolfi\.codex\worktrees\mcpk-clean-rereview\LegacyParkourCompat`
- Branch: `fix/mcpk-clean-rereview-dispositions`
- The clean-review disposition updates were committed at `10d4937470804e6ffd75b35930a9c3d62044b9be` (edge-backoff r3) and `59b6d55a88d724a91026553fff52e4027831d2f1` (registry/catalog/checkpoint updates).
- Merged default branch tip `1ea23480755a2574abd5b5855827ded5d5f50702` in merge commit `7b221a6909c23d4e6423cd41023e5bca122607de`.
- The merge conflicted in the MCPK catalog/checkpoint because `main` removed the r2 candidates and restored the older unresolved wording. Resolution retained the explicitly requested corrected candidates and partial-review status; unrelated main changes were merged unchanged.
- No full pair or wiki-lane acceptance is claimed.
