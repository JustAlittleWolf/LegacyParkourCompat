# 1.21.5 → 1.21.6: modifier lifecycle and consumers

## Source identity and readiness

Host: Windows 11, PowerShell. Canonical source namespace: Mojmap. Read-only source paths are under `decompiled_minecraft/<version>/mojmap/`.

- `1.21.5`: adjacent `mojmap.provenance.json` and `mojmap.ready.json`; ready marker says `status=ready`, exact `versionId=1.21.5`, `mapping=mojmap`, 5,921 source files. The source manifest has 5,921 entries and every listed Java source exists and matches its SHA-256 (0 mismatches). Source-manifest SHA-256: `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; ready-marker SHA-256: `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`; provenance SHA-256: `b0832a4e1507bd74ac300fae2fca78668964b21ef2d05be8cff01b9d1d60da8e`. Exact client SHA-256 from ready marker: `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.
- `1.21.6`: hidden `mojmap/.decompile-provenance.json` records the exact `1.21.6.json` metadata URL, client artifact and client mappings input hashes, Mojmap pipeline, runtime/tool identity, and 6,103 source hashes. All 6,103 listed Java sources exist and match (0 mismatches). Provenance/source-map SHA-256: `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`; client artifact URL ends `/740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar`; mappings URL ends `/848855615bc81e3db1c85e69b6afb150807a1261/client.txt`.

## Bounded source findings

### Effect modifier attach, replacement and removal

No movement-relevant lifecycle difference found. In both releases, `LivingEntity.onEffectAdded` attaches the effect's attribute modifiers only off the logical client; `onEffectUpdated` removes the effect's modifiers and adds them again at the updated amplifier, then refreshes dirty attributes; `onEffectsRemoved` removes each effect's modifiers and refreshes once after the loop. `MobEffect` removes modifiers by ID and adds amplifier-scaled replacements. The order, gates and destination handling match. `MobEffectInstance.java` and `MobEffect.java` are byte-identical between the pair. `AttributeInstance` and `AttributeMap` differ in NBT save/load versus packed serialization, but their modifier add/replace/remove operations are unchanged; this serialization change is outside the assigned lifecycle claim.

- 1.21.5: `net/minecraft/world/entity/LivingEntity.java`, `onEffectAdded` 1019–1025, `onEffectUpdated` 1035–1047, `onEffectsRemoved` 1049–1065, `refreshDirtyAttributes` 1067–1075; `net/minecraft/world/effect/MobEffect.java`, `removeAttributeModifiers` / `addAttributeModifiers` 155–174.
- 1.21.6: same file/member order at lines 1044–1050, 1060–1072, 1074–1090 and 1092–1100; `MobEffect.java` remains at lines 155–174.

### Equipped glider and equipment modifier lifecycle

No player-glider modifier delta found. `Items.ELYTRA` is byte-identical: the item has the `GLIDER` component and an `EQUIPPABLE` chest-slot component, with no attribute modifier declaration. `LivingEntity.canGlideUsing` is byte-identical and requires the GLIDER component, its configured equipment slot, and that the next damage does not break it.

The general equipment refresh sequence is also textually identical: when an old slot stack is replaced, stop its location-based effects; for each new nonempty, unbroken stack, remove any same-ID transient modifier then add the new modifier; finally run that stack's location-changed enchantment effects on the server. Breaking equipped gear removes its modifiers and stops those effects. This preserves destination attribute IDs and operation ordering. The 1.21.6 harness/Happy Ghast additions are non-player equipment behavior and do not establish a player movement change.

- 1.21.5: `net/minecraft/world/entity/LivingEntity.java`, `detectEquipmentUpdates` / equipment collection around 2550–2630; `onEquippedItemBroken` and `stopLocationBasedEffects` 3464–3475; `canGlideUsing` 3622–3629; `net/minecraft/world/item/Items.java`, `ELYTRA` 989–1005.
- 1.21.6: corresponding lines 2600–2680, 3520–3531, 3678–3685 and `Items.java` 1040–1056.
- Soul Speed's location-changed effect is present in both `net/minecraft/world/item/enchantment/Enchantments.java` around 507–519: it adds `MOVEMENT_SPEED` (`ADD_VALUE`, `perLevel(0.0405F, 0.0105F)`) and `MOVEMENT_EFFICIENCY` (`ADD_VALUE`, constant `1.0F`). The declaration and `EnchantmentAttributeEffect` source are byte-identical across the pair.

### Direct player movement-attribute consumers

No source difference found in the examined player movement consumers: `Player.getSpeed` returns the `MOVEMENT_SPEED` attribute as float; the player movement-speed baseline is set from abilities; crouch input reads `SNEAKING_SPEED`; `LivingEntity.getBlockSpeedFactor` interpolates block factor using `MOVEMENT_EFFICIENCY`; jump power uses `JUMP_STRENGTH`, block jump factor and the Jump Boost effect; gravity reads `GRAVITY`; water travel reads `WATER_MOVEMENT_EFFICIENCY`; sprinting adds/removes the same movement-speed modifier. The paired source expressions are unchanged. The 1.21.6 `LocalPlayer` input-state edits were not assessed as state/input behavior; only its `SNEAKING_SPEED` consumer was checked.

References: `net/minecraft/world/entity/player/Player.java` 569, 783, 1470–1471 (1.21.5) / 579, 792, 1482–1483 (1.21.6); `net/minecraft/client/player/LocalPlayer.java` 634 on both; `net/minecraft/world/entity/LivingEntity.java` 473–494, 2101–2111, 2160–2168, 2205, 2210–2258 and 2446–2450 (1.21.5), corresponding movement expressions at 487–508, 2133–2143, 2192–2200, 2237, 2242–2309 and 2498–2502 (1.21.6).

### Enchantment-default coverage boundary

`Enchantments.java` and `EnchantmentAttributeEffect.java` are canonically present and byte-identical; the source-defined Soul Speed movement effect above is covered. No data/resource JSON defaults are present in either canonical decompilation tree (the only `.json` files are provenance metadata), so no claims are made for resource-defined enchantment defaults. `ElytraItem.java` is absent on both sides, but this is not a missing-source blocker: the exact item setup is in `Items.java`, and glide eligibility is in `LivingEntity.canGlideUsing`.

## Hashes for examined source payloads

| Relative source file | 1.21.5 SHA-256 | 1.21.6 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/effect/MobEffect.java` | `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66` | same |
| `net/minecraft/world/effect/MobEffectInstance.java` | `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36` | same |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `6e557daca10617e6a8f4af17d6436a8d1165cd175048e98ea795629a274c44cb` | `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `60651acdcd2cc66c590b1203978b26e2b253892861f1156004d5040d728210bf` | `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079` |
| `net/minecraft/world/item/Items.java` | `d258c31e88d6e7208b7c196a7b7408172f7c54050e11442e54894a1d31f4f5d5` | `fe3b56b3f1949467eaa496522b37a630adf6c3b973c468a8e58aedeea5480d59` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356` | same |
| `net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java` | `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270` | same |
| `net/minecraft/world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/client/player/LocalPlayer.java` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
