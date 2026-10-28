# Independent lookup matrix audit: 1.16–1.18.2

Matrix audited: `lookup-acceptance-matrix.md`, SHA-256 `13318F2DD74F66605FAF108422031FC0FE43BB944E803FBA05F41DFA50B2E696`.

Scope: the 11 release rows from 1.16 through 1.18.2 inclusive. For every row, the consumer is `mojmap/net/minecraft/client/player/LocalPlayer.java` under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>`. Each canonical consumer file's SHA-256 matches its matrix row. The direct `Mth.sin` and `Mth.cos` calls occur in `LocalPlayer.updateAutoJump`'s input/yaw vector fallback in all audited source bodies.

| Release(s) | Consumer SHA-256 (each listed release) | Direct `Mth.sin/cos` call lines | Identity check |
|---|---|---|---|
| 1.16, 1.16.1 | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | 910–911 | Ready sidecars identify matching release and `mojmap`; `mojmap.sources.sha256` entry matches consumer hash. |
| 1.16.2 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | 885–886 | Ready sidecar identifies matching release and `mojmap`; source-manifest entry matches. |
| 1.16.3, 1.16.4 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | 885–886 | Hidden `mojmap/.decompile-provenance.json` `sources` entry matches. The older provenance object has only `pipeline`, `runtime`, `tools`, `inputs`, and `sources` top-level keys, consistent with the matrix's older-provenance classification. |
| 1.16.5 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | 885–886 | Ready sidecar identifies matching release and `mojmap`; source-manifest entry matches. |
| 1.17 | `08dbb8615cc6c7776f35ce269223699d39f758d15786babfe11e497421c5f4d4` | 917–918 | Hidden `mojmap/.decompile-provenance.json` `sources` entry matches; its older provenance shape matches the matrix classification. |
| 1.17.1 | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | 915–916 | Ready sidecar identifies matching release and `mojmap`; source-manifest entry matches. |
| 1.18, 1.18.1 | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | 913–914 | Ready sidecars identify their matching releases and `mojmap`; source-manifest entries match. |
| 1.18.2 | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | 911–912 | Ready sidecar identifies matching release and `mojmap`; source-manifest entry matches. |

Verdict: all 11 rows' consumer paths, SHA-256 values, direct-call claims, and identity classifications are supported by the matching canonical consumer files and provenance metadata. No discrepancy found in this subset.
