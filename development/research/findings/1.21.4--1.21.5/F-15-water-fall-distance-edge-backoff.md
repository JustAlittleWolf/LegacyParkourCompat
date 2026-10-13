# F-15: Water fall-distance accumulation changes sneak edge back-off eligibility

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, checkFallDamage 1237-1252.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, checkFallDamage 1274-1292.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, isStayingOnGroundSurface 348-349; maybeBackOffFromEdge 1069-1117; isAboveGround 1120-1122.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, isStayingOnGroundSurface 363-364; maybeBackOffFromEdge 1026-1075; isAboveGround 1077-1079.

Difference: For a player whose downward movement occurs in water, 1.21.4 increases `fallDistance` and 1.21.5 does not. On a later non-grounded move, with abilities-based flight off, sneak held, mover type SELF/PLAYER, requested Y nonpositive, and support within the step-height query, choose 1.21.4 state `fallDistance >= maxUpStep` and 1.21.5 state `fallDistance < maxUpStep`. 1.21.4's `isAboveGround` is then false; 1.21.5 evaluates the downward no-collision query and can return true when support lies within the queried depth. That changes whether 1.21.5 enters the horizontal edge-back-off loops before collision resolution. The 1.21.5 support query includes the small inset already covered by F-10; the branch difference remains reachable with a support footprint that has margin beyond that inset.

Movement meaning: `Entity.move` runs the edge-back-off query before applying this move's collision result and before `checkFallDamage` updates the distance for that move. The changed water accumulation therefore feeds a later movement call. The player override of `isStayingOnGroundSurface` makes the gate reachable with sneak input. This finding uses fall distance only as an input to a player movement predicate; it does not cover fall-damage calculation, landing damage, attack criticals, or other combat outcomes. The source proves a changed player edge-back-off eligibility condition after downward movement in water. When the eligibility branch enters and a proposed horizontal offset lacks support within the allowed query, 1.21.5 reduces that horizontal movement in 0.05 increments while 1.21.4 can skip those checks.


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
      "path": "1.21.4/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isStayingOnGroundSurface",
      "lines": "348-349",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
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
      "lines": "1120-1122",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkFallDamage",
      "lines": "1274-1292",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isStayingOnGroundSurface",
      "lines": "363-364",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "maybeBackOffFromEdge",
      "lines": "1026-1075",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "isAboveGround",
      "lines": "1077-1079",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
