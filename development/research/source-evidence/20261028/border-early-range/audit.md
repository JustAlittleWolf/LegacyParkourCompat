# Audit record: early border-range source inputs

All source reads and hashes below refer to the existing, read-only literal source trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap` and cache records at `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache`. No source was generated, regenerated, or modified. SHA-256 hashes were rebound from the current files / local integrity manifests during this audit.

## Version manifest and release metadata chain

The local `version_manifest_v2.json` SHA-256 is `AC37F92737DF1A9D7660047136FEDD3EDEBD90BD6E552F53DDC3F222079BC8FA`.

| Release | Version metadata URL from manifest / pin | Manifest SHA-1 | Local `version.json` SHA-256 | Local `version.json.pin.json` binding |
|---|---|---|---|---|
| 1.21.6 | `https://piston-meta.mojang.com/v1/packages/8dd125560431a618094435343acccd45191bb006/1.21.6.json` | `8dd125560431a618094435343acccd45191bb006` | `EA1C91CCB9760C9F7E697C29ED679427DD20CDAC9CE35937125E5690973DF6DD` | Same URL and SHA-256 |
| 1.21.7 | `https://piston-meta.mojang.com/v1/packages/24c116056f9d403403d7b7c06fe018e75aaca464/1.21.7.json` | `24c116056f9d403403d7b7c06fe018e75aaca464` | `B7EBF2FB9A9CC655F874386A55731E87FA20D35FCBAE43BB92DDB4DF081FC51A` | Same URL and SHA-256 |
| 1.21.8 | `https://piston-meta.mojang.com/v1/packages/5d557fffba309d5d68efa24a42bf73856008c5e3/1.21.8.json` | `5d557fffba309d5d68efa24a42bf73856008c5e3` | `3BF5CDD444DFA89199FE4F8A92B6C79F4E4FA8977FF52029C3ACD99378506FAD` | No `.minecraft-decompile-cache\1.21.8` directory or `version.json.pin.json` was present; SHA-256 is from the 1.21.8 source bundle's `artifacts.sha256` record only. |

For .6/.7, the manifest's exact release ID and version-metadata URL/SHA-1 correspond to the local version pin URL and the local file's SHA-256. The .8 root source-bundle metadata gives the manifest URL/SHA-1 and its `artifacts.sha256` records the version JSON SHA-256, but the named local decompile cache has no .8 version JSON or pin to independently bind those two records.

## Client and mappings input artifacts

| Release | Input | Provenance URL | SHA-256 and verification source |
|---|---|---|---|
| 1.21.6 | `client.jar` | `https://piston-data.mojang.com/v1/objects/740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar` | `9592DF8587D6D3EC0334C873AD432DC76006D6129AFDCE9A7EAC52638339A24A`; actual local cache file matched its `.pin.json` |
| 1.21.6 | `client_mappings.txt` (official file `client.txt`) | `https://piston-data.mojang.com/v1/objects/848855615bc81e3db1c85e69b6afb150807a1261/client.txt` | `2FF82B34CE5F849AAAFDFF51E23B9448C4263881238B780A441FE1061A6FC261`; actual local cache file matched its `.pin.json` |
| 1.21.7 | `client.jar` | `https://piston-data.mojang.com/v1/objects/a2db1ea98c37b2d00c83f6867fb8bb581a593e07/client.jar` | `6D0AA64C6A0AEE3270253317F51E9E816F94CD3B16B44F5D605615C39FC62FB9`; actual local cache file matched its `.pin.json` |
| 1.21.7 | `client_mappings.txt` (official file `client.txt`) | `https://piston-data.mojang.com/v1/objects/8d83af626cae1865deaf55fbf96934be4886fd45/client.txt` | `328F2C5849C7C5FC4F2B1EAE4F74A7CFE6BB337C7938242AC5C4F9EAC89B1E50`; actual local cache file matched its `.pin.json` |
| 1.21.8 | `client.jar` | Not recorded in the local .8 source-bundle provenance; the official version metadata URL is recorded above. | `EA74E9C5E92D01F95F3D39196DDB734E19A077A58B4C433C34109487D600276F`; recorded in the .8 `artifacts.sha256` manifest and agrees with `mojmap.ready.json` `clientSha256`. The input file itself is not present in the named `.minecraft-decompile-cache` path. |
| 1.21.8 | `client_mappings.txt` | Not recorded in the local .8 source-bundle provenance; the official version metadata URL is recorded above. | `DCE035FEDCC047CE0306C69892176B5EB9036BCC2845C87198163F12BB1D67CA`; recorded in the .8 `artifacts.sha256` manifest. The input file itself is not present in the named `.minecraft-decompile-cache` path. |

For .6/.7, the official mapping inputs are identified by their Mojang `client.txt` URLs and their local file hashes match the cache pin records. Their source tree folder is named `mojmap`; neither `.decompile-provenance.json` explicitly records that namespace nor an exact decompiler invocation. The provenance records include a pipeline string, tool hashes, runtime and a `sources` hash map. Those facts support input identity and tree-local labeling, but do not justify claiming the missing namespace/invocation fields were recorded.

