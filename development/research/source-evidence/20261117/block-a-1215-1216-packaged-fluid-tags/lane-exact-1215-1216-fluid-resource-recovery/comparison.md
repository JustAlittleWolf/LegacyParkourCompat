# Exact default fluid tag comparison: 1.21.5 → 1.21.6

Scope: raw Mojang-published client ZIP defaults only. This records packaged tag membership, `replace` presence/value, absent entries and ordering. It makes no claim about datapacks or runtime resolution.

## Input identity and verification

- **1.21.5:** Official version metadata URL `https://piston-meta.mojang.com/v1/packages/b341cd0e91419ab72b5d9dadad8d86a045d6b9a7/1.21.5.json`; metadata SHA-1 `b341cd0e91419ab72b5d9dadad8d86a045d6b9a7` (matches Mojang version manifest and downloaded bytes); metadata SHA-256 of recovered bytes `1751e36c5dda2bbf847d543d4f9a59c51035fe474b8528aca7f76c688f32fe88`. Metadata declares client URL `https://piston-data.mojang.com/v1/objects/b88808bbb3da8d9f453694b5d8f74a3396f1a533/client.jar`, SHA-1 `b88808bbb3da8d9f453694b5d8f74a3396f1a533`, size `28984409`; recovered client matches all three. Recovered client SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` matches the source ready marker's client SHA-256 and `artifacts.sha256` client row. `mojmap.ready.json` says `ready`, version `1.21.5`; source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` and artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656` both match the corresponding manifest files.
- **1.21.5 metadata pin discrepancy:** The ready artifact manifest's `1.21.5/version.json` SHA-256 is `4a9a484109ee5c3f430b685ddc719712e962e8d06c761cd58ec0a1e6fea2f18c`, which does not match the SHA-256 `1751e36c5dda2bbf847d543d4f9a59c51035fe474b8528aca7f76c688f32fe88` of official metadata bytes whose SHA-1 matches the manifest's exact metadata identity. The referenced original `version.json` artifact is not present locally, so this cannot be reconciled here.
- **1.21.6:** Official metadata is locally pinned at `https://piston-meta.mojang.com/v1/packages/8dd125560431a618094435343acccd45191bb006/1.21.6.json`; metadata SHA-1 `8dd125560431a618094435343acccd45191bb006`, size `43719`, SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`. Both local pin JSON values and computed SHA-256 match. Metadata declares client SHA-1 `740a125b83dd3447feaa3c5e891ead7fbb21ae28`, size `29516555`; local client matches both, and its computed SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` matches its local pin JSON.
- **1.21.6 publication marker:** This checkout has the source tree and local pinned artifacts, but no `decompiled_minecraft/1.21.6/mojmap.ready.json` or `decompiled_minecraft/1.21.6/artifacts.sha256`; no ready-publication claim is made for this side.

## Raw resource inventory

| Version | Exact ZIP entry | Exact current filename | Raw bytes | SHA-256 |
| --- | --- | --- | ---: | --- |
| 1.21.5 | `data/minecraft/tags/fluid/water.json` | `raw/1.21.5/data/minecraft/tags/fluid/water.json` | 74 | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` |
| 1.21.5 | `data/minecraft/tags/fluid/lava.json` | `raw/1.21.5/data/minecraft/tags/fluid/lava.json` | 72 | `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b` |
| 1.21.6 | `data/minecraft/tags/fluid/water.json` | `raw/1.21.6/data/minecraft/tags/fluid/water.json` | 74 | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` |
| 1.21.6 | `data/minecraft/tags/fluid/lava.json` | `raw/1.21.6/data/minecraft/tags/fluid/lava.json` | 72 | `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b` |

## Comparison

- Raw tag identity: `minecraft:water` and `minecraft:lava` are present at the exact `fluid/` paths on both versions. No matching `data/minecraft/tags/fluids/water.json` or `.../fluids/lava.json` entry exists in either ZIP.
- `replace`: the property is absent from all four raw JSON files (vanilla default behavior applies).
- Includes: none; all `values` entries are direct IDs, so there are no named provided include files to extract.
- Ordered `minecraft:water` values on both versions: `minecraft:water`, then `minecraft:flowing_water`.
- Ordered `minecraft:lava` values on both versions: `minecraft:lava`, then `minecraft:flowing_lava`.
- Difference: no raw default membership, order, `replace` or include difference in these four entries.

All four raw files have UTF-8 bytes, no BOM and LF line endings. This report is UTF-8 with LF line endings and no BOM.