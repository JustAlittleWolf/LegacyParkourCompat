# MOD-02: equipment attribute NBT slot filtering differs

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: equipment-provided movement attribute modifiers; `MOD-02`
- Classification: conditional, source-confirmed movement-attribute input change
- Confidence: source-confirmed parser and equipment-update difference; no concrete map item data established
- Applicability: a player-equipment `ItemStack` has an `AttributeModifiers` NBT entry for `generic.movementSpeed` with a `Slot` value that differs from the slot being equipped
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `ItemStack.getAttributeModifiers()` (lines 667-687) parses each NBT entry and adds any valid modifier under its `AttributeName`; it never reads the `Slot` key. Its SHA-256 is `32917e0c486e8ba2a2dc9f91545c4f2a95c78e3a341f3ba1340993efac99c360`.
- B `ItemStack.getAttributeModifiers(EquipmentSlot)` (lines 700-721) adds a valid NBT modifier only when `Slot` is absent or equals the requested slot key (lines 709-713). `addAttributeModifier` writes the optional slot key (lines 723-739). Its SHA-256 is `9e031b6f4e06c608aa844c6b3eb1d6ec0875a7ebbb8d78ecb34404b42945e692`.
- A `LivingEntity` equipment update compares five equipment entries and removes/reapplies modifiers with the unfiltered no-slot method (lines 1274-1287); SHA-256 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`. B iterates `EquipmentSlot.values()` and passes the current slot to remove/apply modifiers (lines 1505-1536); SHA-256 `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.
- Player movement registers the same `0.1F` base speed in both versions (A `PlayerEntity` line 134; B line 155) and exposes the movement-speed attribute to its movement-speed consumer (A line 1299; B line 1394). PlayerEntity hashes: A `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`; B `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- The other bounded MOD-02 paths were compared: sprint modifier UUID/value/operation and `setSprinting`, Speed and Slowness effect modifier formulas and application/removal lifecycle, Jump Boost impulse, and normal four-armor-slot Depth Strider lookup/friction match. The `getSpeed() * 1.0F` versus `getSpeed()` water-acceleration expression is preserved as an exact source difference with no finite-value behavior claim. These paths do not broaden this finding.

## Source-level difference

For the stated NBT precondition, A accepts the modifier based on its attribute name and valid UUID without considering its declared equipment slot. B applies the modifier only for the matching equipment slot (or when no slot is declared). Since player movement consumes `generic.movementSpeed`, this changes the attribute input when such an item is equipped. The source establishes the conditional code path, not the presence of a matching item in a particular map.

## Reachability and uncertainty

The player equipment update path reaches the ItemStack modifier parser while equipping or refreshing equipment. A custom or otherwise NBT-defined item with the specified movement-speed modifier and mismatched `Slot` value satisfies the parser condition. No map inventory, plugin, or command-generated item was inspected, so map-specific prevalence and resulting movement trajectory are unknown.

## Handoff

Source finding only. Keep the application conditional until a source-backed map item or an implementation-side input contract establishes reachability for a target map.
