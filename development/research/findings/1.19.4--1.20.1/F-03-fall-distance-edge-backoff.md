

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 1983-1991.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2562-2567.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1040-1093.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1043-1094.

Difference: 1.19.4 resets fall distance during LivingEntity travel only when current Y velocity is non-positive and Slow Falling is present. 1.20.1 resets it in LivingEntity.aiStep before travel whenever Slow Falling or Levitation is active. Thus with Levitation, and with rising movement under Slow Falling, 1.20.1 clears the prior fall-distance value before player movement while 1.19.4 can retain it.

Movement meaning: For a local player, sneaking input makes Player#isStayingOnGroundSurface true. Entity.move calls the Player override of maybeBackOffFromEdge before clipping for SELF or PLAYER movement. That method requires non-flying state and requested Y <= 0, then checks isAboveGround. If not onGround, isAboveGround compares fallDistance with maxUpStep and performs a no-collision query using a vertical offset derived from fallDistance. Clearing fallDistance can change whether backoff runs and the horizontal 0.05-step probe. The changed writer-to-reader path can affect horizontal movement near an edge for the stated guards. This is not a claim that every Levitation or Slow Falling tick changes position; the result depends on prior fall distance and collision geometry. Fall-damage consumers are outside movement scope.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_19_4/EffectFallDistanceReset.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_20/EffectFallDistanceReset.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_20_2/EffectFallDistanceReset.java"
    ],
    "hooks": [
      "EffectFallDistanceResetHook",
      "LevitationFallDistanceHook",
      "SlowFallingFallDistanceHook"
    ],
    "limits": [
      "EffectFallDistanceResetHook and old effect-specific hooks exist. Entire changed reset/edge-support chain requires fidelity/applicability review. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "travel",
      "lines": "1983-1991",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2562-2567",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "maybeBackOffFromEdge",
      "lines": "1040-1093",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "maybeBackOffFromEdge",
      "lines": "1043-1094",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
