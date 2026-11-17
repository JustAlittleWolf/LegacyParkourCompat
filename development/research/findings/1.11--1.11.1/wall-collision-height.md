# Ordinary wall collision boxes become shorter

1.11, Ornithe Feather Gen2 build 2. `net/minecraft/world/World.java`, `getCollisions(Entity, Box)`, lines 960–995. `net/minecraft/block/Block.java`, `addCollisions`, lines 353–355. `net/minecraft/block/WallBlock.java`, `COLLISION_SHAPES`, lines 45–62, and `getCollisionShape`, lines 88–93.

1.11.1, Ornithe Feather Gen2 build 2. `net/minecraft/world/World.java`, shared collision collector, lines 960–1015, including the ordinary `forceShape=false` call at line 997. `net/minecraft/block/WallBlock.java`, `SHAPES` and `COLLISION_SHAPES`, lines 30–64, and `addCollisions`, lines 92–98.

In 1.11, the ordinary collector calls the inherited block callback, which reads the wall's tall collision array. Each horizontal connection mask selects a box of height 1.5. In 1.11.1, the wall overrides the callback and resolves its ordinary neighbor properties before adding the short `SHAPES` entry. Straight north–south and east–west masks 5 and 10 have height 0.875. The other fourteen masks have height 1.0. X/Z bounds and horizontal-mask indexing agree, and `UP` does not enter the index. The direct collision-shape getter still returns the tall array in 1.11.1.

A player collision query that strictly overlaps the upper portion of the old box can collect this wall in 1.11 while the corresponding 1.11.1 callback contributes no box. This describes the ordinary collector for the loaded, stationary wall.
