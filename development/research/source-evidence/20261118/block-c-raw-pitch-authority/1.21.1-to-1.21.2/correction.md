# Scope correction: vanilla `/tp` reaches the local pitch setter

This standalone correction binds to the original author report SHA-256 `fee374a8b55ec931727265edad321cf8cd4e6c67f8d31b7c3f1f79ae9dc9d97e`, which matches its exact file. The supplied audit verdict identity `245811badbe89fbf2dbe2f47cc3270b1b052b95415de1b76fcdbbb800b7ea` is 61 hexadecimal characters and cannot be a full SHA-256; the current audit verdict file instead hashes to `245811badbe89fbf2dbe2f47cc3270b1b052b95415de1b76b96fcdbbb800b7ea` (64 hex characters). The correction records the supplied identity verbatim and binds its audit evidence to the current full file hash. The original `report.md` is preserved unchanged.

## Correction

The original report's blanket sentence that vanilla map `/tp` cannot provide an out-of-range pitch to the local setter is incorrect. It followed the base `Entity.teleportTo` clamp and missed the 1.21.1 `ServerPlayer` override. A normal vanilla command path can send a finite out-of-range local pitch in 1.21.1:

1. `TeleportCommand.performTeleport` applies `Mth.wrapDegrees` to the requested pitch (1.21.1 `server/commands/TeleportCommand.java`, lines 275–277). The vanilla rotation argument accepts a finite numeric pitch without constraining it to ±90; for example `200` wraps to `-160` (`util/Mth.java`, `Mth.wrapDegrees(float)`).
2. For a same-dimension player, 1.21.1 dynamically dispatches the command's `Entity.teleportTo` call to `ServerPlayer.teleportTo` (`server/level/ServerPlayer.java`, lines 1410–1425). That override sends the wrapped pitch directly to `ServerGamePacketListenerImpl.teleport` at line 1419 instead of invoking the base method's pitch clamp.
3. The listener uses `player.absMoveTo` and then constructs `ClientboundPlayerPositionPacket` from the original pitch argument (1.21.1 `server/network/ServerGamePacketListenerImpl.java`, lines 993–1006). `absMoveTo`/`absRotateTo` clamps the server entity's stored pitch to -90 (`world/entity/Entity.java`, lines 1374–1383), but does not mutate the local `$$4` packet argument. For an absolute rotation, the outgoing packet therefore still carries `-160`.
4. On the client, `ClientPacketListener.handleMovePlayer` resolves the packet's pitch and calls `Player.setXRot` (1.21.1 `client/multiplayer/ClientPacketListener.java`, lines 620–680). The 1.21.1 setter stores finite `-160` unchanged. This is a vanilla command-to-client setter route, not a fabricated packet. The resulting local pitch is read by player movement such as fall-flying movement.

For the corresponding 1.21.2 command route, `ServerPlayer.teleportTo` calls `super.teleportTo` (1.21.2 `server/level/ServerPlayer.java`, lines 1604–1618). The base `Entity.teleportTo` clamps pitch to `[-90,90]` before constructing its `TeleportTransition` (`world/entity/Entity.java`, lines 2868–2871); `ServerPlayer.teleport` forwards that transition through the connection, and `ClientboundPlayerPositionPacket` is made from the bounded transition. The client therefore receives `-90` for the same `200`-degree command pitch, rather than `-160`.

This is an end-to-end vanilla `/tp` difference with both a server teleport-route change and the client setter change in play. Attribute it separately from the isolated local mouse witness; do not use this route to claim a client-only cause. The original report's setter arithmetic and mouse-path analysis remain valid. Retract only its blanket no-vanilla-teleport-witness conclusion. This source trace establishes reachability in vanilla code; it does not establish runtime parity or a particular map's use of the command.

## Exact source identities

All source bytes are in the canonical read-only Mojmap trees. SHA-256 values:

| Version | Source path | SHA-256 |
|---|---|---|
| 1.21.1 | `net/minecraft/server/commands/TeleportCommand.java` | `176096e6b7477da7a411d076f748c13531f0647e547f580474b3fc36a755afac` |
| 1.21.2 | `net/minecraft/server/commands/TeleportCommand.java` | `33e4741d89446ad3fae4991858a23a69780177fa649764dc39cf49e7a8ec2b5e` |
| 1.21.1 | `net/minecraft/server/level/ServerPlayer.java` | `ccdac581288507aae64bd844beefe9ac2e174f316458e181f23ce5631a4b1ba7` |
| 1.21.2 | `net/minecraft/server/level/ServerPlayer.java` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` |
| 1.21.1 | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `cc73bb8fab95346aea10f7018ba7de6c723cde8a52a4ae40629b5370b6ff6d10` |
| 1.21.2 | `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` |
| 1.21.1 | `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21.2 | `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.21.1 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| 1.21.2 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` |


