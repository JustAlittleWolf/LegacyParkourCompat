# Independent lookup matrix audit: late 1.21 rows

Audit target: `.native-source/20261028/ordinary-closure/autojump-trig-applicability/lookup-acceptance-matrix.md`, SHA-256 `13318F2DD74F66605FAF108422031FC0FE43BB944E803FBA05F41DFA50B2E696`.

Scope: matrix entries `1.21` through `1.21.10` inclusive. For every release, I inspected the canonical consumer at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\mojmap\net\minecraft\client\player\LocalPlayer.java`. In `updateAutoJump`, each file contains one direct `Mth.sin(this.getYRot() * (float) (Math.PI / 180.0))` call and one direct `Mth.cos(...)` call at the cited lines. The computed SHA-256 matches the matrix. Identity pins were read from the release manifest and ready sidecars, or from the literal hidden `.decompile-provenance.json` source map as applicable.

| Release | Direct `Mth.sin` / `Mth.cos` lines | Consumer SHA-256 | Source identity evidence |
|---|---:|---|---|
| 1.21 | 903 / 904 | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | Older provenance; hidden `mojmap/.decompile-provenance.json` `sources` map pins this path/hash. Run details missing. |
| 1.21.1 | 903 / 904 | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | Ready/manifests; `mojmap.ready.json` and `mojmap.sources.sha256` exist; manifest pins this path/hash. |
| 1.21.2 | 903 / 904 | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | Older provenance; hidden `mojmap/.decompile-provenance.json` `sources` map pins this path/hash. Run details missing. |
| 1.21.3 | 903 / 904 | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | Ready/manifests; `mojmap.ready.json` and `mojmap.sources.sha256` exist; manifest pins this path/hash. |
| 1.21.4 | 933 / 934 | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | Ready/manifests; `mojmap.ready.json` and `mojmap.sources.sha256` exist; manifest pins this path/hash. |
| 1.21.5 | 946 / 947 | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | Ready/manifests; `mojmap.ready.json` and `mojmap.sources.sha256` exist; manifest pins this path/hash. |
| 1.21.6 | 946 / 947 | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | Older provenance; hidden `mojmap/.decompile-provenance.json` `sources` map pins this path/hash. Run details missing. |
| 1.21.7 | 946 / 947 | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | Older provenance; hidden `mojmap/.decompile-provenance.json` `sources` map pins this path/hash. Run details missing. |
| 1.21.8 | 946 / 947 | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | Ready/manifests; `mojmap.ready.json` and `mojmap.sources.sha256` exist; manifest pins this path/hash. |
| 1.21.9 | 936 / 937 | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | Older provenance; hidden `mojmap/.decompile-provenance.json` `sources` map pins this path/hash. Run details missing. |
| 1.21.10 | 936 / 937 | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | Ready/manifests; `mojmap.ready.json` and `mojmap.sources.sha256` exist; manifest pins this path/hash. |

Verdict: all eleven assigned rows match the direct-call, consumer-path/hash, and source-identity claims in the matrix. No discrepancy found in this subset.
