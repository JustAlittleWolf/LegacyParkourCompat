# F-08: Powder snow contact shape can follow the player's collision shape in 1.21.5

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, checkInsideBlocks 1092-1098.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java`, getEntityInsideCollisionShape 107-111.

Difference: For a player whose entity collision context gives powder snow a nonempty collision shape, 1.21.5 uses that shape for movement contact callbacks; in particular, `fallDistance > 2.5` selects the `0.9F`-high falling collision shape. 1.21.4 uses the full block shape for the inside callback scan regardless of that entity collision shape. When 1.21.5's entity collision shape is empty, 1.21.5 falls back to the same full block shape used by 1.21.4.

Movement meaning: Player movement -> `Entity#checkInsideBlocks` -> PowderSnowBlock entity-inside shape -> callback intersection -> `PowderSnowBlock#entityInside` -> `makeStuckInBlock`. The player's fallDistance and entity collision context select 1.21.5's shape. This callback's stuck movement multiplier is a direct movement-state writer. 1.21.4's inherited shape and 1.21.5's override/collision-shape helper paths are cited above; registry defaults are not needed for this condition. The source proves that 1.21.5 can test a smaller inside-contact shape where 1.21.4 tests the full block. Contact callbacks may therefore differ for movement that intersects only the portion removed by 1.21.5's context-specific shape. The callback affects the stuck speed multiplier, but no concrete trajectory was measured; exact intersection depends on the player's bounding box and movement segment.


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
      "path": "1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java",
      "member": "getEntityInsideCollisionShape",
      "lines": "353-355",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
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
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java",
      "member": "getEntityInsideCollisionShape",
      "lines": "107-111",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 de72fb6e70616d7727bf324b0da4a98250cabd97ad39ecbe1062732d47f39770; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkInsideBlocks",
      "lines": "1092-1098",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
