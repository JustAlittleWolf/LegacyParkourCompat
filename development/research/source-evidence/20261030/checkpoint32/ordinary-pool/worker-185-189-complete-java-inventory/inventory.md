# Adjacent Java source inventory: 1.8.5–1.8.9

## Scope and result

This inventory closes the adjacent Java path/hash matrix for `1.8.5 → 1.8.6 → 1.8.7 → 1.8.8 → 1.8.9` using only the exact read-only `ornithe-feather` roots below and the assigned source-only reports/audits. It is source inventory evidence, not a full-pair freeze or runtime-parity claim.

The complete machine-readable matrix is [`adjacent-source-hashes.tsv`](adjacent-source-hashes.tsv): 1,612 rows per adjacent edge, 6,448 data rows total. Every record carries edge, relative Java path, status, older SHA-256 and newer SHA-256. All five source inventories have 1,612 paths and match their respective source manifests exactly. Across all four edges there are no additions or removals; changed counts are 21, 16, 28 and 26. The path sets therefore stay constant through the chain.

| Adjacent edge | Same paths | Changed hashes | Added | Removed | Existing disposition audit |
|---|---:|---:|---:|---:|---|
| 1.8.5 → 1.8.6 | 1,591 | 21 | 0 | 0 | Hash-sweep report and independent audit; later composed by group closure and full-freeze audit |
| 1.8.6 → 1.8.7 | 1,596 | 16 | 0 | 0 | Hash-sweep report and independent audit; later composed by group closure and full-freeze audit |
| 1.8.7 → 1.8.8 | 1,584 | 28 | 0 | 0 | Hash-sweep report and independent audit; later composed by group closure and full-freeze audit |
| 1.8.8 → 1.8.9 | 1,586 | 26 | 0 | 0 | Endpoint bridge and independent audit, ordinary movement report and audit, group closure and full-freeze audit |

## Exact source identities and manifest checks

All source roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`, mapping family Ornithe Feather Gen2 build 2. For each root I hashed all recursive `.java` files and compared the relative paths and hashes with the embedded mapping-root provenance `sources` map for 1.8.5–1.8.8, and with `1.8.9/ornithe-feather.sources.sha256` for 1.8.9. Missing, extra and mismatching paths: zero for every release.

| Release | Client artifact SHA-256 | Source manifest used | Manifest SHA-256 | Provenance SHA-256 | Java files |
|---|---|---|---|---|---:|
| 1.8.5 | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `1.8.5/ornithe-feather/.decompile-provenance.json` (`sources` map) | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | 1,612 |
| 1.8.6 | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` | `1.8.6/ornithe-feather/.decompile-provenance.json` (`sources` map) | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` | 1,612 |
| 1.8.7 | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` | `1.8.7/ornithe-feather/.decompile-provenance.json` (`sources` map) | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` | 1,612 |
| 1.8.8 | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `1.8.8/ornithe-feather/.decompile-provenance.json` (`sources` map) | `588f83dc64ce0bdf0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | `588f83dc64ce0bdf0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | 1,612 |
| 1.8.9 | `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` | `1.8.9/ornithe-feather.sources.sha256` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` (`ornithe-feather.provenance.json`) | 1,612 |

The older four mapping roots have embedded provenance markers but lack separate release-parent provenance markers, as recorded by their prior independent audit. The matrix relies on the embedded source maps and the direct on-disk path/hash comparison; this does not fill that publication-marker limitation.

## Compositional changed-edge routing

The changed Java path sets and counts in the new matrix exactly agree with the prior independently audited sweep for the first three edges and the prior bridge inventory for the fourth. No changed path is novel relative to those audited path inventories, so there is no unclassified edge requiring a new paired-body investigation. Existing changed-hunk dispositions apply as follows:

