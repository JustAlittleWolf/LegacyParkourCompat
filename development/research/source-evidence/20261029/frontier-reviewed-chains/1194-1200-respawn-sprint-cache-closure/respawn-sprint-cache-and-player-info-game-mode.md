# Respawn sprint/shift cache and player-info game-mode path

## Exact source identity

Compared exact Mojmap decompilations from the canonical source root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- **1.19.4 parent pins:** `1.19.4/mojmap.ready.json` says `versionId=1.19.4`, `mapping=mojmap`, `sourceFileCount=4740`; its `sourceManifestSha256` matches the SHA-256 of `mojmap.sources.sha256` (`6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`). The parent pins client SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`; `artifacts.sha256` also pins `1.19.4/client.jar`, `client_mojmap.jar`, and `client_mappings.txt`. The exact-run provenance identifies Mojmap and the 1.19.2–1.19.4 batch.
- **1.20 mapping-root pin:** `1.20/mojmap/.decompile-provenance.json` SHA-256 is `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. Its input map pins release metadata `.../9e7847f933f4e81e45dd1dfb24bab7d4324ca023/1.20.json` (`24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`), client mappings `.../a4cd9a97400f7ecfe4dba23e427549ebc5815d66/client.txt` (`fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`), and client JAR `.../e575a48efda46cf88111ba05b624ef90c520eef1/client.jar` (`5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`). There is no separate 1.20 ready marker/source sidecar at the release parent. I rehashed all 4,786 1.20 Mojmap Java files against the embedded `sources` map: zero mismatches. Each source file listed below also matches its sidecar/map entry.

## Respawn construction and cache effect

1.19.4 Mojmap: `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleRespawn`, lines 1060–1129; `net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, `createPlayer`, lines 377–383.

1.20 Mojmap: the same listener, `handleRespawn`, lines 1065–1143; game mode `createPlayer`, lines 370–376.

Difference: 1.19.4 unconditionally calls the four-argument `createPlayer(..., old.isShiftKeyDown(), old.isSprinting())`. In 1.20, that call is under `packet.shouldKeep((byte)2)`; the else branch uses the three-argument overload, which delegates to `createPlayer(..., false, false)` in both versions. The four-argument game-mode overload simply constructs `LocalPlayer` with those two booleans in both versions. `LocalPlayer` stores them as `wasShiftKeyDown` and `wasSprinting` (`LocalPlayer.java:134–145` in 1.19.4; `:136–147` in 1.20). The same keep-bit condition controls copying the old entity-data values into the replacement player (`ClientPacketListener.java:1105–1110` old; `:1116–1121` new).

Downstream use: in both versions, `LocalPlayer.tick()` invokes `sendPosition()` on the non-passenger path after `super.tick()` (1.19.4 `LocalPlayer.java:187–200`; 1.20 `:189–202`). `sendPosition()` calls `sendIsSprintingIfNeeded()` and compares current `isShiftKeyDown()` with `wasShiftKeyDown`; mismatches send `START_SPRINTING`/`STOP_SPRINTING` or `PRESS_SHIFT_KEY`/`RELEASE_SHIFT_KEY`, then update the cache (old `:218–228,270–279`; new `:220–229,272–280`). On the controlled-passenger path, only the sprint cache check is reached, and only for a locally controlled root vehicle (`old :190–197`; `new :192–199`). Thus, when keep-bit 2 is false and old cache values differ from the replacement player's current input/sprint state, the first eligible tick can send a different command. This is client command-send bookkeeping: these fields are read only by those send checks, while `LocalPlayer.isShiftKeyDown()` reads `input.shiftKeyDown` and `Entity.isSprinting()` reads shared entity flag 3 (old `LocalPlayer.java:594–597`, `Entity.java:2013–2019`; new `LocalPlayer.java:597–600`, `Entity.java:2116–2122`). The cache difference does not directly alter local movement physics or those state predicates before that tick. No claim is made about when or whether the peer processes a sent command.

Respawn game-mode path: both handler versions finish with `gameMode.setLocalMode(packet player type, previous player type)` (old `ClientPacketListener.java:1129`; new `:1143`), and both `MultiPlayerGameMode.setLocalMode` implementations update local abilities without invoking a player velocity callback (old/new `MultiPlayerGameMode.java:84–88`). Consequently, the respawn game-mode assignment itself does not produce the player-info velocity behavior below.

## Separate player-info game-mode response

1.19.4 Mojmap `ClientPacketListener.java:1726–1733` applies `UPDATE_GAME_MODE` by updating the `PlayerInfo` entry only. In 1.20, `ClientPacketListener.java:1740–1750` first checks that the game mode changed and that the entry UUID matches the current local player, then calls `LocalPlayer.onGameModeChanged(newMode)` before updating the entry. The new 1.20 method (`LocalPlayer.java:1053–1057`) sets the local player's Y component of delta movement to `0.0` when the new mode is `SPECTATOR`.

Movement meaning: on the reachable local-player UUID + changed-to-spectator branch, executing the 1.20 player-info handler immediately clears vertical velocity. This is a direct player movement-state write and can be observable before the next ordinary local-player tick if this handler runs first; no packet-arrival ordering is asserted. It is separate from the respawn cache initialization above.

## Source hashes for this closure

The hashes below are SHA-256 of the compared Java files; all six per-version values match the relevant 1.19.4 parent sidecar or 1.20 embedded source map.

| Source | 1.19.4 | 1.20 |
| --- | --- | --- |
| `ClientPacketListener.java` | `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715` | `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b` |
| `LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `MultiPlayerGameMode.java` | `a2d6b02ef8849cc036bbc54e81327c2ab20603ae9d350c37b65b1de8a8342c98` | `20cf40ac52fab10eb29f6a8181dfdcdcb5feb268ee10b5a851f6fc8aa2a2747e` |
| `Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `Player.java` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |

This is a bounded source closure for the assigned respawn-cache and player-info/game-mode paths. It does not close the pair's remaining ordinary movement or packet-fed input dependencies, establish a 1.20.x patch cutover, or infer server packet production/processing.
