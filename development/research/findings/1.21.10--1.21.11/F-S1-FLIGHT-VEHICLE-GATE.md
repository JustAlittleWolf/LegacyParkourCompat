# F-S1-FLIGHT-VEHICLE-GATE: Mayfly double-press flight toggle is gated while riding a non-jumpable vehicle

1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/Entity.java`, rideTick 2234-2240.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, tick 2542-2583; tick 2542-2654.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 751-770; jumpableVehicle 522-524; tick 195-220; isControlledCamera 664-666.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 791-811.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/client/multiplayer/ClientLevel.java`, tickEntities 321-360.

Difference: With `mayfly` enabled, outside spectator mode, not swimming, after the first jump press starts the seven-tick trigger, a second jump press toggles flight in 1.21.10 even when the player is a passenger. In 1.21.11, that toggle proceeds only if there is no vehicle or `jumpableVehicle()` is non-null. 1.21.10 player riding a non-jumpable vehicle therefore toggles `Abilities.flying` in 1.21.10 but not 1.21.11 for the same input sequence. The condition is an additive gate; the jump timer and remaining toggle operations are otherwise unchanged in the paired body.

Movement meaning: The passenger path is reachable: `ClientLevel.tickEntities` filters out passenger entities, then its `tickPassenger` path calls `rideTick()` for Player entities. `LocalPlayer.rideTick()` delegates through `LivingEntity.rideTick()` to `Entity.rideTick()`, which invokes virtual `tick()`. The local player tick calls the superclass tick after its loaded gate; `LivingEntity.tick()` reaches `aiStep()`. The new guard therefore is not dead code on the passenger path. This finding does not emulate the vehicle's motion or input handling. `Abilities.flying` is the state written by the toggle and sent through the existing `onUpdateAbilities()` path. The same aiStep later adds vertical player delta from jump/shift input when flying and the local player is the controlled camera. The state can remain set across a later dismount until another existing condition changes it, so a dismount before grounding can expose the different flight input state. Source proves that the 1.21.10/1.21.11 guards produce different local `Abilities.flying` values for the stated precondition and that this value gates the local camera-controlled vertical velocity write. The report predicts that a later airborne dismount can therefore enter different player-side flight-input behavior.


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
      "member": "aiStep",
      "lines": "751-770",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "791-811",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "jumpableVehicle",
      "lines": "522-524",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/client/multiplayer/ClientLevel.java",
      "member": "tickEntities",
      "lines": "321-360",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2a4bf7bac40707bf0d7d2feaa1f6564f5aff7aae1b923cf94270165425dc8ee3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "rideTick",
      "lines": "2234-2240",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "tick",
      "lines": "2542-2583",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "tick",
      "lines": "2542-2654",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
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
      "path": "1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "isControlledCamera",
      "lines": "664-666",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
