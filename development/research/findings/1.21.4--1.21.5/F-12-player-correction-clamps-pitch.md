# F-12: Client position correction clamps the resulting pitch in 1.21.5

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, getInterpolation 2176-2178.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleMovePlayer 707-715.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/PositionMoveRotation.java`, calculateAbsolute 35-58; calculateAbsolute 35-63.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleMovePlayer 720-728.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/PositionMoveRotation.java`, calculateAbsolute 34-62; of 24-28.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/util/Mth.java`, clamp 93-95.

Difference: For a local-player correction whose resolved pitch falls outside `[-90°, 90°]`, 1.21.4 writes the computed pitch unchanged while 1.21.5 clamps it to the nearest endpoint. Position and delta-movement calculation remain on the direct-set branch because this handler passes `false`. Pitch is an input to fall-flying travel, so the stored state can select different glide math on a later travel tick. This does not establish how often ordinary servers emit out-of-range correction pitches.

Movement meaning: `ClientPacketListener#handleMovePlayer` handles a clientbound position correction for a non-passenger local player. It resolves relative fields and writes the resulting pitch through the correction helper. Fall-flying travel reads `getXRot()` in `LivingEntity#updateFallFlyingMovement`; surrounding branch conditions are covered separately by S3-GLIDE/F-06. The paired source proves a different stored pitch whenever the computed correction pitch exceeds the new bounds. Downstream pitch-dependent movement can consume that difference. The source audit did not measure a resulting trajectory or verify server-side pitch limits.


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
      "path": "1.21.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleMovePlayer",
      "lines": "707-715",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/PositionMoveRotation.java",
      "member": "calculateAbsolute",
      "lines": "35-58",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/PositionMoveRotation.java",
      "member": "calculateAbsolute",
      "lines": "35-63",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java",
      "member": "handleMovePlayer",
      "lines": "720-728",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/PositionMoveRotation.java",
      "member": "calculateAbsolute",
      "lines": "34-62",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/util/Mth.java",
      "member": "clamp",
      "lines": "93-95",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "getInterpolation",
      "lines": "2176-2178",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/PositionMoveRotation.java",
      "member": "of",
      "lines": "24-28",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 75baa0b6f99d1b9d18f157cbbf2c6d209b13af6719e960c7da1c451587433cc3; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
