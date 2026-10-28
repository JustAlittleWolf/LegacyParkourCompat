# Independent full pane selector alias audit r2

## Scope and inputs

Independent read-only audit of the corrected 1.12-era pane FaceShape selector and the 1.11.2 pane default-state `isCube` selector. Followed `development/workflows/discovery/WORKFLOW.md` and `development/workflows/discovery/source/WORKFLOW.md`. No product implementation, product history, backlog, wiki inventory, tests, build, game/runtime, or source generation was inspected or used.

The audit is bound to these final source-artifact bytes:

| Artifact | SHA-256 |
|---|---|
| `pane-262-faceshape-bridge/inventory-supplement.md` | `C14E1E7D7109BEE7F8AB2CCC72AA44E3673485B765FB458AC802478CB08D2E97` |
| `pane-faceshape-overrides/legacy-block-id-crosswalk.md` | `9C2CDC0CFA4A1A6CE37E1B4AC22AB19183E3006968ADC3EA4C7F896D2FF6E486` |
| `pane-faceshape-overrides/legacy-1112-block-id-crosswalk.md` | `1BAC7F23D7DB8561099CB8DBC0350BEE73B39AC89FE738358EE8A348DD59B982` |

Canonical decompiled inputs checked read-only:

- 1.12 Ornithe Feather: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.12/ornithe-feather/`
- 1.11.2 Ornithe Feather: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.11.2/ornithe-feather/` (provenance identifies `feather-gen2-1.11.2+build.2`)
- 26.2 unobfuscated: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/`

Checked key source fingerprints include 1.12 `block/Block.java` SHA-256 `E4A90ECA411E7B0E14F5018F7385EC29891F6CDF1A1E917FA648B5712F9B33A1`, 1.12 `block/PaneBlock.java` `83862F6633B8241C3788E9C9A7E4FD0275C010EC790019B8003908996F4814B3`, and current 26.2 `references/BlockIds.java` `0F342F57727CFE99966DA9706DA7DDB330654CB59684529166B6F175171BA69F`, `references/BlockItemIds.java` `D9D57F2F04801DA5D8D583C23B87B08F45D8B6C681980D1A77308753C801BAC6`, `world/level/block/ColorCollection.java` `717FB27A7B1D0F68B3C2F371408385AD84CCDCC1D76248338897D8CF59DB2BE2`, and `world/level/block/Blocks.java` `F3F2FAEED23E9697407069A1D523107491590B8710175523EA05294D5BD00435`.

## Independent checks and verdict

- **1.12 registrations and aliases:** The corrected crosswalk has 254 unique numeric IDs (179 exact-key, 3 renamed, 72 split), and each row has at least one current candidate. Compared its ID set with 1.12 `Block.java` registrations; all rows are represented, including ID 0 registered through `AIR_KEY`. Extracted 454 unique candidate IDs from all rows and checked them against current 26.2 `BlockIds`/`BlockItemIds` key declarations, including color IDs expanded by the source `ColorCollection`; no candidate is absent.
- **1.12 base-SOLID set:** Recounted 185 unique materialized IDs and checked every member against the current 26.2 key set. The corrected snow entries are consistent with exact registrations: old ID 78 `snow_layer` maps to current `minecraft:snow` and its horizontal provider is `UNDEFINED`; old ID 80 `snow` maps to `minecraft:snow_block` and inherits base `SOLID`. This is the corrected selector set; the prior supplement digest and its AD9B crosswalk binding are historical only.
- **1.11.2 registry and `isCube` roster:** The table has 236 unique numeric IDs (0–255 with gaps), each with current aliases. Comparing against 1.11.2 `Block.java` registrations found every table ID, including ID 0 via `AIR_KEY`. The roster counts are 143 direct-false providers, 8 conditional `SlabBlock` providers (`this.isDouble()` on the registered default state), and 85 base-inherited true providers. All 61 listed direct/conditional override source-file hashes match the canonical 1.11.2 files. The 432 alias occurrences resolve to 406 unique current IDs; none is absent from the current 26.2 block-key sources.
- **Current/native boundary:** Independently enumerated 1,076 current 26.2 block keys from the current key declarations and colored collections. The old crosswalk candidate sets cover 454 current IDs; later IDs outside those explicit aliases remain native. This does not use broad modern tags to classify historical leaves, wood variants, potted plants, or other expanded families.
- **Horizontal FaceShape providers:** Enumerated 60 declarations in the 1.12 block package: `Block` plus 59 override declarations. Every override class is named in the corrected supplement. The checked pane expression at `PaneBlock.java:139` is `(!excluded && SOLID) || MIDDLE_POLE_THIN`; horizontal query directions are the four horizontal faces. Flower pots and torches return `UNDEFINED`; trapdoors return `SOLID` only for vertical faces satisfying the top/bottom closed gate, so every horizontal result is `UNDEFINED`; cauldrons return horizontal `SOLID` but the pane exclusion suppresses it; liquids return `UNDEFINED`. Pane `MIDDLE_POLE_THIN` remains connecting even if an excluded class were to return it. Slabs depend on double-vs-single; stairs resolve virtual properties and depend on facing, half, shape, and neighboring same-half stair states. These align with the supplement's selector gates.
- **Renamed/split state selectors:** Water and flowing water share current `water` (and lava variants share `lava`); the face-shape result is invariant to fluid level. Snow layer aliases must read the current `snow` layer state if reproducing old height-dependent behavior outside this horizontal FaceShape operation. Flower-pot content does not change the old horizontal result. Standing/wall redstone torch IDs encode orientation; old lit/unlit identity does not change this result. Slab and stair state/neighbor inputs are explicitly bounded in the supplement. No unresolved alias or state input was found for the two audited selector operations.

## Limits

The supplement records the known aggregate ready/source-manifest limitation for the 1.12/1.12.1 source bundle. This audit verified the relevant canonical source files directly by exact path, intact method bodies, and source-file hashes; it does not claim to repair that broader publication-manifest limitation. The 1.11.2 crosswalk itself limits its state-migration coverage to registered default-state `isCube` provider selection. This audit does not assert arbitrary-state 1.11.2 geometry equivalence.

No tests, build, game/runtime, or client/server execution were performed.
