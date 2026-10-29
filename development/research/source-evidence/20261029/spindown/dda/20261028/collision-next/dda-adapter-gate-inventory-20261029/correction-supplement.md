# Immutable evidence correction supplement

This supplement preserves `report.md` unchanged and narrows two evidence bindings for its existing statements. It adds no source findings and makes no new adapter-readiness claim.

## ID 146 cache-consumer evidence

The statement that accepted direct ray-writer-to-ordinary-movement-cache consumer evidence currently includes Anvil ID 145, Chest ID 54, and Trapped Chest ID 146 is supported by the writer-matrix correction and its accepted compatible rebind review below. The original matrix wording that only IDs 145 and 54 were established is superseded only for the ID 146 row; the original report and audit remain preserved.

- `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/trapped-chest-146-correction.md` — SHA-256 `2368CAF3DAF8B95EB35AFB3F3107C16C9DBFEC6D7F60DEFB6737724F4215290A`.
- `.native-source/20261028/collision-next/dda-writer-coverage-matrix-review/id146-rebind-review.md` — SHA-256 `A78F0CE756DF1B6DB2AAE1726B741013EB555798FCEDC22E51440B9C21656464` (ACCEPT, compatible rebind).
- `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/trapped-chest-146-correction-hashes.json` — SHA-256 `F96E5422E119ED9600C60FEA190410882CB8C21A1991DBD230FA7DF3A96662DC`.

This supports only the three named bounded cache-consumer findings. It does not establish cache validity across other writers/callers, a complete provider adapter, or whole-operation traversal parity.

## IDs 64–131 metadata coverage wording

The original range table `crosswalk.tsv` had wording for IDs 64–127 that contradicted its metadata projection rows and static check. The corrected derived table is the appropriate evidence for the report's assertion that these IDs each have all 16 metadata nibble rows. IDs 128–131 remain registration-level and are not assigned invented metadata coverage.

- `.native-source/20261028/collision-next/dda-first-hit-crosswalk-20261029/range-064-131/crosswalk-corrected.tsv` — SHA-256 `FD3DE09FE023C6F14026BF66ACCB41CA623F31818208CD0C07D8C5884212E2B7`.
- `.native-source/20261028/collision-next/dda-first-hit-crosswalk-20261029/range-064-131/metadata-coverage-correction.md` — SHA-256 `152CEFCDEC09751FB0FEF6ABEDA39CBCA067E590FCF756A01502EED1A9CCF4AF`.
- `.native-source/20261028/collision-next/dda-first-hit-crosswalk-20261029/range-064-131/metadata-coverage-correction-check.txt` — SHA-256 `F4F0D49D9AF929C1E795890C1FABEA65357E79F8E5FF9823DD7135C60893555C` (PASS: 68 rows for IDs 64–131, complete 0–15 metadata coverage for IDs 64–127, IDs 128–131 unchanged).

This correction concerns metadata row coverage only. The crosswalk's OPEN current-state mapping, provider, neighbor, block-entity, cache-history, and adapter gates remain unchanged.

## Bound inventory revision

The inventory report supplemented here is `report.md`, SHA-256 `A1DC2D9332AFD9EEE8DC96DCA49F3F20FA264E6FFEFD1A064F8B1343E5C1255B`. The report is not edited; this supplement and its sidecar bind the corrected evidence identities.
