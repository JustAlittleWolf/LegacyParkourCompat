# Newer-side resource inventory (Minecraft 1.15.2)

Original input jar: `build/minecraft-decompile-cache/1.15.2/client.jar`; SHA-256 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`.

The following exact jar entries were inspected directly from the original client jar. Hashes are SHA-256 of the uncompressed entry bytes.

| Jar entry | SHA-256 | Content relevant to navigation |
|---|---|---|
| `data/minecraft/tags/blocks/ice.json` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | `ice`, `packed_ice`, `blue_ice`, `frosted_ice` |
| `data/minecraft/tags/fluids/water.json` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | `water`, `flowing_water` |
| `data/minecraft/tags/fluids/lava.json` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | `lava`, `flowing_lava` |
| `data/minecraft/tags/blocks/bee_growables.json` | `9C4180B3A1015D700E15D5BBD699A19A46F4FE759BD31CE4C9EA92860DEEAC80` | `crops`, `sweet_berry_bush` |
| `data/minecraft/tags/blocks/beehives.json` | `8B9102847F605DD72AA69DD6B7E4BCFFDCA25F385FD2F9FFD8EF862A28A18415` | `bee_nest`, `beehive` |

These tag values are not themselves a movement difference. They are recorded for later consumer tracing. The B jar contains no `data/minecraft/enchantments/` entries; this is not absence evidence because enchantments in this version are code-registered. Pair tag contents and referenced values against A only after the 1.14.4 owner supplies a verified jar identity.