For .8, the root `mojmap.provenance.json` explicitly records `versionId: 1.21.8`, `namespace: mojmap`, and an exact command using `--versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap`; its ready marker names Vineflower and binds the source/artifact manifests. The artifact-manifest hashes above are recorded evidence, not a local re-hash of absent .8 input files. Because the local .8 version JSON is absent from the specified cache and the provenance record does not list input artifact URLs, this audit does not invent .8 `client.jar` or mappings URLs.

## Source file hash rebinding

All rows below were computed from the literal release source paths stated at the top. Identical hash values mean byte-identical files in the three inspected trees.

| File under `net/minecraft` | 1.21.6 SHA-256 | 1.21.7 SHA-256 | 1.21.8 SHA-256 |
|---|---|---|---|
| `world/level/border/WorldBorder.java` | `94EFA5133C7A4344C980B107F937FE397996596BA7E1608DEE5FA5E500C55925` | `94EFA5133C7A4344C980B107F937FE397996596BA7E1608DEE5FA5E500C55925` | `94EFA5133C7A4344C980B107F937FE397996596BA7E1608DEE5FA5E500C55925` |
| `world/entity/Entity.java` | `C403E6176D27B5BFD6AAA0DEA3735FFA4FCF80DBAE58766661DD84453B7E9704` | `C403E6176D27B5BFD6AAA0DEA3735FFA4FCF80DBAE58766661DD84453B7E9704` | `C403E6176D27B5BFD6AAA0DEA3735FFA4FCF80DBAE58766661DD84453B7E9704` |
| `world/level/CollisionGetter.java` | `03752693709491670A3C6B1D91114D46D92D6DFB1F48D3ECEA3725C08B22AC8C` | `03752693709491670A3C6B1D91114D46D92D6DFB1F48D3ECEA3725C08B22AC8C` | `03752693709491670A3C6B1D91114D46D92D6DFB1F48D3ECEA3725C08B22AC8C` |
| `client/multiplayer/ClientLevel.java` | `18219267F00CAE67E19F9243A15D7193111F8182021C57CC057728FC4E103F0D` | `18219267F00CAE67E19F9243A15D7193111F8182021C57CC057728FC4E103F0D` | `18219267F00CAE67E19F9243A15D7193111F8182021C57CC057728FC4E103F0D` |
| `client/multiplayer/ClientPacketListener.java` | `A66556678A93C63C0CEB8953F25261B82BF3BB1971048FBC15B177C6DFFEA8CD` | `A66556678A93C63C0CEB8953F25261B82BF3BB1971048FBC15B177C6DFFEA8CD` | `A66556678A93C63C0CEB8953F25261B82BF3BB1971048FBC15B177C6DFFEA8CD` |
| `world/level/BlockCollisions.java` (additional audit hash) | `E60F4D3CF7537A5E3B8B3AD099A2A7EC167F972460FA01045FD2B2141B6DDAA0` | `E60F4D3CF7537A5E3B8B3AD099A2A7EC167F972460FA01045FD2B2141B6DDAA0` | `E60F4D3CF7537A5E3B8B3AD099A2A7EC167F972460FA01045FD2B2141B6DDAA0` |
| `client/Minecraft.java` (additional audit hash) | `8508BCB2BBE4D48B3628BF4C87F87F719D07B0F60E7811A8BC6322513E55F973` | `8508BCB2BBE4D48B3628BF4C87F87F719D07B0F60E7811A8BC6322513E55F973` | `8508BCB2BBE4D48B3628BF4C87F87F719D07B0F60E7811A8BC6322513E55F973` |

## Source-bundle record hashes

| Release | Record | SHA-256 |
|---|---|---|
| 1.21.6 | `mojmap/.decompile-provenance.json` | `56B036C61094D1D936882BDF5B2B59044512189CB33ADBB075880C727DA9B19E` |
| 1.21.7 | `mojmap/.decompile-provenance.json` | `45BF296D921B2239FD0AEDB2684E2C1CB9B7D7532B4106B8A2FD9B09309CA82D` |
| 1.21.8 | `mojmap.provenance.json` | `90FCB3707371F2CFC5414E0089CFA231F505821D7ACA45211C7C816332BB4CFA` |
| 1.21.8 | `mojmap.ready.json` | `2E3BF302503EB127061CC605797B390F5F4A4B508F09350A59D78CED857BA7D7` |
| 1.21.8 | `artifacts.sha256` | `C8210B15012DCC4E109C2F73015B4117ABEC1F8AB04FDD375DBC11419E9E246C` |
| 1.21.8 | `mojmap.sources.sha256` | `8FFB76CEA647A2BA4FE58E000678F751BEA2C40EA6358BAE492E962ED1D9D008` |

The existing `.native-source/20261028/border-range/evidence.md` remains the source record for the .9/.10 wall-clock and .11 tick-snapshot comparison, including its exact source hashes and line references. This artifact does not infer pair acceptance, coverage completion, or runtime parity from source comparison.
