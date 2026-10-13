# Player collision candidate collection

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(MoverType moverType, double x, double y, double z) {`, lines 462-795.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/world/World.java`, `private boolean getCollisions(@Nullable Entity entity, Box shape, boolean enforceWorldBorder, @Nullable List<Box> collisions) {`, lines 964-1015.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 1017-1040.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1062-1064.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(MoverType moverType, double x, double y, double z) {`, lines 473-776.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/world/World.java`, `public Stream<VoxelShape> getCollisions(@Nullable Entity entity, VoxelShape shape, VoxelShape excludeBlock, Set<Entity> excludeEntities) {`, lines 1755-1758.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/world/WorldView.java`, `default Stream<VoxelShape> getBlockCollisions(VoxelShape shape, VoxelShape excludeBlock, boolean checkWorldBorder) {`, lines 112-152.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShapes.java`, `public static double calculateMaxOffset(Direction.Axis axis, Box box, Stream<VoxelShape> shapes, double maxDist) {`, lines 194-206.

Difference: 1.12.2 collects translated block/entity Boxes. 1.13.2 collects VoxelShapes from a swept query with 1.0E-7 offsets, excludes the initial shape, and merges out-of-border cells into a boundary shape. Same-vehicle entity exclusions remain part of the paired collector contract.

Movement meaning: These collectors supply candidates to player movement. The independent axis epsilon consequences are recorded in F-VOXEL-OFFSET-EPSILON and F-PANE-OFFSET-EPSILON-r3; representation change alone does not establish every trajectory difference.

Original evidence: [F-COLLISION-REPRESENTATION.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-COLLISION-REPRESENTATION.md).
