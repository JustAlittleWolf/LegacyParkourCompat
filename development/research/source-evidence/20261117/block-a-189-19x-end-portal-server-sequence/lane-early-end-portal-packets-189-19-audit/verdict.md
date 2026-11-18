# Independent source audit verdict — 1.8.9 → 1.9 End portal packets

## Terminal verdict

- **End-portal transfer finding:** Accepted. The paired claims about optional 1.8.9 pre-respawn absolute teleport, old-dimension helper argument, forced-spawn selection, placement branch, and final move from resulting player fields are source-confirmed.
- **Client absolute-move acknowledgment finding:** Accepted. Both clients apply absolute XYZ and zero each corresponding velocity component; 1.9 sends its teleport acknowledgment before the client position/angle response, and 1.8.9 has no such acknowledgment.
- **Corrected comparison:** Accepted for its stated coordinates, transfer gates, and source invocation ordering, with one packet-order completeness correction below. Do not describe the listed 1.9 packet events as exhaustive without including the permission event.
- **Pair coverage:** This verdict covers only the assigned ordinary Overworld → End transfer and the absolute player-move packet path. It does not establish full-pair discovery coverage or runtime arrival order.

## Packet-order completeness correction

In 1.9 `PlayerManager.changeDimension(ServerPlayerEntity,int)`, after sending `PlayerRespawnS2CPacket`, the method calls `updatePermissions(player)` before removal and transfer. The called `updatePermissions(ServerPlayerEntity,int)` sends an `EntityEventS2CPacket` when the player and network handler are present (true for this ordinary connected-player route). Therefore the source call sequence includes this permission event after Respawn and before the final absolute move. `comparison.corrected-r1.md` correctly names the `updatePermissions` call, but should identify its packet effect if that sequence is presented as the packet order. This event does not change the accepted End coordinate or client velocity claims.

## Source trace

1. The server-side portal route invokes `entity.changeDimension(1)` for an eligible ordinary player.
2. 1.8.9 `ServerPlayerEntity.changeDimension(int)` reads the End forced spawn and, when non-null, calls the network handler's default `teleport(x,y,z,0,0)` before entering `PlayerManager.changeDimension`. The default uses an empty relative-argument set, updates the server player position/angles, and sends the move packet. The subsequent player-manager method sends Respawn.
3. In both player-manager methods, `i = player.dimension` captures the old dimension before `player.dimension = dimension`. The helper receives `i`, so on Overworld → End its argument is 0 while `entity.dimension` is already 1.
4. In both helpers, the `entity.dimension == -1` and `entity.dimension == 0` branches are false; the spawn branch tests the helper argument. Since it is 0, it selects `toWorld.getForcedSpawnPoint()`, writes its X/Y/Z into the helper's locals/entity and sets yaw/pitch to 90/0. The subsequent `dimension != 1` condition is also true, so integer-cast X/Z are clamped, then the alive-gated placement performs `setPositionAndAngles`, portal-forcer placement, add-to-world, and target-world tick before `setWorld(toWorld)`.
5. Thus 1.8.9's optional early relocation and the helper's initial destination selection use the same forced-spawn accessor. The final move is requested only after the helper and `onChangedDimension`; it uses the resulting `player.x/y/z/yaw/pitch`, so source does not warrant claiming those sent values always equal the raw block coordinates.
6. 1.9 omits the preliminary `ServerPlayerEntity` move. Its `PlayerManager` packet call order is Respawn, permission `EntityEventS2CPacket`, transfer/helper work, final absolute move with teleport ID, then abilities, world/player info and status effects. After the manager returns, `ServerPlayerEntity.changeDimension` sends `WorldEventS2CPacket(1032, ...)`.
7. The server default teleport helper uses empty relative flags in both versions. The client therefore treats each End-arrival move as absolute: it applies XYZ and angles and writes all three player velocity components to zero. 1.9 then sends `AcceptTeleportC2SPacket(packet.getTeleportId())` before its position/angle response; server confirmation is processed only for the matching requested ID. 1.8.9 has no matching confirmation packet or server acknowledgment handler.

These are source call orders. They do not prove when packets arrive or when the client processes them at runtime.

## Publication verification

- 1.8.9 ready marker: `decompiled_minecraft/1.8.9/ornithe-feather.ready.json`, SHA-256 `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; exact release and `ornithe-feather` mapping are declared. The marker's source-manifest digest matches `ornithe-feather.sources.sha256`; all 1,612 entries match their source files.
- 1.9 provenance: `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json`, SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; the pinned input records Ornithe Feather 1.9 mapping metadata and build artifacts. All 1,819 source hashes match with zero mismatches.
- The independent source roster was frozen at `roster.md` before opening the assigned author group. Roster SHA-256: `3cb5349a5377eec87e08422ea89339435e82b3351d2eb9e53417b549d4eb99e1`.

## Audited author payloads

All four supplied author files matched the requested SHA-256 digests before inspection:

- `comparison.corrected-r1.md`: `8244522466c627007fa5c48d91a5039f1c9a2d50c5bca87a2e73a247e6ca2156`
- `finding-end-portal-transfer.corrected-r1.md`: `d19f5768ff111ce0d28337e90a957bd35790ab844189cd4a594aff67a0e345ae`
- `finding-client-absolute-move-ack.md`: `0526e4ecacb312b861687bd50d86c783606b4cb220abe4a0dc9c42f31a377eb8`
- `roster.md`: `1d3eae4814e81ee5bd5132fb8ea28a22f62b0d5ad6e56e00a206146fb5484782`
