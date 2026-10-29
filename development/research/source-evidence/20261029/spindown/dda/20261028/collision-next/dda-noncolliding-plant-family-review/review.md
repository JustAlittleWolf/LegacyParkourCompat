# Independent review: corrected noncolliding plant-like family

**Verdict: ACCEPT** for the bounded source claims in the finding. This review does not accept or claim DDA visitation, first-hit, or overall ray-trace parity.

## Exact inputs

- Finding: `.native-source/20261028/collision-next/dda-noncolliding-plant-family-25-31-32/FINDING.md`
  - SHA-256: `7E3D4F9BC043CA535123E0AECFE6936D1225DA3D21F577C4324AC166477825C7`
- Source hash ledger: `.native-source/20261028/collision-next/dda-noncolliding-plant-family-25-31-32/source-hashes.json`
  - SHA-256: `303FC3154FF1D9B38AF98F5F3C063520970A58F417D840A94C5B527104FF6DCE`
- Canonical 1.8.9 Ornithe Feather manifest SHA-256: `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`
- Canonical 26.2 unobfuscated manifest SHA-256: `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`

I independently recomputed all 17 listed Java source hashes from the read-only canonical source roots and compared each with both the ledger and its canonical `.sources.sha256` entry. Result: 17 checked, 0 mismatches.

## Findings checked

- The correction is right: 1.8.9 `Block.java:878` registers ID 25 as `noteblock`; IDs 30, 31, and 32 are `web`, `tallgrass`, and `deadbush` at lines 883–885. ID 25 is properly excluded from the report's provider family.
- Old `CobwebBlock` and inherited `PlantBlock` movement collision providers return `null`. `TallPlantBlock` metadata 0/1/2 represents dead bush/tall grass/fern, and invalid values normalize to 0. `TallPlantBlock` and `DeadBushBlock` set x/z bounds to `0.5 ± 0.4` and y to 0..0.8 in their constructors.
- Old `Block.rayTrace` calls `updateShape` before reading the instance bounds; the cited blocks do not replace the base no-op update method. Cobweb retains the base unit bounds. These facts support the report's bounded provider statement and do not establish traversal behavior.
- The 26.2 `BlockStateData` entries map `web` to `cobweb`; tallgrass metadata states to `dead_bush`, `grass`, and `fern`; and `deadbush` to `dead_bush`. `DataFixers` renames `grass` to `short_grass`. The listed current registrations for cobweb, short grass, fern, and dead bush use `.noCollision()`, which clears the collision flag; base collision lookup then returns an empty shape.
- `WebBlock` inherits the base unit outline. `TallGrassBlock` and `DryVegetationBlock` use `Block.column(12, 0, 13)`, so current plant outlines differ from the historical 0.8-wide, 0.8-high ray bounds. The report does not conflate outline with movement collision.

The source paths and cited details support the report's aliases, state normalization, and local provider comparisons. Its stated limits are appropriate. No product code, tests, builds, or runtime were inspected or used.
