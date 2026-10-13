# Slow input sprint-start restriction

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, isVisuallyCrawling 2338-2340; updateInWaterStateAndDoFluidPushing 1295-1301.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, updateInWaterStateAndDoFluidPushing 1316-1322.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, hasEffect 926-928; startUsingItem 3127-3138; aiStep 2680-2808.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, hasEffect 926-928; startUsingItem 3138-3149; aiStep 2691-2819.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 638-739; isCrouching 593-598; aiStep 638-800.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 643-753.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/player/Player.java`, tryToStartFallFlying 1525-1533.

Difference: 1.21.4 rejects slow-moving local-player sprint start unless underwater; 1.21.3 lacks that additional start gate.

Movement meaning: Existing crouch/crawl input state can select a different sprint flag for the following travel. This start predicate is independently resolved from stopping an existing sprint.


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
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "638-739",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "643-753",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "isCrouching",
      "lines": "593-598",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": "638-800",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "isVisuallyCrawling",
      "lines": "2338-2340",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tryToStartFallFlying",
      "lines": "1525-1533",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "hasEffect",
      "lines": "926-928",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "hasEffect",
      "lines": "926-928",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "startUsingItem",
      "lines": "3127-3138",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "startUsingItem",
      "lines": "3138-3149",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "updateInWaterStateAndDoFluidPushing",
      "lines": "1295-1301",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "updateInWaterStateAndDoFluidPushing",
      "lines": "1316-1322",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2680-2808",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2691-2819",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
