# Bootstrap and utility route-closure correction

This supplement preserves the immutable route-closure report at SHA-256 `F3FBCFF515FE78B40F59B63B03A26C308147490B92B9D960BB6C4AF503704D90` and binds the independent substantive audit at SHA-256 `F5E1E28F81D214A8FC7E47AEA0791EC511981CA1331B0E1A2FB14938E0546008`. The report's conclusion remains supported: these five paths establish no additional local-player movement finding. The audit identified three route-description qualifications.

- The Bootstrap skull/pumpkin dispenser fallback can call `ArmorItem.dispense`, whose nearby-entity search may equip an eligible player's empty HEAD slot. The changed empty-stack result check can affect dispenser success/failure sound bookkeeping after a one-item equip; the installed item and NBT remain the same. This closes the player equipment route as sound-only for the Bootstrap delta.
- `DefaultedList` is reachable through `PlayerInventory` to `PlayerEntity.getEquipment` and `LivingEntity.updateEquipment`, including cached prior equipment. Empty and ordinary non-empty slot behavior preserves the equipment/modifier result. This is a relevant movement consumer but does not establish a new delta; the malformed zero-count stack lead remains separately attributed to item/enchantment behavior.
- `BlockPos.add(Vec3i)` preserves coordinates for a zero vector, but a `Mutable` receiver in 1.11.2 returns an immutable copy where the older method preserves receiver identity. The inspected callers do not reach local-player movement or collision queries.

No full-pair freeze, runtime parity, or unrelated route closure is claimed.
