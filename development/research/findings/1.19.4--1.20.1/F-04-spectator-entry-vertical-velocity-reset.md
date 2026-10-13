# F-04: Spectator entry zeros local-player vertical velocity in 1.20.1

1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, onGameModeChanged 1053-1057.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/level/ServerPlayer.java`, setGameMode 1267-1272.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java`, changeGameModeForPlayer 50-62.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleGameEvent 1286-1288.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/server/level/ServerPlayer.java`, setGameMode 1276-1281.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java`, changeGameModeForPlayer 50-62.

Difference: 1.20.1's game-mode update path calls the local player's new callback before updating the matching PlayerInfo game-mode entry. The callback preserves the current X and Z velocity components and writes a new delta movement with Y = 0.0 when the new mode is spectator. 1.19.4's bounded game-mode handler only changes the local game-mode selection and has no equivalent local-player velocity callback.

Movement meaning: The server emits the UPDATE_GAME_MODE broadcast only after a changed game mode is accepted. On the client, the callback is reachable only for an entry whose new game mode differs from the stored entry, whose UUID matches the current local player, and whose new mode is spectator. 1.19.4 preexisting nonzero vertical component is the value this writer clears. This is a direct movement input; damage resolution, respawn semantics and the rest of the external-writer inventory are separate. 1.19.4 local player's vertical velocity is source-confirmed to differ at the spectator transition under those guards.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "setGameMode",
      "lines": "1267-1272",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5eeea9be89db11000daa95cada0a4120c5eda1014aae9e868fba0bee8de05606; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java",
      "member": "changeGameModeForPlayer",
      "lines": "50-62",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2515b55164734977c775d851a12e5e4d2c1c2e47a92163fbd4c43c05f0138275; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleGameEvent",
      "lines": "1286-1288",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "setGameMode",
      "lines": "1276-1281",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4168d5d45d043f5edc0d9cafeeff2540cf544bae92b53f1b018fd1ee8887dd61; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java",
      "member": "changeGameModeForPlayer",
      "lines": "50-62",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d9d7b218fa8ceb8c8f04fc03223cd1b05b95307910eb66099c968414f1dc96a9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "onGameModeChanged",
      "lines": "1053-1057",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
