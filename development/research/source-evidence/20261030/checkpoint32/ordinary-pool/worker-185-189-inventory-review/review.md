# Independent inventory review: 1.8.5–1.8.9 adjacent Java matrix

## Verdict

**Accept the inventory’s Java path/hash matrix and changed-edge routing as source-tree inventory evidence, with no correction. Do not elevate it to a full-pair freeze.** I independently recomputed all 6,448 pair/path records against the five current read-only source trees and the release source manifests. All row path sets, endpoint hashes, and `same`/`delta` statuses agree. The four delta sets equal the prior audited changed-path lists exactly. No unknown/new Java delta was found that requires a new paired-body inspection. Existing disposition evidence composes for this inventory purpose; the prior terminal/dependency coverage limitation remains.

## Exact inputs and identities

- Inventory: `.native-source/20261030/ordinary-pool/worker-185-189-complete-java-inventory/inventory.md`, SHA-256 `0731eac7a0a6452a53fc0baefb5fb1178a856308e9046f0c6f5a2a60dfe58961`.
- Matrix: `.native-source/20261030/ordinary-pool/worker-185-189-complete-java-inventory/adjacent-source-hashes.tsv`, SHA-256 `14e9dfe46e4ee15f898f36959028a0f66ad7b225d1e6e8b879cea3f130fbea6f`.
- Read-only source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.8.5,1.8.6,1.8.7,1.8.8,1.8.9}/ornithe-feather/`.
- Mapping family reported as Ornithe Feather Gen2 build 2. Each root contains 1,612 Java files. For 1.8.5–1.8.8, the embedded `.decompile-provenance.json` `sources` maps each have 1,612 entries and match the current recursive Java path/hash inventories exactly. For 1.8.9, `ornithe-feather.sources.sha256` has 1,612 entries and matches exactly. Each release has zero missing, extra, or hash-mismatching Java paths.

Manifest SHA-256 values independently recomputed: 1.8.5 `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce`; 1.8.6 `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9`; 1.8.7 `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef`; 1.8.8 `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`; 1.8.9 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`. The 1.8.9 provenance marker independently hashes to `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`. I did not verify client artifact bytes anew; this review's inventory verification is bounded to Java files and their source manifests.

## Matrix checks

The TSV has the expected five columns, one header, 6,448 data rows, and 6,448 unique `(edge,path)` keys. Each edge has 1,612 rows. Paths in each edge are case-insensitively ordered and the endpoint path sets are identical at every adjacent boundary. For each individual record, I recomputed both endpoint file SHA-256 values and confirmed the row's recorded values and status (`same` iff equal, `delta` iff different). Record errors: zero.

| Edge | Same | Delta | Added | Removed |
|---|---:|---:|---:|---:|
| 1.8.5 → 1.8.6 | 1,591 | 21 | 0 | 0 |
| 1.8.6 → 1.8.7 | 1,596 | 16 | 0 | 0 |
| 1.8.7 → 1.8.8 | 1,584 | 28 | 0 | 0 |
| 1.8.8 → 1.8.9 | 1,586 | 26 | 0 | 0 |

The delta path sets match exactly: the first three edges against the exhaustive lists in `.native-source/20261028/frontier-c/185-188-method-hash-sweep/report.md`, and 1.8.8→1.8.9 against all paths in `changed-source-hashes.csv`. For the last edge I also confirmed the report’s `boundary-audit-1.8.8--1.8.9.md` is the existing bridge evidence; its inventory is an endpoint/path bridge and paired movement audit, not a new Java path delta source.

## Changed-edge routing and closure boundary

The prior 1.8.5–1.8.8 sweep plus independent audit and correction supply path-by-path dispositions for the first three matrix delta sets. The 1.8.8–1.8.9 changed-source bridge plus independent bridge audit and ordinary-movement report/audit supply the endpoint changed-path inventory and examined hunk dispositions for the final set. I checked the existing group closure and the later independent full-freeze audit to ensure the inventory’s compositional statement preserves their qualified scope. The inventory appropriately says it is Java inventory evidence, limits the movement inference to equal effective input/state under examined slices, retains the 1.8.5 server-only dispenser as a future received-state boundary, and explicitly disclaims full-pair freeze/runtime parity.

Input-report hashes recomputed for this review:

- 185–188 sweep report `d07773bc740feebde32a76f035556c23ab5496bab27e7e479d306fe85c96a7fb`; corrected movement roster `54f735feddba839f19527ae7b9bee36d2f9000e5dda42cb7b36fe7b1c96814e6`; sweep audit `336ed48262297ad046847c8d5a84d7fd234083461786943c81a3da6b19da84aa`.
- 188–189 bridge report `06247fa2ec619c98c56b8d774a6340ed839edf7213ac35e1b9e013e2b221a97a`; changed-path CSV `46f89d86ca6d2b4a4d6e41e30b4e53f905550328bc553d33425ce04a8bef90a2`; bridge audit `19023d9ab5d805561b9e66640a48778c0c9a3a36661ebf23b8c127eb23363598`.
- 188–189 ordinary report `f2357c93a2e327a62f14d20e1fa68e1891e8efdad15b25bbb1c46ccb4b1fe86d`; ordinary audit `edba68edfeba36c16850957617a73bf9945b704df88f505b338f842697bd7de6`.
- Group closure `8e1d55b826a0221c9a0d53942334dac693e8d4b9ae158e5531fb75463e16f257`; resource correction `e1c11425af54a812ac2abdc426dae78f1e5ca6ae6af3ce8b15b2039de4215f1f`; resource-registration supplement `5533875a8ebe54a180d9b45d7b5cdfb11895c55e1f53e886c24decf628b96493`; resource/bootstrap audit `281d8ef34b9da7925991b0226230fc7e56e0d021f7fbd4d2591f0b1134846746`; later full-freeze audit `d3e654f4df75eac640967396b515bed75ce4f07d91344e6fb8ac5cda6dcdb432`.

This audit establishes the integrity and compositional routing of the complete adjacent Java file matrix. It does not supply the absent exhaustive pairwise terminal/dependency inventory or independent tick-graph coverage audit. Full-pair freeze remains unmet, as the existing full-freeze audit already concludes.
