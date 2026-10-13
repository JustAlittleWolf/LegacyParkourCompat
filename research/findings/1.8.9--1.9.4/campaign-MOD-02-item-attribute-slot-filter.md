# MOD-02: equipment attribute NBT slot filtering differs

- **1.8.9**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/MOD-02-item-attribute-slot-filter.md); lines 667-687.
- **1.9.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/MOD-02-item-attribute-slot-filter.md); lines 700-721.

## Difference

For the stated NBT precondition, 1.8.9 accepts the modifier based on its attribute name and valid UUID without considering its declared equipment slot. 1.9.4 applies the modifier only for the matching equipment slot (or when no slot is declared).

Since player movement consumes `generic.movementSpeed`, this changes the attribute input when such an item is equipped.

The source establishes the conditional code path, not the presence of a matching item in a particular map.

## Movement meaning

The player equipment update path reaches the ItemStack modifier parser while equipping or refreshing equipment. 1.8.9 custom or otherwise NBT-defined item with the specified movement-speed modifier and mismatched `Slot` value satisfies the parser condition.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/MOD-02-item-attribute-slot-filter.md).
