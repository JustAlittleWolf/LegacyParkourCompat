# F-2: Spectator attack input reaches Lunge only in 1.21.11

1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, lungeForwardMaybe 1655-1659.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java`, postPiercingAttack 1675-1679.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, attack 953-1007.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, attack 951-1005.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, attack 420-425.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/network/protocol/game/ServerboundInteractPacket.java`, createAttackPacket 45-47.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java`, handleInteract 1711-1725.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/GameType.java`, updatePlayerAbilities 62-80.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java`, changeGameModeForPlayer 55-84.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/client/multiplayer/MultiPlayerGameMode.java`, attack 425-430.

Difference: Use a vanilla Player in SPECTATOR mode with a main-hand vanilla spear carrying Lunge, food level at least 7, no vehicle, not fall-flying and outside water. Retain the spear while entering spectator: the paired `ServerPlayerGameMode.changeGameModeForPlayer` source changes the game-mode/ability fields and does not clear inventory. Aim at a vanilla attackable living target in the same world, inside both the old 3-block entity-interaction range and the new held-item attack range, and choose an ordinary target that does not trigger `skipAttackInteraction`; an enabled spear and attack-strength state that passes the identical `cannotAttackWithItem` predicate satisfy the input gates. No health or damage outcome is needed: 1.21.11's Lunge call occurs after that sub-branch. For this state, 1.21.11's vanilla attack input dispatches the generic interaction packet. The server accepts the loaded-client/target/border/range guards and invokes `Player.attack`. SPECTATOR supplies `mayfly=true`, so the Player-specific food gate passes; the server-side enchantment route reaches the Lunge effect. With Lunge level `n`, the direct vector is evaluated in this order: obtain look angle; add the local `(0,0,1)` direction; multiply coordinates by `(1,0,1)`; scale by `n * 0.458F`; add to the Player's existing delta movement; set velocity synchronization markers and the 10-tick grace timer. 1.21.11 nonzero horizontal velocity response is produced for a nonvertical look direction. For the equivalent 26.1.2 state and attack-button input, vanilla client handling takes the spectator branch, calls `spectate(target)` and returns before sending an attack packet. Independently, 26.1.2's server `handleAttack` rejects `isSpectator()` before dispatching `Player.attack`. 26.1.2 therefore produces no Lunge Player delta-velocity write on this route. The 26.1.2 Lunge food predicate itself would pass at food level at least 7 if its post-attack caller ran; the route guard is the isolated difference. Existing food, inventory, target and pose/movement state are predicate inputs; no attack/damage result or non-player trajectory is compared.

Movement meaning: The exact call path is 1.21.11 attack button -> `Minecraft` entity-hit branch -> `MultiPlayerGameMode.attack` packet send -> server `handleInteract` -> `Player.attack` -> server `LivingEntity.lungeForwardMaybe` -> main-hand Lunge effect -> direct `Player.addDeltaMovement`. 26.1.2's button branch and server handler each stop the same spectator route before `Player.attack` / `postPiercingAttack`. The current game-mode writer and its mayfly output are paired; the item eligibility resources are paired as listed. The target may be an ordinary attackable living entity; no entity motion producer is part of the candidate. Candidate remains pending because the downstream spectator travel/position consumer and all other Lunge entry callers/effect-resource closures have not been exhaustively closed.


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
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java",
      "member": "attack",
      "lines": "420-425",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4d15d5c6b8c280d0e2c6de1b0f41bde83bc0944d400b7f89cabff24679e9a9c0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/network/protocol/game/ServerboundInteractPacket.java",
      "member": "createAttackPacket",
      "lines": "45-47",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d2da5864b00be39cfec4a21e0be54916b13a325593d1a0ebc51f66fe1adca92c; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "handleInteract",
      "lines": "1711-1725",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7e8d899f2da37978b07f80cacab3ea93bc7b5d70b560d0e7b2384f59627dd2dd; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "attack",
      "lines": "953-1007",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "lungeForwardMaybe",
      "lines": "1655-1659",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/GameType.java",
      "member": "updatePlayerAbilities",
      "lines": "62-80",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c39a938ee990ad3ec0e2eebc7323139007366245276b904bba9d5fe687d35ad7; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/server/level/ServerPlayerGameMode.java",
      "member": "changeGameModeForPlayer",
      "lines": "55-84",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f301db520191c8eab112e9edfd51539535c3feae6b3b0a67a521d930928c3364; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/client/multiplayer/MultiPlayerGameMode.java",
      "member": "attack",
      "lines": "425-430",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 674fe62b7debec3fa582735ae1b86247a25211ec03ec19c9f331dd3c44502e37; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "handleAttack",
      "lines": "1797-1823",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7e643834e2ab54bd1b1aeb79f72146ac4e1f6d30b34e7608091a9ab15494d3e7; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java",
      "member": "attack",
      "lines": "951-1005",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java",
      "member": "postPiercingAttack",
      "lines": "1675-1679",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "1011-1049",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c4de10d11104f209de97a002744c277a869a05a86f303b1d68a2a389c8f4c5e9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/world/item/enchantment/Enchantments.java",
      "member": "bootstrap",
      "lines": "1017-1075",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 87c4d7799516cf33a0399398f4e7b503dc8ef1ebb8588fa2d5decfc33fa12157; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "26.1.2",
      "mapping": "unobfuscated",
      "path": "26.1.2/unobfuscated/net/minecraft/util/Mth.java",
      "member": "floor",
      "lines": "61-63",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Canonical source-manifest SHA-256 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "1.21.11--26.1.2 spectator downstream travel/position consumer and complete other Lunge caller/effect-resource closure.",
      "next_action": "Retain pending bounded route; later authorized review must close the named dependencies before implementation eligibility."
    }
  ],
  "runtime": "unverified"
}
```
