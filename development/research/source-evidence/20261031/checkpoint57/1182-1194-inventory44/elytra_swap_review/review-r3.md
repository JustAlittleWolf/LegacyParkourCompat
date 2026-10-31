# Independent review r3: combined Equipable movement finding

**Verdict: REVISE** for draft SHA-256 `556D02529A8B94A19C6FA0D430130120C1911158012511F91C17D35C47099069` and combined source-ledger SHA-256 `2E7109F0F4B58C3D919A48507C3DAA8DAE7840F1FE1FB8B1432BB92AE28E2931`. All 23 ledger entries match their exact local Mojmap source files. The Elytra witness remains supported, but the added ArmorItem/Soul Speed witness needs two applicability/source clarifications before the combined finding is accepted.

## Elytra witness

The old Elytra path only installs into an empty equipment slot and otherwise fails. The 1.19.4 path uses `Equipable.swapWithEquipmentSlot`, which replaces a different, non-binding-cursed chest item, writes a copy of the held Elytra into the equipment slot and returns a copy of the displaced item to the hand. `MultiPlayerGameMode.useItem` invokes `Item.use` on the local client player when not spectator and not on cooldown. On a later eligible airborne jump, LocalPlayer reads the new chest Elytra; `Player.tryToStartFallFlying` sets shared flag 7 locally before `super.aiStep`, so same-tick travel sees the fall-flying branch. The previous Elytra-only hash is not relied on; this route was checked against the combined source ledger.

## ArmorItem/Soul Speed witness

The item route is correct: 1.19.3 `ArmorItem.use` equips only into an empty target slot and fails if occupied; 1.19.4 delegates to the same shared `Equipable.swapWithEquipmentSlot` operation. `LivingEntity.getBlockSpeedFactor` checks `SOUL_SPEED_BLOCKS` at the movement support position and returns `1.0F` when any equipped Soul Speed enchantment is found. The exact vanilla tag providers include both Soul Sand and Soul Soil; both versions register Soul Sand with `speedFactor(0.4F)`. The fallback to `Entity.getBlockSpeedFactor` uses the block's speed factor, including the movement-support block when the entity's current block has the neutral factor. `Entity.move` then scales stored horizontal delta movement by the returned factor in both versions, without a logical-side guard. This yields a genuine movement velocity difference after a local equipment swap.

## Corrections needed

1. State that the previously equipped boots have no Soul Speed enchantment. As drafted, the prior boots could already have Soul Speed, in which case the old and new movement factors may match.
2. Cite the base fallback calculation as well as the Soul Speed override: `Entity.getBlockSpeedFactor` (1.19.3, 748–756; 1.19.4, 750–758). Clarify that `Entity.move` applies the horizontal multiplier to stored delta movement after the current move, so the resulting velocity changes subsequent horizontal movement. The existing `Entity.move` citations 657–659 and 659–661 are correct.

The existing conditional no-overwrite caveat is appropriate. No server-side item-use, enchantment, or flight-state production is inferred. No builds, tests, launches, or library execution were performed.
