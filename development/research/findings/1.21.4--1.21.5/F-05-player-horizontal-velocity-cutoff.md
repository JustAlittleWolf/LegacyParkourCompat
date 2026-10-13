# F-05: Player horizontal velocity is cut off by vector length in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2708-2724.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2686-2709.

Difference: For a player with horizontal velocity `(0.0025, 0.0025)`, 1.21.4 zeros both components because each absolute value is below `0.003`. 1.21.5 preserves both because their squared horizontal length, `1.25E-5`, is greater than the `9.0E-6` cutoff. The reverse shape of difference is not possible: if a player's squared horizontal length is below `9.0E-6`, each component is necessarily below `0.003`, so both versions zero both. Thus 1.21.5 retains some diagonal low-speed vectors that 1.21.4 removes. Vertical cutoff and non-player cutoff are unchanged.

Movement meaning: `LivingEntity.aiStep` executes this velocity cleanup before player input, jumping and travel. LocalPlayer reaches the inherited method via `super.aiStep()`. The changed components are written through `setDeltaMovement` and then consumed by travel. The branch is selected by the vanilla `EntityType.PLAYER` identity; server/client movement authority controls later travel but not this cutoff. The source proves the differing threshold geometry and a concrete velocity precondition. 1.21.5 can carry a retained diagonal low-speed horizontal velocity into subsequent travel where 1.21.4 starts that stage at zero horizontal velocity.


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
      "path": "1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2708-2724",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2686-2709",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
