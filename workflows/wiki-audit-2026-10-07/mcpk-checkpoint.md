# MCPK audit resumable checkpoint — 2026-10-08

**State: PARTIAL, cleanly committed.** This is a bounded MCPK wiki and integrity-consumer checkpoint, not a frozen full release-pair audit. No Minecraft Wiki pages, implementation sources, or other audit-track findings were consulted. No tests, builds, games, servers, or Docker actions were run.

## Closed in this checkpoint

- Inspected and recorded the MCPK pages listed in [mcpk-wiki.md](mcpk-wiki.md); the wiki itself says its Version Differences page is no longer maintained and stops at 1.18.
- Published the exact Feather consumer evidence snapshot [mcpk-feather-r1-finding-snapshot.md](mcpk-feather-r1-finding-snapshot.md), keyed by revision `feather-r1-2026-10-07`.
- Consumer-verified all six immutable Feather JAR snapshots against their sidecars and `revision.json`; verified all 11,958 source files and 222 original artifact-manifest inputs other than the unavailable original derived JAR, with zero hash mismatches. The source owner reported that the independent operations audit passed.
- Recorded the Feather-backed findings and exact endpoint evidence in [mcpk-source-adjudication.md](mcpk-source-adjudication.md) and [mcpk-1.8.9-1.9.4.md](mcpk-1.8.9-1.9.4.md).
- Published bounded, revision-independent snapshots for 1.15 Soul Sand, 1.15 Honey, 1.17 Powder Snow, 1.17 Big Dripleaf, the 1.17 swimming-entry gate, 1.15 slipperiness sample examples, and the 1.16.1→1.16.2 sneak edge-backoff predicate. All four reviewed findings now have r2 successors.
- The Big Dripleaf r2 snapshot records the 10-tick first-shape delay discrepancy and scopes `entityInside` to a player-relevant, not player-exclusive, path. The swimming r2 snapshot preserves first-entry versus continuation behavior and adds the unchanged flying-player override and tick call order. The slipperiness r2 snapshot corrects the exact-case `SoulsandBlock.java` path and records the coordinate-flooring sources.
- The original derived mapped JARs remain unavailable. Their equivalence to the revised snapshots is **UNPROVEN**; the old cache mismatch is not waived.

## Still open

- Independent reviewer acceptance of the exact `mcpk-feather-r1-finding-snapshot.md` and its dependencies. Until then, affected source/wiki pairs remain partial and no pair freeze is claimed.
- A clean independent review of all four r2 candidates is pending. The prior review technically accepted the edge-backoff and Big Dripleaf claims, requested changes to swimming and slipperiness, but its own process note blocks formal acceptance for all four. The original snapshot files remain immutable and are superseded. Exact commit:path, Git blob, and content SHA-256 identities are recorded in [the re-review candidate register](mcpk-clean-rereview-candidates-2026-10-08.md).
- Continue the remaining MCPK claim checks listed under “Still unresolved” in [mcpk-wiki.md](mcpk-wiki.md): exact 1.11.1 wall-height interval; full 1.13 water math/flow and remaining shapes; 1.14 movement edge cases; Y=256 water-exit boundary; player-affecting effects/enchantments; and 1.17 powder-snow tick timing.
- Do not treat the wiki's TODO-only 1.18 section or its lack of later sections as evidence for 1.19+ or 26.x behavior.

## Resume protocol

1. Resume this worktree and branch, then confirm the clean state:

   ```powershell
   Set-Location 'C:\Users\Wolfi\.codex\worktrees\mcpk-wiki-audit-recovered\LegacyParkourCompat'
   git status --short --branch
   git log --oneline -5
   ```

2. Keep all four r2 snapshots pending until a clean reviewer with the allowed lane/source scope checks their recorded commit:path and content SHA identities. The prior review does not formally accept any of the four. The preserved MCPK page fetch failed with HTTP 403, so page wording/revision attribution is catalog provenance only. Check Feather snapshot/dependency acceptance before any pair-freeze claim.
3. Keep each claim tied to exact endpoint identity, source hashes, and—where affected—revision `feather-r1-2026-10-07`.
4. Do not rewrite the original source markers or manifests, do not claim the revised JARs equal the unavailable originals, and do not run tests/builds/games/servers/Docker without explicit authorization/admission.

## Git checkpoint

- Worktree: `C:\Users\Wolfi\.codex\worktrees\mcpk-wiki-audit-recovered\LegacyParkourCompat`
- Branch: `feat/mcpk-wiki-audit`
- Corrected immutable r2 snapshots and the lane links were committed at `78683ba65928004ce8b7b6b9371359164a68d43b`.
- Merged default branch tip `6e0803b3fb17eeb8a3a9861ac2638828ab3200ea` in merge commit `de69ddb1f016a062bec36238ea2c40e4c7cceed5`.
- The merge conflicted in the MCPK catalog/checkpoint because `main` removed the r2 candidates and restored the older unresolved wording. Resolution retained the explicitly requested corrected candidates and partial-review status; unrelated main changes were merged unchanged.
- No full pair or wiki-lane acceptance is claimed.
