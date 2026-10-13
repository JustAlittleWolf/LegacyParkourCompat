# F-10: Player edge support probe geometry changes in 1.21.5

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1069-1117; isAboveGround 1120-1126.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, maybeBackOffFromEdge 1026-1074; isAboveGround 1077-1085.

Difference: When the player is not flying, has non-upward movement of type `SELF` or `PLAYER`, is staying on a ground surface and passes `isAboveGround`, `maybeBackOffFromEdge` repeatedly probes collision support while reducing horizontal movement in `0.05` increments. 1.21.5's `canFallAtLeast` tests a horizontally inset AABB and a shallower downward extent than 1.21.4. The probe therefore asks a different `Level.noCollision` question near horizontal boundaries and near the lower support margin. Its distance argument widens from `float` to `double`; existing callers still form the `isAboveGround` difference from float operands, while max-step height is also float.

Movement meaning: `Entity.move` invokes virtual `maybeBackOffFromEdge` before collision resolution. `Player` supplies this override, so the player route reaches the changed query for `SELF`/`PLAYER` moves satisfying the method guards. The source proves that 1.21.4 and 1.21.5 test different support volumes before deciding whether to retain or subtract horizontal movement. It can change the loop outcome for world/player positions where collision intersects the changed boundary regions.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_21_4/EdgeBackoffProbeBounds.java"
    ],
    "hooks": [
      "SneakEdgeProbeHook"
    ],
    "limits": [
      "Probe bounds Change exists; target segment extension/noCollision operation and broader collision providers remain separate. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "maybeBackOffFromEdge",
      "lines": "1069-1117",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isAboveGround",
      "lines": "1120-1126",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "maybeBackOffFromEdge",
      "lines": "1026-1074",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isAboveGround",
      "lines": "1077-1085",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
