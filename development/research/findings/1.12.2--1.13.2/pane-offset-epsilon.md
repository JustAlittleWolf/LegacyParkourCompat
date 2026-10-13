# F-PANE-OFFSET-EPSILON — pane center collision clipped differently at an axis boundary (float and shape corrected snapshot r3)

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/PaneBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {`, lines 54-75.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/util/math/Box.java`, `public double intersectX(Box box, double limit) {`, lines 185-203.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/PaneBlock.java`, `public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos) {`, lines 82-84.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShape.java`, `public double calculateMaxDistance(Direction.Axis axis, Box box, double maxDist) {`, lines 184-186.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShape.java`, `protected double calculateMaxDistance(AxisCycle axisCycle, Box box, double maxDist) {`, lines 188-243.

Difference: Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player positioned by `Entity.setPosition(0.13749,64.2,0.13750005)`.

The exact resulting double box is X `[-0.16251001192092895,0.43749001192092896]`, Y `[64.2,65.99999995231629]`, Z `[-0.16249996192092894,0.43750006192092894]`, using the float `f` and `g` conversions and operation order cited above.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity damping, and `0.003125` is not below the strict `0.003` cutoff.

Zero input adds no velocity.

On the ordinary airborne, non-liquid, non-fall-flying path, the next `moveRelative` call therefore passes `(0.003125,0,0)` to `Entity.move` in both versions.

Movement meaning: Use the registered `glass_pane` at `(0,64,0)`, with AIR in the four horizontal neighbor positions and no water.

The 1.12.2 state resolves to four false connection flags; the 1.13.2 placement/default path yields the same flags and `WATERLOGGED=false`.

Both providers contribute only the center pane voxel for this state.

Use a locally controlled airborne player positioned by `Entity.setPosition(0.13749,64.2,0.13750005)`.

The exact resulting double box is X `[-0.16251001192092895,0.43749001192092896]`, Y `[64.2,65.99999995231629]`, Z `[-0.16249996192092894,0.43750006192092894]`, using the float `f` and `g` conversions and operation order cited above.

No other collision shapes, entities, or world-border contact are present.

Set horizontal input to zero and deliver the equivalent entity-velocity packet with X integer `25` and Y/Z integers `0`.

The paired packet handler and `lerpVelocity` set `velocityX=25/8000.0=0.003125`.

The local-player path skips remote-velocity d.
