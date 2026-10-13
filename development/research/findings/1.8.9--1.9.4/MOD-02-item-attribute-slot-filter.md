# MOD-02: equipment attribute NBT slot filtering differs

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/item/ItemStack.java`, `public Multimap<String, AttributeModifier> getAttributeModifiers() {`, lines 667-687.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/item/ItemStack.java`, `public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {`, lines 700-721.

Difference: For the stated NBT precondition, 1.8.9 accepts the modifier based on its attribute name and valid UUID without considering its declared equipment slot. 1.9.4 applies the modifier only for the matching equipment slot (or when no slot is declared).

Since player movement consumes `generic.movementSpeed`, this changes the attribute input when such an item is equipped.

The source establishes the conditional code path, not the presence of a matching item in a particular map.

Movement meaning: The player equipment update path reaches the ItemStack modifier parser while equipping or refreshing equipment. 1.8.9 custom or otherwise NBT-defined item with the specified movement-speed modifier and mismatched `Slot` value satisfies the parser condition.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements MOD-02: equipment attribute NBT slot filtering differs; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/item/ItemStack.java",
      "member": "public Multimap<String, AttributeModifier> getAttributeModifiers() {",
      "lines": "667-687",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 32917e0c486e8ba2a2dc9f91545c4f2a95c78e3a341f3ba1340993efac99c360; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 9e63a98f55057f8dc16f4e254a1650d31ef722a4d6eee422529e64e8051b86b3."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/item/ItemStack.java",
      "member": "public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {",
      "lines": "700-721",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9e031b6f4e06c608aa844c6b3eb1d6ec0875a7ebbb8d78ecb34404b42945e692; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5be9777e8e04eb96e60387471693a194df9ded229c8ae69746ec40cd72214feb."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-MOD-02-item-attribute-slot-filter is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-MOD-02-item-attribute-slot-filter",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for MOD-02-item-attribute-slot-filter",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
