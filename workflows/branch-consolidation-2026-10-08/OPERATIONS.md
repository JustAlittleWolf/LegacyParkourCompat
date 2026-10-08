# Documentation history preservation

This record preserves unique documentation branch ancestry after the 2026-10-08 consolidation. Seven independently accepted, bounded review reports were copied byte-for-byte into current documentation paths. The water-current F-1 report was already identical in `main`; no duplicate was added. Pose F005 ACCEPT and F07 REQUEST CHANGES both remain recorded.

The 38 heads listed as `PRESERVE_BRANCH_HISTORY_ONLY` are merged with the `ours` strategy so their audit trails remain reachable without restoring stale report trees or changing current coordinator guidance. Six heads with no branch-only documentation content are recorded as cleanup candidates, subject to checkout/ref safety checks. Exact branch names, OIDs, trees, reasons, and merge modes are in `documentation-ref-dispositions.json`.

Accepted reports bind only the cited immutable finding or snapshot. Source discovery, implementation, wiki coverage, and runtime validation remain partial or open as previously recorded. This operation does not claim pair completeness.
