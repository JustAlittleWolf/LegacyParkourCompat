# Partial inside-shape swept geometry

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, applyEffectsFromBlocks 740-784; setAsInsidePortal 2214-2225.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, applyEffectsFromBlocks 751-797.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2775-2777.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java`, boxTraverseBlocks 172-190.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/EndPortalBlock.java`, class declaration / field initialization 25-63.

Difference: 1.21.3 uses a recentered final player box and reverse displacement; 1.21.4 uses the segment-start box and forward displacement for partial entity-inside shapes.

Movement meaning: An existing partial shape such as End Portal may be intersected differently and write portal processing state. Block-cell enumeration is a separate operation.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_21/InsideBlockContact.java"
    ],
    "hooks": [
      "InsideBlockContactHook"
    ],
    "limits": [
      "Inset hook exists but no Change implementing the compared old cell traversal and swept partial-shape geometry was identified. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "applyEffectsFromBlocks",
      "lines": "740-784",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "applyEffectsFromBlocks",
      "lines": "751-797",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java",
      "member": "boxTraverseBlocks",
      "lines": "172-190",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java",
      "member": "getEntityInsideCollisionShape",
      "lines": "353-355",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java",
      "member": "getEntityInsideCollisionShape",
      "lines": "353-355",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java",
      "member": "block",
      "lines": "42-44",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java",
      "member": "block",
      "lines": "42-44",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2775-2777",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/level/block/EndPortalBlock.java",
      "member": "class declaration / field initialization",
      "lines": "25-63",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 725bbf4f36daa1f57f343d3f3123a6c527f13d7bf3c51fba1b27515b65c8c863; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setAsInsidePortal",
      "lines": "2214-2225",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
