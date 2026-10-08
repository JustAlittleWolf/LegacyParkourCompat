# MCPK audit resumable checkpoint — 2026-10-07

**State: PARTIAL, cleanly committed.** This is a bounded MCPK wiki and integrity-consumer checkpoint, not a frozen full release-pair audit. No Minecraft Wiki pages, implementation sources, or other audit-track findings were consulted. No tests, builds, games, servers, or Docker actions were run.

## Closed in this checkpoint

- Inspected and recorded the MCPK pages listed in [mcpk-wiki.md](mcpk-wiki.md); the wiki itself says its Version Differences page is no longer maintained and stops at 1.18.
- Published the exact Feather consumer evidence snapshot [mcpk-feather-r1-finding-snapshot.md](mcpk-feather-r1-finding-snapshot.md), keyed by revision `feather-r1-2026-10-07`.
- Consumer-verified all six immutable Feather JAR snapshots against their sidecars and `revision.json`; verified all 11,958 source files and 222 original artifact-manifest inputs other than the unavailable original derived JAR, with zero hash mismatches. The source owner reported that the independent operations audit passed.
- Recorded the Feather-backed findings and exact endpoint evidence in [mcpk-source-adjudication.md](mcpk-source-adjudication.md) and [mcpk-1.8.9-1.9.4.md](mcpk-1.8.9-1.9.4.md).
- The original derived mapped JARs remain unavailable. Their equivalence to the revised snapshots is **UNPROVEN**; the old cache mismatch is not waived.

## Still open

- Independent reviewer acceptance of the exact `mcpk-feather-r1-finding-snapshot.md` and its dependencies. Until then, affected source/wiki pairs remain partial and no pair freeze is claimed.
- After the relevant pair freeze/release, continue the remaining MCPK claim checks listed under “Still unresolved” in [mcpk-wiki.md](mcpk-wiki.md): exact 1.11.1 wall-height interval; full 1.13 water math/flow and remaining shapes; 1.14 movement edge cases; 1.15 bed/slab examples; Y=256 water-exit boundary; player-affecting effects/enchantments; and 1.17 powder-snow tick timing.
- Do not treat the wiki's TODO-only 1.18 section or its lack of later sections as evidence for 1.19+ or 26.x behavior.

## Resume protocol

1. Resume this worktree and branch, then confirm the clean state:

   ```powershell
   Set-Location 'C:\Users\Wolfi\.codex\worktrees\mcpk-wiki-audit\LegacyParkourCompat'
   git status --short --branch
   git log --oneline -5
   ```

2. First check whether the independent reviewer accepted the exact snapshot and dependencies. If not, resolve that review before making any pair-freeze claim.
3. Continue the open wiki claims only after the applicable source/wiki pair has been frozen and released for the wiki lane. Keep each claim tied to exact endpoint identity, source hashes, and—where affected—revision `feather-r1-2026-10-07`.
4. Do not rewrite the original source markers or manifests, do not claim the revised JARs equal the unavailable originals, and do not run tests/builds/games/servers/Docker without explicit authorization/admission.

## Git checkpoint

- Worktree: `C:\Users\Wolfi\.codex\worktrees\mcpk-wiki-audit\LegacyParkourCompat`
- Branch: `feat/mcpk-wiki-audit`
- Task tip before this checkpoint commit: `a8965a3` (`docs: snapshot Feather r1 MCPK evidence`)
- `main` remained at `002137b`; it is an ancestor of the task branch, so there were no newer main commits to merge or semantically reconcile.
- The checkpoint itself is committed separately; see the current branch tip in `git log`.
