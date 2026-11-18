# 1.15.2–26.2 packaged fluid-tag comparison

**Verdict: CLOSED for the four packaged vanilla default tag entries.** Both endpoint archive payloads list the same two membership IDs for each tag: water uses `minecraft:water` and `minecraft:flowing_water`; lava uses `minecraft:lava` and `minecraft:flowing_lava`. Neither endpoint resource has tag-reference includes. Resource bytes differ, and the archive directory changes from plural `fluids` to singular `fluid`.

## Exact entries and raw payload hashes

| Version | Exact archive entry | Bytes | Raw SHA-256 | `replace` | Parsed `values` | Includes |
|---|---|---:|---|---|---|---|
| 1.15.2 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | present (`false`) | `minecraft:water`, `minecraft:flowing_water` | none |
| 1.15.2 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | present (`false`) | `minecraft:lava`, `minecraft:flowing_lava` | none |
| 26.2 | `data/minecraft/tags/fluid/water.json` | 74 | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` | absent | `minecraft:water`, `minecraft:flowing_water` | none |
| 26.2 | `data/minecraft/tags/fluid/lava.json` | 72 | `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b` | absent | `minecraft:lava`, `minecraft:flowing_lava` | none |

## Input identity

`resource-manifest.json` records the exact local ready and artifact sidecars, their full hashes and client artifact pins. The 1.15.2 raw client came from the official URL in its exact version metadata; its metadata and client SHA-1 values were checked, and the client SHA-256 matches the canonical ready marker and artifact sidecar. The 26.2 local client bytes match the canonical client SHA-256 and SHA-1 from the locally pinned official metadata record.

The captured Mojang version manifest advertises a different metadata URL/SHA-1 for 26.2 than the previously pinned source metadata record. This comparison uses the exact canonical local client artifact and metadata pin already established in the source-resource reference; the captured catalog observation is recorded in the manifest.

## Limits

This closes membership in the packaged vanilla defaults only. It does not establish membership after user/world datapack merging, pack priority, reload state, or runtime-effective membership. It makes no claim about fluid travel behavior or full movement parity.
