# Independent LF projection round-trip receipt

- Result: **COMPLETED**
- Projection manifest raw SHA-256: `fe37dc8c647538a93ffac7dba7cd566c3b97dc60812baf3ed1c36122bfb9566f` (expected digest matched).
- Verification: all three original/projection pairs pass strict UTF-8 decoding; projections have no BOM and contain LF line endings only; each projection is exactly its original with CRLF pairs converted to LF; declared zero-based offsets match independently computed offsets; inserting CR at every declared offset reconstructs original bytes with exact length and SHA-256.
- Verdict binding: projection and original retain the same accepted bounded no-difference conclusion and bind the original author coverage and frozen blind audit by their raw SHA-256 values.

| Role | Original SHA-256 / bytes | LF projection SHA-256 / bytes | Removed-CR LF offsets | Checks |
|---|---:|---:|---|---|
| source | `da97215a9d4a68f9525b2365b98129fb40b87a8c57922f7cbde17a39f6587c10` / 7090 | `071b7addb94396988cf9efcd25578e76b44611c6e7ffae4f3edd639acb2e75cf` / 7089 | 7088 | exact normalization; exact round trip |
| blind-review | `0f923672ee090d01bbd8da876b33613bb524e4c45654d84c65256da79b2e2b67` / 12556 | `469bd86cee270d9ae6cea7da7ca2528c1a222e8b3a47c052f41ada87f22f0ae7` / 12554 | 12552, 12553 | exact normalization; exact round trip |
| verdict | `6118c9a09012043174f5581b1f9f8840d442a3acfb7e3145a866708614028de8` / 5802 | `4c649fdaeda3344733e90f81b365b934d21ed4a820342177d636c3863821b861` / 5801 | 5800 | exact normalization; exact round trip |

Verdict conclusion preserved: `ACCEPTED — bounded no-difference finding for the assigned ordinary, unmounted, non-spectator local-player Nether portal slice.`

