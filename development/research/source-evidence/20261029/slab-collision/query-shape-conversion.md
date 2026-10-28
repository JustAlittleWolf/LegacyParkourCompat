# 1.13.0 slab collector query-shape conversion gate

## Result

This source-only comparison closes a conservative runtime eligibility check for the 1.13.0 query shapes used by the slab collector. The 1.13 and 26.2 AABB constructors and movement expansion arithmetic are structurally the same for valid ordered boxes, but `VoxelShapes.block(Box)` and `Shapes.create(AABB)` do **not** have universally identical conversion rules. A producer can use the current conversion only when a source-derived check confirms that the old and current constructors choose the same representation on all operands; otherwise it must return to native collision behavior.

This is scoped to converting the start, shifted-start exclusion, and expanded-plus-inflated sweep AABBs. It does not establish general equivalence for arbitrary shapes, does not establish chunk/query-envelope equivalence, and does not claim runtime reachability.

## Exact conversion difference

1.13 `VoxelShapes.block(Box)` calls `findBits(min,max)` on each axis. Its `findBits` accepts bounds inside `[-1.0E-7, 1.0000001]`, then tries bit counts 0 through 3. For both endpoints it tests `abs(value * 2^bits - floor(value * 2^bits)) < 1.0E-7`. When all axes return a bit count it rounds endpoints with `Math.round` and creates a bit-set shape; otherwise it creates an `ArrayVoxelShape` retaining the exact min/max coordinates. When all three bit counts are zero, it returns canonical `block()` only if the input box contains `(0.5,0.5,0.5)`, else it returns empty.

26.2 `Shapes.create(AABB)` first returns empty if **any** axis width is `< 1.0E-7`. Otherwise its `findBits` uses `abs(value * 2^bits - round(value * 2^bits)) < 1.0E-7 * 2^bits`. If all bit counts are zero it unconditionally returns canonical `block()`; otherwise the bit-set/array choice and endpoint rounding follow the same broad construction pattern.

Consequences:

- Thin or degenerate input differs: current conversion empties any AABB with an axis width below `1.0E-7`; old conversion can retain an array shape. Player start boxes normally have much larger dimensions, but the adapter must check rather than assume this for every captured input.
- Even for ordinary-width boxes, the per-axis `findBits` predicates can disagree. The new predicate rounds to the nearest integer and scales its tolerance by the interval count; the old predicate measures distance from `floor` with a fixed tolerance. Values just below a grid point can be accepted by the new rule but rejected by the old rule; values just above can fall inside the new scaled tolerance while outside the old one. In those cases one release snaps coordinates into a discrete grid and the other retains exact array coordinates.
- This can happen with a width far above the early-empty threshold: on one axis, an interval from `0.49999995` to `1.0` fails the old bit checks (it keeps an exact-coordinate array) but passes the current bit-1 check (it rounds the lower endpoint to `0.5`). This illustrates why checking only for positive or non-thin AABB dimensions is insufficient.
- At the all-zero-bit special case, old conversion performs a center containment check while current conversion returns the full unit shape. Under the additional width-at-least-`1.0E-7` rule and equal bit-count result, an input classified as zero bits on all axes necessarily spans the center in each axis; still, an eligibility checker should compare this branch explicitly instead of assuming it.
- Both box classes normalize constructor endpoints with per-axis `Math.min`/`Math.max`. The old `expanded(dx,dy,dz)` and current `expandTowards(dx,dy,dz)` apply the same sign-gated addition to the selected min or max endpoint. Old `expand(epsilon)` and current `inflate(epsilon)` both subtract the amount from each min and add it to each max. For valid inputs, this operand arithmetic and its order match.

## Sufficient conversion check

For each captured AABB operand used by the old collector (the start box, the shifted start box, and the swept box after applying the requested expansion and `1.0E-7` inflation), a conservative checker can compare the old and current shape-construction result without constructing a second legacy implementation:

1. Require every axis width to be at least `1.0E-7`. This avoids the current early-empty rule.
2. For each axis compute both exact source predicates for each endpoint at bit counts 0 through 3 and require that the selected bit count is identical. Old predicate: `abs(s - floor(s)) < 1.0E-7`; current predicate: `abs(s - round(s)) < 1.0E-7 * 2^bits`, where `s=value*2^bits`, preceded by each source's same outer `[-1.0E-7,1.0000001]` bounds check.
3. If all axes select bit count zero, require the old center test and current canonical-block result to agree. With valid positive widths and equal selected bits this should hold, but it is cheap and makes the condition explicit.

