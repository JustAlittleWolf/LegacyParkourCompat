# Roster erratum 4 — dynamic piston state lookup

To confirm that the saved moving-piston block entity shape reaches the collision query rather than a cached static shape, add `net/minecraft/world/level/block/Block.java`. Pair it with the already-listed `Blocks.java`, `BlockBehaviour.java`, `BlockState.java`, and `MovingPistonBlock.java`: the moving-piston registry properties set `dynamicShape()`, the state-cache initializer skips cache creation for such blocks, and the state collision-shape accessor delegates to the block implementation. This remains part of the assigned provider route.
