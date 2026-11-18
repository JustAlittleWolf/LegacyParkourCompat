# Post-transfer handler and helper tick dependency (source-only)

Pair: Minecraft 1.8.9 → 1.9 stable; Ornithe Feather sources on both sides. This note is conditional source-order analysis. It distinguishes packet send calls and in-memory writes from packet arrival or any claim about a runtime transfer occurring during a particular tick.

## 1.8.9 handler writes

`server/network/handler/ServerPlayNetworkHandler.java:128-153`, `tick()`, only resets `moved`, increments handler ticks, handles keepalive, cooldowns and idle timeout. It does not snapshot player XYZ, call `player.tickPlayer()`, or write player position. An End transfer cannot be attributed to this method alone from its source body.

The ordinary non-riding, awake `handlePlayerMove` branch (`:262-293`, then movement at `:315-358`) has two relevant sub-operations:

1. Before `player.tickPlayer()`, it copies current `player.x/y/z` to `teleportTargetX/Y/Z` (`:263-265`) and to local `q/r/s` (`:266-268`), then replaces `q/r/s` with packet coordinates when `hasPos` (`:275-283`). It chooses packet yaw/pitch only when `hasAngles` (`:285-288`).
2. After `player.tickPlayer()`, it unconditionally calls `player.updatePositionAndAngles(teleportTargetX, teleportTargetY, teleportTargetZ, t, u)` (`:290-293`). A nested End transfer's `networkHandler.teleport(...)` changes those handler target fields and updates the player before sending its absolute move (`ServerPlayNetworkHandler.java:364-397`). If a transfer happens inside this player tick, the later write reads the latest target established by the transfer call, then the now-false handler `teleported` gate returns from the movement handler at `:292-293`. This is not a restore from an immutable pre-tick snapshot.

There is a separate conditional later point: `player.move(v,w,x)` (`:315`) can enter `Entity.move`, whose block-collision loop calls `Block.onEntityCollision` (`entity/Entity.java:607-660`). If End transfer occurs within that move, the transfer's final server `teleport(...)` send occurs nested inside the move; after it returns, the handler unconditionally reapplies the already-computed C2S target `q/r/s` and angles at `:332`. That write can therefore overwrite the in-memory position established by the already-issued absolute S2C send. The handler's 1.8.9 `teleported` boolean, which `teleport(...)` sets false and the client movement response may set true when it is within squared distance `< 0.25` of the target (`:202-210`, `:368-397`), is distinct from the S2C packet's relative-argument set. The End-arrival `teleport(...)` overload uses `Collections.emptySet()` (`:364-365`), so its position and angles are absolute.

The source establishes these nested call and assignment orders; it does not establish packet arrival or which conditional collision-rejection path follows in a particular world.

## 1.9 handler writes and acknowledgment

`server/network/handler/ServerPlayNetworkHandler.java:161-224`, `tick()`, calls `resetPosition()` (`:162`), then `player.tickPlayer()` (`:163`), then unconditionally writes `firstGoodX/Y/Z` back to the player with its current yaw/pitch (`:164`) before continuing handler checks. `resetPosition()` (`:226-233`) snapshots the player's entry XYZ into both `firstGoodX/Y/Z` and `lastGoodX/Y/Z`.

If End transfer occurs inside that `tickPlayer()` call, `PlayerManager.changeDimension` calls the handler's absolute `teleport(...)` after helper placement. In 1.9 that helper stores `requestedTeleportPosition = new Vec3d(x+d,y+e,z+f)` (`:494-498`); with the End-arrival empty relative set, d/e/f are zero. It updates the player to that request, assigns `requestedTeleportId`, records the handler tick/time, and calls `sendPacket(PlayerMoveS2CPacket(...,requestedTeleportId))` (`:509-515`). On return from `tickPlayer()`, handler `tick()` still writes its earlier `firstGoodX/Y/Z` snapshot at `:164`. Thus source order has the final absolute send call before that later in-memory write; `firstGood` is not rewritten by the transfer helper. This says nothing about delivery/arrival order at the client.

A matching acknowledgment has a direct source correction path: `handleAcceptTeleport` (`:348-363`) first verifies `packet.getId() == requestedTeleportId`, then calls `player.updatePositionAndAngles(requestedTeleportPosition.xyz, current yaw/pitch)`; if `player.isInTeleportationState()`, it copies the request XYZ to `lastGoodX/Y/Z` and calls `onTeleportationDone()`; it finally clears `requestedTeleportPosition`. `ServerPlayerEntity.changeDimension` enters this state before the End-transfer dispatch (`server/entity/living/player/ServerPlayerEntity.java:454-478`; completion resets the flag at `:1032-1037`). This is a conditional later handler write back to the stored requested target, not evidence that or when a matching ACK arrives. While the request remains pending, `handlePlayerMove` (`:367-389`) does not enter its normal move branch; after more than 20 handler ticks it resends the stored target (`:378-388`).

