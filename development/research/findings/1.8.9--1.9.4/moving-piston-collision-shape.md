# moving piston collision shape

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(World world, BlockPos pos, BlockState state) {`, lines 118-130.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(World world, BlockPos pos, BlockState movedState, float progress, Direction facing) {`, lines 162-197.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(BlockState state, World world, BlockPos pos) {`, lines 134-137.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos) {`, lines 79-81.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos, float progress) {`, lines 83-88.

Difference: - 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java` `getCollisionShape(world,pos,state)` samples `getProgress(0.0F)`, reverses it for extension, and asks for one moved-state collision box (118-130); the helper gets the moved block's collision shape and adjusts only the bound on each facing axis (162-197).

- 1.9.4 source: paired `MovingBlock.java` Its `getCollisionShape()` delegates to `MovingBlockEntity.getShape(world,pos)` (134-137).

The paired entity `getShape()` unions shapes at current `progress` and `lastProgress` (79-80), while each shape uses the moved state's outline shape translated by `getAmountExtended(progress) * facing` (83-87). `getAmountExtended()` returns `progress - 1` for extension or `1 - progress` for retraction (75-77).

Movement meaning: - Disposition: source-level moving-collision provider delta established for an advancing full-cube state.

The distinct 1.8.9 single-bound adjustment and 1.9.4 current/previous-shape union, plus collision-shape versus outline-shape provider, prevent an equivalence claim.
