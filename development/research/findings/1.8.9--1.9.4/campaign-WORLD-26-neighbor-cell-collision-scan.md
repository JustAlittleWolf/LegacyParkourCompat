# WORLD-26: neighbor-cell collision scan and protruding piston-head arms

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(Entity entity, Box shape) {`, lines 891-945.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List<Box> collisions, Entity entity) {`, lines 87-93.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 899-961.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1018-1055.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 65-68.

Difference: - Source hashes: 1.8.9 `World.java` , `Block.java` , `PistonHeadBlock.java` ; 1.9.4 `World.java` , `Block.java` , `PistonHeadBlock.java` .

Movement meaning: - Disposition: this is a conditional source-level clipped-displacement difference for the specified local move and collision context: 1.8.9 retains `dx=0.2`, 1.9.4 clips to `dx=0.05`.

Full candidate enumeration, other block/entity collisions, world-border and loaded-chunk interactions, and step-candidate outcomes remain dependencies of the broad COLL-01 slice.

Original evidence: [WORLD-26-neighbor-cell-collision-scan.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-26-neighbor-cell-collision-scan.md).
