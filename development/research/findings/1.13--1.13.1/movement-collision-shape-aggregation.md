# Movement collision shape aggregation

1.13, Ornithe Feather: `net/minecraft/world/WorldView.java`, `getBlockCollisions` and `getCollisions`, lines 113–246; `net/minecraft/entity/Entity.java`, `move`, lines 578–598.
1.13.1, Ornithe Feather: `net/minecraft/world/WorldView.java`, `getBlockCollisions`, lines 112–152; `WorldAccess.java` inherits this path; `World.java`, `getCollisions`, lines 1755–1758; `net/minecraft/util/math/BlockPos.java`, `iterateRegionMutable`, lines 212–238. Relevant 1.13.2 block-collector bodies are identical.

1.13 visits blocks with X outer, Z middle and Y inner. It joins translated partial shapes into column, slice and whole-region shapes, separately records canonical full-block cells, then joins the optimized partial result with one full-cell shape. Eligible entity geometry is joined into that result. Movement clips against the resulting single shape.

1.13.1 emits partial block shapes in X-fast order, followed by one accumulated full-block shape and then entity shapes. Its `VoxelShapes.calculateMaxOffset` (lines 194–205) tests the strict `abs(distance) < 1.0E-7` threshold before each next shape; 1.13 uses its single-shape axis routine (`VoxelShapes.java`, lines 207–266). Construction order, full-cell classification, query/exclusion/chunk/border gates and eligible entity inputs are part of the operation.

For the source-reviewed two-bottom-stone-slab setup, with partial shapes at X=2 and X=3, stated player box and X request 2, 1.13 leaves `4.9999999918171056e-8` while 1.13.1/1.13.2 return zero. This demonstrates a movement displacement difference for that conditional setup. It does not establish input-only reachability, general modern-client equivalence or runtime parity.
