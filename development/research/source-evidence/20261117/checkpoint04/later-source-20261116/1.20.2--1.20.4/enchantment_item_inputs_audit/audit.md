# Independent audit: 1.20.2 → 1.20.4 enchantment item inputs

**Verdict: ACCEPT — bounded Java source claims verified.** This does not close the packaged-resource membership gate or freeze the pair.

Audited immutable input `build/later-source-20261116/1.20.2--1.20.4/enchantment_item_inputs/coverage.md`, SHA-256 `9143963851CFDCF69DBE801D0221EC2A30684AEC5B9A802A70547DF163855950`.

## Identity and cited files

For both exact Mojmap source trees, the root-sidecar provenance hashes, ready marker hashes, and `mojmap.sources.sha256` hashes agree with the report. Ready identities resolve to the expected releases and Mojmap namespace. Each of the nine cited Java paths was hashed from the source tree and its digest matched the corresponding source-manifest entry and report table. Eight are byte-identical across these endpoints; `ItemStack.java` has the separately reported endpoint hashes. In particular, `EnchantmentHelper.java`, `Enchantment.java`, `EnchantmentCategory.java`, `Enchantments.java`, `WaterWalkerEnchantment.java`, and `SoulSpeedEnchantment.java` are identical across the pair, making their identical bounded behavior well-grounded.

## Source checks

- `EnchantmentHelper.getDepthStrider(LivingEntity)` forwards to `getEnchantmentLevel(Enchantments.DEPTH_STRIDER, entity)`. The generic helper enumerates the configured slot map and keeps the maximum item enchantment level.
- `Enchantment.getSlotItems` visits each configured `EquipmentSlot`, obtains `LivingEntity.getItemBySlot`, and includes nonempty stacks. `DEPTH_STRIDER` is constructed with `ARMOR_SLOTS`, whose values are HEAD, CHEST, LEGS, FEET. `SOUL_SPEED` is constructed with `EquipmentSlot.FEET` only.
- Per-stack lookup scans the stack's NBT `Enchantments` list, matches the enchantment registry ID, and returns the stored level (the compound level is clamped to 0..255). It does not recheck `canEnchant` or use an item tag. The helper class has another `canEnchant` use in the separate enchanting-offer path; that does not occur in the item-level or entity-level lookup under review.
- `WaterWalkerEnchantment` and `SoulSpeedEnchantment` both select `ARMOR_FEET`; that category's enchanting predicate accepts an `ArmorItem` with declared slot FEET. This eligibility predicate is distinct from lookup's configured slot enumeration, so the report correctly distinguishes Depth Strider's four-slot runtime read from feet-only category eligibility.
- The report's remaining packaged dependency is `data/minecraft/tags/blocks/soul_speed_blocks.json` for `minecraft:soul_speed_blocks`. This audit does not establish resource contents or archive membership. The resource gate and full pair coverage remain open as the report says.

I found no factual defect in the bounded Java lookup/eligibility claims or source identities. No source, product, backlog, or Git files were changed.
