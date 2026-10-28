# 1.13.0 slab collector query adapter gates

## Result

A neutral adapter can capture the exact primary `Entity.move` collision query operands and independently rescan the current world for its old-style block envelope. The ordinary 26.2 `Iterable<VoxelShape>` is insufficient as the historical collector input: it discards block position/state and canonical-full identity, uses a different boundary scan, and applies current shape-intersection filtering before yielding. Preserving the 1.13.0 aggregate for arbitrary arrangements of the 19 source-closed slab keys therefore requires a producer adapter that replays the old collector over raw current block states; it cannot be implemented as a clip-only consumer of modern yielded shapes.

The source supports an exact, narrow *slab-only collision-shape* policy: for every position considered by the old scan, empty collision shapes contribute nothing; every nonempty state must be one of the 19 1.13 keys closed by `slab-1130-all-slab-state-aliases/closure.md`, with its `type` state mapped to bottom/top/double. The accepted alias review confirms the geometry and context-independent provider for those keys. Any other nonempty state, unavailable chunk, border substitution, entity shape, unsupported query route, or uncertain box conversion must return native fallback. This report does not establish full movement parity or broad current-vs-historical shape equivalence.

## Exact query inputs and flow

In 1.13, `Entity.move` has a primary collision call at `Entity.java:580` after no-clip, piston, cobweb, and sneak-edge adjustments. The query receives the then-current entity box and the adjusted `x/y/z`. It runs only when at least one adjusted component is nonzero. The method later clips that one joined result in fixed Y, X, Z order (`Entity.java:580-619`). A step-up attempt makes a second, distinct `getCollisions` query (`Entity.java:612`), after restoring a prior box and substituting candidate movement. That query must be out of the primary adapter's supported route or independently captured with its own box and movement; do not reuse the first aggregate.

`WorldView.getCollisions(entity, box, dx,dy,dz)` forms the movement query shape as:

1. `start = VoxelShapes.block(box)`;
2. `startShift = block(box moved by -1e-7 on each positive movement axis, +1e-7 on each zero/negative axis)`;
3. `sweep = block(box.expanded(dx,dy,dz).expand(1e-7)) ONLY_FIRST startShift`;
4. `getBlockCollisions(sweep, start, bl=false, borderFlag)`.

Thus the adapter must capture the **actual** box and adjusted vector at each call, not the requested vector at entry to movement, and must reproduce the shifted-origin exclusion and epsilon-expanded sweep. The enclosing function has no entity exclusion set for movement (`Collections.emptySet()`); the later entity pass excludes same-vehicle entities and unions both their collision shape and the source entity's collision-against shape. For this block-only adapter, require that the historical entity-collision pass contributes no shapes. The old world-border gate may substitute canonical full blocks outside the border and updates the entity's outside-border bit; require a proven far-from-border route with stable border state, or fall back.

The earlier sneak-edge `hasNoCollisions` loop is a separate boolean query which can alter `x/z` before the primary call. For a claim about the whole move, exclude the grounded sneaking-player edge-backoff route unless its predicate is separately restored. Capturing only the later primary query does not repair a different earlier block predicate. Exclude `noClip`, zero movement, and step-up re-entry from this adapter route.

## Historical block scan requirements

`WorldView.getBlockCollisions` derives its scan bounds from the sweep shape: `floor(min)-1` inclusive through `ceil(max)+1` exclusive on X/Y/Z. Traversal is X outer, Z middle, Y inner. It checks loaded-chunk status once per X/Z column at Y=64, skips the two-axis corner columns, and on those perimeter columns omits the top Y cell. With `bl=false`, every returned canonical `VoxelShapes.block()` shape is written to a separate `BitSetVoxelSet` without a start-shape or sweep-overlap test. Partial shapes are included only when they do not overlap the start shape and do overlap the sweep shape. Partial shapes are joined within the Y loop, then per X/Z column, then across X; the partial union is optimized and finally ORed with the canonical-full accumulator. These checks and grouping must be retained even if the adapter's eligible nonempty states are restricted to slabs.

Raw identity matters for double slabs: the accepted alias closure establishes full-cube geometry, and current `SlabBlock.getShape` explicitly returns `Shapes.block()` for `DOUBLE`. A translated box or a flattened shape iterable alone cannot recover whether it came from a state whose returned shape was the canonical full shape. The producer must inspect the unshifted per-state collision shape and key/state before translation, or use a complete callback that supplies those values.

