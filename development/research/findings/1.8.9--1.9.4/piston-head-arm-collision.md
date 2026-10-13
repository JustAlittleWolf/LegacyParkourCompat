# piston-head arm collision boxes

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List<Box> collisions, Entity entity) {`, lines 87-93.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `private void setArmShapeForCollisions(BlockState state) {`, lines 95-120.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 65-68.

Difference: - 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java` `addCollisions()` selects the face shape, dispatches it, selects the arm shape, dispatches it, then restores full mutable bounds (87-93); the six arm boxes are selected in `setArmShapeForCollisions()` (95-120).

- 1.9.4 source: paired `PistonHeadBlock.java` `addCollisions()` adds the state face shape plus one direction-indexed arm constant (65-85; constants 25-36).

Movement meaning: - Disposition: paired head-face boxes match, while each direction's connecting-arm collision box differs.

The states and adjacent piston base are not folded into this provider comparison; no player clipping outcome is claimed.
