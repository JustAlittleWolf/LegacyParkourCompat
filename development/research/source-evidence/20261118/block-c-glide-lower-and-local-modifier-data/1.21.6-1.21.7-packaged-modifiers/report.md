# 1.21.6–1.21.7 packaged movement modifier byte gate

## Exact inputs and binding

This is an offline, read-only inspection of the primary checkout's canonical decompile cache. The exact Mojmap source trees are `decompiled_minecraft/1.21.6/mojmap/` and `decompiled_minecraft/1.21.7/mojmap/`; their `.decompile-provenance.json` manifests identify the original client JAR inputs, official Mojang client mapping inputs, source hashes and the decompiler pipeline. The `Enchantments.java` source hash is `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356` on both sides.

| Release | Canonical cached input | Version binding | Size | Actual client JAR SHA-256 | Cache pin SHA-256 | Result |
|---|---|---|---:|---|---|---|
| 1.21.6 | `.minecraft-decompile-cache/1.21.6/client.jar` | Local `version.json` SHA-256 matches its pin (`ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`) and has `id=1.21.6`; its `downloads.client.url` matches the URL in `client.jar.pin.json` (`740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar`); metadata client SHA-1 equals the URL object hash. | 29,516,555 | `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` | `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` | Exact match |
| 1.21.7 | `.minecraft-decompile-cache/1.21.7/client.jar` | Local `version.json` SHA-256 matches its pin (`b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a`) and has `id=1.21.7`; its `downloads.client.url` matches the URL in `client.jar.pin.json` (`a2db1ea98c37b2d00c83f6867fb8bb581a593e07/client.jar`); metadata client SHA-1 equals the URL object hash. | 29,522,178 | `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9` | `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9` | Exact match |

The official client mapping input digests recorded in the source provenance are 1.21.6 `client.txt` SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` and 1.21.7 `client.txt` SHA-256 `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`. These are the exact inputs recorded by the canonical Mojmap source preparation.

## Bounded source consumer and packaged entries

In the 1.21.6 Mojmap source, `net/minecraft/world/item/enchantment/Enchantments.java` registers the player movement attributes for Depth Strider (lines 347–367, `WATER_MOVEMENT_EFFICIENCY`), Soul Speed (lines 490–555, `MOVEMENT_SPEED` and `MOVEMENT_EFFICIENCY`, with location predicates), and Swift Sneak (lines 558–578, `SNEAKING_SPEED`). `net/minecraft/world/entity/LivingEntity.java` consumes water movement efficiency in water travel (around lines 2305–2311) and movement efficiency for block speed (around lines 485–488). The player owns the sneaking-speed attribute (Player.java around lines 252–263). The corresponding packaged JSON defaults are below.

Entries were streamed from each original JAR using read-only `ZipArchive` access; no archive was extracted and no cache files were modified. Entry SHA-256 covers the uncompressed entry bytes. Equal entry lengths and SHA-256 values establish byte-identical payloads for this bounded comparison.

| Packaged entry | Uncompressed bytes (both) | 1.21.6 and 1.21.7 entry SHA-256 | Byte result | Movement semantics |
|---|---:|---|---|---|
| `data/minecraft/enchantment/depth_strider.json` | 774 | `a19a75083ed4229e1890333b5bfb71639e81c2fda5dbe80aee43251f05d5a934` | Identical | `minecraft:water_movement_efficiency`, `add_value`, `minecraft:enchantment.depth_strider`; linear amount base `0.33333334`, per level above first `0.33333334`; max level 3, feet slot. |
| `data/minecraft/enchantment/soul_speed.json` | 7,312 | `01a3752231e3bacf7d65627f3c7b48349446d35c527a58347e7f28456a247e07` | Identical | Location-changed effects add `minecraft:movement_speed` at base `0.0405`, per level above first `0.0105`, and `minecraft:movement_efficiency` at `1.0`; same active/flying/on-ground/soul-speed-block predicates; max level 3, feet slot. |
| `data/minecraft/enchantment/swift_sneak.json` | 693 | `b5417e7c4e8705c897751ae6d0ed377030216af39c788519aaf00be8d3865956` | Identical | `minecraft:sneaking_speed`, `add_value`, `minecraft:enchantment.swift_sneak`; linear amount base `0.15`, per level above first `0.15`; max level 3, legs slot. |
| `data/minecraft/enchantment/frost_walker.json` | 3,096 | `2a3c9fd51dea59e196f4288df4cc27a83dc80cb4724898e6ea95bd806996d954` | Identical | No movement attribute modifier. The entry has a location-changed `replace_disk` effect creating frosted ice and a damage-immunity effect. Checked as a boundary case; it is an indirect block-world effect, not a direct player movement attribute default. |

## Result and bounds

No packaged byte or semantic difference was found for these defaults between 1.21.6 and 1.21.7. The bounded set covers the direct player movement attributes identified above (including the water movement attribute) and checks Frost Walker's adjacent indirect effect. It does not establish full pair coverage, audit all enchantment/world effects, establish an intermediate release boundary, or make a runtime-parity claim. All inspected cache and source inputs were left unchanged.

