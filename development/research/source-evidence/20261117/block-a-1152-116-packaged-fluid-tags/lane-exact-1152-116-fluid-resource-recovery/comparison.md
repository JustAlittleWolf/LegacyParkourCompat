# 1.15.2–1.16 packaged fluid-tag comparison

**Verdict: CLOSED — packaged default water and lava resources are byte-for-byte identical.** Both exact stable Mojang client archives contain the same two entries and memberships: water has `minecraft:water` and `minecraft:flowing_water`; lava has `minecraft:lava` and `minecraft:flowing_lava`. Neither resource references another tag.

## Exact entries and raw hashes

| Version | Exact archive entry | Bytes | Raw SHA-256 | `replace` | Parsed `values` | Includes |
|---|---|---:|---|---|---|---|
| 1.15.2 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | present (`false`) | minecraft:water, minecraft:flowing_water | none |
| 1.15.2 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | present (`false`) | minecraft:lava, minecraft:flowing_lava | none |
| 1.16 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | present (`false`) | minecraft:water, minecraft:flowing_water | none |
| 1.16 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | present (`false`) | minecraft:lava, minecraft:flowing_lava | none |

## Input identity

`resource-manifest.json` records full ready-marker, source-manifest, artifact-manifest, exact official metadata and client hashes. The 1.15.2 client is reused read-only from the earlier private lane and independently rehashed. The 1.16 client and metadata were fetched from their exact official Mojang URLs. Both client SHA-1 values match their official metadata; both client SHA-256 values match the local ready markers and artifact sidecars.

## Limits

The conclusion is limited to packaged vanilla archive defaults. It says nothing about custom datapack/world merging, pack priority, reload state, runtime-effective membership, fluid travel, or complete movement parity.
