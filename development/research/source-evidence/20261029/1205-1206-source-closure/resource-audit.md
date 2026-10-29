# Independent audit: 1.20.5 → 1.20.6 packaged resource evidence

## Verdict

The two locally recovered client archives match their exact version metadata client objects, SHA-1 values, sizes, and surviving ready/artifact SHA-256 pins. The 536-path resource inventory is complete for its declared scope in each archive, and every inventoried payload hash and byte count independently matches. The paired path/hash table has 536 unchanged rows and no added, removed, or changed payloads.

One provenance metadata correction is needed: the recorded 1.20.6 ZIP entry count is 23,402, but the recovered archive contains 23,403 distinct ZIP entries. This does not affect the separately revalidated 536 tag JSON path inventory or its zero-diff result.

## Archive and version-object verification

| Version | Version JSON binding | Client object URL | JAR size | JAR SHA-1 | Recomputed JAR SHA-256 | Surviving ready/client pin | ZIP entries actual / recorded |
|---|---|---|---:|---|---|---|---:|
| 1.20.5 | Exact historical URL `https://piston-meta.mojang.com/v1/packages/90a26a9473aa77e384beadcc6c88a00edd4c9a8d/1.20.5.json`; JSON id `1.20.5`; SHA-1 `90a26a9473aa77e384beadcc6c88a00edd4c9a8d`; SHA-256 `d4e0df98325bc46ee023141b21b1230ad6c9266745835867bf6ab3eb1051a9b7`. These match `decompiled_minecraft/1.20.5/mojmap.provenance.json` and the raw JSON. | `https://piston-data.mojang.com/v1/objects/c6b92b2374a629f20802bb284f98a4ee790e950a/client.jar` | 26,565,623 | `c6b92b2374a629f20802bb284f98a4ee790e950a` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`; artifact manifest `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6` | 23,402 / 23,402 |
| 1.20.6 | Official cached manifest SHA-256 `ac37f92737df1a9d7660047136fedd3edebd90bd6e552f53ddc3f222079bc8fa` matches the file at the recorded path. Its `1.20.6` entry supplies URL `https://piston-meta.mojang.com/v1/packages/876ef010b51e9e646712d8322e383a84859d3b39/1.20.6.json` and SHA-1 `876ef010b51e9e646712d8322e383a84859d3b39`; the raw JSON id, SHA-1, and SHA-256 (`e25adfd8fe13c23465518b3059b8e44257f3f057425c61cbee5cf1bcd27f53a5`) agree. | `https://piston-data.mojang.com/v1/objects/05b6f1c6b46a29d6ea82b4e0d42190e42402030f/client.jar` | 26,565,641 | `05b6f1c6b46a29d6ea82b4e0d42190e42402030f` | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; artifact manifest `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31` | **23,403 / 23,402** |

The 1.20.5 ready marker reports status `ready`, exact version metadata id `1.20.5`, client SHA-256 and artifact-manifest SHA-256 above. Its artifact manifest was independently rehashed to the ready pin. The exact historical 1.20.5 URL comes from its surviving version-specific provenance, not the current cached manifest entry. No current 1.20.5 substitute was used.

The 1.20.6 ready marker reports status `ready`, version metadata id `1.20.6`, client SHA-256 and artifact-manifest SHA-256 above. Its artifact manifest was independently rehashed to the ready pin. The 1.20.6 provenance file is a batch record and omits per-input metadata/client fields; the exact version JSON URL and its object hash are independently bound by the hash-verified cached official version manifest and the recovered raw JSON. The archive-provenance bundle's `versions[1].versionJson.url` is null, so the local manifest is needed to reconstruct that URL. No missing object identity pin was found: the metadata SHA-1/JSON SHA-256, URL, client URL/object SHA-1, archive SHA-256, and ready/artifact pins all agree.

## Inventory recomputation

- `resource-inventory.tsv`: 1,072 rows, exactly 536 distinct paths for each version, no duplicate `(version,path)` rows.
- For each recovered ZIP, independently enumerated all JSON entries under `data/minecraft/tags/**` and `data/minecraft/datapacks/update_1_21/data/minecraft/tags/**`; each archive has exactly 536 such paths, and each path set equals the corresponding inventory path set.
- Read each selected ZIP member in-place (no extraction) and recomputed the uncompressed payload byte count and SHA-256. All 1,072 rows match; no missing or extra path and no content/hash/size mismatch.
- Recomputed pair statuses and fields by path from the inventory and compared with `resource-diff.tsv`; all 536 rows match. Status totals: 536 unchanged, 0 added, 0 removed, 0 changed.

## Finding

Correct the 1.20.6 `zipEntryCount` in `archive-provenance.json` from `23402` to `23403`, or document why the producer intentionally excludes one central-directory entry. The archive has 23,403 unique names, so this is not explained by duplicate entries. This count discrepancy is independent of the verified 536-resource inventory and does not alter the zero-diff conclusion.

Audit method: static SHA-1/SHA-256 hashing, JSON reads, ZIP central-directory enumeration, and direct reads of selected ZIP member bytes. No payload was executed, extracted to a source/cache location, or decompiled. No canonical source/cache, product, history, wiki, or Git state was changed.
