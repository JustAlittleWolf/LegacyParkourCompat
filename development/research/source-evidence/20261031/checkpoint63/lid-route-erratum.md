# Audit erratum 1 — ShulkerBox witness uses the direct one-axis block scan

This immutable erratum corrects the route named for the tiny-progress ShulkerBox shape witness in `audit.md` and `coverage.tsv`. Those originals remain unchanged. The ShulkerBox finding remains **ACCEPTED_CONDITIONAL**; the pair-coverage verdict is unchanged.

## Correct route for the tiny-progress witness

The tiny-progress WEST-facing ShulkerBox fixture uses one nonzero SELF X displacement. The correct route is:

`Entity.collide(Vec3)` → `Entity.collideBoundingBoxHeuristically` → `Entity.collideBoundingBox` → `Shapes.collide(Axis.X, currentAABB, LevelReader, dx, CollisionContext, stream)` → direct `LevelReader.getBlockState` / `BlockState.getCollisionShape` → `ShulkerBoxBlock.getShape` → `ShulkerBoxBlockEntity.getBoundingBox`.

Because exactly one movement component is nonzero, this branch scans the relevant block states directly and clips against the returned `VoxelShape`. It does **not** construct a swept `Shapes.create(AABB)` query for `CollisionSpliterator`, and does not use `CollisionSpliterator`'s Boolean AND admission filter. The changed value is the ShulkerBox provider shape: 1.16.5 preserves the tiny outward lid strip, while 1.17/1.17.1 encode the shape as `Shapes.block()`.

For the source-bound fixture, hold the ShulkerBox at `(0,0,0)`, facing WEST, with equal reachable progress `1.4901161193847656e-8F`, and a standing LocalPlayer at `(-0.35000001192092896, 0.2, 0.5)`. Hold the current player AABB and SELF X request `0.049999999` equal across releases. The old strip clips X to `0.0499999925494194`; the new full-block provider leaves the requested `0.049999999` unchanged. The difference is about `6.45e-9`, and both squared displacements clear the `Entity.move` position-write threshold. This is a static source response witness, not a runtime trajectory result.

The direct author's `shulkerbox-dynamic-collision-pair/report.erratum-1.md` corrects the positive-progress domain: three opening ticks reach `0.30000001192092896F`; after switching to closing, three `0.1F` subtractions leave `1.4901161193847656e-8F`, which survives the exact `progress <= 0.0F` reset. The author erratum's source hashes were independently rehashed into `audit.erratum-1-source-rehash.tsv` against the release manifests and embedded provenance: 51 of 51 rows match. Its report hash and the other erratum inputs are recorded in `audit.erratum-1-input-hashes.tsv`.

## Separate route for the moving-piston witness

The short-pose moving-piston fixture in `collision-query-review/20261031-short-pose-addendum.md` remains a distinct multi-axis case. Its request has three nonzero components, so the heuristic uses the block-query route through `CollisionGetter.getBlockCollisions` and `CollisionSpliterator`; that constructor applies `Shapes.create` to the swept query, and the provider/query shapes are joined with Boolean AND. The tiny-progress ShulkerBox provider witness must not be attributed to this route.

The separate `ShulkerBoxBlockEntity.moveCollidedEntities` direct-response witness in `coverage.tsv` is also unchanged by this route correction.
