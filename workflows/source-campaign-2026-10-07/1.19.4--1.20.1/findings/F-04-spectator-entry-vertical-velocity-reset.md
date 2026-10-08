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

- A server sender: `ServerPlayer#setGameMode` lines 1267-1272 calls `ServerPlayerGameMode#changeGameModeForPlayer` and, on success, sends `CHANGE_GAME_MODE`; the game-mode method lines 50-62 rejects an unchanged mode and broadcasts an `UPDATE_GAME_MODE` player-info packet after a change. `ServerPlayer.java` SHA-256 `5eeea9be89db11000daa95cada0a4120c5eda1014aae9e868fba0bee8de05606`; `ServerPlayerGameMode.java` SHA-256 `2515b55164734977c775d851a12e5e4d2c1c2e47a92163fbd4c43c05f0138275`.
- A client path: `ClientPacketListener#handleGameEvent CHANGE_GAME_MODE` lines 1286-1288 sets the local game-mode selection; the complete `LocalPlayer.java` source has no `onGameModeChanged` method. `ClientPacketListener.java` SHA-256 `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715`; `LocalPlayer.java` SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`.
- B server sender: `ServerPlayer#setGameMode` lines 1276-1281 calls `ServerPlayerGameMode#changeGameModeForPlayer` and, on success, sends `CHANGE_GAME_MODE`; the game-mode method lines 50-62 rejects an unchanged mode and broadcasts an `UPDATE_GAME_MODE` player-info packet after a change. `ServerPlayer.java` SHA-256 `4168d5d45d043f5edc0d9cafeeff2540cf544bae92b53f1b018fd1ee8887dd61`; `ServerPlayerGameMode.java` SHA-256 `d9d7b218fa8ceb8c8f04fc03223cd1b05b95307910eb66099c968414f1dc96a9`.
- B client path: `ClientPacketListener#handlePlayerInfoUpdate UPDATE_GAME_MODE` lines 1745-1750 invokes the callback only when the mode changed, the client player exists, and the profile UUID matches. `LocalPlayer#onGameModeChanged` lines 1053-1057 replaces the Y component of delta movement with `0.0` when the new mode is `GameType.SPECTATOR`. `ClientPacketListener.java` SHA-256 `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b`; `LocalPlayer.java` SHA-256 `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2`.

## Evidence artifact identities

- A artifact record `A-1.19.4-MOJMAP-ORIGINAL`: exact release 1.19.4, original client artifact verified; client jar SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`; remapped Mojmap jar SHA-256 `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`; source manifest `../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap.sources.sha256` SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`; artifact manifest `../../../build/movement-campaign-2026-10-07/ready/1.19.4/artifacts.sha256` SHA-256 `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`.
- B artifact record `B-1.20.1-MOJMAP-ORIGINAL`: exact release 1.20.1, original client artifact verified; client jar SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`; remapped Mojmap jar SHA-256 `221d543b9f80ca42faaaa8e3f7cebc4fe1879b8f7ba954bb6ca62f8250be8b77`; source manifest `../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap.sources.sha256` SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; artifact manifest `../../../build/movement-campaign-2026-10-07/ready/1.20.1/artifacts.sha256` SHA-256 `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`.

## Source-level difference

B's game-mode update path calls the local player's new callback before updating the matching PlayerInfo game-mode entry. The callback preserves the current X and Z velocity components and writes a new delta movement with Y = 0.0 when the new mode is spectator. A's bounded game-mode handler only changes the local game-mode selection and has no equivalent local-player velocity callback.

## Reachability and dependencies

The server emits the UPDATE_GAME_MODE broadcast only after a changed game mode is accepted. On the client, the callback is reachable only for an entry whose new game mode differs from the stored entry, whose UUID matches the current local player, and whose new mode is spectator. A preexisting nonzero vertical component is the value this writer clears. This is a direct movement input; damage resolution, respawn semantics and the rest of the external-writer inventory are separate.

## Consequence and uncertainty

A local player's vertical velocity is source-confirmed to differ at the spectator transition under those guards. This finding does not claim a later trajectory or gameplay outcome. The first changed release inside the pair remains unknown.

## Handoff

Preserve the version-specific vertical velocity reset when emulating the 1.20.1 local-player game-mode transition. Other respawn, ability, cooldown and mount writers remain open.
