# F-S4-PLAYER-ENTITY-PUSH — source finding snapshot

1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/Entity.java`, push 1720-1749.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, push 1738-1767; push 1755-1758; push 1775-1780.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, isPushable 3077-3079; pushEntities 2940-2964; doPush 2989-2991.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, pushEntities 3077-3079; isPushable 3203-3205; aiStep 2940-2964; pushEntities 3066-3090; aiStep 2989-2991; doPush 3115-3117.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/level/Level.java`, getPushableEntities 752-754.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/level/Level.java`, getPushableEntities 740-742.

Difference: For a Player in the paired `can_glide_through` block states while fall-flying, the 1.21.11 source can apply a direct Player velocity response from nearby entity pushing where 1.21.10 does not. This is a separate consumer of the climbable tag difference recorded in `F-S3-GLIDE-THROUGH-CLIMBABLE`; it does not broaden that finding's fall-flying travel claim.

Movement meaning: The bounded state requires an alive, nonspectator Player who is fall-flying with the flight ability disabled, and whose current block state is one of the seven states added to the 1.21.11 `can_glide_through` tag expansion. Existing `onClimbable()` logic then returns true in 1.21.10 and false in 1.21.11. The vanilla tick path reaches `LivingEntity.pushEntities()` through virtual `tick()`/`aiStep()` dispatch and Player's super calls. If a nearby pushable entity is returned, the entities are not in the same vehicle, neither relevant entity is `noPhysics`, the Player is not a vehicle, and the horizontal separation meets `Mth.absMax(dx,dz) >= 0.01F`, the Player's argument-side push guard rejects the direct velocity write in 1.21.10 and permits it in 1.21.11. The write adds the computed vector to Player delta movement. 1.21.10/1.21.11 preserve the same push formula; 1.21.11 checks all three vector components are finite and sets `needsSync`, while 1.21.10 adds the vector and sets `hasImpulse`. This is a source-level movement consequence only. `S4-player-entity-push` closes the direct Player pushability dependency `D-S3-CLIMBABLE-PUSHABILITY`. Non-player movement, vehicle movement, damage, and combat are excluded. Independent finding-specific blind source review is pending.


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
      "path": "1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "isPushable",
      "lines": "3077-3079",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "pushEntities",
      "lines": "3077-3079",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "isPushable",
      "lines": "3203-3205",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "pushEntities",
      "lines": "2940-2964",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2940-2964",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "pushEntities",
      "lines": "3066-3090",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "doPush",
      "lines": "2989-2991",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2989-2991",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "doPush",
      "lines": "3115-3117",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "push",
      "lines": "1720-1749",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "push",
      "lines": "1738-1767",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "push",
      "lines": "1755-1758",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "push",
      "lines": "1775-1780",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/level/Level.java",
      "member": "getPushableEntities",
      "lines": "752-754",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 dc79073f598a2cb86fc1384e4459e9d384b10f4beb3694159786f2804b7d19fe; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/level/Level.java",
      "member": "getPushableEntities",
      "lines": "740-742",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 de329fdbeca632a1b63074e1735890b2624abfc57b7a579f43eef36caefef98d; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
