# 1.16.1–1.16.2 packaged fluid-tag comparison

**Verdict: CLOSED — the packaged default water and lava resources are byte-for-byte identical.** In both exact Mojang client archives, water includes `minecraft:water` and `minecraft:flowing_water`; lava includes `minecraft:lava` and `minecraft:flowing_lava`. No tag-reference includes occur.

## Exact entries and raw hashes

| Version | Exact archive entry | Bytes | Raw SHA-256 | `replace` | Parsed `values` | Includes |
|---|---|---:|---|---|---|---|
| 1.16.1 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | present (`false`) | minecraft:water, minecraft:flowing_water | none |
| 1.16.1 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | present (`false`) | minecraft:lava, minecraft:flowing_lava | none |
| 1.16.2 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | present (`false`) | minecraft:water, minecraft:flowing_water | none |
| 1.16.2 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | present (`false`) | minecraft:lava, minecraft:flowing_lava | none |

## Input identity

`resource-manifest.json` records the full ready-marker, source-manifest, artifact-manifest, official version-metadata, and client archive SHA-256/SHA-1 values. For both releases, the client JAR SHA-1 matches exact official Mojang metadata and its SHA-256 matches the ready-marker and canonical artifact-manifest pin.

## Limits

This closes the packaged vanilla default resources only. It makes no claim about membership after user/world datapack merging, pack priority, reload state, runtime-effective membership, fluid travel, or complete movement parity.
