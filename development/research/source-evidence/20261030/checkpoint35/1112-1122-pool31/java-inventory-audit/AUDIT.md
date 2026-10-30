# Independent Java inventory audit: 1.11.2 to 1.12.2

Audit date: 2026-10-30. Scope was the source-only inventory and the canonical `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` source trees. No movement-lane findings, wiki material, product implementation, backlog, or product history was consulted.

## Verdict

The CSV matrix and its README/summary arithmetic are independently reproduced. No omitted, duplicated, misclassified, or hash-mismatched Java paths were found. Both per-release Java inventories match their source manifests by exact relative path and SHA-256; the ready metadata counts/byte totals, manifest identities, and the documented ready/provenance identities match.

## Recomputed matrix

- CSV SHA-256: `f41e7591f77d44268dc58d7b42a496b7385ffeb79992568c2dac50785d0f948c` (matches README and summary).
- Rows and unique exact-case paths: 2,066 / 2,066.
- Unchanged: 1,324; hash changed: 581; added: 145; removed: 16.
- Count arithmetic: 1,324 + 581 + 145 + 16 = 2,066.
- Every row's side-specific presence, SHA-256, and status was recomputed against the source files using ordinal, case-sensitive relative-path keys. Both complete source path sets were covered; zero discrepancies.

## Recomputed source identities

| Release | Java files / bytes | Source manifest SHA-256 | Ready marker SHA-256 | Provenance SHA-256 |
|---|---:|---|---|---|
| 1.11.2 | 1,921 / 9,898,384 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` |
| 1.12.2 | 2,050 / 10,518,527 | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` |

For both sides, observed Java count and total file bytes equal the ready marker; every manifest entry matches a source file hash, with no extra/missing paths; the manifest file hash equals the ready marker's declared source-manifest hash. Ready and provenance records identify the expected release and `ornithe-feather` namespace. Mapping artifacts identify Feather Gen2 build 2 for that release, and provenance records Vineflower 1.12.0 and the successful `decompileMinecraft` batch command for 1.10.2, 1.11.2, and 1.12.2.

## Method

Enumerated all `.java` files recursively under each canonical mapped source root, computed SHA-256 and byte totals from the files, parsed and checked each source-manifest entry, then rebuilt the path-union matrix and statuses from those independently computed inventories. Separately hashed the matrix, ready markers, provenance records, and manifests.
