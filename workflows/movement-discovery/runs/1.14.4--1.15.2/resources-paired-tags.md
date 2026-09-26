# Paired block/fluid tag inventory

Input jars: A 1.14.4 SHA-256 `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`; B 1.15.2 SHA-256 `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`. The inventories were read directly from both original client jars after independently verifying A's hash against its owner's provenance.

## Inventory result

- A contains 52 `data/minecraft/tags/blocks/*.json` and `.../tags/fluids/*.json` entries; B contains 58.
- 51 entry paths are common. Fifty have identical uncompressed bytes. The only common entry whose bytes changed is `data/minecraft/tags/blocks/bamboo_plantable_on.json`.
- A-only entry: `data/minecraft/tags/blocks/dirt_like.json` (SHA-256 `CBEDCEDB203119E9312A8FBA25E282DDA4ED7C1E73121AE5F8746232B8B73DDE`). Its values are dirt, grass block, podzol, coarse dirt and mycelium.
- B-only entries: `bee_growables.json`, `beehives.json`, `crops.json`, `flowers.json`, `portals.json`, `shulker_boxes.json`, and `tall_flowers.json`, all under `data/minecraft/tags/blocks/`.

## Relevant unchanged values

| Entry | A SHA-256 | B SHA-256 | Result |
|---|---|---|---|
| `data/minecraft/tags/blocks/ice.json` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | `801D74E956B208F0C5106D6FDFEF47D249145948793EAE454C3BEF80B7C8D2A6` | Same four ice blocks |
| `data/minecraft/tags/fluids/water.json` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | `698E1662335B6241879B380A58D478EE019A1E789362B004050B5CCAC421AD18` | Same still/flowing water values |
| `data/minecraft/tags/fluids/lava.json` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | `F3A67622F1F6A4C69E7792F1E3731B04236962B5BE1821671ACA0D1B59C316DD` | Same still/flowing lava values |

## Changed and new entries

- `bamboo_plantable_on.json`: A SHA-256 `C906DD6EAF99018B42E85D4DBD99D2125E56B9A139E7E6BD1980C8FE0B697401`; B SHA-256 `9D806316F075528EC519BC9DAAEA7B886DA346162854C17A611F83783C3F51C4`. A expresses dirt-like substrates through `#minecraft:dirt_like`; B expands the same five values inline. The only consumers found in the versioned Java trees are `BambooBlock` and `BambooSaplingBlock` support/growth checks. This changes tag representation, not the accepted substrate set. No difference in player movement is established by this tag delta.
- `data/minecraft/tags/blocks/portals.json`: B SHA-256 `FECDF2F3A61C8521647BB59802F4ECC18569D4B86786700499AC11E5718F80A9`; values are Nether portal, End portal and End gateway. A has no such resource. B consumes `BlockTags.PORTALS` in `LivingEntity.findStandUpPosition` during server-side dismount handling; see finding `F06`.
- `bee_growables.json`: B SHA-256 `9C4180B3A1015D700E15D5BBD699A19A46F4FE759BD31CE4C9EA92860DEEAC80`; values are crops and sweet berry bush.
- `beehives.json`: B SHA-256 `8B9102847F605DD72AA69DD6B7E4BCFFDCA25F385FD2F9FFD8EF862A28A18415`; values are bee nest and beehive.
- Bee-related tags are consumed by the new Bee entity's target/hive behavior. Bee is inspected only for its attack effect on player velocity; its independent motion is outside scope. See finding `F07`.
- Remaining B-only crop, flower and shulker-box tag values are not movement mechanics on their own. Their consumers must be considered only if they form a dependency of player movement or an external player velocity/position update.

## Scope note

These resources are datapack inputs. Identical values or missing files alone do not establish a Java behavior conclusion. Findings cite their consumer, registration and the exact jar entries where those data values matter.

