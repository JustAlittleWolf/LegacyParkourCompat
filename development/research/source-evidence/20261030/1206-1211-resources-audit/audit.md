# Independent resource audit: 1.20.6 → 1.21.1

## Verdict and exact scope

Accepted for the bounded claim that the pinned vanilla client default packs contain equal Soul Speed support-block tags and equal foot/leg armor enchantability tag closures, and that the 1.21.1 default Depth Strider, Swift Sneak, and Soul Speed movement definitions match their 1.21.1 Java bootstrap registrations. The checked 1.20.6 client contains no JSON definitions for these three enchantments; its registrations are Java-only. No separate packaged-resource movement difference is established.

This verdict is bound to the two exact client archives and metadata below. It covers these default-pack payloads and direct tag-reference closure only. It does not claim equality under resource/data-pack overlays, or when the clients have unequal effective tags or received attributes. For movement comparisons involving these effects, the bounded equal-received-state inputs are the effective Soul Speed block-tag result where read, and same-entity/same-tick `MOVEMENT_SPEED`, `MOVEMENT_EFFICIENCY`, `SNEAKING_SPEED`, and `WATER_MOVEMENT_EFFICIENCY`. It does not establish server effect-production equivalence or attribute update/packet timing across block transitions.

## Immutable evidence identity

| Version | Exact archive | Size | SHA-256 | Adjacent metadata SHA-256 | Metadata binding |
|---|---|---:|---|---|---|
| 1.20.6 | `.native-artifacts/pinned-clients/1.20.6.jar` | 26,565,641 | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` | `6b597941f4b6ed85890b234683b3ec5b1ddf1092cb42b4773e4ef6130c72b039` | `id=1.20.6`; client size agrees; official URL SHA-1 `05b6f1c6b46a29d6ea82b4e0d42190e42402030f` |
| 1.21.1 | `.native-artifacts/pinned-clients/1.21.1.jar` | 26,836,906 | `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99` | `5603a965deddb519da59866f3df784ed11c4fef81953d22781e76cebefeac8c3` | `id=1.21.1`; client size agrees; official URL SHA-1 `30c73b1c5da787909b2f73340419fdf13b9def88` |

The 1.20.6 and 1.21.1 Mojmap source files independently checked were `decompiled_minecraft/<version>/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, with SHA-256 `26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381` and `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266`, respectively. They match the canonical source manifest entries.

## Five packaged tag payloads

The legacy plural `tags/blocks` and `tags/items` paths correspond to singular `tags/block` and `tags/item` in 1.21.1. All five uncompressed payloads have equal bytes across archives (equal sizes and SHA-256):

| Payload | 1.20.6 entry | 1.21.1 entry | Bytes | SHA-256 both |
|---|---|---|---:|---|
| Soul Speed support blocks | `data/minecraft/tags/blocks/soul_speed_blocks.json` | `data/minecraft/tags/block/soul_speed_blocks.json` | 74 | `8ba7eac6f7c74d25601ef4455b71e3947fdb8e51b8fafd55dddee48397b144b6` |
| Foot armor enchantability reference | `data/minecraft/tags/items/enchantable/foot_armor.json` | `data/minecraft/tags/item/enchantable/foot_armor.json` | 49 | `849645e1ac79d7b11ad1c06dd370ab00ee05e237ae47634caf829446e1f98d45` |
| Leg armor enchantability reference | `data/minecraft/tags/items/enchantable/leg_armor.json` | `data/minecraft/tags/item/enchantable/leg_armor.json` | 48 | `eb588169cf0b0cf65673b607740792413be94efc19fa9c8ebc84f55626c0f468` |
| Foot armor members | `data/minecraft/tags/items/foot_armor.json` | `data/minecraft/tags/item/foot_armor.json` | 206 | `8d8cc28d2199e4121f5083dd74141ac506b7f1e36937806e65e55078558c0110` |
| Leg armor members | `data/minecraft/tags/items/leg_armor.json` | `data/minecraft/tags/item/leg_armor.json` | 224 | `6b757043346bbf76cf181d9241940277c2608b69b5b6e475e8a6079f6deaab6f` |

The decoded closure is equal: Soul Speed blocks are soul sand and soul soil; `enchantable/foot_armor` references `foot_armor`, whose six members are the leather, chainmail, golden, iron, diamond, and netherite boots; `enchantable/leg_armor` references `leg_armor`, whose six members are the corresponding leggings. The 1.20.6 and 1.21.1 `VanillaBlockTagsProvider` declarations also both add only soul sand and soul soil to `SOUL_SPEED_BLOCKS` (source lines 687 and 732).

## Three 1.21.1 enchantment payloads vs. Java bootstrap

| 1.21.1 entry | Bytes | SHA-256 | Movement definition checked against `Enchantments.java` |
|---|---:|---|---|
| `data/minecraft/enchantment/depth_strider.json` | 782 | `a20d7566e443b975048df1f3fb3c43578aa560d3e1a76ac4efccf9cc02cb6812` | Feet, max level 3, `#minecraft:enchantable/foot_armor`; additive `generic.water_movement_efficiency`, linear base and per-level-above-first `0.33333334`. Matches `Enchantments.java:344-368`, including `LevelBasedValue.perLevel(0.33333334F)`. |
| `data/minecraft/enchantment/swift_sneak.json` | 700 | `b418b18fff7928996893cb453a035fe60318d1f41d68380b80863afbed27e390` | Legs, max level 3, `#minecraft:enchantable/leg_armor`; additive `player.sneaking_speed`, base and per-level-above-first `0.15`. Matches `Enchantments.java:559-582`, including `LevelBasedValue.perLevel(0.15F)`. |
| `data/minecraft/enchantment/soul_speed.json` | 7,488 | `27034fb68ef1acbbda4a42e8867586db7b3968172dbcebb8782c93f7d80e9952` | Feet, max level 3, `#minecraft:enchantable/foot_armor`; location-changed movement-speed effect uses `0.0405` base and `0.0105` per level above first with the same vehicle/flying/active-enchantment/block-or-airborne condition structure; location-changed movement-efficiency adds `1.0` on `#minecraft:soul_speed_blocks`. Matches `Enchantments.java:439-522`, including `perLevel(0.0405F, 0.0105F)` and the conditional `MOVEMENT_EFFICIENCY` effect. |

The 1.20.6 archive has no `data/minecraft/enchantment/{depth_strider,swift_sneak,soul_speed}.json` entries. In its `Enchantments.java` (lines 74-106), they are Java `register` calls using `WaterWalkerEnchantment`, `SoulSpeedEnchantment`, and `SwiftSneakEnchantment` with the corresponding armor tags, levels, and slots. This is consistent with the 1.20.6 static implementation and the absence of JSON definitions; it is not evidence that those enchantments have no behavior.

The additional 1.21.1 `data/minecraft/tags/enchantment/exclusive_set/boots.json` is 81 bytes, SHA-256 `c83340128937b499d2e4becefbce42ca0134fbbf77d551ce3d1ca1adcfb5b5ef`; it contains Frost Walker and Depth Strider. This checked incompatibility relation does not change the movement response for an already-equivalent equipped state.

## Audit method and limits

Both archive hashes were verified before opening them as ZIPs; archive sizes and adjacent metadata IDs, declared sizes, official client SHA-1 values, and metadata-file hashes were checked. Targeted entries were read from each archive, counted and SHA-256 hashed over uncompressed bytes. Parsed payload references and memberships were checked against the matching paths and source declarations. Source registration values and conditions were read directly from the exact Mojmap files above. No download, decompilation, build, test, launch, or product-source edit was performed.
