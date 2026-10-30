# 1.11.2 to 1.12.2 Java source inventory

Inventory classification only; statuses indicate relative path presence and SHA-256 equality, not movement relevance.

## Source identity

- Mapping: Ornithe Feather Gen2 build 2 (ornithe-feather)
- Decompiler: Vineflower 1.12.0
- 1.11.2 canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.11.2\ornithe-feather`
- 1.12.2 canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12.2\ornithe-feather`
- Both ready markers identify the expected concrete release and mapping, and match the source-manifest SHA-256.
- Both provenance records identify the exact release, mapping namespace, Feather mapping artifacts and successful decompile run.

| Release | Java files | Source bytes | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---:|---:|---|---|---|
| 1.11.2 | 1,921 | 9,898,384 | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
| 1.12.2 | 2,050 | 10,518,527 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |

## Path/hash matrix

The matrix contains every path in the union of both Java source inventories, with a SHA-256 per present side and one status:

- Unchanged: same path and same SHA-256
- Hash changed: same path, differing SHA-256
- Added: present only in 1.12.2
- Removed: present only in 1.11.2

| Status | Paths |
|---|---:|
| Unchanged | 1,324 |
| Hash changed | 581 |
| Added | 145 |
| Removed | 16 |
| **Union** | **2,066** |

All 3971 per-version source files were SHA-256 checked against their declared source manifests. Each manifest hash matches its ready marker; both source-file counts and byte totals match ready metadata. No source inventory integrity mismatch was found.

## Artifact hashes

- `java-file-matrix.csv` SHA-256: `f41e7591f77d44268dc58d7b42a496b7385ffeb79992568c2dac50785d0f948c`
- `inventory-summary.json` SHA-256: `c3090353a2f5b421a91bea59dd4513ed28b33638cdc189c284ec3e49c012ac89`
