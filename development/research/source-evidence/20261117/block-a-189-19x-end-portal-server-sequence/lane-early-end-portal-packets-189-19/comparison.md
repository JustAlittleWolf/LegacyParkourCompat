# Comparison: 1.8.9 → 1.9 End-portal transfer packets

Mappings are Ornithe Feather on both releases. File line ranges below are inclusive.

## Ordinary player entering the End

Route context: `block/EndPortalBlock.java`, `onEntityCollision` calls `entity.changeDimension(1)` on the server for the eligible portal contact. The comparison below is bounded to the ordinary `ServerPlayerEntity` transfer branch and does not assess unrelated portal routes.

### 1.8.9

- `server/entity/living/player/ServerPlayerEntity.java:447-459`, `changeDimension(int)`: for dimension 0 → 1, increment the End-entry stat, read `getForcedSpawnPoint()`, and, when non-null, call `networkHandler.teleport(forcedX, forcedY, forcedZ, 0.0F, 0.0F)`. Then call `PlayerManager.changeDimension`.
- `server/PlayerManager.java:435-458`, `changeDimension(ServerPlayerEntity,int)`: set the target dimension; send `PlayerRespawnS2CPacket(dimension, old-world difficulty, old-world generator type, gamemode)`; remove/re-add the player around transfer; call `onChangedDimension`; call `networkHandler.teleport(player.x, player.y, player.z, player.yaw, player.pitch)`; set the interaction manager's world; then send world/player info and status effects.
- `server/PlayerManager.java:460-513`, `changeDimension(Entity,int,ServerWorld,ServerWorld)`: because entering dimension 1 is neither source dimension -1 nor 0, choose `toWorld.getSpawnPoint()`; assign `d=blockPos.getX()`, `entity.y=blockPos.getY()`, `e=blockPos.getZ()`; set angles to `90.0F, 0.0F`; for target dimension 1 the later `dimension != 1` placement/clamp block is skipped; finally set the entity world. This is the final End target used by the subsequent network-handler teleport.
- `server/network/handler/ServerPlayNetworkHandler.java:364-397`, `teleport`: resolve relative coordinates/angles, update the server player position/angles, then send `PlayerMoveS2CPacket(x,y,z,yaw,pitch,relativeArgs)` with no teleport ID.

Thus, with a non-null forced spawn, source call/send order has an absolute forced-spawn move (integer X/Y/Z, yaw/pitch 0) before the respawn packet, followed later by an absolute move at the End spawn coordinates with yaw 90 and pitch 0. If forced spawn is null, the first move is absent. Both absolute moves enter the client handling path below.

### 1.9

- `server/entity/living/player/ServerPlayerEntity.java:471-478`, `changeDimension(int)`: the dimension 0 → 1 branch increments the End-entry stat and calls `PlayerManager.changeDimension`; it has no forced-spawn lookup or preceding teleport.
- `server/PlayerManager.java:488-513`, `changeDimension(ServerPlayerEntity,int)`: send the same respawn constructor fields first, then call `updatePermissions`, remove/re-add around transfer, call `onChangedDimension`, and issue the final `networkHandler.teleport(player.x, player.y, player.z, player.yaw, player.pitch)`. It then sets the interaction-manager world, sends abilities, then world/player info and status effects.
- `server/PlayerManager.java:515-568`, `changeDimension(Entity,int,ServerWorld,ServerWorld)`: the End target calculation and final angles are the same as 1.8.9: `getSpawnPoint()` X/Y/Z and yaw `90.0F`, pitch `0.0F`; the `dimension != 1` placement/clamp block is skipped.
- `server/network/handler/ServerPlayNetworkHandler.java:490-516`, `teleport`: resolve relative coordinates/angles, increment `requestedTeleportId` (wrapping from `Integer.MAX_VALUE` to 0), record the requested position/time, update server position/angles, then send `PlayerMoveS2CPacket(x,y,z,yaw,pitch,relativeArgs,requestedTeleportId)`.
- After `PlayerManager.changeDimension` returns, `ServerPlayerEntity.changeDimension` sends `WorldEventS2CPacket(1032, BlockPos.ORIGIN, 0, false)`.

The optional pre-respawn forced-spawn move is absent in 1.9. The final absolute End move still uses `toWorld.getSpawnPoint()` and the same angles. 1.9 adds the listed permission/ability and teleport-ID protocol operations; this comparison does not infer runtime scheduling beyond source call/send order.

## Client application of absolute player move

- 1.8.9 `client/network/handler/ClientPlayNetworkHandler.java:516-565`, `handlePlayerMove`: read packet position and angles; add the current component only for a relative flag, otherwise set that velocity component to `0.0`; apply relative pitch then relative yaw; call `updatePositionAndAngles`; send `PlayerMoveC2SPacket.PositionAndAngles(player.x, player.getShape().minY, player.z, player.yaw, player.pitch, false)`; then initialize first-position state if needed.
- 1.9 `client/network/handler/ClientPlayNetworkHandler.java:545-594`, `handlePlayerMove`: the same coordinate/angle branches, velocity zeroes, position update, and position-response fields; immediately after applying the update, send `AcceptTeleportC2SPacket(packet.getTeleportId())`, then send the same position/angle response, then initialize first-position state if needed.
- For the transfer moves described above, `relativeArgs` is empty, so all three velocity components are zeroed and the packet position/angles are applied as absolute values. The 1.8.9 client has no teleport-ack call in this handler; 1.9 acknowledges the packet ID before sending its position/angle response.

## Finding drafts

- `finding-end-portal-transfer.md`: conditional pre-respawn forced-spawn move removed in 1.9; final End target retained.
- `finding-client-absolute-move-ack.md`: same absolute position/velocity writes; 1.9 adds a teleport acknowledgment before the position/angle response.

## Relevant source hashes

SHA-256 values are for exact decompiled files in the verified source trees.

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/block/EndPortalBlock.java` | `1cb419f44592e68244e06b34799c6354a2bcc3d08bd849e0709fca36b54e1e40` |
| 1.9 | `net/minecraft/block/EndPortalBlock.java` | `5e04abb22c37fb6c8af646d93702b54c5abac4737edd92a8b2fc5c593bbf2ba5` |
| 1.8.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81` |
| 1.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` |
| 1.8.9 | `net/minecraft/server/PlayerManager.java` | `0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d` |
| 1.9 | `net/minecraft/server/PlayerManager.java` | `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86` |
| 1.8.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` |
| 1.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| 1.8.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| 1.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |