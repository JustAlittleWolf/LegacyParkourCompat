# Carpet collision height
1.7.10, Ornithe Feather: `net/minecraft/block/CarpetBlock.java`, `getCollisionShape(World,int,int,int)`, lines 29–32.
1.8, Ornithe Feather: `net/minecraft/block/CarpetBlock.java` has no collision override; `net/minecraft/block/Block.java`, `getCollisionShape(World,BlockPos,BlockState)`, lines 299–301 is inherited. Carpet remains registered as id 171/key `carpet` in both versions (`Block.java:662` / `:1089`).
Difference: the 1.7.10 override sets max Y to `y + 0 * 0.0625F`, equal to min Y. `Box.intersects` rejects zero-volume overlap using `<=`/`>=` comparisons (`util/math/Box.java:172–176` / `:153–157`). With no override in 1.8, the base provider uses constructor maxY `0.0625F`, producing a 1/16-high collision box.
Movement meaning: a player querying carpet collision can pass through its surface in the old profile and clip/step against it in 1.8. This is a predicted collision difference; no trajectory was run.
