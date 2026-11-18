# Supplemental route closure — client receiver

This file records the client-side receiver and acknowledgement discovered while tracing the exact server-issued packet route. The blind core roster was frozen first as `source-roster.md` (SHA-256 `99c88a5a28d948d3d753089b9752edd3473505188ca2873da189f77fb1b4b4d5`) before reading the author report. The author report is used only after this freeze; its exact identity is recorded in `verdict.md`.

Mapping: Ornithe Feather in each release. Relative source path: `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` under each `decompiled_minecraft/<version>/ornithe-feather/` root. Method `handlePlayerMove(PlayerMoveS2CPacket)` lines: 565 (1.11.2), 576 (1.12), 578 (1.12.1), 578 (1.12.2). The entire method bodies were compared. Across each release they read packet x/y/z/yaw/pitch; add current player values only for corresponding relative flags, otherwise zero each position velocity; update player position/angles; send `AcceptTeleportC2SPacket(teleportId)`; then send `PlayerMoveC2SPacket.PositionAndAngles(player.x, player.getShape().minY, player.z, player.yaw, player.pitch, false)`. These statements and their order are identical in all four versions. The client route therefore has no paired 1.12.2 handler change.

Source hashes, matching the same per-release indexes described in `source-roster.md`:

| release | ClientPlayNetworkHandler.java SHA-256 |
|---|---|
| 1.11.2 | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` |
| 1.12 | `633c59b54bdbe79a2430b59215033206c886249b154044ca95965b375ba3f8e6` |
| 1.12.1 | `15e5e391df2e799ce04cac91af6feb7b9dc866a8963cfb03bc1bd04f375b986f` |
| 1.12.2 | `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c` |

`PlayerMoveS2CPacket` (present in the frozen roster) stores and writes exactly x, y, z, yaw, pitch, relative-argument flags, teleport ID, and dispatches to `ClientPlayPacketHandler.handlePlayerMove`. `AcceptTeleportC2SPacket` stores/writes the id and dispatches to `ServerPlayPacketHandler.handleAcceptTeleport`; its hash is `fb1c5b363e88df9a1edfa02fd569b4c0890e253d837724dc81a7aba08af61603` in all four releases.