# F-14: Sleeping takes precedence over retained fall-flying pose in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, setPose 367-369.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, setPose 375-377.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, startSleeping 3395-3410.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, startSleeping 3395-3410; startSleeping 3386-3401.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-195; aiStep 749-750; aiStep 770-771.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-195; aiStep 749-750; aiStep 770-771.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, tick 329; updatePlayerPose 434-461; canGlide 1474-1475; tryToStartFallFlying 1528-1535; startFallFlying 1537-1539; updatePlayerPose 449-461.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, tick 329; tick 344; updatePlayerPose 449-461; getDesiredPose 465-475; canGlide 1452-1453; tryToStartFallFlying 1507-1514.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/BedBlock.java`, useWithoutItem 83-119; useWithoutItem 81-117.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/BedBlock.java`, useWithoutItem 83-119; useWithoutItem 81-117.

Difference: Bed use is handled on the server: `BedBlock#useWithoutItem` returns immediately on the client, and the server-side `ServerPlayer#startSleepInBed` checks the sleep conditions and then calls `Player#startSleepInBed` -> `LivingEntity#startSleeping`. The paired server checks include range and obstruction but no grounded or fall-flying predicate. `startSleeping` sets `Pose.SLEEPING`, moves the player to the bed, records the sleeping position, sets delta movement to `Vec3.ZERO`, and marks an impulse. It does not clear the fall-flying shared flag. During the following `LivingEntity.aiStep`, active fall flying calls `updateFallFlying`; on the server the flag is cleared only when `canGlide()` fails. 1.21.4 retained state therefore requires the player to remain airborne, not be a passenger, not have flying abilities enabled, lack Levitation, and have an equipped, non-breaking item whose components permit gliding in that slot. The ordinary fall-flying writer is reachable through both client input and the server command path. `LocalPlayer.aiStep` calls `Player.tryToStartFallFlying()` on jump input and sends `START_FALL_FLYING`; `ServerGamePacketListenerImpl.handlePlayerCommand` invokes the same method. On success, `tryToStartFallFlying()` requires `!isFallFlying()`, `canGlide()`, and `!isInWater()`, then `startFallFlying()` sets shared flag 7. For a player, `canGlide()` also requires abilities-based flight to be off, airborne state, no passenger, no Levitation, and an eligible equipped item. Vanilla Elytra supplies `GLIDER` and chest-slot `EQUIPPABLE`; `canGlideUsing` verifies both components, matching equipment slot and that the next durability damage will not break it. `Player.tick` calls `super.tick()` before `updatePlayerPose()`. Both versions first require the player to fit the swimming dimensions and then apply their desired-pose/fallback checks. 1.21.4 1.21.4 chooses `FALL_FLYING` before it checks `isSleeping()`. In 1.21.5, `getDesiredPose()` checks `isSleeping()` before `isFallFlying()`. If both states remain active and the fit gates pass, 1.21.4 changes the pose back to `FALL_FLYING`; 1.21.5 keeps `SLEEPING`.

