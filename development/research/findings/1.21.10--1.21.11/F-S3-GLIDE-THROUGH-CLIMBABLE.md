# F-S3-GLIDE-THROUGH-CLIMBABLE: Fall-flying Players no longer take the climbable fallback on tagged vines

1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java`, onClimbable 1662-1678; travelFallFlying 2383-2397.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, onClimbable 1669-1687.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/player/Player.java`, onClimbable 1955-1957; travel 1295-1318.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/player/Player.java`, travel 1360-1383.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java`, tag 320-324.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/tags/BlockTags.java`, class declaration / field initialization 125.

Difference: For a Player with `abilities.flying == false`, `isSpectator() == false`, `isFallFlying() == true`, and a current block state at `blockPosition()` that is one of the vanilla-default tag intersection states (vine, twisting vine or plant, weeping vine or plant, cave vine or plant), outside the earlier fluid-travel branch, 1.21.10 reports the Player climbable and 1.21.11 reports it not climbable. The `Player.onClimbable()` wrapper delegates to `LivingEntity.onClimbable()` under this ability guard. 1.21.10 then calls `travelInAir()` and `stopFallFlying()`; 1.21.11 follows the fall-flying movement branch. This is a branch and state-write difference, not a measured trajectory.

Movement meaning: The intersection blocks are present in 1.21.10's vanilla `climbable` tag and are existing blocks in the 1.21.10 endpoint artifact. This is a direct player movement-path difference; it does not depend on custom tags for the stated vanilla-default case. Server/resource-pack tag overrides are not assessed. The glide formula itself is otherwise unchanged for an equal branch decision.


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
      "member": "onClimbable",
      "lines": "1662-1678",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "onClimbable",
      "lines": "1669-1687",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "onClimbable",
      "lines": "1955-1957",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java",
      "member": "tag",
      "lines": "320-324",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c17f2a6c6654721019e575377523edb0ac68c71b8325188a4e6412962c83474; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/tags/BlockTags.java",
      "member": "class declaration / field initialization",
      "lines": "125",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbec38e3; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "travel",
      "lines": "1295-1318",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.11",
      "mapping": "mojmap",
      "path": "1.21.11/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "travel",
      "lines": "1360-1383",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd *1.21.11/client_mappings.txt. Canonical source-manifest SHA-256 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.10",
      "mapping": "mojmap",
      "path": "1.21.10/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travelFallFlying",
      "lines": "2383-2397",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39 *1.21.10/client_mappings.txt. Canonical source-manifest SHA-256 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
