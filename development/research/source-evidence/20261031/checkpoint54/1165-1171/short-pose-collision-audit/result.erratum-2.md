# Erratum 2 — reopen short-pose partial-provider consequences

This immutable erratum supersedes the no-difference conclusion in `result.erratum-1.md`; all earlier artifacts remain unchanged. The controlling independent source-bound review is [collision-query-review/20261031-short-pose-addendum.md](../collision-query-review/20261031-short-pose-addendum.md), SHA-256 `69AA4C1584F0FFD927297493F5FA00DE07B962A75FB5DE2623A0B00440399F67` (also verified against its adjacent `.sha256` file).

The reviewer provides a conditional 1.16.5 → 1.17 swimming-pose witness through the exact `Entity.move → CollisionGetter.getBlockCollisions → CollisionSpliterator → Shapes.create → joinIsNotEmpty` route. It holds equal logical inputs and uses a persisted moving-piston state. For the specified 3-axis request, 1.16.5's continuous query produces an X overlap of about `1.496e-7`, above the IndirectMerger threshold, while the 1.17 grid query produces an overlap of about `5.96e-8`; coalescing leaves no jointly filled interval in 1.17. The partial moving-piston provider is therefore admitted only in 1.16.5, and the reviewer derives different resolved X movement (`-0.1499998503953552` versus `-0.15`). The witness is conditional on the saved moving-piston progress input; it does not assert a natural tick sequence reaches that value and is not a runtime trajectory proof.

This counterexample disproves Erratum 1's general statement that sub-`1.0E-7` endpoint shifts cannot change partial-shape AND admission. The short-pose partial-provider consequence is open again; do not use Erratum 1 as a no-difference closure for it. Only these branches remain closed by the bounded work to date:

- Standing and crouching query dimensions do not pass the relevant grid encoding in the reviewed analysis.
- The full-block `CollisionSpliterator` fast path uses the raw AABB intersection check, independent of `entityShape` encoding.

All short-pose partial-provider cases outside the reported conditional witness remain open. This erratum does not claim full collision-provider, source-pair, or movement-pair closure. The independent addendum includes the detailed geometry, exact member/source anchors, and endpoint merger replay; its source hash ledger binds the witness sources to the version manifests.
