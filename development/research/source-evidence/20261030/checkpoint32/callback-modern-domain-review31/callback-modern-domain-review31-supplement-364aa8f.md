# Final preflight review supplement

**Reviewed revision:** `364aa8fd38056218340782fca39a739b57f355cc`

## Result: candidate-domain coverage is complete for the reviewed composition

The previous traversal gap is closed in this revision. For each endpoint movement, `supportsBatch` now checks both (1) the old 1.21.2/1.21.3 straight-sweep candidates used by historical `apply`, and (2) the exact native 26.2 callback candidate traversal for every segment 26.2 would form. It also unions the selected `BlockCallbackTraversalHook`'s candidate stream when that hook applies, covering the composed segment mixin on fallback. No false-negative candidate path was found in this bounded review.

### Source comparison

- `EntityInsideBlocksHistoricalSweep.supportsBatch` mirrors the native 26.2 list split: optional `axisDependentOriginalMovement` present and nonzero endpoint `delta`; it iterates `Direction.axisStepOrder(axisOrder)`, skips zero axis components, builds `segmentTo` by changing only the selected coordinate, then advances `segmentFrom`.
- The preflight's axis-order selection calls the same resolved `BlockCallbackAxisOrderHook` with `(axisDependentOriginalMovement, delta)` and the same `appliesTo` filter used by `EntityCallbackTraversalMixin`; absent an applicable hook, both retain the recorded operand. It then calls `Direction.axisStepOrder` on the resulting vector, matching native's order construction.
- Each modern segment uses `Entity.makeBoundingBox(segmentTo).deflate(1.0E-5F)` through `EntityInvoker`, which matches the exact 26.2 `checkInsideBlocks(Vec3,Vec3,...)` setup.
- `containsModernEyeblossomCandidate` invokes exact 26.2 `BlockGetter.forEachBlockIntersectedBetween(from,to,queryBox,inspect)`. This covers its `square(1.0E-5F)` short branch, initial corner candidates, furthest-corner DDA, final corner candidates, de-duplication, candidate order, and visitor's short-circuit semantics without a parallel handwritten traversal.
- It then checks the selected `BlockCallbackTraversalHook` stream under the same `appliesTo` predicate used by `EntityBlockCallbackSegmentMixin`. If that hook preserves native traversal (`Optional.empty()`), the exact native pass already covered it; if it replaces traversal, its visited positions are checked as an additional superset. Contact/admission hooks only filter positions from those streams; they do not create positions outside them. Budget/early-stop logic likewise filters callback delivery, while the preflight enumerates a superset.
- The old scan pass uses the shared final entity query box, as does 1.21.2/1.21.3 `boxTraverseBlocks`, and remains necessary because it covers the historical candidate set that the eventual historical `apply` will dispatch.

The scan performs block-state reads and can return false before `applyEffectsFromBlocks` chooses the historical path. Its visitor has no callback, collector, visited-set, or entity-state effects. Stopping each stream once Eyeblossom is found is safe because the guard only needs existence, not full enumeration after rejection.

## Scope and limits

This confirms the requested Eyeblossom candidate-domain guard and its alignment with the exact native/composed traversal and axis/box choices. It does not establish movement parity, callback admission equivalence, profile-wide behavior beyond the selected traversal hooks reviewed here, or runtime mixin application. No product files were changed; no tests, build, or game launch were run.
