# Narrow claim review verdict

## Bound revisions

- Author comparison: `12110-12111-local-locomotion/pose-size/source/comparison.md`, SHA-256 `4161addb8fb0cb46c9a9743694c6abb4436c77f2e5675c938c0f23dbb192c1b5`.
- Independent blind freeze: `12110-12111-local-locomotion/pose-size/blind-audit/frozen.md`, SHA-256 `0ecd72e05f1ae0d6a88eda368ba3e81de885921a3350f3dfe36d518c9d423950`.

Both input hashes match the assigned revisions.

## Verdict: accepted within the frozen boundary

The author''s bounded conclusion agrees with the independent freeze:

- With requested pose treated as the same `Pose.CROUCHING` input and equivalent fit outcomes, pose choice and `setPose` writer are equivalent.
- For the ordinary player and non-sleeping poses in scope, effective type dimensions and resulting AABB are equal.
- The first pose-data refresh stores dimensions and eye height before rebuilding the box at the current position. The later size-growth reposition branch excludes `Player`.
- 1.21.11''s `reapplyPosition` adds a `lastKnownPosition = null` write before the same-position `setPos`. After that refresh, a later `computeSpeed` seeds from the current position and therefore records a zero speed sample. This is an auxiliary cache effect. No changed player displacement or trajectory is established by these claims.

## Limits and residuals

The verdict covers the held-equal requested crouch pose, equivalent fit outcomes, non-sleeping dimensions, and first refresh/reposition sequence. It does not accept or assess sprint/sneak input gates, timers, swimming/crawling behavior, collision-fit internals, or downstream applicability of the new speed sample to other consumers. No separate finding is authorized by this review.

The author''s exact per-method body SHA-256 values and extraction procedure are recorded in the bound comparison revision; this narrow review did not re-extract those bodies. The independent freeze supports the equivalence and order claims from the manifest-bound source bodies, but does not independently bind those body-level digests.