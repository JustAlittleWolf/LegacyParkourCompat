# 1.13.0 union geometry and axis arithmetic in 26.2

## Question and conclusion

This is a source-only comparison of the accepted 1.13.0 collector contract and the 26.2 voxel-shape APIs. It is limited to block collision shapes and the union/axis-clip operation; it does not revisit collector eligibility or the accepted slab witness.

**26.2 can express the same joined occupied geometry and reproduce the 1.13.0 axis result when it is given the same accepted shapes, preserves their exact coordinates, and applies the same OR grouping. Its per-shape axis clipping arithmetic is source-equivalent. The public 26.2 shape constructors do not guarantee that representation for every 1.13.0 shape: narrow boxes are discarded, near-grid coordinates can be discretized differently, and `optimize()` extracts boxes in a different order. Therefore general exact 1.13.0 union/clip parity for an arbitrary supported block-only set is not proven by the current APIs alone.**

## OR join and shape representation

In 1.13.0, `WorldView.getBlockCollisions` groups partial shapes by X/Z column: each Y collision is OR-joined into `voxelShape4`, columns are OR-joined into the X accumulator `voxelShape3`, and X accumulators are OR-joined into `voxelShape2` (`WorldView.java:124-175`). It then runs `optimize()` on the partial result and OR-joins one `IntVoxelShape` for the separately accumulated full cells (`:178-184`). The collection order and these grouping boundaries are part of the construction procedure.

The 26.2 API has the needed basic operations: `Shapes.joinUnoptimized(a,b,OR)` builds a two-input union, `Shapes.or` calls optimized `join`, and `VoxelShape.optimize()` rebuilds from boxes. `joinUnoptimized` uses the same staged coordinate merge shape as 1.13.0: X merger first, Y next, Z last; each merger uses the same strict `1.0E-7` tie/coalescing comparisons, and the bit-set join visits X/Y/Z indices in that order. `Shapes.or(first, tail...)` is a left fold, so code with the old grouping metadata can spell the same sequence of pairwise OR calls.

The corresponding coordinate-merger bodies are structurally aligned: 1.13.0 `SimpleIndexMerger` and 26.2 `IndirectMerger` both choose the first coordinate when `first < second + 1.0E-7`, retain a coordinate only when it is not within the strict `1.0E-7` coalescing threshold of the last, and update the source indexes when a coordinate is coalesced. Disjoint and cube-grid mergers likewise preserve the same coordinate order and least-common-multiple grid rule (maximum merger cost 256). The 26.2 names and internal representations differ, but those pairwise joins can express the same grid and filled-cell geometry where the input coordinate lists are the same.

The full-cell accumulator has no direct public 26.2 `IntVoxelShape` counterpart. For geometry, a block-only caller can instead translate `Shapes.block()` to each accepted full-cell position and OR those unit shapes, or emit the full cells as boxes in the old accumulator's bounds and union them. Both represent the same occupied cells when positions are known. They do not by themselves recover the old accumulator's exact voxel coordinate array or prove that a supplied iterable identifies which inputs belonged to that accumulator.

To mirror the old grouping for known candidate metadata, the conversion sequence is: preserve each candidate's world-coordinate box boundaries; keep old partial and full-cell inputs separate; perform partial joins in the old X/Z/Y accumulation nesting with `joinUnoptimized`; apply partial-shape optimization; construct the full-cell union; then perform the final OR join. An unordered or already flattened iterable is not sufficient to reconstruct the old grouping boundaries and canonical-full-cell classification from shapes alone.

## Shape-conversion limits

Two constructor differences prevent a universal exact-conversion claim:

1. **Thin shapes.** 1.13.0 `VoxelShapes.block(Box)` falls back to a non-empty two-coordinate `ArrayVoxelShape` when a dimension cannot use the local cube grid, with no minimum-width rejection (`VoxelShapes.java:63-96`). 26.2 `Shapes.create` returns `Shapes.empty()` if any dimension is strictly less than `1.0E-7` (`Shapes.java:60-95`). The public `ArrayVoxelShape` constructors in 26.2 are package-private (`ArrayVoxelShape.java:14-24`), so a caller using the public `Shapes` factories cannot convert every such 1.13.0 shape without losing it. The accepted slab witness is not thin and is unaffected by this case.
2. **Near-grid coordinates.** The 1.13.0 `findBits` checks `abs(scaled - floor(scaled)) < 1.0E-7` (`VoxelShapes.java:99-115`). 26.2 checks distance to `round(scaled)` against `1.0E-7 * intervals` (`Shapes.java:102-120`). These tests are not identical. For example, with `intervals=2`, a scaled coordinate just below an integer can pass the modern test while failing the old floor-based test. If that causes a modern factory to choose a cube grid, it stores rounded grid coordinates; the old factory would retain exact array coordinates. This can change the represented boundaries before OR or clipping. Supplying already-created 26.2 shapes does not repair a prior conversion difference.

