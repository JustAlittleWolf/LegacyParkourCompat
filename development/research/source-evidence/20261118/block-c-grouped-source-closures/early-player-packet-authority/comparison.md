# Early player correction authority comparison

## Result

**COMPLETED** for the bounded Feather-source slice: 1.8.9 against 1.9, with the correction handler, packet, entity setter, and relevant server sender checked again at 1.9.1, 1.9.2, 1.9.3, and 1.9.4. The direct player correction behavior is stable from 1.9.0 through 1.9.4 in the examined sources. The source evidence places the change at the 1.8.9 → 1.9 boundary; no claim is made about unexamined releases or snapshots.

## Evidence and comparison

All paths are relative to the primary checkout's `decompiled_minecraft/<version>/ornithe-feather/`. The 1.8.9 and 1.9.4 trees lack `.decompile-provenance.json`; treat their local files as legacy, unverified-provenance source. The 1.9, 1.9.1, 1.9.2, and 1.9.3 trees have manifests. Source hashes below identify the exact compared payloads, but do not repair those two provenance gaps. No online mappings or other research lanes were used.

For manifest-backed sides, the local mapping identities are Feather gen2 build.2 for 1.9 (1.9+build.2), 1.9.1 (1.9.1+build.2), 1.9.2 (1.9.2+build.2), and 1.9.3 (1.9.3+build.2). Each of the four source files in the hash table matched its corresponding manifest source hash for all four releases. The 1.8.9 and 1.9.4 mapping artifact identities cannot be recovered from the local output manifests because those manifests are absent.

- **Correction data and flags remain the same:** `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java`, 1.8.9 lines 25–47, 78–103; 1.9.4 lines 22–52, 86–111. Both encode XYZ doubles, yaw/pitch floats, then an unsigned-byte mask; flags X/Y/Z/YAW/PITCH map to bits 0–4. The 1.9 packet adds a VarInt teleport ID after that mask. Thus the correction's position/angle payload and relative-flag meaning are the same, while the complete protocol packet is not identical on wire.
- **Client state application:** `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, 1.8.9 `handlePlayerMove` lines 517–565; 1.9.4 lines 546–595. It queues to the client thread, begins with the packet values, adds current position per relative XYZ flag (otherwise zeroes only that velocity component), and adds current yaw/pitch per relative-angle flag. It then calls `PlayerEntity.updatePositionAndAngles`. The first-correction `!started` path also writes `lastX/Y/Z` to current X/Y/Z and marks the player started. `prevX/Y/Z` are not written in this path.
- **1.9 protocol acknowledgment:** 1.9.4 handler line 581 sends `AcceptTeleportC2SPacket(packet.getTeleportId())` immediately after applying the correction and before the client position-and-angles response. That acknowledgment is absent in 1.8.9. On the server, `ServerPlayNetworkHandler.teleport` sends the packet at line 515; `handleAcceptTeleport` checks the ID at lines 348–365 and settles the requested server position. This is server-authored teleport confirmation. It is not server-side replay of client historical movement and should not be conflated with server admission/check policy.
- **Position setter difference:** `net/minecraft/entity/Entity.java`, 1.8.9 `updatePositionAndAngles` lines 876–893 assigns `x/lastX`, `y/lastY`, `z/lastZ`, yaw/lastYaw, pitch/lastPitch, then shapes position and applies `setRotation` (which wraps yaw/pitch by `% 360.0F`). At 1.9.4 lines 989–1007, the same path clamps X and Z to `[-3.0E7, 3.0E7]`, leaves Y unclamped, clamps pitch to `[-90.0F, 90.0F]`, writes the same current/last fields, shapes position, and applies the same rotation wrapper. So ordinary in-range corrections preserve the same state; finite out-of-range X/Z and pitch differ. Neither version's correction setter updates `prevX/Y/Z` or velocity.
- **Other packet writers kept distinct:** 1.9.4 `handleExplosion` at client handler lines 886–895 adds packet impulse to the local player's velocity; it is a separate player-response operation, not the correction's per-axis velocity reset. `handlePlayerRespawn` begins at line 863 and replaces/respawns the player as lifecycle handling. `handleVehicleMove` lines 1492–1498 updates a mounted non-player entity and replies with a vehicle packet; it is outside player-only correction behavior.

## Witnesses

A normal finite correction gives the same movement state at both sides. Starting at `(10, 64, 10)`, velocity `(1, -0.5, 0.25)`, yaw/pitch `(30, 10)`, receive finite packet values `(2, 3, 4, 15, 5)` with relative flags `{X, Y, Z, YAW}`. Both compute `(12, 67, 14)`, yaw/pitch `(45, 5)`, preserve all three velocity components, and set the correction's current and last positions together. The initial `!started` write repeats the last-position assignment; neither path changes `prevX/Y/Z`.

For a finite boundary witness, absolute X `30,000,001` with in-range Y/Z and ordinary angles is left at `30,000,001` by the 1.8.9 setter and clamped to `30,000,000` by the 1.9 setter. An absolute pitch of `120` similarly ends at `120` in 1.8.9 but `90` in 1.9. These establish setter deltas without NaN/Infinity. They say nothing about whether a server would originate/admit those values; the client correction path itself applies the packet.

## Patch stability and hashes

Across 1.9, 1.9.1, and 1.9.2 the complete `ClientPlayNetworkHandler.java`, `Entity.java`, `PlayerMoveS2CPacket.java`, and `ServerPlayNetworkHandler.java` file hashes were identical. At 1.9.3 and 1.9.4, the client handler and entity files changed elsewhere, but the extracted `handlePlayerMove` method hash stayed `0938d46b9efb4eb3eca4ec195b747c09c43156079ddcb926aff87a5ad79a381c`; the packet and server-handler whole-file hashes also stayed identical between those two patch points. The extracted `updatePositionAndAngles` method hash is also identical across 1.9.0–1.9.4 (`6c86e11acef82d02ae9beb0fbb81400c8f235658e0dd41b04357781377e7994e`). The 1.9.3-to-1.9.4 evidence supports no later correction-behavior change in this slice.

SHA-256 (exact local files):

| File | 1.8.9 | 1.9 / 1.9.1 / 1.9.2 | 1.9.3 / 1.9.4 |
|---|---|---|---|
| `ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` | `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` |
| `Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `PlayerMoveS2CPacket.java` | `3c2c044610df527df20561b4ded36aca91c774b7d0520f9e4cfe657030bf1baf` | `8bad09e146ce746085103081b7e3c6d09855114868267e12be8d869f34f0e77e` | same as 1.9/.1/.2 |
| `ServerPlayNetworkHandler.java` | `47ef077a7fa0f74cf44e1c8bda449d1b220bf56503b2fef98fd8016e8092df5b` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` | `f96587355f356f95362dbca2c57c043fdb3ee8ee2fe81589f4c3379ab1a0336b` |


## Limits

This is a bounded direct inbound player-correction comparison, not complete movement-tick coverage, runtime parity, or independent audit. Examined only the named packet, client handler, entity setter, and the directly related server correction sender/ack methods at 1.8.9 and 1.9 through 1.9.4. Server movement admission/check logic, gameplay traces, other packet state writers beyond the cited distinctions, 1.8.x patch points, 1.9 snapshots, and other release lines remain outside this assignment.
