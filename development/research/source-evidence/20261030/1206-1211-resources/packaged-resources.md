# Pinned default resource supplement: 1.20.6 → 1.21.1

This supplements `coverage.md`; it preserves that report and closes its exact default packaged-resource gate for the assigned enchantments and tags. Scope is the pinned client JAR default data pack and its direct tag-reference closure. This does not cover user/server pack overlays or runtime packet timing.

## Archive and metadata identity

Each client archive was SHA-256 hashed before it was opened. Its neighboring version metadata JSON identifies the same version, gives the same file size, and names the official Mojang client URL and SHA-1.

| Version | Client JAR SHA-256 | Size | Metadata JSON SHA-256 | Metadata client URL / SHA-1 |
|---|---|---:|---|---|
| 1.20.6 | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` | 26,565,641 | `6b597941f4b6ed85890b234683b3ec5b1ddf1092cb42b4773e4ef6130c72b039` | `https://piston-data.mojang.com/v1/objects/05b6f1c6b46a29d6ea82b4e0d42190e42402030f/client.jar` / `05b6f1c6b46a29d6ea82b4e0d42190e42402030f` |
| 1.21.1 | `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99` | 26,836,906 | `5603a965deddb519da59866f3df784ed11c4fef81953d22781e76cebefeac8c3` | `https://piston-data.mojang.com/v1/objects/30c73b1c5da787909b2f73340419fdf13b9def88/client.jar` / `30c73b1c5da787909b2f73340419fdf13b9def88` |

The adjacent metadata `id` values are `1.20.6` and `1.21.1`; each metadata `downloads.client.size` matches the local archive length. Those metadata records bind the archive identity above.

## Exact archive inventory and contents

The old and new clients use plural versus singular registry directory names for tag resources. After following the enchantability tag references, the five directly relevant shared default tag payloads are byte-identical; the SHA-256s below are over the uncompressed entry bytes.

| Payload | 1.20.6 entry | 1.21.1 entry | Bytes | SHA-256 on both sides |
|---|---|---|---:|---|
| Soul Speed support blocks | `data/minecraft/tags/blocks/soul_speed_blocks.json` | `data/minecraft/tags/block/soul_speed_blocks.json` | 74 | `8ba7eac6f7c74d25601ef4455b71e3947fdb8e51b8fafd55dddee48397b144b6` |
| Foot armor enchantability reference | `data/minecraft/tags/items/enchantable/foot_armor.json` | `data/minecraft/tags/item/enchantable/foot_armor.json` | 49 | `849645e1ac79d7b11ad1c06dd370ab00ee05e237ae47634caf829446e1f98d45` |
| Leg armor enchantability reference | `data/minecraft/tags/items/enchantable/leg_armor.json` | `data/minecraft/tags/item/enchantable/leg_armor.json` | 48 | `eb588169cf0b0cf65673b607740792413be94efc19fa9c8ebc84f55626c0f468` |
| Foot armor members | `data/minecraft/tags/items/foot_armor.json` | `data/minecraft/tags/item/foot_armor.json` | 206 | `8d8cc28d2199e4121f5083dd74141ac506b7f1e36937806e65e55078558c0110` |
| Leg armor members | `data/minecraft/tags/items/leg_armor.json` | `data/minecraft/tags/item/leg_armor.json` | 224 | `6b757043346bbf76cf181d9241940277c2608b69b5b6e475e8a6079f6deaab6f` |

The tag closure resolves identically at both endpoints:

- `soul_speed_blocks` contains `minecraft:soul_sand` and `minecraft:soul_soil`.
- `foot_armor` contains leather, chainmail, golden, iron, diamond, and netherite boots. `enchantable/foot_armor` contains `#minecraft:foot_armor`, so it resolves to those same six items.
- `leg_armor` contains leather, chainmail, golden, iron, diamond, and netherite leggings. `enchantable/leg_armor` contains `#minecraft:leg_armor`, so it resolves to those same six items.

