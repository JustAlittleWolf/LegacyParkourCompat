# First-hit provider eligibility crosswalk: legacy IDs 132–197

Snapshot: 2026-10-29. Source-only inventory. This output does not inspect product code, propose an implementation, or claim traversal/parity readiness.

## Row and source validation

`crosswalk.tsv` contains exactly one row for each registered numeric identity 132 through 197, inclusive. The old ID/key/object constructor expressions were extracted from canonical 1.8.9 `net/minecraft/block/Block.java`; the raw expressions were whitespace-normalized only. That source hash is in `source-and-evidence-hashes.json`. Its original hash matches the accepted complete 0–197 registry inventory.

The accepted `dda-writer-coverage-matrix-audit/report.md` is the family-closure index. Its entire `artifact-hashes.json` reference set was revalidated against current bytes before this crosswalk was emitted. Every family label in the table is a pointer into that index. The accepted matrix supersedes rediscovery; this crosswalk adds row-level exact registry identity/constructor data for this numeric partition and preserves the matrix's stated open gates.

Canonical current source evidence was pinned by SHA-256 for 26.2 `BlockStateData.java`, `DataFixers.java`, and `Blocks.java`; see `source-and-evidence-hashes.json`. No target was inferred from a same-name match. A row whose cited accepted family does not bind an exact alias and state projection is marked OPEN. The current source hashes are search/evidence pins, not proof that every row has a closed target.

## How to read the rows

- `camera_canRayTrace` records the accepted camera flags and uses the default `Block.canRayTrace()` allowance only as the base behavior. A virtual eligibility/provider override remains family-specific and must be read from the cited exact family report. The camera path skips the collision-shape prefilter and stops at the first non-null ray result.
- `ray_provider_dispatch` gives the accepted default writer-then-clip path unless the cited family identifies a custom override or repeated dispatch. An OPEN entry means no family closure was cited for that ID.
- `mutable_updateShape_writer_family` points to the current accepted closure where one exists. It is not a statement that every row mutates shared bounds.
- `current_key_and_state_projection` and `current_collision_or_outline_provider` remain OPEN unless the accepted family closure supplies the exact mapping/provider for the row. Do not substitute a current same-name key or a generic outline for that missing evidence.
- `unresolved_adapter_inputs` retains state, neighbor, paired-cell, block-entity, chunk, cache-history, and invocation inputs that need source closure before an adapter can safely consume the row.

## Bounded conclusion

The row inventory is complete for old registry identities 132–197, and all references to the accepted matrix are hash-validated. It is not a complete current-state/provider map: IDs without a matching accepted family closure, plus the explicit residual gates in accepted family reports, remain OPEN. Even rows in an independently accepted family report are only family-scoped source findings; they do not establish global old DDA adapter eligibility or shared-bound cache validity. The aggregate accepted matrix still distinguishes provider/state mapping from cache-writer/caller lifecycle and first-hit ordering.

`validation.txt` records cardinality and reference-hash checks. `source-and-evidence-hashes.json` binds canonical source pins and every accepted matrix artifact hash used for this inventory.
