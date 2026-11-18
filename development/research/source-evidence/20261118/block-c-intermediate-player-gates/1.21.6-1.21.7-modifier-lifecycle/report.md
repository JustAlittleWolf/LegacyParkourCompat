# 1.21.6 → 1.21.7 modifier lifecycle lane

## Source identity and readiness

Canonical decompiled Java sources are present at `decompiled_minecraft/1.21.6/mojmap/` and `decompiled_minecraft/1.21.7/mojmap/`. Both contain `.decompile-provenance.json`; the root writer-lock file was not held during inspection. The provenance manifests identify the exact Mojang client and official mapping-text inputs:

| Release | Mapping family | Client SHA-256 | Mapping text SHA-256 | Release metadata SHA-256 | Manifest SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.21.6 | Mojmap | `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` | `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` | `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd` | `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` |
| 1.21.7 | Mojmap | `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9` | `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50` | `b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a` | `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` |

Each manifest's selected relevant source hashes were recomputed and matched. Manifest source inventories list 6,103 Java files for 1.21.6 and 6,104 for 1.21.7. The canonical trees contain no packaged non-Java data/resource files apart from the provenance manifest, so movement-enchantment packaged defaults are unavailable in this source publication and were not inferred from data-generator Java.

## Bounded comparison

**Effect modifier attach, replace, remove, and affected attributes — no change found.** `LivingEntity.addEffect` inserts a new instance before `onEffectAdded`; a replacement first updates the existing instance and only calls `onEffectUpdated` when that update reports a change (`LivingEntity.java:978–999`). `forceAddEffect` writes the replacement before its callback (`1012–1021`). Removal takes the instance out of `activeEffects`, then invokes `onEffectsRemoved` (`1030–1042`). On the logical server, the add callback attaches the effect's attribute modifiers; update removes old effect modifiers then adds the updated amplifier's modifiers before dirty-attribute refresh; removal removes modifiers and refreshes dirty attributes (`1044–1089`). All these source bytes match between releases. `MobEffect.java` and `AttributeMap.java` also match, including modifier destination and removal by modifier ID.

**Equipped-item modifier refresh and glider path — no change found.** `LivingEntity.detectEquipmentUpdates` collects changed slots; for old nonempty stacks it stops location-based effects, then for each changed nonempty/nonbroken new stack iterates slot modifiers, removes a same-ID modifier from the destination `AttributeInstance`, and adds the new modifier as transient (`2626–2673`). This is the observed generic equipment modifier path; Java source does not define a glider-specific movement attribute modifier. Glider eligibility is a direct predicate: the stack must have `GLIDER`, its `EQUIPPABLE` slot must match, and the stack must not be about to break (`canGlide:2893–2905`, `canGlideUsing:3678–3685`). These methods and `ItemStack.forEachModifier` match byte-for-byte in both releases. No conclusion is drawn about equipment data defaults absent from the canonical tree.

**Direct player movement attribute consumers — no change found.** The selected player movement paths and destination attribute definitions are unchanged: `Player.java:579, 792, 1483` reads/sets `MOVEMENT_SPEED`; `LivingEntity.java:2133–2140` refreshes the sprint modifier by remove-then-add, `2192–2202` reads `JUMP_STRENGTH`, and `2235–2243` reads `GRAVITY` and applies slow-falling's cap. `Attributes.java`, `Player.java`, `LivingEntity.java`, `Entity.java`, and `LocalPlayer.java` have identical SHA-256 values across the pair. `AttributeInstance` also retains the same duplicate-ID rejection, remove, transient add, and permanent replace operation ordering.

Movement-enchantment helper Java is present and unchanged (`EnchantmentHelper.java`, `Enchantments.java`); no packaged enchantment data files are present to compare. Non-player inventory/equipment behavior is outside this lane and is not claimed as audited.

## Evidence hashes

The following relevant canonical source hashes are identical on both sides and matched their respective provenance manifests:

- `net/minecraft/world/entity/LivingEntity.java` — `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`
- `net/minecraft/world/entity/player/Player.java` — `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`
- `net/minecraft/world/entity/ai/attributes/AttributeMap.java` — `06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079`
- `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` — `c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346`
- `net/minecraft/world/entity/ai/attributes/Attributes.java` — `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020`
- `net/minecraft/world/effect/MobEffect.java` — `a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66`
- `net/minecraft/world/effect/MobEffectInstance.java` — `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36`
- `net/minecraft/world/item/ItemStack.java` — `f454d70486abdd066be5d5dc89c0d5b61ec6920fb702fe397d3d12bf902936c2`
- `net/minecraft/world/item/enchantment/EnchantmentHelper.java` — `b1c8ce1ef431d57488b4c6f991aa01e9d8372adaa077df8ff55a419d9b9448a0`
- `net/minecraft/world/item/enchantment/Enchantments.java` — `ee37699b95dc530fdd91dadef27d19a54bc7c4febe79bca40cc1ee438a626356`