There are no `data/minecraft/enchantment/{depth_strider,swift_sneak,soul_speed}.json` entries in the 1.20.6 client JAR; those built-in enchantments are registered in Java source. In 1.21.1, the corresponding built-in enchantment definitions are packaged as these default data resources:

| 1.21.1 entry | Bytes | SHA-256 |
|---|---:|---|
| `data/minecraft/enchantment/depth_strider.json` | 782 | `a20d7566e443b975048df1f3fb3c43578aa560d3e1a76ac4efccf9cc02cb6812` |
| `data/minecraft/enchantment/swift_sneak.json` | 700 | `b418b18fff7928996893cb453a035fe60318d1f41d68380b80863afbed27e390` |
| `data/minecraft/enchantment/soul_speed.json` | 7,488 | `27034fb68ef1acbbda4a42e8867586db7b3968172dbcebb8782c93f7d80e9952` |

The additional `data/minecraft/tags/enchantment/exclusive_set/boots.json` is 81 bytes, SHA-256 `c83340128937b499d2e4becefbce42ca0134fbbf77d551ce3d1ca1adcfb5b5ef`. It contains `minecraft:frost_walker` and `minecraft:depth_strider`; the 1.20.6 static source expresses that incompatibility in the Frost Walker compatibility implementation. This tag does not change the movement response for an already-equivalent equipped enchantment state.

## Default enchantment effects checked against source

- **Depth Strider:** `supported_items` references `#minecraft:enchantable/foot_armor`, slot is `feet`, maximum level is 3, and the `minecraft:attributes` effect adds to `minecraft:generic.water_movement_efficiency` with `linear` base `0.33333334` and `per_level_above_first` `0.33333334`. This matches the 1.21.1 `Enchantments.java` bootstrap registration and the prior accepted travel comparison.
- **Swift Sneak:** `supported_items` references `#minecraft:enchantable/leg_armor`, slot is `legs`, maximum level is 3, and the effect adds to `minecraft:player.sneaking_speed` with `linear` base and per-level increment both `0.15`. This matches the 1.21.1 source registration and the prior accepted consumer/default comparison.
- **Soul Speed:** `supported_items` references `#minecraft:enchantable/foot_armor`, slot is `feet`, and maximum level is 3. Its location-changed movement-speed effect uses base `0.0405` and per-level-above-first `0.0105`; the requirement excludes vehicles and flying, then follows active-enchantment branches requiring the support tag or airborne state. A second location-changed attribute effect adds `1.0` to `minecraft:generic.movement_efficiency` when movement is affected by `#minecraft:soul_speed_blocks`. This tag reference resolves through the byte-identical tag payload above. These values and condition structure match the 1.21.1 `Enchantments.java` bootstrap registration. The 1.20.6 client instead has static Java enchantment logic and has no corresponding enchantment JSON payload.

No disagreement was found between the three 1.21.1 packaged default enchantment definitions and their source bootstrap counterparts. The remaining difference is the already-reported source/consumer transition to attribute-backed effects; this archive comparison adds no separate default-tag or packaged-resource movement finding.

## Closed and still dynamic

For the vanilla client default pack, the checked Soul Speed block tag and both enchantment item-tag inheritance paths are now closed: the tag JSON bytes and resolved memberships match exactly. The 1.21.1 default Depth Strider, Swift Sneak, and Soul Speed JSONs also match the source registrations.

This does not close active datapack/resource-pack overlays. They can replace tag membership and, in 1.21.1, loaded enchantment registry definitions. It also does not close server-produced attribute state or its arrival timing: the old Soul Speed block-factor consumer directly evaluates the local block tag/equipped enchantment while the new consumer reads `MOVEMENT_EFFICIENCY`; the relevant equal-state comparison still requires equal same-tick received `MOVEMENT_SPEED`, `MOVEMENT_EFFICIENCY`, `SNEAKING_SPEED`, and `WATER_MOVEMENT_EFFICIENCY` values. The report supports parity claims for the vanilla default resources under equal effective state, not for arbitrary overlays or unequal state transitions.

Static archive inspection only. No tests, builds, game/server launches, downloads, source generation, or decompilation were performed.
