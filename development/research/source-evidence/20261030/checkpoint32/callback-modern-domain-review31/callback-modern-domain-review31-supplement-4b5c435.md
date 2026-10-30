# Corrective preflight review supplement

**Reviewed revision:** `4b5c435afd2fd7f04bd9cbf8f7b3c8c58ce5143f`

## Finding: composed traversal is still not covered

The correction fixes the earlier omission of axis-split segments and derives each segment's target box with `makeBoundingBox(segmentTo).deflate(1.0E-5F)`. It also uses the axis-order hook with the same original operand and resolved delta passed through `EntityCallbackTraversalMixin`, falling back to the recorded optional vector as native does. However, the candidate enumerator used by preflight, `BlockCallbackDda.visitSegment`, is not the 26.2 `BlockGetter.forEachBlockIntersectedBetween` candidate traversal. It can omit modern candidates, so the unsupported-domain guard still has a false-negative path.

### Exact algorithm differences

Product `mechanic/hook/blockcallback/BlockCallbackDda.java`, `visitSegment`:

1. Treats any segment with `travel.lengthSqr() < Mth.square(0.99999F)` as stationary and checks only `BlockPos.betweenClosed(target)`.
2. For longer segments, seeds its DDA at `target.getMinPosition().subtract(travel)` and its loop adds cells only after stepping to the next crossed voxel; it then appends the target box candidates.
3. Uses the old 1.21.6-style clamped-hit/box-size enumeration and a 17-attempt cap (`if (attempts++ > 16) break`).

Exact 26.2 unobfuscated `world/level/BlockGetter.java`, `forEachBlockIntersectedBetween` and `addCollisionsAlongTravel`:

1. Uses the short-segment threshold `travel.lengthSqr() < Mth.square(1.0E-5F)`.
2. On a moving segment, visits `BlockPos.betweenCornersInDirection(aabbAtTarget.move(travel.scale(-1.0)), travel)` first, then swept DDA cells, then `BlockPos.betweenCornersInDirection(aabbAtTarget, travel)`; the visited set deduplicates these three portions.
3. Derives the moving sweep from `getFurthestCorner(delta)`, clips that corner's path, and traverses the opposite corner range. There is no 17-attempt cap in this BlockGetter method. `Entity.checkInsideBlocks` separately applies its segment budget to the visitor's iteration argument.

Consequently, the current helper's `0.99999F` threshold can skip modern swept/start-box candidates for ordinary sub-block movements, and its moving path does not seed the modern starting-box candidates. The old historical `scan.candidateCells` check does not close this gap: that traversal also has the old `0.99999F` threshold and is not the full modern per-segment candidate domain.

A direct sub-block witness for the threshold difference is a vertical segment of length `0.5` with the target box deflated at a lower Y than the starting box. The helper visits only the target box, while 26.2's threshold enters the swept branch and explicitly visits the starting box (`target.move(-travel)`) before DDA. For a player box positioned so its start box includes Y cell 2 and the target deflated box ends below 2, put Eyeblossom in that start-only cell: preflight does not inspect it, while modern traversal visits it at iteration 0. This is independent of which axis was selected and remains true when the movement record has one nonzero axis.

### Consistency assessment

- **Axis condition and segment construction:** matches 26.2 list traversal: optional metadata present and endpoint delta nonzero; nonzero axis components only; sequential `segmentFrom`/`segmentTo` updates.
- **Axis ordering operand:** the preflight resolves the same `BlockCallbackAxisOrderHook` using `(recorded original, resolved endpoint delta)` as the mixin redirect; otherwise both preserve the recorded vector. `Direction.axisStepOrder` then operates on that vector.
- **Per-segment query box:** `makeBoundingBox(segmentTo).deflate(1.0E-5F)` matches the 26.2 segment overload.
- **Candidate traversal:** does not match 26.2 due to threshold, starting-box inclusion/order, traversal math and cap. Whole-batch rejection therefore is not proven complete.

## Narrow fix guidance

Give the preflight a side-effect-free candidate enumerator that mirrors 26.2's `forEachBlockIntersectedBetween` exactly for each modern segment, including the `< square(1.0E-5F)` branch, initial and final corner ranges, furthest-corner DDA, deduplication, and relevant stopping/budget behavior. Alternatively, define and prove a conservative superset of all cells that the native visitor can deliver. Keep this candidate check before any `stepOn`, entity callback, collector mutation, or finalization.

No product files were changed; no build, tests, or game launch were run.
