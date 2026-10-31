# Exact-boundary canonical binding

**Verdict: ACCEPT**

Reviewed final canonical draft `.native-source/20261030/1152-1165-finish43/canonical-findings/1.15.2--1.16/player-correction-detaches-vehicle.md`, SHA-256 `A61C274B9CCA6DD4B4F714B378D3A52E1714B4B5AB54D591267AF944D4091593` (recomputed; match), against boundary report `.native-source/20261030/1152-1165-finish43/direct-response-boundaries/report.md`, SHA-256 `1BFA29DE90E99FBB1A82370035D8F7EDAAD2D5F85162458F0D5A6607DE137376` (recomputed; match), and my accepted receiver review.

No material claim drift. Version references and bounded boundary `1.15.2 → 1.16` match the report. The draft correctly says the gate is present in 1.16 and persists through 1.16.5; the boundary report explicitly verifies 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4 and 1.16.5, with the same condition and operation. I spot-checked the exact listener sources for all six releases: the gate is at lines 726–727 in 1.16/.1 and 714–715 in .2–.5.

The order statement is accurate: the detach follows `xOld/yOld/zOld` bookkeeping and precedes correction position, velocity and rotation writes. The 1.16 `Entity.removeVehicle` clears `vehicle` and removes this passenger (lines 1630–1635); the endpoint receiver review separately established the direct local-player movement relevance. This verdict binds only to the canonical draft hash above. It does not extend the boundary outside audited releases or claim full-pair coverage or runtime parity.
