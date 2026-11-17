# Independent connected-shape crosswalk correction audit r2 — ACCEPT

Pair: exact `1.11.2→1.12`, Ornithe Feather. Audited corrected crosswalk `dependencies/connected-shape-state-crosswalk-r2.md`, SHA-256 `EDA620F3A69BF7115B45DEC4328CC48E2E23A720595D66E34B0AE83EAA3694DE`; original crosswalk SHA-256 `C5D142FF3F4BB9327194ABC989E1539EF9F1339291F1B1B19D408CEC20A01017`; r1 REQUEST CHANGES verdict SHA-256 `ECFF55BB9EEF16EFC268CEEC70F2A9819E58AF17FFD28E83A5B43A8589C8F53F`. Bound pane, fence and wall candidates and their accepted verdict hashes match the identities in the original crosswalk/r2 inputs.

## Correction review

The correction is supported by the exact 1.12 sources. `PaneBlock.shouldConnectTo` accepts a non-excluded `SOLID` face or `MIDDLE_POLE_THIN`; stained glass is absent from the listed exceptions. `StainedGlassBlock` extends `TransparentBlock`, which extends `Block` and supplies no face-shape override, so it inherits `Block.getFaceShape` returning `SOLID`. Thus stained glass connects, consistent with the explicit 1.11.2 stained-glass clause. Stained-glass panes and iron bars are pane blocks and connect through the horizontal `MIDDLE_POLE_THIN` face; ordinary glass connects through `SOLID`. The corrected exception list remains the class/identity list in 1.12 `PaneBlock`, including `AbstractLeavesBlock`, without adding stained glass.

The accepted pane-plus-leaves witness is retained: the old cube-based predicate accepts the leaves neighbor; 1.12 excludes `AbstractLeavesBlock` on its solid-face arm. The corrected text does not turn stained glass into a new difference. It also carries forward the previously accepted fence/wall gate per-axis witness and preserves the caller boundary: the three collision methods resolve virtual properties only when `forceShape` is false, while the forced branch consumes the supplied state; shape accessors resolve separately. No forced-route movement reachability is claimed.

ACCEPT for this correction and retained boundary only. This is not a full state-matrix, geometry, caller-reachability, pair-coverage, intermediate-boundary, trajectory or runtime audit.
