# F-04: Spectator entry zeros local-player vertical velocity in 1.20.1

1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, onGameModeChanged 1053-1057.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/level/ServerPlayer.java`, setGameMode 1267-1272.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java`, changeGameModeForPlayer 50-62.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleGameEvent 1286-1288.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/server/level/ServerPlayer.java`, setGameMode 1276-1281.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java`, changeGameModeForPlayer 50-62.

Difference: 1.20.1's game-mode update path calls the local player's new callback before updating the matching PlayerInfo game-mode entry. The callback preserves the current X and Z velocity components and writes a new delta movement with Y = 0.0 when the new mode is spectator. 1.19.4's bounded game-mode handler only changes the local game-mode selection and has no equivalent local-player velocity callback.

Movement meaning: The server emits the UPDATE_GAME_MODE broadcast only after a changed game mode is accepted. On the client, the callback is reachable only for an entry whose new game mode differs from the stored entry, whose UUID matches the current local player, and whose new mode is spectator. 1.19.4 preexisting nonzero vertical component is the value this writer clears. This is a direct movement input; damage resolution, respawn semantics and the rest of the external-writer inventory are separate. 1.19.4 local player's vertical velocity is source-confirmed to differ at the spectator transition under those guards.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-04-spectator-entry-vertical-velocity-reset.md).
