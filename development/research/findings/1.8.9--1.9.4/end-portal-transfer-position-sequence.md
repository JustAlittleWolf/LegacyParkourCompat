# End portal transfer has an extra forced-spawn teleport in 1.8.9

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(ServerPlayerEntity player, int dimension) {`, lines 435-458.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(Entity entity, int dimension, ServerWorld fromWorld, ServerWorld toWorld) {`, lines 460-513.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, `public void changeDimension(int dimension) {`, lines 440-464.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(ServerPlayerEntity player, int dimension) {`, lines 489-514.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/server/PlayerManager.java`, `public void changeDimension(Entity entity, int dimension, ServerWorld fromWorld, ServerWorld toWorld) {`, lines 516-569.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, `public Entity changeDimension(int dimension) {`, lines 458-488.

Difference: When 1.8.9's forced spawn point is non-null, 1.8.9 first teleports the player to that coordinate and sends the corresponding server-to-client position packet.

The subsequent `PlayerManager` transfer helper replaces that position with the destination world's End spawn point, and the manager sends its final teleport packet. 1.9.4 skips the early forced-spawn teleport and performs the shared destination-spawn assignment/final teleport, while also using its newer teleport acknowledgement state.

The source establishes an extra intermediate position write and correction packet in 1.8.9; it does not establish a client tick or player trajectory between packets.

The final destination position writer is the same.

Movement meaning: When 1.8.9's forced spawn point is non-null, 1.8.9 first teleports the player to that coordinate and sends the corresponding server-to-client position packet.

The subsequent `PlayerManager` transfer helper replaces that position with the destination world's End spawn point, and the manager sends its final teleport packet. 1.9.4 skips the early forced-spawn teleport and performs the shared destination-spawn assignment/final teleport, while also using its newer teleport acknowledgement state.

The source establishes an extra intermediate position write and correction packet in 1.8.9; it does not establish a client tick or player trajectory between packets.

The final destination position writer is the same.
