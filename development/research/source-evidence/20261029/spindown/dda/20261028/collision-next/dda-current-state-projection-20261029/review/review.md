# Independent review: 1.8.9 numeric block state to 26.2 DataFixer projection

## Verdict

**ACCEPT, bounded to the source-backed serialized block-state projection for the eight listed IDs and 16 metadata nibbles each, plus the complete 26.2 legacy-table candidate inventory.** This does not establish collision-shape equivalence, ray behavior, provider-input translation, or adapter readiness.

## Reviewed revision and hash closure

- Finding: `FINDING.md`, SHA-256 `756BBDF0848524F657D0484A526F1F65038842D78C0A13802C3DB2EFBBBE1C14`.
- Source/evidence/generated-file manifest: `source-hashes.json`, SHA-256 `7D60A12E39C718ABD390116F723A7935B1C7A230E6E1D6A0493BE8524558F77E`.
- Current-name candidate table: `block-state-data-current-name-projection.jsonl`, SHA-256 `C7A8B04869425690B2D6BD200C9630752FC1A58C34526B6A5F51898F64B0DC02`.
- Qualified subset: `qualified-default-state-subset.jsonl`, SHA-256 `A918E0BBD5FC6BC128696E5012AB83A674CFED4A2D54FB2D5BBB2552D0698906`.
- Qualifier source checker: `qualify-default-state-subset.cjs`, SHA-256 `EC6551B4D7CC04308FD1E89B5FD96E41DF75C66E6CBD17787EF3BB8E9635D29E`.

I recomputed all 32 canonical source hashes, 18 accepted-input hashes, and 13 generated-artifact hashes. Every path existed and every digest matched. The listed inputs include both accepted old metadata-projection partitions and reviews, the corrected 0–63/64–131/132–197 crosswalk evidence, and current block-key/state-definition sources.

## Source and transformation checks

- `ChunkPalettedStorageFix.Section.upgrade` forms the old numeric palette index and calls `BlockStateData.getTag` by index. The report correctly distinguishes this from `BlockNameFlatteningFix`, which handles `BLOCK_NAME` integer/string choices. It does not claim that a table index alone proves an old runtime state.
- The table extractor matches `BlockStateData.register` and `finalizeMaps`: 1,695 unique table indices, 254 block defaults, and 3,168 rows for IDs 0–197 × metadata 0–15. Direct entries and default-filled entries are separated. The `FILTER_ME` skull sentinel is treated through `ChunkPalettedStorageFix.Section.setBlock`'s AIR substitution, not as a registered live state.
- The ordered name path includes all 21 `BlockRenameFix.create` registrations (105 extracted pairs, no unresolved expressions). The source implementation applies these renames to block-state names; the report correctly preserves distinct block-name and palette routes and marks neighbor/property-dependent transforms rather than flattening them into per-row certainty.
- The 128-row subset contains IDs 4, 7, 22, 41, 42, 45, 48, and 57 exactly once for every metadata value 0–15. The source joins each old key and L0/M0 default-only decoder row to an empty-property legacy alias. It checks the current `BlockItemIds` key, the matching two-argument `Blocks.java` registration, both registration overloads, and the current generic `Block` state-definition/default construction. Each selected current default tuple is empty.
- The report explicitly limits the accepted projection to serialized block-state tuples. It leaves remaining IDs and metadata candidate-only where source equivalence or contextual fixes are open; it makes no collision or adapter claim.

The earlier draft issues encountered during review were corrected before this verdict: current registry extraction now follows actual registration arguments and color-group registration; required registry helper sources are hash-bound; the two old metadata partitions are cited separately; and the subset checker verifies the old-key/rule join and current default-state construction with source line anchors.

No product files were inspected or changed. No build, tests, or runtime were run.
