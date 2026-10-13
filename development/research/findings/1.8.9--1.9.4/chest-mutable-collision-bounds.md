# Chest mutable collision bounds

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/ChestBlock.java`, `public void updateShape(WorldView world, BlockPos pos) {`, lines 56-68.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/ChestBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 63-73.

Difference: 1.8.9 ChestBlock.updateShape writes shared singleton bounds from north/south/west/east neighbor precedence. Generic collision dispatch consumes those bounds. 1.9.4 resolves adjacency at the queried chest position.

Movement meaning: Conditional on a latest north-neighbor writer and a later isolated chest query, the preserved isolated airborne witness clips dz=0.4 to 0.2 versus 0.2625. Chest and anvil writers are independent providers.
