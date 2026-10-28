# 1.13.0 slab collector adapter source contract

## Finding status

This is a source-only adapter contract, not a new independently reviewed movement finding and not runtime evidence. It relies on the accepted 1.13.0 collector report and correction, the accepted union/math audit, and the accepted all-19 slab state-alias closure/review. The canonical files listed in `sources.sha256` were re-hashed from the read-only source roots for this contract.

## Exact historical operation

The ordinary 1.13.0 `Entity.move` path invokes `WorldView.getCollisions(entity, startBox, dx, dy, dz)` once for nonzero movement, then clips the returned **single shape** against Y, X, Z in that order, moving the entity box between axes (`Entity.java:578-598`). For the ordinary call, `onlyFirst=false`. The collision shape is collected from a shaped sweep-minus-start query:

1. `start = block(startBox)`.
2. `exclusion = block(startBox moved by (-1e-7 if the requested axis is positive, otherwise +1e-7) on each axis)`.
3. `bounds = block(startBox expanded by the full requested vector, then expanded by 1e-7) ONLY_FIRST exclusion`.
4. `collidesWithBorder` can replace positions outside the border with canonical full-block shape; this contract excludes that route.

The integer scan uses lower bounds `floor(bounds.min)-1` and exclusive upper bounds `ceil(bounds.max)+1` independently for X/Y/Z. The loops are X ascending outermost, Z ascending next, Y ascending innermost. It tests chunk loadedness once for each X/Z using `(x,64,z)`. It skips positions where both X and Z are at the scan perimeter, and at X- or Z-perimeter positions it skips only the top scan Y cell. Any adapter must use these exact scan bounds, iteration order, skips, and loaded-region decision; the 26.2 cursor's different enumeration and pruning are not interchangeable.

For each remaining loaded position, the old collector obtains the state collision shape. If `collidesWithBorder` and the position is outside the border it substitutes `VoxelShapes.block()`; the supported slab domain requires `collidesWithBorder=false` and all scanned candidate positions inside the border. An empty shape is ignored.

**Full-shape identity is a special rule.** The collector classifies a full cell by reference identity `shape == VoxelShapes.block()`, not by bounds or a “full block” property. With `onlyFirst=false`, it sets the cell in the `BitSetVoxelSet` immediately and does not apply the exclusion or query-overlap predicates to that full-cell branch. For the accepted slab roster, only `type=double` returns this canonical full shape; it must enter the old full-cell accumulator even when the position's unit cube overlaps the starting box or does not intersect the shaped query. This makes producer state/position/raw-shape identity and the full scan envelope essential. `isFullBlock` is not a substitute: the 1.13 `SlabBlock` also reports top slabs as full for that unrelated predicate.

For non-full (partial) shapes, the collector translates the shape by integer block position and appends it only if it does **not** overlap the exclusion shape and **does** overlap `bounds`, both using the old shape-overlap Boolean operation. It OR-joins those partial candidates by X/Z column while visiting Y, then joins each column into its X accumulator, then each X accumulator into the whole-region partial shape. After the loops it optimizes this partial union, creates one integer-grid shape from the accumulated canonical full cells using the original scan origin, and performs one final unoptimized OR. The result is passed once to the single-shape axis clipper. Preserve pairwise grouping, order, optimize point, full-cell separation, and final join; a flattened shape iterable does not encode them.

## Supported general domain

The implementation-ready supported domain is one ordinary player-movement collision query where:

- the query is the normal `Entity.move` collision route, with one start box and original `(dx,dy,dz)`; no step-up, alternate/second query, `onlyFirst`, or unrelated collision API;
- the actor is not colliding with the world border and every position traversed by the old padded scan is within the border;
- every X/Z chunk needed by that exact scan is available; if any is unavailable or cannot be checked compatibly, use native behavior;
- there are no entity collision candidates for the old query and no current entity-collider entries; entity filtering/union is outside this block-only contract;
- every nonempty block collision shape encountered in the old scan is one of the 19 source-closed 1.13 slab registry keys, represented by its corresponding 26.2 key and `type` (`bottom`, `top`, `double`) and either value of `waterlogged`; all other scanned states must have empty collision shape under the active collision context;
- there are no other sources of collision geometry, including a border shape or collision-only synthetic output.

This covers **arbitrary arrangements and counts** of those 19 slabs in the scan region, including mixed slab keys, all slab types, and waterlogged values. It is not a two-slab/witness special case. The all-19 closure independently confirms the one-to-one registry keys, six property combinations, collision geometry, context-independent slab shape selection, and base provider gates. Later-only 26.2 slab keys stay native.

This domain uses the current world as a provider of the received block states. It does not claim equivalence for future block types, entities, border handling, unloaded/changing chunks, historical chunk-loading side effects, or collision states not in the 1.13 roster.

