# 1.14.4–1.15 packaged fluid-tag comparison

**Verdict: CLOSED for the packaged default water/lava entries.** The exact archive entry paths and parsed memberships are listed below. This comparison covers source/flowing IDs, `replace`, and any tag includes in these four vanilla client resources.

## Exact entries and raw hashes

| Version | Exact archive entry | Bytes | Raw SHA-256 | `replace` | Parsed `values` | Includes |
|---|---|---:|---|---|---|---|
| 1.14.4 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | present (`false`) | minecraft:water, minecraft:flowing_water | none |
| 1.14.4 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | present (`false`) | minecraft:lava, minecraft:flowing_lava | none |
| 1.15 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | present (`false`) | minecraft:water, minecraft:flowing_water | none |
| 1.15 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | present (`false`) | minecraft:lava, minecraft:flowing_lava | none |

## Input identity

`resource-manifest.json` pins each source-ready marker, source manifest, artifact manifest and client row. The 1.14.4 archive was read from the local pinned cache; its client SHA-256 matches the source ready marker and cache/artifact pins, while its SHA-1 and byte size match official Mojang metadata. The exact 1.15 client and metadata were reused read-only from the assigned earlier lane; SHA-256, SHA-1 and size were rechecked against its ready/artifact pins and official metadata.

## Limits

This comparison is limited to packaged vanilla defaults. It makes no claim about user/world datapack merging, pack priority, reload state, or runtime-effective membership.
