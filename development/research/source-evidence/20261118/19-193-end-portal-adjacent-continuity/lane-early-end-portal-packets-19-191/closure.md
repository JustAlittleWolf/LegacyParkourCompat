# 1.9 → 1.9.1 End portal transition and client correction closure

## Scope and verdict

Exact pair: Minecraft 1.9 → 1.9.1, Ornithe Feather mappings, both `build.2`. This closes only (1) the ordinary logical-server Overworld→End EndPortalBlock-to-PlayerManager transition and correction send sequence and (2) the dependent client correction response packet order. No runtime trajectory or destination-world geometry is assessed.

**Verdict: exact no-difference closure for the assigned slices.** Every relied-on Java source file below has the same programmatically measured SHA-256 in both releases; the actual digest matches the version-root provenance manifest on each side. The source facts below therefore apply identically to 1.9 and 1.9.1.

## Exact source identity

Both version roots contain `.decompile-provenance.json` (not an unverified legacy tree). The manifest SHA-256 values are:

- `1.9/ornithe-feather`: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`
- `1.9.1/ornithe-feather`: `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`

The manifests identify Feather `1.9+build.2` artifact SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` and client artifact SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` for 1.9; Feather `1.9.1+build.2` SHA-256 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` and client SHA-256 `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` for 1.9.1. For every row below, the actual source digest was computed with SHA-256 and equals the digest recorded in each release's `sources` manifest entry.

| Source file (Feather relative path) | 1.9 actual SHA-256 | 1.9.1 actual SHA-256 |
|---|---|---|
| `net/minecraft/block/EndPortalBlock.java` | `5e04abb22c37fb6c8af646d93702b54c5abac4737edd92a8b2fc5c593bbf2ba5` | same |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` | same |
| `net/minecraft/server/PlayerManager.java` | `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86` | same |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` | same |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` | same |
| `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` | `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e` | same |
| `net/minecraft/network/packet/c2s/play/AcceptTeleportC2SPacket.java` | `fb1c5b363e88df9a1edfa02fd569b4c0890e253d837724dc81a7aba08af61603` | same |
| `net/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket.java` | `acc2ec0bf5f5ef64bff47b411a065ee3c41b07053c95de9a5ed19d2eb2f941a7` | same |

## Server-side transition and correction send

- `EndPortalBlock.onEntityCollision` (`EndPortalBlock.java:62–70`): calls `entity.changeDimension(1)` only when the entity is not riding, has no passengers, can use portals, the world is not client-side, and the entity shape intersects the portal block shape moved to its position.
- `ServerPlayerEntity.changeDimension` (`ServerPlayerEntity.java:455–485`): sets `inTeleportationState = true`; for a player currently in dimension 0 sent to dimension 1 it increments `ENTER_THE_END` and retains destination `dimension = 1`, then calls `PlayerManager.changeDimension(this, 1)`. The manager call happens before the later world-event and health/XP/hunger cache writes.
- `PlayerManager.changeDimension(ServerPlayerEntity,int)` (`PlayerManager.java:488–513`): captures source dimension in local `i`, captures `serverWorld` from the current dimension, then updates `player.dimension` to the destination and resolves `serverWorld2`. It sends `PlayerRespawnS2CPacket` first, calls `updatePermissions`, removes the player from the source world, clears `removed`, calls the helper with the **captured source** `i`, calls `onChangedDimension`, then calls `networkHandler.teleport(player.x, player.y, player.z, player.yaw, player.pitch)`. Only after that teleport call does it set the interaction manager's world, send abilities, destination world info and player info, then each status-effect packet. Returning to `ServerPlayerEntity.changeDimension`, the caller sends world event 1032.
- `PlayerManager.changeDimension(Entity,int,ServerWorld,ServerWorld)` (`PlayerManager.java:515–565`): the helper argument is `i == 0`, while `entity.dimension` has already been updated to `1`. Therefore neither `entity.dimension == -1` nor `entity.dimension == 0` matches; the fallback branch selects `toWorld.getForcedSpawnPoint()` because the helper argument is 0 (the `dimension == 1` test that selects `getSpawnPoint()` is false). It writes the selected block's X/Y/Z and sets position/angles to `(x,y,z,90F,0F)`. The source-world `tickEntity(entity,false)` call is gated by `entity.isAlive()`. Since the helper argument is 0, `dimension != 1` is true: it clamps `(int)d` and `(int)e` to `[-29999872,29999872]`, and, if alive, sets position/angles again, calls `toWorld.getPortalForcer().onDimensionChanged(entity,g)` using `g` captured from the pre-helper yaw, adds the entity to `toWorld`, and ticks it there. `entity.setWorld(toWorld)` follows the conditional. The destination placement/PortalForcer/add/tick block is thus reachable on this path and has an `isAlive()` gate; the source tick also has its own `isAlive()` gate.
- `ServerPlayNetworkHandler.teleport` (`ServerPlayNetworkHandler.java:490–516`): the five-argument overload delegates with `Collections.emptySet()`. Empty relative arguments make target XYZ exactly `(x,y,z)`; it stores those coordinates in `requestedTeleportPosition`, increments/wraps the teleport id, records request time, updates server player position/angles, and sends `PlayerMoveS2CPacket(x,y,z,yaw,pitch,emptySet,id)`. The packet serializes doubles X, Y, Z, floats yaw and pitch, relative-argument flags, then teleport id (`PlayerMoveS2CPacket.java:22–51`). This is an absolute correction; the teleport request is sent before the later abilities/world-info/player-info/status-effect sends listed above.

## Client response and acknowledgment order

`ClientPlayNetworkHandler.handlePlayerMove` (`ClientPlayNetworkHandler.java:545–586`) reads packet XYZ and angles. For each of X, Y and Z, an absent relative flag means it leaves that coordinate absolute and sets the corresponding player velocity component to `0.0`; with this empty relative set, all three velocity components are cleared in X→Y→Z order. It then calls `playerEntity.updatePositionAndAngles(d,e,f,g,h)`, sends `AcceptTeleportC2SPacket(teleportId)`, and next sends `PlayerMoveC2SPacket.PositionAndAngles(playerEntity.x, playerEntity.getShape().minY, playerEntity.z, playerEntity.yaw, playerEntity.pitch, false)`.

On the server, `handleAcceptTeleport` (`ServerPlayNetworkHandler.java:348–364`) accepts only a matching request id, reapplies the requested XYZ with current player yaw/pitch, and, when `isInTeleportationState()` is true, writes `lastGoodX/Y/Z` from the requested teleport position and calls `onTeleportationDone()`; it then clears the pending request. This identifies the precise acknowledgment-side accepted-position write relevant to the absolute correction. It does not write `firstGoodX/Y/Z` in this handler.

## Disposition

The assigned 1.9→1.9.1 server transition, helper argument/gates, absolute correction, client velocity/XYZ/ack ordering, and acknowledgment-side accepted-position write are unchanged. No source difference was found in these bounded slices; no finding is warranted from this pair. This does not claim runtime trajectory parity, destination geometry equivalence, or closure of other movement areas.