## Required adapter input and producer gap

The ordinary 26.2 movement route constructs a shape list in order: entity candidates, optional border shape, then `Level.getBlockCollisions(source, sweptAABB)`. `BlockCollisions.computeNext` visits a current `Cursor3D` range, reads current block state, obtains its context collision shape, filters it against the exact swept AABB, translates it, and returns one shape per qualifying block. This list is **not sufficient** to reconstruct 1.13.0 generally:

- shape-only values lose block position, state/key, and pre-translation identity with `Shapes.block()`;
- modern block iteration can omit 1.13 full-scan cells because 1.13 accumulates every canonical full shape in its padded scan cells without the overlap tests used for partial shapes, while modern iteration returns a full block only if its unit cube intersects the exact AABB;
- current cursor order is X-fastest, then Y, then Z, not the historical X/Z/Y grouped scan;
- the current output does not identify why a candidate was omitted or whether the old query's chunk/perimeter/exclusion gates would have accepted it;
- entity and border elements cannot safely be reconstructed from block shapes.

The least additional producer capability is an immutable collection-query context retaining the original start box, requested movement vector, entity/query route, collision context, border state, and any excluded-entity set. A collector adapter must obtain each scanned block's position, registry key/state, raw local collision shape **before translation**, and the canonical-full identity classification; then it must apply the 1.13 query/filter procedure and group/union as above. The reliable way to include the legacy full-cell halo is to scan the exact old integer envelope and query block states directly (or prove a producer hook reports every position in that envelope, including non-overlapping canonical-full cells); iterating only the modern returned shapes cannot do this. For this all-slab domain, current slab shape data is source-proven context-independent, but for every other block the adapter must fall back as soon as it finds any nonempty collision shape.

The existing movement operation must collect once from the original request and feed the resulting single union to the already-resolved axis clip operation reused for the axis sequence. Do not add a second axis hook. If the source path provides only per-axis calls with changed boxes/vectors and cannot recover the original query, it must stay native.

## Construction and clipping boundary

For the 19 mapped slab keys, 1.13 and 26.2 return the same exact unit/half-height slab coordinates, independent of context and waterlogging; double returns each release's canonical block shape. These shapes and integer translations have dyadic coordinates and no thin dimension. The accepted union/math audit closes the needed public 26.2 pairwise-OR, optimization, integer-full-cell geometry, and per-shape axis-clip procedures for equal coordinates/cells; the adapter must use `joinUnoptimized(..., OR)` in the historical grouping and call the existing single-shape clip routine once per axis. Passing the partial shapes separately to the modern iterable clipper is incorrect because its pre-next-item strict `abs(distance)<1e-7` gate remains active.

The audit does not prove a universal converter for arbitrary shapes. It specifically identifies 26.2 `Shapes.create` rejecting dimensions `<1e-7`, near-grid `findBits` differences, and optimization box-decomposition order. Those cases cannot arise from the supported slab shapes and integer translation. Any extension to non-slab shapes or other coordinates is a new source-closure requirement, not an inference from this contract.

## Source references

- 1.13 `WorldView.getBlockCollisions` and `getCollisions`: `WorldView.java:113-218`; entity collection/filters: `WorldView.java:220-246`.
- 1.13 normal movement and shape reuse: `Entity.java:578-598`.
- 1.13 slab shape and the separate `isFullBlock` predicate: `SlabBlock.java:24-65`.
- 1.13 boolean join and single-shape clip implementation: `VoxelShapes.java:121-156,207-266`; old scan order: `BlockPos.java:212-238`.
- 26.2 entity collision route/order and step-up requery: `Entity.java:1141-1167,1195-1259`.
- 26.2 block cursor bounds, state/provider access, and shape outputs: `BlockCollisions.java:31-62,79-109`; cursor order: `Cursor3D.java:31-73`.
- 26.2 collision iterable API: `CollisionGetter.java:82-106`.
- 26.2 slab shape/state schema: `SlabBlock.java:31-65`.
- 26.2 union/clip operations and known shape-conversion boundaries: `Shapes.java` and `VoxelShape.java` ranges in the accepted union/math audit.

## Precise remaining implementation gate

Source closes a general arbitrary-arrangement **slab-only, block-only, loaded normal-query** operation and establishes that a plain 26.2 shape iterable is insufficient. Product integration still must demonstrate that the hook can recover the original query and either scan the old integer envelope with state/position/raw shape or receive complete equivalent producer metadata. It must prove at runtime/source call-site that all scanned non-slab blocks are collision-empty, entities/border are absent, chunks are loaded, and the route is not step-up before selecting the historical operation. If any gate cannot be established, preserve the native collision result. No claim is made that runtime accepts all gates or that this implementation has parity beyond its bounded domain.
