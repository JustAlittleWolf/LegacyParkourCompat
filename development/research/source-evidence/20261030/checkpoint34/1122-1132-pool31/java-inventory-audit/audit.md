# Java inventory audit — 1.12.2 → 1.13.2

**Audit date:** 2026-10-30
**Result:** Passed; no discrepancies found.
**Scope:** Inventory and source provenance verification only. This does not establish pair movement coverage, source-coverage freeze, or movement parity.

## Inputs and identities

| Input | SHA-256 |
|---|---|
| Frozen comparison inventory, `.native-source/20261030/1122-1132-pool31/full-java-path-hash-inventory.tsv` | `928EBE28B371CC5D01302D45C09A9EBF11E3275BC67234032C235079F82159F6` |
| 1.12.2 `ornithe-feather.ready.json` | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` |
| 1.12.2 `ornithe-feather.provenance.json` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` |
| 1.12.2 `ornithe-feather.verification.json` | `7f836ebc38f06b3cffe663d0899360028e9d695a04606fea1a9ff185e84651c0` |
| 1.12.2 `ornithe-feather.sources.sha256` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.12.2 `artifacts.sha256` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` |
| 1.13.2 `ornithe-feather.ready.json` | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` |
| 1.13.2 `ornithe-feather.provenance.json` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |
| 1.13.2 `ornithe-feather.verification.json` | `5fd1e406a0ccc1c7a57e18cd7566f23332e1bf3c3322b59a4b25ae2d1f2d7bba` |
| 1.13.2 `ornithe-feather.sources.sha256` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.13.2 `artifacts.sha256` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` |

Canonical source roots independently enumerated and hashed:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12.2\ornithe-feather`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13.2\ornithe-feather`

Both ready markers say `status: ready`, mapping `ornithe-feather`, and identify their source and artifact manifests. Both provenance markers identify the matching version and namespace. Both verification markers say `verified-identical-source-and-input-artifacts`; each reports zero source differences.

## Recomputed comparison

I recursively enumerated every `*.java` under both canonical roots, converted paths to slash-separated paths relative to each root, and computed SHA-256 directly from file bytes. I compared both resulting path/hash maps and their presence/absence against every inventory row, and checked each row’s status against the recomputed hashes.

| Measure | Recomputed | Expected / inventory |
|---|---:|---:|
| 1.12.2 source files | 2,050 | 2,050 |
| 1.13.2 source files | 2,711 | 2,711 |
| Inventory rows / unique paths | 2,910 / 2,910 | 2,910 |
| Same | 434 | 434 |
| Changed | 1,417 | 1,417 |
| Added | 860 | 860 |
| Removed | 199 | 199 |
| Missing, extra, duplicate, or inconsistent path/hash/status rows | 0 | 0 |

The inventory has no duplicate paths and is ordinally path-sorted. Every listed old/new hash matches the corresponding canonical source file; the full old/new source path sets are represented by the inventory.

## Manifest and marker checks

For each version, I recomputed the SHA-256 of the source and artifact manifests and compared them with the hashes declared in the ready marker. The source manifests have 2,050 and 2,711 entries, respectively; all entries parse, each path exists under its canonical root, and every listed source file hash matches. Counts and total source bytes also match the ready markers:

| Version | Java files | Source bytes | Source manifest rows/hash matches ready | Artifact manifest rows/hash matches ready |
|---|---:|---:|---|---|
| 1.12.2 | 2,050 | 10,518,527 | 2,050; yes | 38; yes |
| 1.13.2 | 2,711 | 14,770,525 | 2,711; yes | 42; yes |

Artifact-manifest identities and declared counts were verified; individual artifact payload files were not rehashed. Marker and manifest identities are the SHA-256 values listed above.

## Conclusion and boundary

The frozen inventory hash matches the coordinator-provided digest exactly, and its complete row set, statuses, and both source-side hashes reproduce against the canonical trees. Ready/provenance/verification metadata and source-manifest identities/counts are internally consistent with the trees. No inventory discrepancy was found. This audit is limited to inventory integrity and does not audit movement findings, coverage completeness, or a pair’s freeze/acceptance state.
