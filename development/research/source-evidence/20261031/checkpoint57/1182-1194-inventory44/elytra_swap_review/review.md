# Independent review: Elytra chest equipment swap movement route

**Verdict: REVISE.** The source route and movement consequence are supported, and all ten cited-file hashes match the local Mojmap roots. Correct one 1.19.4 method range and tighten the invocation conditions before treating the draft as accepted.

## Verified route

- 1.19.3 `ElytraItem.use` checks the target equipment slot and, only when empty, copies the held Elytra into it and consumes the held stack. A nonempty chest slot returns `fail` without mutation.
- 1.19.4 `ElytraItem.use` delegates to `Equipable.swapWithEquipmentSlot`. The helper first obtains held/equipped stacks; when the equipped item has no binding curse and differs from held, it writes held-stack copy into the equipment slot. For an occupied slot, it writes the previous equipment item back to the hand. The slot and hand mutations occur before the client-side statistic gate and therefore run on both sides.
- Both `MultiPlayerGameMode.useItem` methods invoke the held `ItemStack.use` in the local prediction callback; the comparison `$$7 != $$5` then applies a returned hand stack if the item returned a different object. The 1.19.4 helper also writes the hand itself when swapping.
- In both LocalPlayer versions, the jump-to-Elytra check reads `EquipmentSlot.CHEST`, checks item type/damage, and calls `tryToStartFallFlying`; its return triggers a START_FALL_FLYING command. Player’s method checks airborne/non-fall-flying/non-water/no-Levitation, then directly sets shared flag 7 through `startFallFlying`. LocalPlayer calls `super.aiStep` afterward: line 814 in 1.19.3, line 795 in 1.19.4. Thus LivingEntity travel in that same tick sees the local fall-flying flag and selects the Elytra branch.

## Corrections needed

1. The 1.19.4 `Player.tryToStartFallFlying` body is lines **1570–1580**. Lines 1582–1584 are `startFallFlying`, not the end of `tryToStartFallFlying`. Cite the setter separately as 1582–1584.
2. Constrain the item-use invocation to `MultiPlayerGameMode.useItem`'s non-spectator, non-cooldown path (method lines 350–361 and 365–369). These are ordinary gates, but the current draft describes the call without mentioning them.
3. For the same-tick route, cite `LocalPlayer.aiStep`'s post-jump `super.aiStep` call (1.19.3 line 814; 1.19.4 line 795). This makes explicit why travel reads the newly set flag before returning to the outer game tick.

The draft’s conditions for the following jump/movement are otherwise sound. The server may reject or overwrite locally predicted equipment/flags, so its stated conditional overwrite caveat is appropriate; no server production claim is established.

Hash verification: all entries in `elytra-use-swaps-chest-equipment-source-hashes.tsv` exactly match the six versioned source files.

No build, test, launch, or external library execution was used.