Movement meaning: Ordinary jump input -> `LocalPlayer.aiStep` -> `Player.tryToStartFallFlying` -> `START_FALL_FLYING` packet -> `ServerGamePacketListenerImpl.handlePlayerCommand` -> successful `Player.startFallFlying` flag-7 write. `Items.ELYTRA` supplies the required `GLIDER` and chest `EQUIPPABLE` components; `LivingEntity.canGlideUsing` and the inherited/player glide gates establish the applicable equipped state. Server bed interaction -> `ServerPlayer#startSleepInBed` -> `LivingEntity#startSleeping` -> sleeping pose and position, zero velocity, sleeping-position state without clearing flag 7; next server `LivingEntity.aiStep`/`canGlide` -> `Player.tick`/`updatePlayerPose` -> `Entity.onSyncedDataUpdated`/`refreshDimensions` -> player bounding box and eye height. The bed callback's client-side branch does not start sleep locally. On client, the server supplies synchronized sleeping/fall-flying state; the local `Player` pose update may consume it, while `RemotePlayer` overrides pose updating. The authoritative server pose decision is the relevant state transition. The finding-specific shared-flag writer, local input and server packet callers, and vanilla Elytra component/equipment path are now closed by the paired source evidence above.


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
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/level/block/BedBlock.java",
      "member": "useWithoutItem",
      "lines": "83-119",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 6fa509ef173bb0d670294c66495ee5aa388a0fb2bbed07e21757ac44a0e56d20; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/BedBlock.java",
      "member": "useWithoutItem",
      "lines": "83-119",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 93231daf73d11a6b8d72e6f42f4dcf8b928e443d34708e0a4c2ee50c1c88d4dd; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "startSleepInBed",
      "lines": "1113-1157",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d4fd29d9bed9698797e14cf9883e53f04ff4735afb2fee2972085cd82bf0d8f2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "startSleepInBed",
      "lines": "1113-1157",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 81ec90de6bf1521f45f1552c60af1ebb1a83fbcfa2eefb41b99e25b99d2f0d1b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tick",
      "lines": "329",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tick",
      "lines": "329",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "updatePlayerPose",
      "lines": "434-461",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "canGlide",
      "lines": "1474-1475",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tryToStartFallFlying",
      "lines": "1528-1535",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "startFallFlying",
      "lines": "1537-1539",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "startSleeping",
      "lines": "3395-3410",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "startSleeping",
      "lines": "3395-3410",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "191-195",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "191-195",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "749-750",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "749-750",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "handlePlayerCommand",
      "lines": "1512-1515",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c5467cc282f361b5ab5540066385240f847d101c68afdef8655908f3fb3b2c8b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/item/Items.java",
      "member": "class declaration / field initialization",
      "lines": "971-982",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e872f8251d0b4f4a8b8ed1609dda56b57d7bc8ce35fc4477095334cbd6d3df0b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/item/Items.java",
      "member": "class declaration / field initialization",
      "lines": "971-982",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d258c31e88d6e7208b7c196a7b7408172f7c54050e11442e54894a1d31f4f5d5; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setPose",
      "lines": "367-369",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/level/block/BedBlock.java",
      "member": "useWithoutItem",
      "lines": "81-117",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 6fa509ef173bb0d670294c66495ee5aa388a0fb2bbed07e21757ac44a0e56d20; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/BedBlock.java",
      "member": "useWithoutItem",
      "lines": "81-117",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 93231daf73d11a6b8d72e6f42f4dcf8b928e443d34708e0a4c2ee50c1c88d4dd; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/server/level/ServerPlayer.java",
      "member": "startSleepInBed",
      "lines": "1067-1111",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 81ec90de6bf1521f45f1552c60af1ebb1a83fbcfa2eefb41b99e25b99d2f0d1b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tick",
      "lines": "344",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "updatePlayerPose",
      "lines": "449-461",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "updatePlayerPose",
      "lines": "449-461",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "getDesiredPose",
      "lines": "465-475",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "canGlide",
      "lines": "1452-1453",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tryToStartFallFlying",
      "lines": "1507-1514",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "startFallFlying",
      "lines": "1516-1518",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "startSleeping",
      "lines": "3386-3401",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "770-771",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "770-771",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "handlePlayerCommand",
      "lines": "1596-1599",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/item/Items.java",
      "member": "class declaration / field initialization",
      "lines": "989-1001",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e872f8251d0b4f4a8b8ed1609dda56b57d7bc8ce35fc4477095334cbd6d3df0b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/item/Items.java",
      "member": "class declaration / field initialization",
      "lines": "989-1001",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d258c31e88d6e7208b7c196a7b7408172f7c54050e11442e54894a1d31f4f5d5; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setPose",
      "lines": "375-377",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
