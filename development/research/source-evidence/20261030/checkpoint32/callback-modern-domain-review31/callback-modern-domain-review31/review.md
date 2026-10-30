# Callback modern-domain preflight review

**Reviewed revision:** `67dbf3736a9cd464422a849a8d704ee37620c57f`

## Finding: preflight misses the modern axis-segment callback domain

`EntityInsideBlocksHistoricalSweep.supportsBatch` is not a sound whole-batch guard for Eyeblossom. It tests only the 1.21.2/1.21.3 single straight sweep produced by `candidateCells(from,to,finalQueryBox)`. In 26.2, `Entity.checkInsideBlocks(List)` can instead split a `Movement` into ordered axis-only segments when `axisDependentOriginalMovement` is present and the endpoint delta is nonzero. A block in an axis segment's swept domain can therefore be absent from the single old straight-sweep candidates. If that cell is an Eyeblossom, preflight returns true and `apply` takes the historical override; the native modern callback traversal is suppressed.

### Exact source evidence

- Product `change/v1_21_2/EntityInsideBlocksHistoricalSweep.java`, `supportsBatch` (lines 56–74): for each movement it reads only `from` and `to`, then tests `scan.candidateCells(from, to, queryBox)` and rejects on `EyeblossomBlock`. It never reads axis-dependent movement metadata.
- Product `change/v1_21_2/entity/EntityInsideBlocksScan.java`, `candidateCells` (lines 37–58): this is one DDA from `to` back to `from` with a final-box sweep; it has no axis segmentation.
- Exact 26.2 source `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java`, `checkInsideBlocks(List)` (lines 1278–1301): when `movement.axisDependentOriginalMovement().isPresent()` and `delta.lengthSqr() > 0`, it loops over `Direction.axisStepOrder(...)`, calls the segment visitor separately for each nonzero axis displacement, and advances `pos` after each segment. The fallback is one from/to segment only when that metadata condition is false.
- Exact 26.2 `Entity.java`, segment overload (lines 1308–1377): each segment derives `makeBoundingBox(to).deflate(1.0E-5F)` and sends that segment to `BlockGetter.forEachBlockIntersectedBetween`.
- Exact 1.21.2 Mojmap `Entity.java`, `checkInsideBlocks(List, Set)` (near the reported member): iterates movement endpoints and calls `BlockGetter.boxTraverseBlocks(from,to,oneFinalQueryBox)` once per movement; it has no axis-dependent split.
- Exact 1.21.2 Mojmap `BlockGetter.boxTraverseBlocks` (same file): uses a single straight segment plus final query-box candidates. This confirms the old traversal is not the union of the modern segmented path domains.

### Concrete geometric witness

Use a normal player-sized box (width 0.6, height 1.8), center initially `(0.5, 0.0, 0.5)`, endpoint center `(5.5, 5.0, 0.5)`, and axis order X then Y. Let the final query box be the final player box deflated by `1.0E-5F`. A modern X-only first segment moves the box horizontally at its initial Y; its swept cells include `(4,0,0)`. The old straight segment rises five blocks while moving five blocks in X. At X cell 4 its swept vertical band is around Y=3–5; its DDA candidate expansion and final-box candidates do not include `(4,0,0)`. Thus an Eyeblossom at `(4,0,0)` is within a modern callback candidate domain but not in the old preflight's tested domain. This is a domain counterexample independent of callback-stage metadata or ordering.

The Eyeblossom candidate is a concrete modern-only feature: exact 26.2 `world/level/block/EyeblossomBlock.java`, `entityInside` (around lines 101–122) contains its callback. Its implementation's current bee/server gates may make a particular player/client encounter side-effect-free, but they do not repair the preflight's stated unsupported-domain guarantee.

## Narrow fix guidance

Make the side-effect-free preflight enumerate the complete 26.2 callback candidate domain for each movement: the ordered axis segments when the modern metadata gate applies, including each segment's own target query box and modern traversal semantics, otherwise the single from/to segment. Reject the batch if any modern callback candidate is Eyeblossom before step/callback/finalization effects run. Preserve the old DDA solely for the historical application pass. Do not approximate this by widening the old straight sweep; that can reject unrelated cells and still fail to model modern traversal's per-segment query-box/candidate rules.

No product files were changed; no build, tests, or game launch were run.
