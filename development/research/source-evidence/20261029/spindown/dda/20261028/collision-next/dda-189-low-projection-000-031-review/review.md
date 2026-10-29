# Independent review: 1.8.9 metadata projection, IDs 0–31

**Verdict: ACCEPT**

## Scope and evidence

Reviewed only the assigned `dda-189-low-projection-000-031` report, projection TSV, rule TSV and source-hash list; the two declared projection inputs (registry crosswalk and high-partition schema input); and the canonical 1.8.9 Ornithe Feather sources and their ready/source manifests. No product sources, other projection lanes, builds, tests, or runtime checks were used.

The ready marker identifies version `1.8.9`, mapping `ornithe-feather`, and status `ready`. Its declared source-manifest SHA-256 is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, matching the actual manifest hash. All 20 canonical source hashes listed by the partition match both the ready source manifest entries and the actual files. All five input/output digests in the partition source-hash list also match their files. The review hash manifest records these source files and the checked artifacts.

## Checks and findings

- The TSV has all 11 declared columns; none of their values are blank. It has 512 rows, 512 unique `(legacy_id, metadata)` pairs, all 32 IDs from 0 through 31, and exactly metadata 0 through 15 once per ID.
- All 16 rule IDs are referenced. The `registered_ids` sets in the rules cover every row's legacy ID, without gaps or conflicting row references.
- Each row's candidate key exactly matches the assigned registry crosswalk value for that ID. All rows use `candidate_only_current_state_unresolved` and the state-projection text explicitly says unresolved. This includes the ID 26 palette candidate, which remains explicitly ambiguous. No current-state equivalence is asserted.
- The rules and row expressions match the canonical decoder operations: inherited instance default state for L0; enum `byId` decoders for L1–L3, L6, L11, L15; sapling type mask/stage bit for L4; direct liquid level for L5; log variant/axis masks for L7; leaves masks for L8; sponge low-bit wet flag for L9; dispenser facing mask and triggered bit for L10; bed horizontal direction/part/conditional occupied bit for L12; masked rail shape and powered bit for L13; piston facing/extended bits for L14. Enum helper rules correctly record fallback to ID 0 for out-of-range values; these are valid decoder outcomes, not failures.
- The sole decode failures are sticky piston ID 29 at metadata 6, 7, 14, and 15. `getFacing` masks with 7, returns null above direction ID 5, and `StateDefinition.StateImplementation#set` rejects null because it is not among the property's allowed values. The classification and failure text match these four cases; no other rows are classified as failures.
- The report's totals, decoder-failure summary, rule count, provenance description, and unresolved-conversion statement agree with the checked artifacts.

## Static check summary

The static checks found no missing/duplicate rows, metadata gaps, missing columns, blank required values, unmapped rule references, crosswalk candidate mismatches, candidate-resolution violations, or unexpected decoder failures. Source anchors in the rules were checked against the hash-verified canonical sources. This is a source/artifact review only; it does not establish product integration or runtime parity.