## Why the modern iterable is not a drop-in producer

`CollisionGetter.getCollisions` places entity collision shapes before block collisions. `getBlockCollisions` returns a lazy `BlockCollisions` iterator whose default result provider maps `(pos, shape)` to `shape`; consumers therefore receive neither position nor state. `BlockCollisions.computeNext` obtains the current block state and context shape, but then returns only the translated shape. It skips cursor corners, applies the modern cursor classification (`TYPE_FACE`/`TYPE_EDGE` guards), and for partial shapes yields only nonempty shapes intersecting its `Shapes.create(box)` query shape. Canonical full shapes yield only when the unexpanded query AABB intersects the unit cell. This differs from 1.13.0, where the canonical-full accumulator is filled with no geometric overlap check for every cell reached by the old scan.

The modern scan range is built with `floor(min-1e-7)-1` through `floor(max+1e-7)+1` inclusive, then `Cursor3D` advances with X fastest, Y next, Z outermost. The old collector instead has its own floor/ceil-derived half-open range and X/Z/Y order. At exact or epsilon-near integer boundaries, the two enumerated cells can differ. Therefore consuming modern yielded shapes cannot soundly recover the old full accumulator, omitted cells, or group boundaries. A custom provider scan needs the original old query operands plus raw block state/shape access; if it relies on the modern iterator it must fall back whenever its information-loss or filtering could affect the result, which is not a useful general guarantee for arbitrary slab arrangements.

## Admissibility checks and native fallbacks

A producer may select the 1.13.0 aggregate only when all of these are established for that invocation:

- It is the primary non-step-up movement query, with the captured current box and already-adjusted nonzero movement vector.
- The query box's old `VoxelShapes.block`/expand/epsilon/ONLY_FIRST construction and each per-state overlap decision are reproduced exactly. Direct scalar AABB comparisons are not established as equivalent near degenerate or rounding-tolerance cases by this source audit; where exact old/current representation is not established, fall back.
- The old loaded-column gate and the current source's block-state availability agree for every scanned X/Z column. Current `Level.getChunkForCollisions` requests `ChunkStatus.FULL` with `create=false`; do not treat a missing returned chunk as air. Fall back if any scanned column is unavailable or the loaded-state correspondence is uncertain.
- No old entity shape is added by `WorldView.m_19803363`; no old world-border replacement is possible and outside-border state is not changed by the call. Otherwise use native collection/clip behavior.
- Every nonempty collision shape encountered in the exact old scan belongs to one of the 19 proven old slab registrations. Empty shapes can be skipped. A non-slab state with a nonempty shape, an unknown registry key/state encoding, or a context-dependent provider falls back.
- The raw local shape is retained before moving by block position; `shape == canonicalFull` decides the full accumulator. Preserve old X/Z/Y traversal and partial join grouping, the optimized partial shape, and final full OR.
- The caller does not request the separate sneak-edge boolean predicate or step-up collision query through this adapter. A mismatched query context/route falls back.

This is a feasible producer design for arbitrary supported slab arrangements, not a claim that existing 26.2 collector output already satisfies it. Remaining producer-side proof/implementation gates are exact legacy-box operation parity and old/current loaded-column equivalence. The source facts above show where to capture operands and what callback metadata a custom producer needs.

## Source identities

The canonical paths were read-only. The ready markers identify 1.13 / `ornithe-feather` (Feather Gen2 `1.13+build.2`) and 26.2 / `unobfuscated`. The adjacent independently accepted slab alias closure is relied upon only for the 19-key, state-geometry, and context-independent collision-provider subset.

- 1.13 `Entity.java:473-626`, primary call at 580 and step-up call at 612.
- 1.13 `WorldView.java:113-185` block scan; `187-268` sweep, entity, and border behavior.
- 26.2 `Entity.java:711`, `1141-1260` movement query, step-up, collider aggregation, axis order.
- 26.2 `CollisionGetter.java:84-112` entity/block composition, block iterator, border route.
- 26.2 `BlockCollisions.java:31-110`; `Cursor3D.java:19-68`; `EntityGetter.java:54-72`.
- 26.2 `AABB.java:155-181,221-232`; `SlabBlock.java:33-61`; `BlockBehaviour.java:327-328`; `Level.java:769`; `LevelReader.java:132-134`.
