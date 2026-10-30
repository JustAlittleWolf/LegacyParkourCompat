# Independent block/provider matrix integrity audit

## Verdict

The supplied matrix has complete path coverage for the assigned subset: all 239 rows are unique and equal the union of the canonical `net/minecraft/block/**` Java paths and the four specified world-provider siblings in 1.11.2 and 1.12.2. Recomputed statuses agree for every row. All 473 present owned files hash to their corresponding canonical source-manifest entries. No owned path is missing or misclassified in the matrix.

This audit checks path coverage, status classification, and source-file/manifest integrity only. It does not independently review method deltas or movement interpretations, and it makes no full-pair freeze claim.

## Recomputed path/status counts

| Status | Matrix | Recomputed |
|---|---:|---:|
| unchanged | 116 | 116 |
| hash_changed | 118 | 118 |
| added | 5 | 5 |
| removed | 0 | 0 |
| Total paths | 239 | 239 |

Unique matrix paths: 239. Canonical owned-path union: 239. Duplicate rows: 0. Canonical paths missing from matrix: 0. Matrix paths outside the owned union: 0. Status mismatches: 0. Present endpoint files hashed: 473. Manifest mismatches: 0.

## Canonical source identity and integrity

Both ready markers report status `ready`, mapping `ornithe-feather`, Feather Gen2 build 2, and Vineflower 1.12.0. For each endpoint, the source manifest SHA-256 matches the ready marker; manifest entry count and summed file bytes match the ready metadata.

| Endpoint | Mapping artifacts | Source files / bytes | Source-manifest SHA-256 | Ready-marker SHA-256 | Provenance SHA-256 |
|---|---|---:|---|---|---|
| 1.11.2 | `feather-gen2-1.11.2+build.2-mergedv2.jar`, `feather-gen2-1.11.2+build.2.tiny` | 1,921 / 9,898,384 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` |
| 1.12.2 | `feather-gen2-1.12.2+build.2-mergedv2.jar`, `feather-gen2-1.12.2+build.2.tiny` | 2,050 / 10,518,527 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` |

The block subtree contains 230 Java files in 1.11.2 and 235 in 1.12.2, with no non-Java files. `World.java`, `WorldView.java`, `WorldRegion.java`, and `WorldChunk.java` exist at both endpoints and are included in the owned union.

## Audited artifact hashes

- Matrix SHA-256: `6b7fcf36677d2c0714d8a02d9c12d4f0abac647e05b6e80a7d61d328066caefb` (matches the supplied identity).
- Per-path recomputed and manifest-expected file hashes: [path-hashes.tsv](path-hashes.tsv), SHA-256 `2359ddfe507abd5a652add3e16038b913bfa4ae8bf9c0b32c7e8ea430e783ff2`.

The TSV has one row per matrix path, both endpoint hashes where present, the corresponding manifest hashes, and the matrix/recomputed status. Added paths have no 1.11.2 file or manifest entry.
