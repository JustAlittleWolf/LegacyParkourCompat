# fence side collision differs beside a cauldron

1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public boolean shouldConnectTo(WorldView world, BlockPos pos) {`, lines 124-134.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {`, lines 59-80.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {`, lines 124-130.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `protected static boolean shouldConnectTo(Block block) {`, lines 132-138.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {`, lines 59-80.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/CauldronBlock.java`, `public FaceShape getFaceShape(WorldView world, BlockState state, BlockPos pos, Direction face) {`, lines 253-259.

Difference: The paired `FenceBlock.addCollisions` methods match.

Both resolve virtual properties when `forceShape` is false, add the center post, then add each rail selected by the resolved directional properties.

The paired player collision collector in `World.getCollisions(Box)` reaches block-state `addCollisions` with `forceShape=false` through the unchanged state/block dispatch.

In 1.11.2, `FenceBlock.shouldConnectTo` accepts a non-fence neighbor only if its material is solid-blocking and its state is a cube, except pumpkins; cauldron is `Material.IRON` but `isCube` returns false, so the east property is false.

In 1.12.2, the fence asks the east neighbor for its west `FaceShape`; `CauldronBlock.getFaceShape` returns `SOLID` for horizontal faces.

Cauldron is not in `Block.isExceptionForAttachment` or the additional explicit fence exceptions, so the 1.12.2 east property is true.

With a fence at `(0,0,0)` and a cauldron at `(1,0,0)`, the fence's 1.12.2 east collision rail is x `[0.625,1]`, z `[0.375,0.625]`, y `[0,1.5]`; the 1.11.2 center post in both versions is x/z `[0.375,0.625]`, y `[0,1.5]`.

The cauldron's five collision boxes are unchanged: a base to y `0.3125` and four walls to y `1.0`.

Its outline `getShape` is full cube in both versions, but movement collision dispatch uses its five `addCollisions` boxes.

Movement meaning: Both `PlayerEntity.tick()` bodies set `noClip` to `isSpectator()`.

Thus a non-spectator creative-flight player remains subject to `Entity.move` collision clipping. `PlayerEntity.moveRelative` takes the flight branch when `abilities.flying && !isRiding()` and still reaches movement through the inherited entity movement path; flight does not itself set `noClip`.

Concrete source-level geometry: a 0.6-wide player with feet at y `1.1`, centered at x `1.5`, z `0.5`, fits within the cauldron's interior width/depth of `0.75`.

The AABB clears every cauldron collision box vertically because its minimum y `1.1` exceeds their maximum y `1.0`.

At z `0.5`, its width overlaps the fence post and east rail.
