# Independent audit: 1.8.5–1.8.8 method/hash sweep

## Scope and separation

This audit independently checked the author report and hash table against read-only canonical decompiled sources at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/ornithe-feather/`. It did not inspect product code, product history, wiki inventories, or backlog. It did not edit the author’s report or TSV. It is a hash-sweep audit only, not a complete tick-graph inventory or pair freeze.

Author artifacts audited:

- `.native-source/20261028/frontier-c/185-188-method-hash-sweep/report.md`, SHA-256 `d07773bc740feebde32a76f035556c23ab5496bab27e7e479d306fe85c96a7fb` (independently recomputed; matches).
- `.native-source/20261028/frontier-c/185-188-method-hash-sweep/movement-core-sha256.tsv`, SHA-256 `c732445768bca1f6561d056997fdd4075df569af9473c4264c6c28f092058578` (independently recomputed; matches).

## Release and mapping-root provenance

Each mapping-root `.decompile-provenance.json` was parsed independently. The listed Mojang client object ID and SHA-256, Ornithe Feather metadata input hash, Feather Gen2 mapping artifact/build and its SHA-256, provenance-file SHA-256, and all source-file hashes were checked against the on-disk mapping root. All four roots contain only the `ornithe-feather` mapping directory.

| Release | Client artifact object ID | Client JAR SHA-256 | Feather mapping build | Mapping metadata SHA-256 | Mapping artifact SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|---|---|
| 1.8.5 | `5d39957c61d19042f8bd669a6faa99989cf37083` | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `1.8.5+build.2` | `57219f03e298b874f71aca9aedc913b3637b0598d4be1312c8babcc6a1a33606` | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.6 | `faa55e34ded35089d34fe921ea83d317fc152e93` | `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100` | `1.8.6+build.2` | `6815d58a74d666853a7e61f23e3a3cc86596ec02dab374765da12c02018d2a0b` | `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81` | `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9` |
| 1.8.7 | `d546a6a092060c85f1eb1d9213ff823c558b1255` | `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0` | `1.8.7+build.2` | `690dedfac870ea1fe38cbf622ec2d32ee91efd124a6485a3f3454650ff4160cc` | `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b` | `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef` |
| 1.8.8 | `0983f08be6a4e624f5d85689d1aca869ed99c738` | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `1.8.8+build.2` | `39221eeab4552d7ad873c303a9237ddbb1faad19e7302a80d359fe2b27eaa3f9` | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |

For every release, the source root contains exactly 1,612 `.java` files; the mapping-root provenance records exactly 1,612 source paths; each recorded path exists and its on-disk SHA-256 matches; there are no extra Java paths. The source files therefore match their respective mapping-root source inventories byte for byte.

The separate release-parent marker `decompiled_minecraft/<release>/.decompile-provenance.json` is absent for all four releases. The embedded mapping-root provenance does identify the client artifacts and mapping inputs above, but it is not the absent parent marker. This audit establishes the identities recorded in the mapping roots and the integrity of their generated Java files; it does not claim a separate release-parent identity record.

## Adjacent source hash sweep

I independently hashed every Java path in each adjacent pair. All four source inventories have the same 1,612 paths. The changed-path sets exactly match the author's listed paths, and their counts match:

| Pair | Changed Java paths | Author count/list |
|---|---:|---|
| 1.8.5 → 1.8.6 | 21 | Match |
| 1.8.6 → 1.8.7 | 16 | Match |
| 1.8.7 → 1.8.8 | 28 | Match |

Across the three intervals, none of the changed paths is an entity/player movement implementation or block collision/movement provider. The changed-path sets were examined as source diffs, including enclosing method context. The movement-relevant-looking 1.8.5 → 1.8.6 hunks add an operator gate for block-entity NBT placement and update related item/sign callers and sanitization; these concern placement authorization and block-entity data. The 1.8.6 → 1.8.7 hunks concern version strings, locale-safe text formatting, resource-pack handling/downloads, sign-text serialization/formatting, HTTP downloads, and a server debug log. The 1.8.7 → 1.8.8 hunks concern version/resource-pack metadata and lifecycle, rendering/skin helpers, Realms/server query behavior, CPU reporting, and chunk-save bookkeeping. I found no changed hunk in these sets that alters ordinary player movement or a collision/movement provider.

This supports the bounded conclusion “no movement/provider delta identified in these Java path changes.” It does not prove complete movement dependency closure or exclude changes mediated by unchanged classes, resources, runtime state, or unexamined source relationships.

## Core movement class byte identities

The author TSV lists 20 core movement/provider classes. I verified all 80 reported per-release hashes against the canonical source files. There were no missing files or hash mismatches. For each listed class, its reported SHA-256 is identical across all four releases, confirming byte identity for those specific files over this interval. This is file-level pruning evidence, not method-by-method dependency closure.

### TSV formatting defect

The hash values and 20 data rows verify, but the first line is not a valid tab-separated header: it contains literal backtick-plus-`t` text between column labels, while each data row uses actual TAB bytes. Standard TSV readers will not associate the data columns with the header. This is a presentation/parseability defect in the author artifact; no values were changed during this audit.

## Verdict and limits

The author report’s hashes, four mapping-root identities, 1,612-path inventories, adjacent changed-path counts and sets, and 80 core-class hashes are independently confirmed. The inspected candidate hunks support the report’s bounded no-identified-movement/provider-delta conclusion. The release-parent marker absence and TSV header formatting defect are recorded above. This audit does not freeze any pair and does not extend or revise prior findings.
