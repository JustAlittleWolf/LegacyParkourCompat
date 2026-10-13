# MOD-02: equipment attribute NBT slot filtering differs

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/item/ItemStack.java`, `public Multimap<String, AttributeModifier> getAttributeModifiers() {`, lines 667-687.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/item/ItemStack.java`, `public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {`, lines 700-721.

Difference: For the stated NBT precondition, 1.8.9 accepts the modifier based on its attribute name and valid UUID without considering its declared equipment slot. 1.9.4 applies the modifier only for the matching equipment slot (or when no slot is declared).

Since player movement consumes `generic.movementSpeed`, this changes the attribute input when such an item is equipped.

The source establishes the conditional code path, not the presence of a matching item in a particular map.

Movement meaning: The player equipment update path reaches the ItemStack modifier parser while equipping or refreshing equipment. 1.8.9 custom or otherwise NBT-defined item with the specified movement-speed modifier and mismatched `Slot` value satisfies the parser condition.

Original evidence: [MOD-02-item-attribute-slot-filter.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/MOD-02-item-attribute-slot-filter.md).
