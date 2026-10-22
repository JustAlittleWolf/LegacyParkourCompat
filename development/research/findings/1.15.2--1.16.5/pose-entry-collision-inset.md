# Pose entry collision inset
1.15.2, Mojmap: `net/minecraft/world/entity/Entity.java`, `canEnterPose(Pose)`, lines 1605–1607; `net/minecraft/world/entity/player/Player.java`, `updatePlayerPose()`, lines 357–384.
1.16.5, Mojmap: `net/minecraft/world/entity/Entity.java`, `canEnterPose(Pose)`, lines 1640–1642; `net/minecraft/world/entity/player/Player.java`, `updatePlayerPose()`, lines 350–377.
Difference: 1.15.2 calls `noCollision` on the full candidate pose bounding box. 1.16.5 first deflates that box by `1.0E-7`. The player tick's pose-selection order and fallback from the desired pose to crouching and then swimming are otherwise the same in these methods.
Movement meaning: When a candidate pose box intersects a collision shape only within the removed `1.0E-7` margin, 1.16.5 accepts the pose while 1.15.2 rejects it. The accepted pose changes the player's dimensions and bounding box through `setPose`/`refreshDimensions`, affecting subsequent movement collision resolution.
