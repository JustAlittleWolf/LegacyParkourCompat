# Correction: withdraw the axis-order witness

This correction supersedes the reachability claim in `step-up-axis-order-witness.md`; that report remains unchanged for identity/history. The claim is invalid because the proposed Z-before-X candidate does not exist in either compared source method.

## Exact source order

In both 1.8 `Entity.move` (`Entity.java:477–513`) and 1.8.1 (`Entity.java:482–518`), the first raised candidate clips upward, then clips X and moves X, then clips Z and moves Z. The second candidate also clips upward, then clips X and moves X, then clips Z and moves Z. The second candidate differs in its ascent input: it clips against the original box (`var77`) rather than the horizontally expanded box (`var32`) used by the first candidate. It does not reverse the horizontal axis order.

For the proposed coordinates, both candidate X passes still occur while the player's Z interval overlaps the obstacle. Both therefore clip X to `0.20`; both then move Z by `-0.03`. Each candidate's horizontal squared distance is `0.20² + 0.03² = 0.0409`. The ordinary route has the same clipped horizontal distance. `Entity.move`'s fallback condition uses `>=` (`1.8:533`, `1.8.1:538`) and restores the original ordinary route on this tie. No raised route wins, so the proposed old/new endpoint difference is not reached.

The ceiling and full-block shape calculations do not repair this source-order failure. The carpet-overlap setup remains rejected. No alternative route was searched under this correction task.

## Corrected conclusion and scope

Withdraw the claimed positive ordinary-player witness from the preceding report. This follow-up does **not** establish a valid distinct route and does not close player applicability for the 1.8→1.8.1 descent change. The source difference itself remains as documented in the supplied prior coverage. 1.8.2 was not re-audited here; the prior bounded check of its actual-rise rule remains the only 1.8.2 statement. No implementation, test, build, runtime, decompilation, or additional provider-variant search was performed.
