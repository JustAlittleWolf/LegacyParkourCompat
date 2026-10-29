# Independent source audit: 1.19.4→1.20 respawn cache and player-info game mode

## Scope and result

I independently retraced the exact Mojmap client sources for `handleRespawn`, `MultiPlayerGameMode.createPlayer`, the `LocalPlayer` cache consumers, and `UPDATE_GAME_MODE`. The described split is supported: the respawn difference concerns local command-send caches; the 1.20 player-info callback is a separate direct write to local vertical velocity on the matching-local-UUID, changed-to-spectator path. I found no discrepancy in the cited source hashes or in the described behavior within this bounded path.

This audit makes no claim about server packet production/processing, delivery order, profile behavior, other movement/input dependencies, or a later 1.20.x cutover.

## Source identity and integrity

I read the canonical Mojmap tree at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- **1.19.4 publication:** `1.19.4/mojmap.ready.json` identifies `versionId=1.19.4`, `mapping=mojmap`, and 4,740 sources. The source directory and sidecar each contain 4,740 entries. The sidecar's actual SHA-256 equals the ready marker's `sourceManifestSha256`: `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`. The ready marker and `1.19.4/artifacts.sha256` agree on client JAR SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`; the artifact manifest also pins `client-mojmap.jar` (`efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`) and `client_mappings.txt` (`5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606`). The ready marker's source-manifest and client digests therefore match their release-parent pins.
- **1.20 publication:** the release parent contains only `mojmap/`; there is no sibling ready marker, artifacts manifest, source sidecar, or `mojmap.provenance.json`. The mapping-root `.decompile-provenance.json` itself hashes to `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`. Its embedded inputs pin the 1.20 metadata JSON (`24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`), client mappings (`fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`), and client JAR (`5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`). Its embedded source map has 4,786 entries; I rehashed all 4,786 Java sources and found zero missing files or mismatches. Thus 1.20 identity is evidenced by the mapping-root provenance and embedded pins, not by a separate release-parent ready/sidecar publication.
- **Cited source files:** all six hashes per version in the source closure match independently computed SHA-256. For 1.19.4, each also equals its `mojmap.sources.sha256` entry; for 1.20, each equals its embedded provenance `sources` entry.

| Source | 1.19.4 SHA-256 | 1.20 SHA-256 |
| --- | --- | --- |
| `ClientPacketListener.java` | `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715` | `8b21ccd4106abb0698f6872250e665ae3f0fc942c8953b9ddd45976660ed624b` |
| `LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `MultiPlayerGameMode.java` | `a2d6b02ef8849cc036bbc54e81327c2ab20603ae9d350c37b65b1de8a8342c98` | `20cf40ac52fab10eb29f6a8181dfdcdcb5feb268ee10b5a851f6fc8aa2a2747e` |
| `Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `LivingEntity.java` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `Player.java` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |

## Behavioral retrace

### Respawn cache

At 1.19.4 `ClientPacketListener.handleRespawn` (1060–1129), replacement construction always passes the previous player's `isShiftKeyDown()` and `isSprinting()` values to the four-boolean `createPlayer` overload (1097). At 1.20 (1065–1143), that construction is conditional on `shouldKeep((byte)2)` (1103–1107): when set, it passes the old values; when unset, it calls the three-argument overload. In both `MultiPlayerGameMode` versions, the three-argument overload delegates with `false, false` (1.19.4 377–383; 1.20 370–376), and the four-argument overload forwards the booleans to the `LocalPlayer` constructor. The constructor stores them as `wasShiftKeyDown` and `wasSprinting` (1.19.4 140–141; 1.20 142–143). This is consistent with the same keep-bit governing entity-data copying, separately visible at 1.19.4 1105–1110 and 1.20 1116–1121.

The constructor-seeded values are read only by the sprint/shift command comparison and cache-update sites. `sendPosition()` compares `isShiftKeyDown()` to `wasShiftKeyDown`, sends `PRESS_SHIFT_KEY` or `RELEASE_SHIFT_KEY` on mismatch, then updates the cache (1.19.4 218–228; 1.20 220–229). `sendIsSprintingIfNeeded()` analogously compares `isSprinting()` with `wasSprinting`, sends `START_SPRINTING` or `STOP_SPRINTING`, and updates the cache (1.19.4 270–279; 1.20 272–281).

On the ordinary non-passenger path, `tick()` calls `super.tick()` and then `sendPosition()` (1.19.4 187–200; 1.20 189–202). On the passenger path, the sprint cache check is reached only if the root vehicle is locally controlled; that branch calls `sendIsSprintingIfNeeded()` but has no shift comparison (1.19.4 190–199; 1.20 192–201). The independent current-state sources are `LocalPlayer.isShiftKeyDown()` reading `input.shiftKeyDown` (old 594–597; new 597–600) and inherited `Entity.isSprinting()` reading shared flag 3 (old 2013–2017; new 2116–2120). This supports a command-bookkeeping effect when an omitted cache value differs from the current predicate at one of those send checks. It is not itself a direct local physics or predicate write. I make no claim about peer-side processing of any sent command.

At the end of either respawn handler, `gameMode.setLocalMode(...)` calls `MultiPlayerGameMode.setLocalMode`, which sets local/previous modes and updates abilities (1.19.4 and 1.20 84–88). That path does not call `LocalPlayer.onGameModeChanged`; it is distinct from the player-info update below.

### Player-info game-mode callback

In 1.19.4 `ClientPacketListener.applyPlayerInfoUpdate` handles `UPDATE_GAME_MODE` by setting the `PlayerInfo` entry's mode (1726–1733). In 1.20 (1740–1751), before setting the entry, it checks that the entry's current mode differs from the incoming mode, that `minecraft.player` is non-null, and that the local player's UUID equals the entry profile ID (1745–1750). Under this gate it calls `LocalPlayer.onGameModeChanged(newMode)`.

The 1.20 callback (LocalPlayer 1053–1057) acts only when the incoming mode is `SPECTATOR`; it writes delta movement as the existing vector with its Y axis set to `0.0`. Therefore, for a matching local-player UUID whose player-info mode changes to spectator, this handler directly clears local vertical velocity. It does not clear the horizontal components. That response is separate from respawn cache initialization; it can be observed before an ordinary player tick if its handler executes first, without asserting arrival ordering.

## Audit limits

This is source and provenance verification for the named closure only. It does not establish server-side causes, packet scheduling/order, behavior elsewhere in the tick/input pipeline, or compatibility boundaries beyond the two pinned versions.
