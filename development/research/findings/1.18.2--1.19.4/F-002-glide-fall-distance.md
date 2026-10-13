# F-002: Glide updates slow fall distance only when above one block

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, checkSlowFallDistance 2167-2170.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2074-2079.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2048-2051.

Difference: Under the shared glide-branch condition `vertical delta > -0.5`, 1.18.2 always overwrites accumulated `fallDistance` with `1.0F`, including when it was `0.0F` or between zero and one. 1.19.4 sets it to `1.0F` only if the accumulated value was already greater than `1.0F`; values at or below one remain unchanged. The delta is the extra `fallDistance > 1.0F` guard in 1.19.4's helper. Values greater than one are still reduced to one in 1.19.4.

Movement meaning: 1.18.2 local player can reach `LivingEntity.travel()` while fall-flying after the player equips/activates Elytra; the glide branch updates this state before its movement math. The helper is inherited from `Entity` in 1.19.4. `fallDistance` persists as player state and feeds later fall handling, but damage application is server-side. For a gliding player whose vertical delta is above `-0.5`, 1.19.4 preserves a smaller fall distance that 1.18.2 would raise to `1.0F`; for an accumulated value above one, both leave one. This can change later fall-distance-dependent handling after leaving glide.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/GlideFallDistance.java"
    ],
    "hooks": [
      "GlideFallDistanceHook"
    ],
    "limits": [
      "Change writes 1.0F without the later >1 gate, but registration ends at V1_18_2; later old endpoint applicability is not proved by presence. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2074-2079",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543 *1.18.2/client_mappings.txt."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2048-2051",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkSlowFallDistance",
      "lines": "2167-2170",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