If instead the transfer is nested in `handlePlayerMove`'s normal `player.move(m,n,o)` (`:439`), the method had already selected the no-pending-request branch at entry (`:378-389`). A nested transfer sets the requested target and sends its absolute packet, but the current call continues and unconditionally reapplies precomputed inbound C2S `h/i/j` and angles with `player.updatePositionAndAngles(h,i,j,k,l)` (`:460`). The teleportation-state checks suppress the moved-too-quick and moved-wrong diagnoses (`:421-429`, `:451-458`), but do not guard this assignment. A later collision correction may call `teleport(d,e,f,k,l)` (`:462-467`); whether that conditional executes is not inferred. If it does, it issues a new teleport request/ID and send. A later matching ACK moves the player to whichever request is stored at its handler entry, as described above.

Here too, code order proves the write and send calls only. It does not prove transport arrival or the specific branch outcome.

## Transfer helper ticks: `World.tickEntity(entity, false)`

For the ordinary overworld-to-End route, `PlayerManager.changeDimension(Entity,...)` calls the source world's `tickEntity(entity,false)` under the alive gate after selecting/setting the initial forced-spawn position; after target placement it calls the destination world's `tickEntity(entity,false)` under another alive gate, then sets the entity world (`1.8.9 server/PlayerManager.java:460-513`; `1.9:515-568`). These are two helper calls, not an unconditional full entity tick.

In both `World.tickEntity(Entity,boolean)` implementations the parameter is `requireLoaded`. With `false`, the outer `!requireLoaded || isAreaLoaded(...)` test passes without requiring the area-loaded check. The method copies current XYZ to `prevX/Y/Z` and yaw/pitch to `lastYaw/lastPitch`; the `if (requireLoaded && entity.inChunk)` gate is false, so it does not increment `entity.ticks` or call `entity.tick()` / `rideTick()`. It proceeds to chunk bookkeeping and non-finite checks. Parent/rider or passenger recursion is also gated on `requireLoaded`, so it does not occur from these helper calls.

- 1.8.9 `world/World.java:1330-1396`: when chunk coordinates or `inChunk` change, it conditionally removes from an old loaded chunk, then adds to a loaded destination chunk or sets `inChunk=false`. There is no entity teleport-flag branch.
- 1.9 `world/World.java:1391-1455`: corresponding chunk bookkeeping removes an old loaded entry; on the destination branch it calls `entity.resetTeleported()` only when the chunk bookkeeping branch is entered, and if that returns false it checks whether the new chunk is loaded. A true flag skips that loaded-check failure and leads to `getChunkAt(...).addEntity(entity)`; a false flag plus unloaded destination marks `inChunk=false`. This private entity `teleported` flag is distinct from `ServerPlayerEntity.inTeleportationState`, `ServerPlayNetworkHandler.requestedTeleportPosition/Id`, and the relative arguments on `PlayerMoveS2CPacket`. The End transfer helper itself uses `setPositionAndAngles` and does not assign the entity flag true.

Both helpers test X/Y/Z for NaN or infinity after taking `prevX/Y/Z`, and assign the matching previous coordinate back if non-finite (`1.8.9:1349-1360`; `1.9:1410-1421`). With `requireLoaded=false`, there is no entity tick between snapshot and this check; at this helper boundary, an already non-finite coordinate is copied into its own snapshot and re-assigned unchanged. No normal finite XYZ write is performed by this `World.tickEntity` method after the snapshot; its remaining code updates chunk tracking.

A direct position/velocity write does precede the destination helper call: target End `PortalForcer.onDimensionChanged` (`server/world/PortalForcer.java:29-55` in 1.8.9 and `:30-56` in 1.9) lays out its platform, computes `i=floor(entity.x)`, `j=floor(entity.y)-1`, `k=floor(entity.z)`, calls `entity.setPositionAndAngles(i,j,k,entity.yaw,0.0F)`, and zeros `velocityX/Y/Z`. For an in-range integer forced-spawn point, this places the entity at the same X/Z and one Y below the point before the destination `tickEntity(false)` snapshots. More generally it floors the position after the helper's preceding X/Z clamp. The final manager teleport then sends the resulting player XYZ fields. The source does not justify claims about actual chunk availability, packet delivery, runtime transfer timing, or any additional trajectory.

## Canonical source identity checks

Read-only canonical trees were rechecked against their published source manifests: 1.8.9 Ornithe Feather has 1,612 listed files with 0 hash mismatches; 1.9 Ornithe Feather has 1,819 listed files with 0 hash mismatches. The 1.8.9 source-manifest SHA-256 is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; the 1.9 hidden provenance SHA-256 is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

Relevant source file SHA-256 values:

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` |
| 1.9 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` |
| 1.8.9 | `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| 1.9 | `net/minecraft/world/World.java` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| 1.8.9 | `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| 1.9 | `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.8.9 | `net/minecraft/server/world/PortalForcer.java` | `5fe634ce1537e1fbe6c615a47c23059b66b52f24b9c21f99d52b300f9c00ee44` |
| 1.9 | `net/minecraft/server/world/PortalForcer.java` | `13488be38f1b0426b40388e30fbbfec8ddfef885382b3354a9e7180254b4c947` |
| 1.8.9 | `net/minecraft/server/PlayerManager.java` | `0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d` |
| 1.9 | `net/minecraft/server/PlayerManager.java` | `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86` |