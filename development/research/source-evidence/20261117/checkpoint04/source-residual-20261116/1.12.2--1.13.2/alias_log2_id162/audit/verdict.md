# Independent source audit: `log2` alias/state mapping

**Verdict: ACCEPT**

**Audited report SHA-256:** `5b4acbc4597482d3c5fd9247a903bff6932fa0422e23cea72d807ec530398393` (`coverage.md`, exact bytes).

**Scope:** exact local 1.12.2 → 1.13.2 source identity, cited file hashes, legal `log2` metadata values in 0–15, and the described DataFixer route. This verdict accepts only this bounded source alias/state mapping. It does not establish a movement finding, geometry, full pair coverage, an intermediate cutover, or universal importer behavior.

## Evidence checked

- Both ready markers identify the exact versions and `ornithe-feather` mapping; each records Feather Gen2 build.2 artifacts. Their manifest SHA-256 values match the report and the actual `ornithe-feather.sources.sha256` files: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Every Java file SHA-256 cited in the report matches both its endpoint manifest entry and the corresponding local source file bytes.
- 1.12.2 source confirms ID 162 is `log2`; `Log2Block` derives the species ID from `(metadata & 3) + 4` and axis from `metadata & 12`. The scoped legal species IDs are 4 (acacia) and 5 (dark oak), so the legal values in 0–15 are exactly `0, 1, 4, 5, 8, 9, 12, 13`, with the report’s Y/X/Z/NONE mapping.
- 1.13.2 schema 1450 maps the eight matching legacy block-state records to acacia/dark-oak `_log` targets for Y/X/Z and bark targets for NONE. Schema 1451.1’s chunk palette path computes `id << 4 | metadata` and uses the same indexed target table; the eight values resolve to indices 2592, 2593, 2596, 2597, 2600, 2601, 2604, and 2605. The later schema-1510 block rename maps both bark names to their corresponding `_wood` names.

## Note

The report contains a literal `` `r`n `` inside the cited-hashes Markdown table between the `C_15874933.java` and `C_36170522.java` rows. This is a formatting defect. It does not alter the report’s hash values or prevent their verification against the manifests and source files; it does not change this bounded verdict.
