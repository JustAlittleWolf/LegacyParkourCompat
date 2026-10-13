# The 1.11.2 query includes a lower-row moving-fence collision

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 903-965.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1022-1059.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 58-76.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/world/World.java`, `private boolean getCollisions(@Nullable Entity entity, Box shape, boolean enforceWorldBorder, @Nullable List<Box> collisions) {`, lines 960-1011.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 1013-1036.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1058-1060.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public void addCollisions(World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 318-346.

Difference: Both piston paths can place a movable fence state in a `MOVING_BLOCK` cell: `PistonBaseBlock.canMoveBlock` permits the registered oak fence when its ordinary border/height guards pass, and the push path stores the moved state in a `MovingBlockEntity`.

The oak fence uses `Material.WOOD` (whose default piston behavior is `NORMAL`), has no block entity, and has a collision post from local x/z `0.375` to `0.625` and local y `0.0` to `1.5`.

Use a moving-block cell at `(x,y,z)` with an extending piston facing WEST at progress `0.5`, and a player query box `[x+1.0,x+1.6] × [y+1.3,y+3.1] × [z+0.2,z+0.8]`.

The moved fence's progress offset is `WEST.offsetX * (0.5F - 1.0F) = +0.5`.

Its center collision post therefore occupies `[x+0.875,x+1.125] × [y,y+1.5] × [z+0.375,z+0.625]`, which strictly intersects the query box.

For that query, the moving-block cell is on the x perimeter (`o == i`) but its z coordinate is interior. 1.10.2's scan has `q == 1` and rejects the lower padded y row `r == k` with `q <= 0 || r != k && r != l - 1`. 1.11.2's shared helper reaches the same cell and admits that row because it rejects only the upper row, `q != l - 1`. 1.11.2 then calls the moving entity's collision provider, which translates the fence collision boxes by progress and appends the intersecting box.

In 1.10.2 the cell is not visited, so that candidate contributes no block collision.

This is independent of the retracting piston shape change in F-05.

Movement meaning: The source path is piston movement eligibility and moved-state storage -> `World.getCollisions` block-state dispatch -> `MovingBlock`/`MovingBlockEntity` collision boxes -> the list consumed by `Entity.move`. 1.10.2 locally controlled player reaches inherited `Entity.move` through the living-player travel path; the collision list is therefore a direct player movement input.

The pair's S4-piston and S5-fence slices cover the paired piston state path and fence post geometry. `Material.WOOD` has the default `NORMAL` move behavior, the base `hasBlockEntity` flag defaults false, and `canMoveBlock` admits the fence under valid height/world-border conditions.

Source proves that 1.11.2 supplies this collision box to the player query while 1.10.2 skips the candidate cell.

If the player is moving into the box, it can constrain the movement clipping result; no final position or observed trajectory is claimed.
