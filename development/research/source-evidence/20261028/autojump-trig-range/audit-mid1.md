# Lookup matrix audit: 1.13–1.15.2

**Result:** All 11 scoped rows are supported. No discrepancy found.

**Matrix checked:** `lookup-acceptance-matrix.md`, SHA-256 `13318F2DD74F66605FAF108422031FC0FE43BB944E803FBA05F41DFA50B2E696`.

For each row, I formed the consumer path from the matrix release and suffix under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`, hashed that exact Java file, and inspected its autojump method. Every consumer directly invokes the listed sine and cosine helpers on `yaw * (float)(Math.PI / 180.0)`. The observed consumer hashes match the matrix.

| Release | Consumer suffix | Observed SHA-256 | Direct calls | Identity evidence |
|---|---|---|---|---|
| 1.13 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `MathHelper.sin/cos` | Ready/manifests; exact source-manifest entry matches |
| 1.13.1 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `MathHelper.sin/cos` | Ready/manifests; exact source-manifest entry matches |
| 1.13.2 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `MathHelper.sin/cos` | Ready/manifests; exact source-manifest entry matches |
| 1.14 | `ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f` | `MathHelper.sin/cos` | Ready/manifests; exact source-manifest entry matches |
| 1.14.1 | `yarn/net/minecraft/client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` | `MathHelper.sin/cos` | Older provenance; `.decompile-provenance.json` `sources` entry matches |
| 1.14.2 | `yarn/net/minecraft/client/network/ClientPlayerEntity.java` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` | `MathHelper.sin/cos` | Older provenance; `.decompile-provenance.json` `sources` entry matches |
| 1.14.3 | `yarn/net/minecraft/client/network/ClientPlayerEntity.java` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` | `MathHelper.sin/cos` | Older provenance; `.decompile-provenance.json` `sources` entry matches |
| 1.14.4 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `Mth.sin/cos` | Ready/manifests; exact source-manifest entry matches |
| 1.15 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `Mth.sin/cos` | Ready/manifests; exact source-manifest entry matches |
| 1.15.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `Mth.sin/cos` | Ready/manifests; exact source-manifest entry matches |
| 1.15.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `Mth.sin/cos` | Ready/manifests; exact source-manifest entry matches |

The 1.14.1–1.14.3 hidden provenance objects contain a `sources` map with each consumer's matching hash; their top-level keys do not include invocation/run metadata, consistent with the matrix's older-provenance classification.
