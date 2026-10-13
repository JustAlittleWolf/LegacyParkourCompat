# The 1.11.2 farmland collapse raises intersecting players to the dirt top

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 32-34.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {`, lines 61-71.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 30-32.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `private void setDirt(World world, BlockPos pos) {`, lines 71-79.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {`, lines 59-69.

Difference: Both endpoints register farmland as block ID 60 and invoke `FarmlandBlock.onFallenOn` when `Entity.move` lands with positive accumulated fall distance.

The collapse guard is the same: server world, `random.nextFloat() < fallDistance - 0.5F`, a `LivingEntity`, a player or mob-griefing permission, and `width * width * height > 0.512F`.

For a normal player with fall distance `2.0F`, the random comparison is always true because `nextFloat()` is below `1.0F`.

1.10.2 replaces the farmland block with dirt and then follows the base fall callback. 1.11.2 performs the same block replacement in `setDirt`, obtains the new dirt block's full collision box, queries non-spectator entities intersecting that box, and calls `setPosition(entity.x, box.maxY, entity.z)` on each. 1.10.2 player landing at the center of the farmland block has its feet at the farmland collision top, `posY + 0.9375`; its box intersects the new dirt box up to `posY + 1.0`, so 1.11.2 includes that player and writes its y coordinate to `posY + 1.0`.

Movement meaning: The local/server player movement path clips downward movement, sets `onGround`, reads the landed block at `floor(y - 0.2F)`, and calls `checkFallDamage`.

With positive fall distance, `checkFallDamage` dispatches to the landed block's `onFallenOn`. 1.10.2 player landing on registered farmland reaches the unchanged collapse guard. 1.11.2's dirt-box query includes intersecting non-spectator players and directly updates their position; `Entity.setPosition` rebuilds the bounding box from that new y coordinate.

The source proves an immediate server-side player y-position write to the new dirt top in 1.11.2 for the stated conditions; 1.10.2 makes no corresponding position write.

This finding covers only that direct movement response.

The adjacent fall-damage call remains excluded from the campaign's damage simulation scope.
