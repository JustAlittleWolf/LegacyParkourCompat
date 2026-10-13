# Candidate: cross-dimension teleports move players spectating the entity

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, teleportCrossDimension 2681-2719.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, teleportCrossDimension 2852-2885; teleportSpectators 2888-2894.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/server/level/ServerPlayer.java`, teleport 976-1032.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/server/level/ServerPlayer.java`, teleport 1007-1063; teleport 1007-1064.

Difference: - Exact precondition: a server-level entity successfully completes a cross-dimension `TeleportTransition` while one or more source-level `ServerPlayer` instances have `getCamera() ==` the entity being moved. In 1.21.8, those spectator players receive the same transition through `ServerPlayer#teleport`; 1.21.5 leaves them in their original dimension. ServerPlayer-initiated cross-dimension teleport has the corresponding direct helper call in 1.21.8. - Analysis: this is a direct player position/dimension writer triggered by a native dimension-transfer event. It does not change the target entity's ordinary movement or client correction formulas. The movement consequence is the newly added player relocation and camera reset for active spectators.

Movement meaning: - Analysis: this is a direct player position/dimension writer triggered by a native dimension-transfer event. It does not change the target entity's ordinary movement or client correction formulas. The movement consequence is the newly added player relocation and camera reset for active spectators.
