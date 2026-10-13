# F-07: Nether portal entry callbacks use the portal plane shape in 1.21.5

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, checkInsideBlocks 1086-1098.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, entityInside 103-108.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, getEntityInsideCollisionShape 66-69.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/Block.java`, class declaration / field initialization 49.

Difference: The 1.21.4 movement-contact scan sees the default full-block entity-inside shape for a Nether portal block. 1.21.5 adds an override that returns the portal's axis-oriented shape, which is 4/16 of a block thick. Under the same portal state and player movement, 1.21.5 invokes the inside-portal callback only when the movement intersects that portal plane shape; 1.21.4's full block shape can invoke it elsewhere in the same cell.

Movement meaning: Player movement -> `Entity#applyEffectsFromBlocks` -> `checkInsideBlocks` -> NetherPortalBlock entity-inside shape -> `entityInside` -> `Entity#setAsInsidePortal`. The state reaches portal transition processing on later player ticks. 1.21.4's checked replacement path is the inherited BlockBehaviour default shape; 1.21.5's shape uses the existing AXIS blockstate and portal column geometry. The game mode/portal cooldown gate remains part of the player callback's `canUsePortal(false)` condition. The source proves a thinner contact volume for initiating inside-portal state. This changes where along movement through a portal cell the portal callback can be triggered, which may shift portal-processing timing. No teleport timing or trajectory was measured; cooldown, portal state and subsequent player ticks remain dependencies.


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
      "path": "1.21.4/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java",
      "member": "entityInside",
      "lines": "103-108",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4c2271222823f571a099e558a3aa2c6c3ddcd95911f09f436ac4c68caa218bfe; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
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
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java",
      "member": "getEntityInsideCollisionShape",
      "lines": "66-69",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1cb1b0a796f491deb2317d8a2706442741a627582ca86e3cc7adb6345f975d1b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/Block.java",
      "member": "class declaration / field initialization",
      "lines": "49",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d66c7efd4afb3e52f8d239a1b5fcd00a5c24874d75b61b5b16b794faf200f2c2; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkInsideBlocks",
      "lines": "1086-1098",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
