# F-S1-CLIENT-LOAD-GATE: Local movement ticks wait for load readiness in 1.21.11

1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 195-220.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 211-235.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/player/Player.java`, hasClientLoaded 1875-1877; hasClientLoaded 1875-1888.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, hasClientLoaded 2740-2742; hasClientLoaded 2740-2745.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, tick 2637-2651.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/client/Minecraft.java`, tick 1710-1843.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/client/Minecraft.java`, tick 1743-1872.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, tick 298-305.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, tick 298-305.

Difference: 1.21.10 local player decrements a 60-tick fallback timer at the beginning of every entity tick. When that timer expires, `hasClientLoaded()` returns true even if the listener has not sent/received the level-ready notification, so the rest of `LocalPlayer.tick()` runs. 1.21.11 moved the flag to `ClientPacketListener` and `LocalPlayer.tick()` returns early until the listener's load tracker calls `notifyPlayerLoaded()` and sets the flag. The two load paths are reachable while a client level exists and the client is unpaused: `Minecraft.tick()` ticks entities under those conditions without requiring the loading screen to be absent. During a delayed player-chunk readiness interval, after 60 calls to 1.21.10's local tick gate but before 1.21.11 marks the client loaded, 1.21.10 runs `super.tick()` and its movement path while 1.21.11 skips those operations. The 1.21.11 load tracker can remain pending until the player section is visible or its 30-second timeout; the 1.21.10 fallback is 60 local-player ticks.

Movement meaning: `Minecraft.tick()` first advances multiplayer connection processing through `MultiPlayerGameMode.tick()`, then calls `ClientLevel.tickEntities()` when `level != null` and `!pause`. `ClientLevel` ticks a local player through `tickNonPassenger` if it is not a passenger or through `tickPassenger`/`rideTick` if it is. Both paths invoke `LocalPlayer.tick()`, whose 1.21.10/1.21.11 loaded gates control the local superclass tick, input packet update and subsequent player movement work. 1.21.11's readiness flag is set by `ClientPacketListener.notifyPlayerLoaded()` when `LevelLoadTracker.isLevelReady()` returns true. Source proves that the two versions may run or skip local player movement-tick work under the same delayed-load precondition.


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
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "195-220",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "hasClientLoaded",
      "lines": "1875-1877",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "hasClientLoaded",
      "lines": "1875-1888",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "211-235",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "hasClientLoaded",
      "lines": "2740-2742",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "hasClientLoaded",
      "lines": "2740-2745",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "tick",
      "lines": "2637-2651",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1bb341ee18704d882bb68bf917190be1045649026a99545057118b5cb9bfb348; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/client/Minecraft.java",
      "member": "tick",
      "lines": "1710-1843",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ae782d427ff3f2b0a15bd58fe447bc3a07b216faed243a7618420f816516be50; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/client/Minecraft.java",
      "member": "tick",
      "lines": "1743-1872",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java",
      "member": "tick",
      "lines": "298-305",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bf6541cc58c82cc167a196cfadce23f258967a4dcc250b9c85dd772eda60a979; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java",
      "member": "tick",
      "lines": "298-305",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4d15d5c6b8c280d0e2c6de1b0f41bde83bc0944d400b7f89cabff24679e9a9c0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
