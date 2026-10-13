# Sprint-jump impulse precision

1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/world/entity/Entity.java`, addDeltaMovement 3174-3176.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, addDeltaMovement 3189-3191.
1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, jumpFromGround 2008-2017; getJumpPower 2000-2002.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, jumpFromGround 2069-2082.
1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/world/phys/Vec3.java`, add 88-90.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/phys/Vec3.java`, add 88-90.
1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/util/Mth.java`, sin 47-53.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/util/Mth.java`, sin 48-54.

Difference: 1.20.4 multiplies float yaw trig values by 0.2F before widening; 1.20.6 multiplies widened trig values by double 0.2 before Vec3 addition.

Movement meaning: Existing nonzero-power sprinting Player ground jumps receive differently rounded X/Z impulse. The independent low-power skip requires newer input and is excluded centrally.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_20_2/SprintJumpImpulse.java"
    ],
    "hooks": [
      "SprintJumpImpulseHook"
    ],
    "limits": [
      "SprintJumpImpulseHook computes float trig products with 0.2F. No old low-power cutoff Change is identified; applicability through 1.20.4 requires source boundary reconciliation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.20.4",
      "mapping": "mojmap",
      "path": "1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "jumpFromGround",
      "lines": "2008-2017",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f *1.20.4/client_mappings.txt. Canonical source-manifest SHA-256 fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "jumpFromGround",
      "lines": "2069-2082",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.4",
      "mapping": "mojmap",
      "path": "1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "getJumpPower",
      "lines": "2000-2002",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f *1.20.4/client_mappings.txt. Canonical source-manifest SHA-256 fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.4",
      "mapping": "mojmap",
      "path": "1.20.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "addDeltaMovement",
      "lines": "3174-3176",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f *1.20.4/client_mappings.txt. Canonical source-manifest SHA-256 fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "addDeltaMovement",
      "lines": "3189-3191",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.4",
      "mapping": "mojmap",
      "path": "1.20.4/mojmap/net/minecraft/world/phys/Vec3.java",
      "member": "add",
      "lines": "88-90",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f *1.20.4/client_mappings.txt. Canonical source-manifest SHA-256 fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/world/phys/Vec3.java",
      "member": "add",
      "lines": "88-90",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.4",
      "mapping": "mojmap",
      "path": "1.20.4/mojmap/net/minecraft/util/Mth.java",
      "member": "sin",
      "lines": "47-53",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f *1.20.4/client_mappings.txt. Canonical source-manifest SHA-256 fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.6",
      "mapping": "mojmap",
      "path": "1.20.6/mojmap/net/minecraft/util/Mth.java",
      "member": "sin",
      "lines": "48-54",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745 *1.20.6/client_mappings.txt. Canonical source-manifest SHA-256 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, finding snapshot only",
      "assertion_preserved": true,
      "detail": "Historical acceptance belongs only to the immutable snapshot and its limits; canonical reformatting is not renewed source acceptance.",
      "snapshot": {
        "commit": "b168d90c05e813d6dff2ccd97090dc1602d9d418",
        "path": "workflows/source-campaign-2026-10-07/1.20.4--1.20.6/findings/MC1204-1206-02.md",
        "gitBlob": "12bc1eafdcea3a07e89d4ecd6cf2b4d4207640b9",
        "sha256": "9e073eb434e750f6babac4cd6881b2cf38b1e4aad37523db0f62657b82071315",
        "resolved_commit": "b168d90c05e813d6dff2ccd97090dc1602d9d418",
        "git_blob_sha256": "9e073eb434e750f6babac4cd6881b2cf38b1e4aad37523db0f62657b82071315"
      },
      "review": {
        "commit": "581b5bd2cb44981c4889eaf05ea739134e332b87",
        "path": "workflows/source-campaign-2026-10-07/independent-review-1204-1206-and-f14-2026-10-08.md",
        "gitBlob": "5da64f2289d96700cef04912fecfc420c58b2baf",
        "sha256": "ba037e76ae4fff2d8ab78d537fbf632e4e5c395e86d532da26fd36bd1f1be7e4",
        "decision": "ACCEPT, finding snapshot only",
        "limits": [
          "source pair remains PARTIAL",
          "no first changed release within endpoint interval is inferred",
          "no implementation or runtime conclusion"
        ],
        "git_blob_available": true,
        "git_blob_sha256": "ba037e76ae4fff2d8ab78d537fbf632e4e5c395e86d532da26fd36bd1f1be7e4"
      }
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
