# F-WALL-THREE-ARM-CORNER-GAPS — three-arm wall shape leaves a reachable collision corner

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public Box getCollisionShape(BlockState state, WorldView world, BlockPos pos) {`, lines 100-103.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 84-87.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `private boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {`, lines 146-152.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos) {`, lines 37-39.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos) {`, lines 32-34.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `private boolean shouldConnectTo(BlockState state, FaceShape shape) {`, lines 51-55.

Difference: For NORTH+EAST+SOUTH connected and WEST open, both neighbor formulas produce those three connection bits and `UP=true` when the block above is air. 1.12.2's horizontal direction bits select shape index 13.

Its collision Box is X[4,16] × Z[0,16] × Y[0,24] in sixteenth-block coordinates (the collision table raises the outline's 16/16 height to 24/16).

1.13.2's `UP=true` shape is the OR of the center X[4,12] × Z[4,12], north arm X[5,11] × Z[0,11], south arm X[5,11] × Z[5,16], and east arm X[5,16] × Z[5,11], all with Y[0,24].

This leaves the northwest corner cell region X[4,5] × Z[0,4] empty, while 1.12.2's single Box fills it.

Coordinates above are inclusive shape boundaries; collision requires positive-volume overlap.

Movement meaning: Place open fence gates immediately NORTH, EAST, and SOUTH of the wall, with each facing chosen so its face toward the wall is `MIDDLE_POLE`; leave WEST and the block above air.

Both versions' wall helper accepts `MIDDLE_POLE` from a fence gate, and the open gate collision getters return empty shapes.

With three connections and air above, the 1.12.2 virtual-property formula and 1.13.2 placement/update formula both give `UP=true`.

Put a 0.6-wide player at y=0 with center x=z=-0.3, so its initial X and Z maxima are both 0.

Request movement `(dx,dy,dz)=(0.3,0,0.24)`, with sneaking and on-ground false.

The shared resolver processes Y, then X, then Z.

During X, the player only touches the wall's z=0 boundary, so 1.12.2 does not clip X.

After X, its box overlaps 1.12.2's wall in X; the positive Z request meets the wall at z=0 and clips to zero.
