# F-S1-01: 1.21.4 defers the local player movement tick until level readiness or timeout

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, tick 2457-2459; aiStep 2680-2808.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 189-212.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-217.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, class declaration / field initialization 167-168.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, tick 2435-2454.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, tick 2441-2464.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/multiplayer/LevelLoadStatusManager.java`, LevelLoadStatusManager 17-44.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/multiplayer/LevelLoadStatusManager.java`, LevelLoadStatusManager 17-44.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/Minecraft.java`, runTick 1155-1290; tick 1724-1732.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/Minecraft.java`, runTick 1147-1285.

Difference: For a non-removed, non-passenger, non-frozen local player in an unpaused level, with `clientLoaded == false` and timeout greater than zero, 1.21.3 runs the ordinary inherited local-player tick. 1.21.4 runs only `tickClientLoadTimeout()` and returns without `super.tick()`. 1.21.4's timer starts at 60 and is decremented before the readiness check, so if readiness never arrives, the inherited tick is permitted when the timer first reaches zero. If readiness becomes true, the packet-listener tick sets the flag after that frame's entity-tick phase; local movement resumes on the next entity tick. The readiness status manager is present on both sides and uses the same player-chunk/render-section readiness conditions. The change is 1.21.4's use of that status to gate the local player and its 60-tick fallback. The receiving-level screen itself does not pause the world tick, so the gate changes a reachable tick path.

Movement meaning: `Minecraft.runTick()` -> `ClientLevel.tickEntities()` -> `tickNonPassenger(LocalPlayer)` -> `LocalPlayer.tick()` -> inherited `LivingEntity.tick()` -> `aiStep()` -> jump/travel. 1.21.4 gates this path on a local client readiness flag set by `ClientPacketListener.tick()` or by the timeout. The loaded packet is an external protocol notification; no server movement behavior is inferred here. Source proves 1.21.4 suppresses the ordinary local player/AI/movement tick while the load flag is false and the timeout remains positive. This defers input sampling and local travel during that interval.


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
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "189-212",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "191-217",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "class declaration / field initialization",
      "lines": "167-168",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "tick",
      "lines": "2435-2454",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "tick",
      "lines": "2441-2464",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/multiplayer/LevelLoadStatusManager.java",
      "member": "LevelLoadStatusManager",
      "lines": "17-44",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/multiplayer/LevelLoadStatusManager.java",
      "member": "LevelLoadStatusManager",
      "lines": "17-44",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/Minecraft.java",
      "member": "runTick",
      "lines": "1155-1290",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ff5f4b2907fa383e8ba0a79b9f02518491350e48a3079a15c6cfd53e0dc0ce1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/Minecraft.java",
      "member": "runTick",
      "lines": "1147-1285",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f4c9ba42f66a267ff01ff23f2bc9a8472d6f168effb1fc84314a613f730f94b1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/multiplayer/ClientLevel.java",
      "member": "tickEntities",
      "lines": "248-258",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f99ca9b24f796bed5428e6a3b010c489bfac2f43a35e90ff4fd8dad6d2e45aa1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/multiplayer/ClientLevel.java",
      "member": "tickEntities",
      "lines": "252-262",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d21332aed2f9460594f838bbac2c813572fe5addf2ef0b9f9f4e9ffbd74ad313; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/network/Connection.java",
      "member": "tick",
      "lines": "376-397",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 415eef2cf4b40efc938ef5bb6d05643c1c840a28514b5130b0a669a027e1b400; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/network/Connection.java",
      "member": "tick",
      "lines": "376-397",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c25d7ecc9f1f453fc0568625b4090e1c455419d998232d8190a33c345a1ecead; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/Minecraft.java",
      "member": "tick",
      "lines": "1724-1732",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ff5f4b2907fa383e8ba0a79b9f02518491350e48a3079a15c6cfd53e0dc0ce1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/multiplayer/ClientLevel.java",
      "member": "tickEntities",
      "lines": "248-279",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f99ca9b24f796bed5428e6a3b010c489bfac2f43a35e90ff4fd8dad6d2e45aa1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/gui/screens/ReceivingLevelScreen.java",
      "member": "isPauseScreen",
      "lines": "84-86",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8700b76cc34d8db3ad1f65854e7897d6a2d8d13b41a9c5c28ed222f4ac44aa64; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/gui/screens/ReceivingLevelScreen.java",
      "member": "isPauseScreen",
      "lines": "83-86",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8700b76cc34d8db3ad1f65854e7897d6a2d8d13b41a9c5c28ed222f4ac44aa64; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "tick",
      "lines": "2457-2459",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2680-2808",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/network/Connection.java",
      "member": "tick",
      "lines": "376-380",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 415eef2cf4b40efc938ef5bb6d05643c1c840a28514b5130b0a669a027e1b400; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/network/Connection.java",
      "member": "tick",
      "lines": "376-380",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c25d7ecc9f1f453fc0568625b4090e1c455419d998232d8190a33c345a1ecead; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