When these checks pass, the two conversions select the same discrete dimensions or both select an array representation with the same exact input endpoints. For the discrete case, both then use `Math.round` for identical cell boundaries. If any width or bit-count result disagrees, the adapter must not make the historical collection claim for this invocation.

This check is an eligibility test, not a proposed generic shape-conversion API. The adapter still must form the old operands in the old order from the captured movement query. It must also preserve the old Boolean operations and grouping; passing the current AABB overlap result alone is not a substitute for the old VoxelShape predicates.

## Overlap, exclusion, and slab coordinates

1.13's collector calls `VoxelShapes.overlap(partialShape, startShape, AND)` and retains the partial only when that is false; it also requires `overlap(partialShape, sweepShape, AND)` to be true (`WorldView.java:148-158`). `VoxelShapes.overlap(a,b,op)` and 26.2 `Shapes.joinIsNotEmpty(a,b,op)` both short-circuit separated shapes using strict `max < min - 1.0E-7` comparisons on each axis, then form merged coordinate grids and evaluate the supplied Boolean operation over the voxel cells. `BooleanOp.AND` identifies occupied intersection cells; `BooleanOp.ONLY_FIRST` identifies first-shape cells outside the second; `BooleanOp.OR` joins occupied cells. For equal input coordinate lists, the accepted union/math audit confirms that the old and current coordinate mergers use the same strict coalescing thresholds, cell order, and truth-table operation. Thus the AND intersection gate matches when conversion produces equal coordinates and cells; do not replace it with scalar AABB intersection.

The accepted all-19 alias closure establishes that the supported slab provider shapes have exact integer/half-unit coordinates, and that `double` returns the canonical full-shape object. Those slab shapes are not subject to the `findBits` rounding ambiguity. The ambiguity lies in the dynamic query shapes built from player AABBs and movement, and in combining them with slabs. A direct `AABB.intersects`/`Box.intersects` replacement would be unsound: it does not reproduce the voxel Boolean operation's `1.0E-7` separated-axis tolerance or its coordinate-grid cell evaluation.

## Applicable adapter boundary

The legacy sweep/exclusion construction may use current AABB arithmetic when the adapter captures the actual primary post-adjustment movement box/vector and applies identical sign-gated expansion, movement of the exclusion box, and epsilon inflation. It can then proceed with the 19 mapped slab states only if all three AABB-to-shape checks above pass. Partial slabs are translated by integer block coordinates, preserving their exact half-unit planes; double slabs retain raw canonical-full identity before translation. The accepted collector contract remains authoritative for overlap test order, X/Z/Y grouping, optimization, and full-cell accumulation.

If a captured input is too thin, the old/current `findBits` results diverge, the old all-zero-bits center condition differs, a non-slab nonempty state is encountered, or another required query/provider gate is uncertain, preserve the native collision list. Passing this check alone does not close loaded-chunk correspondence, border/entity/step-up routing, or the overall collector operation.

## Canonical evidence

The ready markers identify 1.13 `ornithe-feather` (Feather Gen2 1.13+build.2) and 26.2 `unobfuscated`. Every selected Java file hash in `source-hashes.sha256` was also compared with its exact source-manifest entry. The hash manifest includes both ready markers, provenance files and source manifests, and the source files cited here.

Relevant source locations:

- 1.13 `VoxelShapes.block(Box)` / `findBits`: `net/minecraft/block/shape/VoxelShapes.java:54-119`.
- 1.13 `VoxelShapes.overlap`: `VoxelShapes.java:159-199`.
- 1.13 `Box` constructor, `expanded`, `grown`, `expand`: `net/minecraft/util/math/Box.java:14-25,107-153`.
- 1.13 slab shape provider: `net/minecraft/block/SlabBlock.java:24-65`.
- 1.13 collector's two partial-shape intersection checks: `net/minecraft/world/WorldView.java:148-158`.
- 26.2 `Shapes.box/create(AABB)` / `findBits`: `net/minecraft/world/phys/shapes/Shapes.java:48-130`.
- 26.2 `Shapes.joinIsNotEmpty`: `Shapes.java:169-219`.
- 26.2 `AABB` constructor, `expandTowards`, `inflate`: `net/minecraft/world/phys/AABB.java:22-33,159-204`.
- 26.2 slab shape provider: `net/minecraft/world/level/block/SlabBlock.java:33-61`.

No product source, build, test, or runtime execution was used.
