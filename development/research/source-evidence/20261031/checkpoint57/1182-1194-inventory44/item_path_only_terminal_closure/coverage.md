# Item and inventory path-only terminal closure

This separate artifact closes the rows marked `PATH_ONLY_MEMBER_UNREVIEWED` or `MENU_OPERATION_PATH_ONLY` in the assigned item/inventory roster. It does not rewrite that roster, reopen the accepted Elytra/Soul Speed review, or claim pair freeze.

## Coverage

- 247 path/edge rows across 155 paths.
- Edge counts: 1.18.2→1.19: 65; 1.19→1.19.1: 6; 1.19.2→1.19.3: 78; 1.19.3→1.19.4: 98.
- Terminal dispositions: 125 no movement dependency; 120 excluded server/state production; 2 direct movement consumer rows (SuspiciousStewItem).
- All row source hashes matched both the frozen row roster and the exact source manifest (0 failures).
- All six Mojmap roots report ready and have `mojmap.ready.json` source-manifest digests matching `mojmap.sources.sha256` (0 failures). Details: `manifest-validation.tsv`.
- Movement-relevant anchor source file hashes and manifest checks: `source-anchor-hashes.tsv` (0 failures).

## Routes and dispositions

`SuspiciousStewItem.finishUsingItem` directly updates effect state on the local player: stored `Effects` entries are resolved through `MobEffect.byId`, converted to `MobEffectInstance` using stored duration or default 160, then passed to `LivingEntity.addEffect`. The 1.18.2 implementation writes and reads `EffectId` as a byte. 1.19+ uses an int; 1.19.3 extracts the same list resolution and add sequence into `listPotionEffects`. For vanilla effect IDs in the compared releases, the type change preserves the resolved effect and the movement-state write. This is a direct consumer route, but no movement-affecting delta was found.

Inventory menu click/quick-move members write slot contents through `Menu`/`Slot.setByPlayer` or container methods. In 1.19 and 1.19.4, equipment slot callbacks route through `InventoryMenu.onEquipItem` to `LivingEntity.onEquipItem`; the changed callback path adds equip sound and the armor EQUIP game event. The slot storage remains the actual inventory writer; no changed callback math or tick movement write was found. Ordinary equipment values are covered separately by the previously accepted Equipable use operation.

`PotionItem.finishUsingItem` applies effects only under `!Level.isClientSide`; potion `useOn` mutates mud block state. `ChorusFruitItem.finishUsingItem` calls `randomTeleport` on the server side. Remaining item/crafting changes feed vanilla block/fluid/entity/item state, non-player/vehicle output, inventory/crafting output, metadata, tooltip or GUI behavior. Each row names the changed member group and caller/sink in `path-dispositions.tsv`.

## Limits

This is a bounded item/inventory terminal source review, not an independent pair-wide audit. It leaves pair freeze unclaimed and does not repeat the accepted Elytra/Soul Speed operation, known movement-use methods, ItemStack four-member review, or duplicate server/state producer audits. No product edits, backlog writes, builds, tests, launches, or source generation were performed.
