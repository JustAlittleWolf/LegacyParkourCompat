# EXT-05: End portal transfer has an extra forced-spawn teleport in A

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: server-applied player position and correction sequence after an End Portal callback; `WORLD-08`, `EXT-05`, `INV-STATE`, `INV-EXTERNAL`
- Classification: source-confirmed conditional player-position/teleport-packet sequence difference
- Confidence: source-confirmed for paired server player transfer methods and shared destination position writer
- Applicability: a server-side player reaches the End transfer path (`dimension == 0` to `1`); A's forced spawn point is non-null for the additional early teleport
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- Reachable callback: `EndPortalBlock.onEntityCollision()` calls `entity.changeDimension(1)` on the server when its endpoint-specific eligibility predicates pass; see `findings/WORLD-08-end-portal-overlap-gate.md` for the paired callback and reachability evidence.
- A `ServerPlayerEntity.changeDimension(I)V`, lines 440-463, checks `dimension == 0 && target == 1`, increments the End-entry stat, reads `getForcedSpawnPoint()`, and if non-null calls `networkHandler.teleport(x,y,z,0,0)` before invoking `PlayerManager.changeDimension`. `ServerPlayerEntity.java` SHA-256 `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81`.
- B `ServerPlayerEntity.changeDimension(I)Entity`, lines 458-487, sets `inTeleportationState = true`, increments the End-entry stat, calls `PlayerManager.changeDimension`, then sends a world event. It has no forced-spawn-point teleport in the End-entry branch. `ServerPlayerEntity.java` SHA-256 `8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd`.
- A/B `PlayerManager.changeDimension(ServerPlayerEntity,int)` then invokes the paired shared coordinate helper. For target dimension 1, both select `toWorld.getSpawnPoint()`, set Y from the selected block, and call `setPositionAndAngles(x,y,z,90.0F,0.0F)` before a final `networkHandler.teleport(player.x,player.y,player.z,player.yaw,player.pitch)`. The helper's scale/clamp/portal-placement path and final world assignment also match. `PlayerManager.java` SHA-256 A/B `0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d` / `4b77ddc263a0d9e17ffa359370ef56f9d1b59834ec53a21f29aeb8cc02a571c3`.
- A `ServerPlayNetworkHandler.teleport(double,double,double,float,float,Set)` updates the player's position/angles and sends a `PlayerMoveS2CPacket` at lines 368-396; B's corresponding method does the same at lines 494-515 while tracking an acknowledgement ID and requested position. `ServerPlayNetworkHandler.java` SHA-256 A/B `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` / `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b`.
- B's `inTeleportationState` writer/consumer pair is present: `changeDimension()` sets it; `tickPortalCooldown()` at lines 1005-1009 suppresses cooldown decrement while true; the server network handler uses it during teleport confirmation/movement handling and calls `onTeleportationDone()` after the matching confirmation. The paired pending-target/acknowledgement movement gates are documented in `findings/EXT-01-player-authority-packet-differences.md`; this finding does not infer a packet sequence or trajectory from them.

## Source-level difference

When A's forced spawn point is non-null, A first teleports the player to that coordinate and sends the corresponding server-to-client position packet. The subsequent `PlayerManager` transfer helper replaces that position with the destination world's End spawn point, and the manager sends its final teleport packet. B skips the early forced-spawn teleport and performs the shared destination-spawn assignment/final teleport, while also using its newer teleport acknowledgement state. The source establishes an extra intermediate position write and correction packet in A; it does not establish a client tick or player trajectory between packets. The final destination position writer is the same.

## Handoff

Source finding only. WORLD-08 callback eligibility, this conditional intermediate teleport, the shared final destination writer and B's teleport acknowledgement movement guards are distinct stages. No implementation or runtime claim is made.