There is also a change in box extraction order. Legacy `VoxelSet.forEachBox(..., true)` scans X then Y and extracts contiguous Z runs, expanding left/right in X and down/up in Y (`VoxelSet.java:234-289`). Modern `BitSetDiscreteVoxelShape.forAllBoxes(..., true)` scans Y then X, extracts Z runs, expands only toward increasing X and Y, and clears the consumed strips (`BitSetDiscreteVoxelShape.java:160-200`). Thus the two public `optimize()` methods use different box decompositions/order (`1.13 VoxelShape.java:70-78`; `26.2 VoxelShape.java:96-100`). For ordinary dyadic block shapes with well-separated boundaries this still yields the same occupied geometry. It does not prove identical sequences of epsilon-sensitive re-joins for arbitrary close coordinates.

## Axis clipping arithmetic

For equal coordinate arrays and filled cells, the 1.13.0 single-shape axis routine and 26.2 `VoxelShape.collide` use the same numerical procedure. Both return the input distance for an empty shape, return zero only for strict `abs(distance) < 1.0E-7`, map the requested axis through the inverse axis cycle, find box indices using binary search at `min + 1.0E-7` and `max - 1.0E-7`, clamp the orthogonal ranges, and scan the positive or negative axis in the same nested order. The candidate boundary delta is `shapeCoordinate - movingMax` for positive movement or `shapeCoordinate - movingMin` for negative movement; the same inclusive `>= -1.0E-7` / `<= 1.0E-7` tests and `Math.min` / `Math.max` update the distance, then the routine returns after the first filled voxel encountered. The `getCoordIndex` and `findIndex` binary-search predicates have the same expression and bounds.

26.2 `Shapes.collide(axis, moving, iterable, distance)` still applies the ordered-stream strict epsilon return before each next shape. To obtain the old one-shape behavior, the caller must join the inputs first and call `joined.collide(axis, moving, distance)` once; passing separate shapes to `Shapes.collide` retains the later-element early return from the accepted report. This reproduces the accepted witness when the joined geometry is represented exactly, but does not close the input-set or shape-conversion limitations above.

## Bounded finding

For common block shapes whose coordinates and filled cells survive conversion unchanged (including the accepted bottom-slab witness), current 26.2 APIs can represent the historical joined geometry. With source-position/group metadata, pairwise OR can follow the 1.13.0 grouping; with identical joined cells and coordinates, the axis clip arithmetic yields the same double result. For an arbitrary candidate set, the public API does not promise exact historical inputs, optimized decomposition, or collector provenance. The accepted `4.9999999918171056e-8` witness remains evidence for its pinned setup only; this audit does not independently recompute it or extend it to other slab combinations.

No product implementation/history, other candidate/review/backlog material, build, test, runtime, or game execution was inspected or used.

## Exact source locations

All source paths below are relative to the canonical mapping roots named in the manifest.

| Release / file | Lines | Use |
|---|---:|---|
| 1.13.0 `net/minecraft/world/WorldView.java` | 124-185 | partial grouping, optimization, final union with full-cell accumulator |
| 1.13.0 `net/minecraft/block/shape/VoxelShapes.java` | 63-115, 121-156 | box construction, `findBits`, optimized/unoptimized OR and index-merger selection |
| 1.13.0 `net/minecraft/block/shape/VoxelShapes.java` | 207-266 | one-shape axis clipping |
| 1.13.0 `net/minecraft/block/shape/VoxelShape.java` | 70-78, 132-134 | optimization and coordinate lookup |
| 1.13.0 `net/minecraft/block/shape/IntVoxelShape.java` | 6-21 | integer-grid full-cell coordinates |
| 1.13.0 `net/minecraft/block/shape/VoxelSet.java` | 234-289 | box decomposition used by legacy optimization |
| 1.13.0 `net/minecraft/block/shape/BitSetVoxelSet.java` | 111-150 | bit-set OR join |
| 1.13.0 `net/minecraft/block/shape/SimpleIndexMerger.java` | 13-69 | overlapping coordinate merge |
| 1.13.0 `net/minecraft/block/shape/DisjointIndexMerger.java` | 11-50 | disjoint coordinate merge |
| 1.13.0 `net/minecraft/block/shape/DiscreteCubeMerger.java` | 10-29 | common discrete-grid merge |
| 26.2 `net/minecraft/world/phys/shapes/Shapes.java` | 52-120, 127-173, 300-321 | box construction, OR/join, merger selection |
| 26.2 `net/minecraft/world/phys/shapes/VoxelShape.java` | 85-100, 159-161, 252-310 | move/optimization, coordinate lookup, axis collision |
| 26.2 `net/minecraft/world/phys/shapes/BitSetDiscreteVoxelShape.java` | 112-158, 160-200 | join and optimization box decomposition |
| 26.2 `net/minecraft/world/phys/shapes/IndirectMerger.java` | 14-86 | overlapping coordinate merge |
| 26.2 `net/minecraft/world/phys/shapes/NonOverlappingMerger.java` | 11-57 | disjoint coordinate merge |
| 26.2 `net/minecraft/world/phys/shapes/DiscreteCubeMerger.java` | 11-39 | common discrete-grid merge |
| 26.2 `net/minecraft/world/phys/shapes/Shapes.java` | 234-244 | ordered iterable clipping gate |
| 26.2 `net/minecraft/world/phys/shapes/ArrayVoxelShape.java` | 14-35 | package-private exact-coordinate constructor |
