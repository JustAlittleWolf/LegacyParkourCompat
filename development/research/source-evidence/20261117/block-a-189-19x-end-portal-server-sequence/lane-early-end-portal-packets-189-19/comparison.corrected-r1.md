# Corrected comparison r1: 1.8.9 → 1.9 End-portal transfer packets

Mappings are Ornithe Feather on both releases. This correction re-traces the `dimension` argument passed to the helper and supersedes the corresponding End-target statements in the original `comparison.md` and `finding-end-portal-transfer.md`; those originals remain unchanged.

## Ordinary player entering the End

Route context: the server-side End portal calls `entity.changeDimension(1)`. The bounded path below follows the ordinary player into `ServerPlayerEntity.changeDimension` and then `PlayerManager.changeDimension`.

### The helper argument and destination source on both releases

In both versions, `PlayerManager.changeDimension(ServerPlayerEntity player, int dimension)` first captures `int i = player.dimension`, then writes the requested target to `player.dimension`, sends the respawn packet, and later calls the helper with `this.changeDimension(player, i, serverWorld, serverWorld2)`. For an ordinary overworld-to-End transfer, the helper's `dimension` parameter is therefore the old dimension `0`, while `entity.dimension` has already been set to target dimension `1`.

Consequently, in both helpers `entity.dimension == -1` and `entity.dimension == 0` are false, so control enters the final `else`. Its selection tests the helper argument: `dimension == 1` is false because the argument is old dimension `0`; it therefore selects `toWorld.getForcedSpawnPoint()`, not `toWorld.getSpawnPoint()`. It reads that block position's X/Y/Z, sets angles to `90.0F, 0.0F`, and, because the old-dimension argument is not 1, the later `if (dimension != 1)` block also runs. That block clamps the integer-cast X/Z to `[-29999872, 29999872]`, then, when alive, sets position/angles, invokes the destination portal forcer, adds the entity to the target world and ticks it. The helper finally calls `entity.setWorld(toWorld)`.

Thus the optional 1.8.9 pre-respawn teleport and the helper's initial final-target coordinates read the same configured destination `getForcedSpawnPoint()` source value. The helper's clamp/portal-forcer/tick work follows the target selection, so the later packet's `player.x/y/z` are the resulting player fields after those operations; source alone does not justify asserting that the emitted final packet coordinates are always the raw block coordinates.

### 1.8.9 ordered writes

- `server/entity/living/player/ServerPlayerEntity.java:447-459`, `changeDimension(int)`: on dimension 0 → 1, read `this.server.getWorld(dimension).getForcedSpawnPoint()`; if non-null, call `networkHandler.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0F, 0.0F)`, then call `PlayerManager.changeDimension`.
- `server/PlayerManager.java:435-458`, `changeDimension(ServerPlayerEntity,int)`: capture old `i`; set the target dimension; send `PlayerRespawnS2CPacket(targetDimension, player.world.getDifficulty(), player.world.getData().getGeneratorType(), player.interactionManager.getGameMode())`; remove/re-add around the transfer; pass old `i` to the helper; call `onChangedDimension`; then send an absolute move by calling `networkHandler.teleport(player.x, player.y, player.z, player.yaw, player.pitch)`; set interaction-manager world; then send world/player info and status effects.
- `server/PlayerManager.java:460-513`, helper: with old argument 0 and already-updated `entity.dimension` 1, choose `toWorld.getForcedSpawnPoint()` as explained above, then run the `dimension != 1` placement/clamp path and set the target world.
- `server/network/handler/ServerPlayNetworkHandler.java:364-397`, `teleport`: update server player position/angles before sending `PlayerMoveS2CPacket(x,y,z,yaw,pitch,relativeArgs)`, with no teleport ID. The optional first move carries the raw forced-point integer X/Y/Z and yaw/pitch 0; the later move carries the resulting `player.x/y/z/yaw/pitch` after helper placement.

### 1.9 ordered writes

- `server/entity/living/player/ServerPlayerEntity.java:471-478`, `changeDimension(int)`: on dimension 0 → 1, increment the End-entry stat and call `PlayerManager.changeDimension`; there is no forced-spawn read or preliminary teleport.
- `server/PlayerManager.java:488-513`, `changeDimension(ServerPlayerEntity,int)`: capture old `i`, set the target dimension, send the same respawn constructor fields, call `updatePermissions`, remove/re-add around transfer, pass old `i` to the helper, call `onChangedDimension`, then send the final absolute move using the resulting player fields; set interaction-manager world; send abilities, then world/player info and status effects.
- `server/PlayerManager.java:515-568`, helper: the same old-argument-0 / target-entity-dimension-1 path selects `toWorld.getForcedSpawnPoint()` and runs the `dimension != 1` clamp/portal-forcer/add/tick placement path. This is the same source value selected by the 1.8.9 optional pre-packet when its non-null gate passes.
- `server/network/handler/ServerPlayNetworkHandler.java:490-516`, `teleport`: update server position/angles and send the corresponding move packet with the incremented `requestedTeleportId`; that helper records the requested position/time and wraps the ID to 0 at `Integer.MAX_VALUE`.
- After the manager transfer returns, `ServerPlayerEntity.changeDimension` sends `WorldEventS2CPacket(1032, BlockPos.ORIGIN, 0, false)`.

The packet difference is that 1.8.9 may send a forced-point absolute move before Respawn, while 1.9 starts with Respawn. Both later target the same forced-spawn accessor in transfer placement and send a final move from the resulting player coordinates; 1.9 adds its teleport ID and later client acknowledgment.

## Client absolute-move handling

- 1.8.9 `client/network/handler/ClientPlayNetworkHandler.java:516-565`: relative position components add to current position; absolute components zero their corresponding velocity; relative angles add current angles; apply position/angles; send `PlayerMoveC2SPacket.PositionAndAngles(player.x, player.getShape().minY, player.z, player.yaw, player.pitch, false)`; initialize first-position state if needed.
- 1.9 `client/network/handler/ClientPlayNetworkHandler.java:545-594`: same position/angle gates and velocity writes, then apply; send `AcceptTeleportC2SPacket(packet.getTeleportId())` before the same position/angle response; initialize first-position state if needed.
- The two End-arrival move writes have empty relative-argument sets. Each is handled as absolute by the client, zeroing all three velocity components. 1.8.9 sends no acknowledgment in this handler.

## Exact source hashes

| Release | Relative file | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/server/PlayerManager.java` | `0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d` |
| 1.9 | `net/minecraft/server/PlayerManager.java` | `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86` |
| 1.8.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81` |
| 1.9 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` |
| 1.8.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` |
| 1.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| 1.8.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| 1.9 | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` |