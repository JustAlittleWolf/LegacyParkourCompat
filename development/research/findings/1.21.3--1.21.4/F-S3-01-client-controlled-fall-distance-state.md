# F-S3-01: 1.21.4 leaves client-controlled player fall distance unchanged on landing

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, move 619-702; checkFallDamage 1216-1231.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, move 627-713; checkFallDamage 1237-1252.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, checkFallDamage 341-371.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, checkFallDamage 339-369.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, isLocalPlayer 347-349; isEffectiveAi 478-480.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/player/Player.java`, isControlledByClient 2146-2148; maybeBackOffFromEdge 1066-1123.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, isControlledByClient 2166-2168.

Difference: On 1.21.3's client-local movement path, a grounded collision invokes `LivingEntity.checkFallDamage()` and then `Entity.checkFallDamage()`, which resets the player's fall distance. On 1.21.4, `Player.isControlledByClient()` is true, so the new negative guard skips the whole callback for the local player. 1.21.4 therefore retains the previous fall-distance value after landing. This is a local movement-state change distinct from whether fall damage is calculated or applied.

Movement meaning: The ordinary player travel path calls `Entity.move()` after movement collision resolution. On 1.21.3, LocalPlayer's `isEffectiveAi()` makes it controlled by the local instance; on 1.21.4 the added controlled-by-client guard excludes the Player. The reset's state is read by `Player.maybeBackOffFromEdge()`. 1.21.3 reachable contrast is a player that lands after accumulating fall distance greater than its `maxUpStep`, then is airborne again while holding shift during a non-positive-Y movement: 1.21.3 starts the new descent with the landing reset, while 1.21.4 still has the prior larger value. That value can make `isAboveGround()` false before the support-geometry check, selecting a different edge-backoff branch. The source proves that 1.21.4 does not clear this client-side player movement state through the landing collision callback and that the state feeds a crouch-edge movement guard. Fall-damage calculation, attack/damage effects, and any health outcome are outside this finding.


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
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "619-702",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkFallDamage",
      "lines": "1216-1231",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "checkFallDamage",
      "lines": "341-371",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isControlledByClient",
      "lines": "2146-2148",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "627-713",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkFallDamage",
      "lines": "1237-1252",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "checkFallDamage",
      "lines": "339-369",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isControlledByClient",
      "lines": "2166-2168",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "isLocalPlayer",
      "lines": "347-349",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "isEffectiveAi",
      "lines": "478-480",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "maybeBackOffFromEdge",
      "lines": "1066-1123",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
