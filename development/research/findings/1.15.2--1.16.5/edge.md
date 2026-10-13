# edge restraint applies during a near-ground fall

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/player/Player.java`, `maybeBackOffFromEdge`, lines 1002–1049.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/player/Player.java`, `maybeBackOffFromEdge`, lines 1010–1065.

Difference: The x/z reduction loops are unchanged: each component is backed off in 0.05 increments while a box shifted down by `maxUpStep` has no collision, then the reduced vector is returned. 1.16.5 changes when the loops run. 1.15.2 requires `onGround`; 1.16.5 replaces that requirement with a near-ground test and additionally excludes flying players. Therefore for a non-flying player using SELF/PLAYER movement while shift is held, not currently on ground, with `fallDistance < maxUpStep` and a collision found by the helper's downward box query, 1.16.5 enters the edge-restraint loops while 1.15.2 returns the original vector.

Movement meaning: `Entity.move()` calls the virtual `maybeBackOffFromEdge(vec3,moverType)` before collision resolution in both endpoint sources.
