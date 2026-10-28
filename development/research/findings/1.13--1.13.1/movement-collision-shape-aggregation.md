# Movement collision shape aggregation

1.13, Ornithe Feather: `net/minecraft/world/WorldView.java`, `getBlockCollisions` and `getCollisions`, lines 113–246; `net/minecraft/entity/Entity.java`, `move`, lines 578–598.
1.13.1, Ornithe Feather: `net/minecraft/world/WorldView.java`, `getBlockCollisions`, lines 112–152; `WorldAccess.java` inherits this path; `World.java`, `getCollisions`, lines 1755–1758; `net/minecraft/util/math/BlockPos.java`, `iterateRegionMutable`, lines 212–238. Relevant 1.13.2 block-collector bodies are identical.

1.13 visits blocks with X outer, Z middle and Y inner. It joins translated partial shapes into column, slice and whole-region shapes, separately records canonical full-block cells, then joins the optimized partial result with one full-cell shape. Eligible entity geometry is joined into that result. Movement clips against the resulting single shape.

1.13.1 emits partial block shapes in X-fast order, followed by one accumulated full-block shape and then entity shapes. Its `VoxelShapes.calculateMaxOffset` (lines 194–205) tests the strict `abs(distance) < 1.0E-7` threshold before each next shape; 1.13 uses its single-shape axis routine (`VoxelShapes.java`, lines 207–266). Construction order, full-cell classification, query/exclusion/chunk/border gates and eligible entity inputs are part of the operation.

The source-reviewed witness uses bottom stone slabs at `(2,64,0)` and `(3,64,0)`, an actor box with `minY=64.1`, `maxY=65.9`, `maxX=1.99999995`, and Z overlap with `[0,1]`, and a request `(2,0,0)`. Cells are loaded, no other collision shapes or entities are present, and the world border is distant. Both slabs lie inside the padded sweep and outside the starting-box exclusion. 1.13 leaves `4.9999999918171056e-8` while 1.13.1/1.13.2 return zero. This demonstrates a movement displacement difference for that conditional setup. It does not establish input-only reachability, general modern-client equivalence or runtime parity.
