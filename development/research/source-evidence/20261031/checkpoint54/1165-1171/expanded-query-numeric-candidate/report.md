# Expanded short-pose collision query: numeric candidate (not yet a closed provider witness)

Pair: 1.16.5 → 1.17. Scope: exact-source local-player collision query path. No runtime execution, state production, or trajectory claim.

## Call path verified

In both endpoint `Entity.java` files, `collideBoundingBoxHeuristically` takes the multi-axis branch when at least two displacement components are nonzero, constructs `aABB.expandTowards(vec3)`, and calls `level.getBlockCollisions(entity, expandedBox)`. `CollisionGetter.getBlockCollisions` forwards that exact AABB to `new CollisionSpliterator(this, entity, aABB)`. Both endpoint `CollisionSpliterator` constructors set `entityShape = Shapes.create(aABB)`. The expanded query therefore reaches `Shapes.create`; the previous short-pose report's claim that it does not is incorrect.

For non-full block shapes, `CollisionSpliterator.collisionCheck` gates provider emission with `Shapes.joinIsNotEmpty(voxelShape2, entityShape, BooleanOp.AND)`. The raw query AABB is separately used for the full-cube fast-path intersection. Once emitted, the same provider VoxelShape stream is consumed by the collision axis clipper. A difference in the `entityShape` can therefore change provider admission if a provider's occupied boundary lies between the two query boundaries.

## Concrete quantization candidate

Use a 0.6F × 0.6F pose (float-to-double width `0.6000000238418579`, half-width `0.30000001192092896`) and put each player-box minimum at `0.12500008`. This corresponds to each player center/bottom coordinate `0.42500009192092897` (for X/Z center and Y bottom). Give the nonzero movement displacement `(0.02499997615814209, 0.02499997615814209, 0.02499997615814209)`. IEEE-754 reconstruction yields each expanded interval `[0.12500008, 0.75000008]`.

For each axis at resolution `i=3`, old `Shapes.findBits` computes scaled endpoints `1.00000064` and `6.00000064`; distance above floor is `6.4e-7`, which fails the old fixed `1.0e-7` test. Resolutions 0–2 also fail; old `Shapes.create(AABB)` therefore retains the exact endpoints in an `ArrayVoxelShape`. New `findBits` compares to nearest integers and permits `1.0e-7 * 8 = 8.0e-7`; both endpoints pass at resolution 3. Rounding produces grid bounds `[1,6]`, so new `Shapes.create(AABB)` returns a `CubeVoxelShape` with bounds `[0.125,0.75]` on all axes. Thus query geometry differs by `8.0e-8` at each min and max boundary.

The movement vector is a conditional collision-operation input, not a proven ordinary tick trajectory. It satisfies the source-level multi-axis gate. Equal raw position, pose and displacement inputs across endpoint calls make the arithmetic comparison well-defined.

## Closure status

This proves a source-level `Shapes.create` representation and bound difference on the actual expanded block-query route. It does **not yet prove** a changed collision result for an era-valid block shape: a discriminating provider must occupy one of the thin query-only boundary strips, and `joinIsNotEmpty`'s `1.0e-7` broad separation check plus exact interval merge must be accounted for. No built-in block/provider meeting that geometric constraint has been established in this report. Therefore this remains an open conditional candidate and cannot be used to close the collision pair gap or claim an independently accepted movement delta.

## Exact source anchors

- `Entity.java`: `collide(Vec3)` and `collideBoundingBoxHeuristically` in both endpoints.
- `CollisionGetter.java`: both `getBlockCollisions(Entity,AABB)` overloads forward the AABB.
- `CollisionSpliterator.java`: constructor's `Shapes.create(aABB)` and non-full shape `joinIsNotEmpty` filter.
- `Shapes.java`: endpoint `create(AABB)` and `findBits` arithmetic.
- `Shapes.java` join logic: broad epsilon and subsequent merged occupancy predicate.

Exact full-file SHA-256 values are in `source-hashes.tsv`; they are computed from the specified source roots and should be checked against the bound source manifests before any publication as accepted evidence.
