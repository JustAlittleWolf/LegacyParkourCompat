# Cake collision height
1.7.10, Ornithe Feather: `net/minecraft/block/CakeBlock.java`, `getCollisionShape(World,int,int,int)`, lines 41–47.
1.8, Ornithe Feather: `net/minecraft/block/CakeBlock.java`, `getCollisionShape(World,BlockPos,BlockState)`, lines 43–48.
Difference: both preserve the bite-dependent X start `(1 + bites * 2) / 16.0F` and 1/16 side inset. The old max Y is `y + 0.5F - 0.0625F` (7/16); the new max Y is `pos.getY() + 0.5F` (1/2).
Movement meaning: the reachable collision top is 1/16 higher in 1.8, changing horizontal clipping and the height available to the step solver when a player moves into cake. This is a predicted effect from the paired boxes; no trajectory was run.
