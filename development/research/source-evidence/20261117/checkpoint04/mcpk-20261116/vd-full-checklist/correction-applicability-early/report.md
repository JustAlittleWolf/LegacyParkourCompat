# Direct client position-correction applicability: 1.8.9, 1.9.4, 1.12.2

## Scope and evidence method

This report compares only the exact decompiled clientbound position-correction receiver in the three named vanilla releases. It makes no claim about product eligibility, intermediate releases, server behavior, or runtime trajectories. No sources were generated and no network, tests, build, or game launch were used.

All three source sets use the `ornithe-feather` mapping namespace (Feather generation 2, build 2). For each tree, the `ornithe-feather.ready.json` source-manifest SHA-256 agrees with the SHA-256 of the local `ornithe-feather.sources.sha256`; the handler, packet, `Entity`, and `PacketUtils` source byte hashes below match entries in that manifest. The associated verification marker says `verified-identical-source-and-input-artifacts`, with zero source-file differences. Its stated limit is that a derived remapped intermediate JAR changed when regenerated; raw input artifacts and the published decompiled source files matched. Identity here is therefore tied to the recorded exact version metadata/client hash, mapping artifact names, toolchain/provenance, local source manifest and independent source verification, not inferred from folder names.

| Release | Mapping/input identity | Source-manifest SHA-256 | Handler SHA-256 |
|---|---|---|---|
| 1.8.9 | Feather Gen2 build.2 (`feather-gen2-1.8.9+build.2-mergedv2.jar` and `.tiny`); client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` |
| 1.9.4 | Feather Gen2 build.2 (`feather-gen2-1.9.4+build.2-mergedv2.jar` and `.tiny`); client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` |
| 1.12.2 | Feather Gen2 build.2 (`feather-gen2-1.12.2+build.2-mergedv2.jar` and `.tiny`); client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c` |

The identity records are `decompiled_minecraft/<version>/ornithe-feather.provenance.json`, `.ready.json`, `.verification.json`, and `.sources.sha256`. Provenance records Vineflower 1.12.0 and the recorded Java 25 / Gradle 9.7.1 toolchain.

## Exact receiver and dispatch

The method is `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)` in all three trees:

- 1.8.9: `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:517-565`.
- 1.9.4: `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:546-595`.
- 1.12.2: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:578-627`.

Each method first calls `PacketUtils.ensureOnSameThread(packet, this, this.minecraft)`, then takes `this.minecraft.player` as the `PlayerEntity`. There is no riding/passenger predicate or player-null predicate in the receiver body. `PacketUtils.ensureOnSameThread` (each release: `net/minecraft/network/PacketUtils.java:8-16`) only schedules `packet.handle(listener)` on the client event loop when called from another thread, then throws its sentinel exception; on the correct thread it returns. The packet class dispatches `handle(ClientPlayPacketHandler)` directly to `handler.handlePlayerMove(this)` (1.8.9 `PlayerMoveS2CPacket.java:50-52`; 1.9.4 and 1.12.2: `:54-56`). `NetworkProtocol` registers `PlayerMoveS2CPacket` in the `CLIENTBOUND` direction (1.8.9 `:130`, 1.9.4 `:176`, 1.12.2 `:184`). These caller and gate facts establish that the examined receiver handles the local client player when this packet is dispatched; source contains no riding-state exception.

## Position-relative flags and velocity

In each receiver, X, Y, and Z are handled independently and in that order. For an axis whose corresponding `packet.getRelativeArgs()` member is present, the packet coordinate is added to the player’s current coordinate and that axis’s velocity field is left untouched by the receiver. If the relative member is absent, the packet coordinate is used as absolute and the matching velocity component is set to `0.0` before the position helper is called:

| Axis | Relative flag present | Relative flag absent |
|---|---|---|
| X | `d += playerEntity.x`; no X velocity write | `playerEntity.velocityX = 0.0` |
| Y | `e += playerEntity.y`; no Y velocity write | `playerEntity.velocityY = 0.0` |
| Z | `f += playerEntity.z`; no Z velocity write | `playerEntity.velocityZ = 0.0` |

The exact receiver ranges above contain these branches. The packet’s `Argument` enum and flag decoding identify X/Y/Z independently (1.8.9 `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java:78-109`; 1.9.4 and 1.12.2: `:86-117`). Pitch and yaw flags affect only their respective angle sums; they do not select or change velocity components (handler: 1.8.9 `:543-549`, 1.9.4 `:572-578`, 1.12.2 `:604-610`).

## State-write order

The receiver reads packet position and angles into locals, processes X then Y then Z (zeroing each non-relative velocity at that point), then resolves pitch before yaw. It next calls `playerEntity.updatePositionAndAngles(d, e, f, g, h)`. In each sampled release this helper writes corrected position/angle state and calls `setPosition` and `setRotation`; it contains no velocity write:

- 1.8.9: `Entity.updatePositionAndAngles`, `net/minecraft/entity/Entity.java:876-893`; position and corresponding last-position fields are assigned together, then yaw/lastYaw and pitch/lastPitch, then position and rotation are applied.
- 1.9.4: `Entity.updatePositionAndAngles`, `:989-1007`; X/Z and pitch are clamped, position/last-position and angle/last-angle fields are written, then position and rotation are applied.
- 1.12.2: `Entity.updatePositionAndAngles`, `:1093-1116`; X/Z and pitch are clamped, current position then last-position fields are written, then angle and last-angle fields, followed by position and rotation application.

After the helper, 1.8.9 sends a `PlayerMoveC2SPacket.PositionAndAngles` response (handler `:551-557`). 1.9.4 and 1.12.2 first send `AcceptTeleportC2SPacket(packet.getTeleportId())`, then the position-and-angles response (1.9.4 `:580-587`; 1.12.2 `:612-619`). All three then have a `!this.started` block that copies current X/Y/Z to the player’s `lastX/lastY/lastZ`, sets `started`, and calls `openScreen(null)` (`1.8.9 :558-564`, `1.9.4 :588-594`, `1.12.2 :620-626`). This startup block follows the correction and is not an applicability gate.

## Bounded conclusion

In the exact sampled sources 1.8.9, 1.9.4, and 1.12.2, a dispatched clientbound player-position correction applies directly to `minecraft.player` without a riding-state guard. Relative position flags preserve the corresponding current velocity component; absolute position axes zero only their matching component before the corrected position is committed. The axis handling, position helper call, and response/startup write order described above are source-confirmed for these three releases only. This sample does not establish the behavior of intervening or other releases.