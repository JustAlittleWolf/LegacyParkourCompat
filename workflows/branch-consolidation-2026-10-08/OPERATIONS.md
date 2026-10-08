# Documentation history preservation

This record preserves all 44 unique branch histories identified in the 2026-10-08 cleanup inventory. Seven independently accepted, bounded review reports were copied byte-for-byte into current documentation paths. The water-current F-1 report was already identical in `main`; no duplicate was added. Pose F005 ACCEPT and F07 REQUEST CHANGES both remain recorded.

All 44 branch heads are merged with the `ours` strategy so their audit trails remain reachable without restoring stale report trees, historical code deltas, or superseded coordinator guidance. Six heads carry stale source/build/code tree deltas rejected or superseded by the separate code audit; their commits are ancestry only and their trees were not imported. Exact branch names, OIDs, trees, reasons, additive paths, and merge modes are in `documentation-ref-dispositions.json`.

Accepted reports bind only the cited immutable finding or snapshot. Source discovery, implementation, wiki coverage, and runtime validation remain partial or open as previously recorded. This operation does not claim pair completeness.

The history-only merge sequence is recorded through `0d30df961b2b2791be99055deed96958cd2313d9`; 43 new `ours` merge commits cover all 44 listed heads. `feat/emulation-eligibility-scope` was already reachable through the merged ancestry. The final documentation record is on `fix/docs-history-preservation`.
