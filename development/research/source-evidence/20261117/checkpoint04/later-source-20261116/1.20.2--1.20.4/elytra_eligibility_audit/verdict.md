# Independent source audit verdict: Elytra eligibility

Candidate report: `build/later-source-20261116/1.20.2--1.20.4/elytra_eligibility/report.md`
Candidate SHA-256: `f1924cd74fb8d0164dd486d82b6629f01c4de29cde92286ef6099cff4db7dcfb`

## Verdict

**Accepted for the bounded endpoint claim: no source difference found in the assigned Elytra item/slot registration, enabled predicate, Player start-fall-flying gate and flag transition, LivingEntity flight-state revalidation, and chest-armor lookup between Minecraft 1.20.2 Mojmap and 1.20.4 Mojmap.** This verdict does not accept complete pair coverage or close caller, item-use, synchronization, or pair-freeze gates.

## Independent evidence checks

- Both canonical ready markers are present and identify the exact version and `mojmap`, Vineflower, Java runtime 25, and source counts (4,904 for 1.20.2; 5,048 for 1.20.4). The actual manifest SHA-256 values match the ready markers: 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`. Manifest line counts match the stated source counts.
- The provenance markers identify Mojmap and exact decompile runs for the stated version IDs. Their run records name batch `1.20.1, 1.20.2, 1.20.4, 1.20.6`, Gradle 9.7.1, Java 25.0.3+9-LTS, and Vineflower 1.12.0.
- Independently recomputed hashes for the scoped canonical files match their source-manifest entries at both endpoints. `ElytraItem.java`, `Items.java`, `Player.java`, `LivingEntity.java`, `Inventory.java`, and `EquipmentSlot.java` match all report values. The shared `LocalPlayer.java` hash is also listed in both manifests and matches the file.
- `ElytraItem.isFlyEnabled` is the same durability comparison at both endpoints, and `getEquipmentSlot()` returns `EquipmentSlot.CHEST`. `Items.ELYTRA` has the same durability and rarity construction. `Player.tryToStartFallFlying()` has the same grounded/already-gliding/water/levitation gates, chest-slot read, item and durability checks, and flag transition. `startFallFlying()` and `stopFallFlying()` are unchanged.
- `LivingEntity.updateFallFlying()` has the same shared-flag, ground, passenger and levitation gates; it reads the chest slot and applies the same item/durability check. Invalid state clears the local flag, while shared-flag mutation remains guarded by `!level().isClientSide`. `isFallFlying()` reads shared flag 7 at both endpoints.
- Player equipment lookup and assignment use the armor list at `EquipmentSlot.getIndex()` at both endpoints. The armor list has four entries, and `EquipmentSlot.CHEST` is armor index 2 at both endpoints. Thus the scoped direct lookup resolves to armor index 2.
- For the requested reachability check, `LocalPlayer` at both endpoints contains the same jumping-input branch, with its existing input-state, flying, passenger, and climbable guards; it checks the chest Elytra and enabled predicate, invokes `tryToStartFallFlying()`, then sends the `START_FALL_FLYING` player-command packet. This confirms the local-player call site exists and is unchanged, without closing the wider input/tick or packet/state synchronization dependency.

## Remaining gates

The report's no-difference conclusion is supported for its assigned slice. Elytra `use()` and downstream `Equipable.swapWithEquipmentSlot` propagation remain outside the reviewed claim. The LocalPlayer call-site check above does not audit its enclosing tick/input callers, equipment synchronization/resource provenance, or server packet handling. Gliding travel math and other movement slices are also outside this assignment. The 1.20.2-to-1.20.4 endpoint comparison does not establish an intermediate patch boundary or freeze complete movement coverage.
