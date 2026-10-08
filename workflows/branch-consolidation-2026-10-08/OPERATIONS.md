# Documentation history preservation

This record preserves all 44 unique branch histories identified in the 2026-10-08 cleanup inventory. Seven independently accepted, bounded review reports were copied byte-for-byte into current documentation paths. The water-current F-1 report was already identical in `main`; no duplicate was added. Pose F005 ACCEPT and F07 REQUEST CHANGES both remain recorded.

All 44 branch heads are merged with the `ours` strategy so their audit trails remain reachable without restoring stale report trees, historical code deltas, or superseded coordinator guidance. Six heads carry stale source/build/code tree deltas rejected or superseded by the separate code audit; their commits are ancestry only and their trees were not imported. Exact branch names, OIDs, trees, reasons, additive paths, and merge modes are in `documentation-ref-dispositions.json`.

Accepted reports bind only the cited immutable finding or snapshot. Source discovery, implementation, wiki coverage, and runtime validation remain partial or open as previously recorded. This operation does not claim pair completeness.

The history-only merge sequence is recorded through `0d30df961b2b2791be99055deed96958cd2313d9`; 43 new `ours` merge commits cover all 44 listed heads. `feat/emulation-eligibility-scope` was already reachable through the merged ancestry. The final documentation record is on `fix/docs-history-preservation`.

## Final cleanup result

Both guarded cleanup passes completed: 652 obsolete local refs removed (225 branches, 216 Codex snapshot aliases, 211 turn-diff aliases). In total, 79 clean worktrees were detached at their existing commits; all 110 worktree paths were preserved.

Retained: main, the 26 canonical source continuation branches, four branches with uncommitted work, 31 unique recovery snapshots, one unique turn-diff state, and all other out-of-scope refs. The campaign remains paused. No tests, build, runtime launch, or production-code changes occurred during consolidation.

The final report archive was pushed and remotely verified at 81f1d2e006c3bcd8ce8ec84ee8cd35b7acb24362. Both cleanup postchecks passed. Local execution evidence is preserved under build/branch-consolidation-2026-10-08/, including cleanup-final-verification.json and post-archive-cleanup-final-verification.json. A journal replacement failure was reconciled against live refs; original partial journals were retained and the recovered journal records all 75 final-pass deletions.

The human authorized shutdown after completion, with at most five requests and no retry after an accepted request. Actual shutdown attempts and results are recorded locally in build/branch-consolidation-2026-10-08/shutdown-state.json; no shutdown request had been issued when this record was committed.