- **1.8.5 → 1.8.6 (21):** The `Bootstrap` change removes command-block-item dispenser behavior; paired registration/caller evidence routes its writes through the logical-server-only dispenser path. It can affect future received block state, so retain that state-producer boundary if scope includes full server evolution from equal initial worlds. It does not alter client movement response with equal received vanilla state. Related `IBlockSource`/`BlockSource` and item/sign/block-entity changes concern the removed behavior or operator-gated block-entity NBT placement. Remaining deltas are UI/version/resource-pack, sign/network/server, diagnostics, or logging. This disposition is in the sweep report/audit and the resource-registration supplement/audit.
- **1.8.6 → 1.8.7 (16):** `ResourcePacks` changes server-pack download naming, retention, and reload timing. Other changes route to UI/text/sign serialization, server/network, diagnostics, HTTP downloads, or metadata. The earlier archive audit covers this resource edge through 1.8.8; no changed movement terminal/provider is identified.
- **1.8.7 → 1.8.8 (28):** `GameOptions`/`ResourcePacks` alter incompatible-pack selection and reload; pack entry/screen changes are UI. `Bootstrap` changes empty skull-owner parsing, not movement/block registration. `AnvilChunkStorage` changes persistence/logging/flush bookkeeping. Remaining deltas route to rendering, UI, Realms, server/network, diagnostics, or utilities. Existing resource evidence identifies presentation text only through this edge, with blockstates/models/block textures unchanged.
- **1.8.8 → 1.8.9 (26):** The bridge audit and ordinary movement audit establish no added/removed Java path and no changed ordinary input/travel roots or block/entity/world provider paths. `GameOptions` adds Realms notifications; `Minecraft` changes version strings; `MathHelper` and command/effect changes preserve the examined expressions, gates, and argument order; `CrashReport` changes the displayed release string. Other deltas route to UI, rendering/audio, Realms, book text, commands, server/network, or diagnostics. The 1.8.8–1.8.9 source/resource gap is covered by the later worker full-freeze audit, whose packaged-resource result is independent of this Java-only matrix.

Composed with the accepted tick/ordinary slices, the unchanged movement roots and providers are evidence for equal source-level movement response under the stated equal effective input, player/effect/runtime state, and received movement-relevant vanilla state premise. The group audits expressly do not establish an exhaustive inventory of every movement terminal/dependency or a full-pair freeze. The server-side dispenser boundary also prevents an inference of equal future worlds from equal initial worlds without further scope-specific closure.

## Immutable input and output hashes

SHA-256 hashes below bind the exact reports used for the compositional dispositions and manifest/source evidence. The required reports and audits were read-only inputs.

| Input | SHA-256 |
|---|---|
| `.native-source/20261028/frontier-c/185-188-method-hash-sweep/report.md` | `d07773bc740feebde32a76f035556c23ab5496bab27e7e479d306fe85c96a7fb` |
| `.native-source/20261028/frontier-c/185-188-method-hash-sweep/movement-core-sha256.corrected.tsv` | `54f735feddba839f19527ae7b9bee36d2f9000e5dda42cb7b36fe7b1c96814e6` |
| `.native-source/20261028/frontier-c/185-188-method-hash-sweep-audit/audit.md` | `336ed48262297ad046847c8d5a84d7fd234083461786943c81a3da6b19da84aa` |
| `.native-source/20261028/frontier-c/188-189-movement-bridge/boundary-audit-1.8.8--1.8.9.md` | `06247fa2ec619c98c56b8d774a6340ed839edf7213ac35e1b9e013e2b221a97a` |
| `.native-source/20261028/frontier-c/188-189-movement-bridge/changed-source-hashes.csv` | `46f89d86ca6d2b4a4d6e41e30b4e53f905550328bc553d33425ce04a8bef90a2` |
| `.native-source/20261028/frontier-c/188-189-movement-bridge-audit/audit.md` | `19023d9ab5d805561b9e66640a48778c0c9a3a36661ebf23b8c127eb23363598` |
| `.native-source/20261028/frontier-c/188-189-ordinary-movement/report.md` | `f2357c93a2e327a62f14d20e1fa68e1891e8efdad15b25bbb1c46ccb4b1fe86d` |
| `.native-source/20261028/frontier-c/188-189-ordinary-movement-audit/audit.md` | `edba68edfeba36c16850957617a73bf9945b704df88f505b338f842697bd7de6` |
| `.native-source/20261030/ordinary-pool/185-189-group-closure/closure.md` | `8e1d55b826a0221c9a0d53942334dac693e8d4b9ae158e5531fb75463e16f257` |
| `.native-source/20261030/ordinary-pool/185-189-group-closure/resource-correction.md` | `e1c11425af54a812ac2abdc426dae78f1e5ca6ae6af3ce8b15b2039de4215f1f` |
| `.native-source/20261030/ordinary-pool/185-189-group-closure/resource-registration-supplement.md` | `5533875a8ebe54a180d9b45d7b5cdfb11895c55e1f53e886c24decf628b96493` |
| `.native-source/20261030/ordinary-pool/worker-185-189-resource-bootstrap/audit.md` | `281d8ef34b9da7925991b0226230fc7e56e0d021f7fbd4d2591f0b1134846746` |
| `.native-source/20261030/ordinary-pool/worker-fullfreeze-185-189/audit.md` | `d3e654f4df75eac640967396b515bed75ce4f07d91344e6fb8ac5cda6dcdb432` |

The matrix has 6,448 data records plus one header row and SHA-256 `14e9dfe46e4ee15f898f36959028a0f66ad7b225d1e6e8b879cea3f130fbea6f`. The final `inventory.md` SHA-256 is reported with the handoff because embedding a file's own hash would change that hash.
