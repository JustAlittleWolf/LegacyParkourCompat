# Independent review: DDA adapter gate inventory

## Verdict

**REVISE, narrowly.** The gate separation and fail-closed conclusion are technically consistent with the accepted evidence, but the synthesis is not fully revision-bound and it cites one partition whose row-level metadata qualifier contradicts the report's own coverage statement.

## Scope and checked hashes

Reviewed the report at SHA-256 `A1DC2D9332AFD9EEE8DC96DCA49F3F20FA264E6FFEFD1A064F8B1343E5C1255B`, the cited writer matrix, default-provider rebind, product-aware reconciliation, all three crosswalk tables and their listed validation artifacts, and the old opaque roster. No product source was inspected. The writer matrix report, default-provider rebind, reconciliation, 0–63 crosswalk, 64–131 crosswalk/static-check, 132–197 crosswalk/validation, and opaque roster finding/CSV all matched the hashes stated in the inventory at review time.

## Findings

1. **The four gates are kept distinct.** The report correctly separates old registration/metadata/opaque classification from current-state/provider translation, lifecycle-wide cache writers/callers, and transactional staging/invalidation/recovery. It correctly treats candidate keys as unproven aliases, the opaque roster as a historical predicate only, and direct cache consumers as a lower bound. Its conclusion that no ordinary useful runtime domain is yet justified follows the accepted adapter reconciliation: unknown operations can leave shared bounds stale, while no complete in-process replay mechanism has been established.

2. **ID 146 correction is not bound in the report's evidence table.** The report's body correctly names Anvil 145, Chest 54, and Trapped Chest 146 as accepted direct cache consumers. The cited matrix report still states only 145 and 54; the accepted immutable supplement that supersedes that two-consumer statement is `dda-writer-coverage-matrix-audit/trapped-chest-146-correction.md` (SHA-256 `2368CAF3DAF8B95EB35AFB3F3107C16C9DBFEC6D7F60DEFB6737724F4215290A`), with independently accepted binding in `dda-writer-coverage-matrix-review/id146-rebind-review.md` (SHA-256 `A78F0CE756DF1B6DB2AAE1726B741013EB555798FCEDC22E51440B9C21656464`). Add both exact references (and the correction hash manifest if desired) to the inventory so its 3-consumer claim resolves to the current accepted revision rather than the superseded matrix wording.

3. **The 64–131 crosswalk contradicts the metadata claim.** Its README/static check say IDs 64–127 each have metadata 0–15, and static row/cardinality verification passes. However, rows in `crosswalk.tsv` literally say “full 0..15 nibble domain coverage not asserted here” while listing 16 decoded projections. The inventory says complete nibble coverage is recorded. Preserve the original crosswalk and add a narrowly bound author correction (or corrected derived table) that resolves this wording; then update the inventory's crosswalk/static-check hashes if they change. Do not broaden to IDs 128–131, which are registration-only.

4. **No adapter readiness overclaim found.** The report does not claim full current-state/provider translation, full lifecycle mutation coverage, or whole DDA parity. It preserves open exact state, neighbor, paired-cell, block-entity, and chunk inputs and correctly distinguishes accepted local provider facts from a usable whole-operation domain.

## Evidence identity

- Reviewed report: `.native-source/20261028/collision-next/dda-adapter-gate-inventory-20261029/report.md`, SHA-256 `A1DC2D9332AFD9EEE8DC96DCA49F3F20FA264E6FFEFD1A064F8B1343E5C1255B`.
- ID 146 correction: `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/trapped-chest-146-correction.md`, SHA-256 `2368CAF3DAF8B95EB35AFB3F3107C16C9DBFEC6D7F60DEFB6737724F4215290A`.
- ID 146 correction rebind review: `.native-source/20261028/collision-next/dda-writer-coverage-matrix-review/id146-rebind-review.md`, SHA-256 `A78F0CE756DF1B6DB2AAE1726B741013EB555798FCEDC22E51440B9C21656464`.
- Crosswalk 64–131: `.native-source/20261028/collision-next/dda-first-hit-crosswalk-20261029/range-064-131/crosswalk.tsv`, SHA-256 `3D943C1D021B241088621B02C91C84E19E915E8AEBB3AFC87C7D97EA3FF36BC9`.
- Crosswalk 64–131 static check: `.native-source/20261028/collision-next/dda-first-hit-crosswalk-20261029/range-064-131/static-check.txt`, SHA-256 `7572F9D8636E5FF18659615B615D1A85BA4A250C8A543A5A24E4769A4BABEB40`.

No source/product code was changed. No build, tests, or runtime were run.
