# F-004 — Fall-flying fall distance changes farmland trample input

1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, checkSlowFallDistance 2147-2151.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2107-2111.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/level/block/FarmBlock.java`, fallOn 89-98.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/level/block/FarmBlock.java`, fallOn 89-98.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java`, class declaration / field initialization 63-65.

Difference: For the same fall-flying branch predicate, 1.19.2 unconditionally overwrites `fallDistance` with `1.0F`; 1.19.3 overwrites it only when the prior value exceeds `1.0F`. Therefore, when the pre-branch value is below `1.0F`, 1.19.2 supplies `1.0F` to the landing callback path and 1.19.3 preserves the smaller value.

Movement meaning: `LivingEntity.travel` -> player `Entity.move` -> `Entity.checkFallDamage` -> landing block `fallOn` -> `FarmBlock.fallOn` -> server `setBlockAndUpdate(farmland -> dirt)` -> replicated block state -> later player collision shape query. The movement response hook `updateEntityAfterFallOn` is a separate call and does not consume `fallDistance`. FarmBlock's relevant code and shapes are identical across 1.19.2/1.19.3; only the upstream travel value differs. Source-proven: the 1.19.2 and 1.19.3 values can produce different farmland-trample predicates. If the server-side random check succeeds, farmland is replaced with dirt; farmland's shape is 15/16 block height while dirt inherits the full-block shape. Predicted only: after the world update reaches a client and the player later collides with that location, the collision surface can differ. The server RNG, block update, and delivery are external inputs; no actual world update or trajectory is claimed.


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
      "Same fallDistance guard difference feeds farmland; existing Change covers older profiles, but resolver does not select V1_18_2 for 1.19.2. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "2107-2111",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkSlowFallDistance",
      "lines": "2147-2151",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/level/block/FarmBlock.java",
      "member": "fallOn",
      "lines": "89-98",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ef03ebba83a5621c1f91bb4f777bd69f1ba3ab6afbc9ed04bcbbbba62cf90fbb; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/level/block/FarmBlock.java",
      "member": "fallOn",
      "lines": "89-98",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ef03ebba83a5621c1f91bb4f777bd69f1ba3ab6afbc9ed04bcbbbba62cf90fbb; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java",
      "member": "class declaration / field initialization",
      "lines": "63-65",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
