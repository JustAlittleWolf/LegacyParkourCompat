# 1.8.9 first-hit adapter crosswalk: old IDs 0–63

This source-only artifact contains one row for each old numeric registry ID 0 through 63. It reuses the reviewed low metadata projection partitions and the current 0–127 source crosswalk, and links already accepted provider-family reports where a family claim exists. It does not inspect product code or assert a complete adapter, operation parity, or runtime behavior.

## Reading the table

- `registration_anchor_and_expression` records the exact 1.8.9 registry expression, including singleton construction and constructor arguments. Multiline expressions are represented by the anchor; consult the hash-bound `Block.java`.
- `metadata_domain`, `projection_rules`, and `projection_row_status` summarize the reviewed 16-row metadata partition for that ID. Exact per-metadata decoder output is in the linked projection TSV and rules file. Invalid old decodes remain explicit in the per-row status counts.
- Ordinary camera ray eligibility is resolved from source: `Block.canRayTrace()` defaults true; Air, Fire, and Liquid have the explicit exclusions; the stairs entry delegates to the wrapped base state. Ray provider dispatch and shared-bound writer anchors come from the registry crosswalk and the accepted provider inventory.
- `current_key_candidate_not_mapping` intentionally retains the unproved candidate alias from the source crosswalk. `current_exact_BlockStateData_mapping` is OPEN for every row: this crosswalk does not promote an ID/name guess to an exact modern state. Match each decoded legacy `Name` plus `Properties` against 26.2 `BlockStateData` before adapter use.
- Accepted family report/review paths are bounded evidence references. Their presence does not close the whole adapter, neighborhood, lifecycle, or cache-validity questions for an ID.

## Static validation

`crosswalk.tsv` has 64 unique numeric IDs, exactly 0–63. All rows retain the sixteen-metadata source projection domain and cite the corresponding reviewed projection partition. `artifact-hashes.json` records SHA-256 for every inspected canonical source and every referenced source artifact/review. No tests, builds, or runtime were run.

## Remaining gate

The table is not a closed first-hit adapter eligibility matrix. Exact old-state-to-current-state conversion, lossless state/context reconstruction, block-entity and paired/neighbor inputs, per-instance mutable-bound lifecycle, current provider composition, and source-order first-hit preservation remain open where not specifically closed by linked family evidence.
