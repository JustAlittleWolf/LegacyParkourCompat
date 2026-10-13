# Anvil mutable collision bounds

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/AnvilBlock.java`, `public void updateShape(WorldView world, BlockPos pos) {`, lines 67-74.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/AnvilBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 98-101.

Difference: 1.8.9 AnvilBlock.updateShape writes shared singleton bounds; generic collision dispatch does not refresh them. 1.9.4 getShape selects the slab from the queried facing. Conditional on the latest older writer selecting X while the queried anvil faces Z, the supplied box differs.

Movement meaning: The preserved isolated airborne witness clips dx=0.4 to 0.1 versus 0.225. This is conditional on writer history and no other clipping candidate, not stable state geometry.

Original evidence: [WORLD-25-mutable-bound-writers-anvil-chest.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-25-mutable-bound-writers-anvil-chest.md).
