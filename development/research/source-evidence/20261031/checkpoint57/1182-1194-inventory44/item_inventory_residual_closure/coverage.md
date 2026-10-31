# Item and inventory residual closure: 1.18.2–1.19.4

Scope was exact changed `net/minecraft/world/item/**` and `net/minecraft/world/inventory/**` paths across adjacent edges. The frozen source roster contains 263 changed path/edge rows and 163 unique paths. Per-row old/new SHA-256 values and honest member dispositions are in `path-dispositions.tsv`.

## Member-level findings

A new movement-relevant shared item-use operation is in draft at `../findings/1.19.3--1.19.4/elytra-use-swaps-chest-equipment.md`. It compares `ElytraItem.use` and `ArmorItem.use` with the new `Equipable.swapWithEquipmentSlot` helper, called by `MultiPlayerGameMode.useItem` on the client.

Two direct movement witnesses are recorded in the draft:

- Elytra can replace an occupied, non-binding-cursed chest item; a later airborne jump reads the local equipped Elytra, `Player.tryToStartFallFlying` sets shared flag 7 locally, and movement takes the fall-flying branch.
- Soul Speed boots can replace occupied, non-binding-cursed boots; the unchanged client `LivingEntity.getBlockSpeedFactor` reads the worn enchantment and `SOUL_SPEED_BLOCKS` tag. On soul sand it returns 1.0 instead of the block's 0.4 factor. Unchanged `Entity.move` multiplies delta movement by that factor.

This is one shared equip-use operation with two witnesses, not two findings. The Soul Speed attribute-modifier writer is server-gated and is not part of the direct consumer claim. The movement draft was broadened after the first review, so independent acceptance is pending renewal on the final digest.

Other reviewed movement-related item members: `FireworkRocketItem.use` is unchanged in the compared member bodies; the changed firework members cover tooltip text, duration setup, and shape-ID lookup. `EnchantmentHelper` changes add the modern-only Swift Sneak bonus and otherwise include registry/API changes or a Silk Touch accessor; no other era-existing movement enchantment formula changed in the inspected deltas. `Potions` deltas are registry/API changes. `FrostWalkerEnchantment.onEntityMoved` produces world block state and is excluded.

The inspected 1.19.4 menu refactor routes slot writes through `Slot.setByPlayer`; the new `InventoryMenu.onEquipItem` callback emits equip sound/game events. No movement math is called in those changed member bodies. The broader inventory/menu member set is not terminally closed.

## Coverage counts and residuals

- 263 changed row records, 163 unique path names.
- 13 path/edge rows in the disposition table identify movement-relevant candidate or checked helper/member changes (candidate pending independent review).
- 8 rows across three inventory/menu files are bounded to their slot/equip refactor; no direct movement call was found there.
- 239 rows across 152 unique paths remain path-only: their member bodies were not terminally inventoried in this lane. The row-level list in `path-dispositions.tsv` is the exact residual set.
- Source identity/hashes for the six roots and full changed-path roster are bound by the parent inventory: `whole-source-path-inventory.md` SHA-256 `35A963C902A62A7942EEBB6395CFB6B936FDCF36338B535997ACE023F9BB3816`; frozen independent roster SHA-256 `C81BA2576795545D3B078BCA88C510415246FE12B7286FD9DA166DE937168954`.
- Exact hashes for the movement candidate, callers, consumers, vanilla Soul Speed tag/provider, and soul-sand factor are in the adjacent `elytra-use-swaps-chest-equipment-source-hashes.tsv` (the same-named draft is in the findings directory).

This closes only the assigned item/inventory slice where the member dispositions say so. It does not claim full pair freeze or full member-level closure for all 163 paths. No build, tests, game launch, product/wiki/backlog edits, or source generation were performed.
