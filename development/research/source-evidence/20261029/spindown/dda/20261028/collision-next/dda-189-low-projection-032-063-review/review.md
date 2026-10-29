# Independent source review: 1.8.9 low metadata projection, IDs 32–63

**Verdict: ACCEPT**

## Scope and result

Reviewed only the assigned partition (`metadata-projection.tsv`, `rules.tsv`, `provenance.tsv`, and its README), its two declared input tables (the assigned 0–127 registry crosswalk and the high-projection schema example), and the canonical local 1.8.9 `ornithe-feather` decompilation named by the partition. No product code, history, or other lane was inspected. No build, test, or runtime was run.

The projection has exactly 512 unique `(legacy_id, metadata)` rows: all 32 registered IDs 32–63 crossed with all 16 stored metadata values 0–15. All required projection fields are populated. Every row references a defined rule, the candidate key matches the assigned crosswalk, and every candidate current-state projection remains explicitly unresolved with status `candidate_only_current_state_unresolved`.

## Decoder and failure audit

The rules and projected values match the canonical source methods and supporting property/enum definitions:

- Default-state aliases for IDs 32, 39–42, 45, 47–49, 52, and 56–58 ignore metadata.
- Piston base, piston head, and moving-block facings accept low bits 0–5 in `getFacing`; low bits 6–7 return null. The following `BlockState.set(FACING, ...)` rejects null as outside the property values. The table marks exactly metadata 6, 7, 14, and 15 as failures for IDs 33, 34, and 36 (12 failures total).
- Dye, yellow/red flower, slab variant/half, TNT, torch, fire, stairs, chest/furnace direction normalization, redstone power, farmland moisture, and standing-sign rotation projections follow their source masks, lookup order, aliases, and defaults. In particular, chest/furnace `Direction.byId` applies `abs(id % 6)` before vertical directions are normalized to NORTH; stairs use `Direction.byId(5 - (metadata & 3))` and bit 2 for HALF.
- Wheat declares AGE in 0–7 and passes metadata directly to `set(AGE, metadata)`. Metadata 8–15 therefore throws for ID 59; all eight rows are marked `old_decode_failure`. Fire AGE and redstone POWER both accept 0–15.

Rule references, decoder anchors, property domains, and failure descriptions are internally consistent with the checked source. No correction is required.

## Static check

A read-only static check confirmed 512 rows, 512 unique ID/metadata keys, 32 IDs, 16 metadata values, the complete Cartesian product, all required non-empty fields, defined rule references, candidate-key agreement with the declared crosswalk, and unresolved current-state fields on every row. Classification totals are 12 piston-facing failures plus 8 wheat-age failures; all other 492 rows are valid old states.

All 22 canonical-source hashes and both declared input-table hashes in `provenance.tsv` match the bytes at their listed paths. SHA-256 values for the reviewed inputs and canonical sources are recorded in `hash-manifest.sha256`.
