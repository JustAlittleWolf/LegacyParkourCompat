# F-02: Bubble-column upper contact accepts empty shapes without fluid

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, onAboveBubbleCol 2483-2493.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/level/block/BubbleColumnBlock.java`, entityInside 49-76.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/BubbleColumnBlock.java`, entityInside 50-59.

Difference: Given an entity inside a bubble-column block, 1.21.4 applies the above-column velocity rule only when the block above is air. 1.21.5 applies it whenever the above block has an empty collision shape and contains no fluid. This includes non-air, collision-empty, fluid-empty blocks. The vertical velocity formula is unchanged; the contact condition expands.

Movement meaning: Entity block-contact traversal -> `BubbleColumnBlock#entityInside` -> above-position block state -> collision shape and fluid state in 1.21.5 -> `Entity#onAboveBubbleColumn` -> vertical delta movement. BubbleColumnBlock is the callback provider; the condition is evaluated directly from the adjacent block state. The same entity movement state writer is reached for player instances. The source proves an expanded precondition for the same direct vertical velocity update. 1.21.4 player overlapping a bubble column under a collision-empty, fluid-empty non-air block can receive the above-column update in 1.21.5 where 1.21.4 does not.


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
      "path": "1.21.4/mojmap/net/minecraft/world/level/block/BubbleColumnBlock.java",
      "member": "entityInside",
      "lines": "49-76",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9427b3e617753f5b3adc7a724fd80c87d4aa0e2f5d0eb38cd2ca34092e449b47; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.5",
      "mapping": "mojmap",
      "path": "1.21.5/mojmap/net/minecraft/world/level/block/BubbleColumnBlock.java",
      "member": "entityInside",
      "lines": "50-59",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c5e854a05c314e63275e24f30eac79dd0f0303071953284efcbc8773af9aa3c7; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5 *1.21.5/client_mappings.txt. Canonical source-manifest SHA-256 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.4",
      "mapping": "mojmap",
      "path": "1.21.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "onAboveBubbleCol",
      "lines": "2483-2493",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23 *1.21.4/client_mappings.txt. Canonical source-manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
