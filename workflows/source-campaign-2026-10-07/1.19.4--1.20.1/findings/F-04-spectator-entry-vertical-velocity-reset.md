# F-04: Spectator entry zeros local-player vertical velocity in 1.20.1

- Older version A: Minecraft 1.19.4
- Newer version B: Minecraft 1.20.1
- Mechanic / coverage slice IDs: direct local-player velocity response to a server-reported game-mode transition; S7-03
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: local player, when a changed server game mode is reported for its UUID and the new mode is spectator
- First changed release: unknown within (1.19.4, 1.20.1]
- Runtime validation: not performed

## Paired evidence

- A caller: ClientPacketListener#handleGameEvent CHANGE_GAME_MODE lines 1286-1288 sets the local game-mode selection; ClientPacketListener.java SHA-256 bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715.
- A absence: the complete LocalPlayer.java source has no onGameModeChanged method; SHA-256 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
- B caller: ClientPacketListener#handlePlayerInfoUpdate UPDATE_GAME_MODE lines 1745-1750 invokes the callback when the mode changed, the client player exists, and the profile UUID matches; ClientPacketListener.java SHA-256 8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b.
- B writer: LocalPlayer#onGameModeChanged lines 1053-1057 replaces the Y component of delta movement with 0.0 when the new mode is GameType.SPECTATOR; LocalPlayer.java SHA-256 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2.

## Source-level difference

B's game-mode update path calls the local player's new callback before updating the matching PlayerInfo game-mode entry. The callback preserves the current X and Z velocity components and writes a new delta movement with Y = 0.0 when the new mode is spectator. A's bounded game-mode handler only changes the local game-mode selection and has no equivalent local-player velocity callback.

## Reachability and dependencies

The callback is reachable only for an UPDATE_GAME_MODE entry whose new game mode differs from the stored entry, whose UUID matches the current local player, and whose new mode is spectator. A preexisting nonzero vertical component is the value this writer clears. This is a direct movement input; damage resolution, respawn semantics and the rest of the external-writer inventory are separate.

## Consequence and uncertainty

A local player's vertical velocity is source-confirmed to differ at the spectator transition under those guards. This finding does not claim a later trajectory or gameplay outcome. The first changed release inside the pair remains unknown.

## Handoff

Preserve the version-specific vertical velocity reset when emulating the 1.20.1 local-player game-mode transition. Other respawn, ability, cooldown and mount writers remain open.
