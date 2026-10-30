# Bounded source report: 1.16.5 → 1.17.1 ordinary local-player tick, travel, and movement modifiers

## Scope and evidence identity

Compared read-only decompiled Mojmap endpoint trees:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap`

The supplied source-only records `1165-1171-ordinary-tick-closure`, `1165-1171-jump-push-audit`, `1170-1171-full-inventory`, and `1170-1171-full-audit` were not found by exact-name search in the permitted workspace or source root. This report retraces the owned slice directly; it does not certify or inherit those records. The endpoint directory names and source layout establish the release labels and Mojmap family, but no publication/mapping-build manifest was present in the inspected source roots, so a separate Mojmap build identifier could not be verified.

SHA-256 was calculated over the exact source files used; hashes are listed below. This is a bounded code review, not whole-pair closure.

## Source-confirmed movement differences

### Jump boost changes the precision of the written vertical jump velocity

- 1.16.5 Mojmap `net/minecraft/world/entity/LivingEntity.java`, `jumpFromGround()`, lines 1882–1895: `getJumpPower()` is assigned to `float f`; when `MobEffects.JUMP` is active, `0.1F * (amplifier + 1)` is added to that float; the resulting float is passed to `setDeltaMovement` and widened to double.
- 1.17.1 Mojmap `net/minecraft/world/entity/LivingEntity.java`, `getJumpBoostPower()` and `jumpFromGround()`, lines 1971–1984: the boost helper returns `double`; `jumpFromGround` adds it to the float `getJumpPower()` result in `double d` and passes that double to `setDeltaMovement`.
- Player reachability: `Player.jumpFromGround()` delegates to `super.jumpFromGround()` in both endpoints (1.16.5 `Player.java:1375–1384`; 1.17.1 `Player.java:1397–1406`); `LivingEntity.aiStep()` invokes this when jump conditions pass (1.16.5 `LivingEntity.java:2438–2442`; 1.17.1 `LivingEntity.java:2525–2529`).
- Difference: with Jump Boost active, 1.16.5 rounds the sum of base jump power and boost to float before storing Y velocity. In 1.17.1 the base term and boost product are float-derived, but their sum is retained as double; the final float rounding is absent. Without Jump Boost, the base jump term remains float-derived and widens unchanged. This is a concrete reachable player jump-state change; no trajectory magnitude prediction was tested.

### Entry into swimming now checks water at the player's feet

- 1.16.5 Mojmap `net/minecraft/world/entity/Entity.java`, `updateSwimming()`, lines 949–955: when not already swimming, sprinting + underwater + not passenger is sufficient to set swimming.
- 1.17.1 Mojmap `net/minecraft/world/entity/Entity.java`, `updateSwimming()`, lines 1056–1064: that same transition additionally requires `level.getFluidState(blockPosition).is(FluidTags.WATER)`.
- Player reachability: `Player.updateSwimming()` delegates to `super.updateSwimming()` unless flying (1.16.5 `Player.java:1420–1426`; 1.17.1 `Player.java:1442–1448`). `Entity.tick()` calls `updateSwimming()` in both endpoints (1.16.5 `Entity.java:382`; 1.17.1 `Entity.java:417`). `Player.travel()` checks `isSwimming()` to apply the player swimming vertical movement adjustment (1.16.5 `Player.java:1387–1417`; 1.17.1 `Player.java:1409–1439`).
- Difference: a non-flying, non-passenger, sprinting player underwater but with a non-water fluid state at feet can no longer newly enter swimming in 1.17.1. The already-swimming branch is unchanged. The player travel branch is therefore reachable with a changed swim-state predicate.

## Checked no-difference slices / dispositions

- Keyboard input: `KeyboardInput.tick(boolean)` is behaviorally identical (1.16.5 lines 12–25; 1.17.1 lines 13–26). Both compute forward/side impulses from key booleans, then under the same slow flag multiply each by `0.3` and cast to float. 1.17.1 adds `MOVING_SLOW_FACTOR = 0.3` but continues using literal `0.3`; the added constant does not alter execution. `Input.java` is byte-identical.
- Local-player ordinary `tick()` / `aiStep()` and sprint gates were compared across the endpoint files. The inspected movement branches preserve gates, operation order and numeric constants; observed differences in this slice are getter/API renames and the unchanged 0.2 item-use impulse scaling. `LocalPlayer.tick()` changes the chunk guard from a floored `BlockPos(x, 0, z)` to block X/Z accessors; these address the same horizontal block coordinates. The new `resetPos()` code is outside the ordinary tick path and was not counted as a finding.
- `Player.travel()` swim vertical adjustment and creative-flight branch are textually the same in both versions. Its inherited `LivingEntity.travel()` has two additional changes: the unloaded-client-chunk gravity fallback compares Y to `level.getMinBuildHeight()` rather than literal `0.0` (1.16.5 lines 2031–2039; 1.17.1 lines 2120–2128), and a new `shouldDiscardFriction()` branch bypasses normal horizontal/vertical friction (1.17.1 lines 2130–2134). The minimum-height change only distinguishes negative-height coordinates made available by the new world-height range; treat this as a modern-world-height feature disposition, not an accepted old-profile difference. The new friction flag defaults false; the only vanilla setter calls in 1.17.1 source are in `LongJumpMidJump` and `LongJumpToRandomPos` mob behaviors. No vanilla player producer/caller exists in the inspected source tree, so the branch is unreachable for a vanilla player on this path.
- Effects: the existing client-visible effect values and `MobEffect`/`MobEffects` definitions are byte-identical. `LivingEntity` effect add/update callback signatures acquire an optional entity argument, but server-side attribute modifier add/remove operations retain the same calls and gates; `MobEffectInstance` changes inspected are equality/serialization structure, not movement tick arithmetic. Jump Boost’s use at `jumpFromGround()` above is the movement difference found.
- Equipment and movement modifiers: `ItemStack.getAttributeModifiers(EquipmentSlot)` (1.16.5 lines 788–813; 1.17.1 lines 845–870), `AttributeInstance`, `AttributeMap`, and `Enchantment.getSlotItems()` have identical bodies/hashes. `EnchantmentHelper.getEnchantmentLevel()` (1.16.5 lines 152–169; 1.17.1 lines 176–193) is identical. Existing Depth Strider/Dolphin’s Grace water movement, Soul Speed attribute add/remove (1.16.5 `LivingEntity.java:430–465`; 1.17.1 `LivingEntity.java:462–498`) and sprint movement-speed modifier setup (`LivingEntity.setSprinting`, corresponding endpoints) show no arithmetic/gate change.
- Modern-only disposition: 1.17.1 adds powder-snow freeze speed modifier production/removal in `LivingEntity.removeFrost()/tryAddFrost()` (lines 500–524), reached from the new freeze tick path; this is for the newly introduced powder-snow/freeze mechanic, which is outside old-profile movement scope. It is not included as a historical movement finding.

## Coverage inventory for this assignment

- **Examined and paired:** `client/player/Input.java`, `KeyboardInput.java`, `LocalPlayer.java`; `world/entity/Entity.java`, `LivingEntity.java`, `player/Player.java`; `world/entity/ai/attributes/AttributeInstance.java`, `AttributeMap.java`; `world/effect/MobEffect.java`, `MobEffectInstance.java`, `MobEffects.java`; `world/item/ItemStack.java`, `ArmorItem.java`, `enchantment/Enchantment.java`, `EnchantmentHelper.java`. Direct player call edges for tick, jump, swim-state update, travel, movement-speed modifiers, effect modifiers, and relevant enchantment reads were checked in those exact source trees.
- **Partial:** effect and equipment producer chains were checked only for player movement-related built-in effects, enchantments, and attribute modifiers; the complete potion/item-use event graph was not enumerated. Local player key-state flow was checked at the vanilla `Input`/`KeyboardInput` abstraction, not platform input plumbing.
- **Unexamined / outside ownership:** full `Entity.move()` / collision and shape-provider dependencies; block/fluid current providers and registrations; piston and external player velocity responses; fall damage; vehicle and non-player physics; server handling; all effects/items not read by movement; integrated pair-wide terminal inventory. No claim of pair closure or independent full-pair audit is made.

## Next bounded action

Send this report and the two source-backed candidates to the pair coordinator. Keep Jump Boost precision and swimming-entry predicate as separate candidate operations. Ask the independent source auditor to retrace these two claims and the explicitly partial dependencies; investigate the unloaded-chunk minimum-height branch only if the coordinator considers negative-height travel in scope for the 1.16.5 → 1.17.1 comparison.

## SHA-256 source-file identity

Hashes are in 1.16.5, then 1.17.1 order.

| Relative source file | 1.16.5 | 1.17.1 |
| --- | --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| `net/minecraft/client/player/Input.java` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` |
| `net/minecraft/client/player/KeyboardInput.java` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` | `ea41065c909e53f1a2cc29ecdb6a9a8f9265d2801cd8b95b996e182c318ecd69` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `05c36f559ada7061a4a2ee8efaf05a21502945af83a893cb4c849749afb84859` | `d0508c94f43e7e90683b8f46586665bd1e1f0dca2fe25274b852f5e6c0ce05e8` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `6cc7410e181197a56040682aad1525ac1a110140bf7e99ad598c886f892345a5` | `6cc7410e181197a56040682aad1525ac1a110140bf7e99ad598c886f892345a5` |
| `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `53f0b1709e503095ef30069f2d265ac0f972b35a49ab75b46e6ce2b15d126b75` | `53f0b1709e503095ef30069f2d265ac0f972b35a49ab75b46e6ce2b15d126b75` |
| `net/minecraft/world/effect/MobEffect.java` | `665e2365c273a8004d327020615b76a0f5faa839bab20f96a99f412c7330e812` | `665e2365c273a8004d327020615b76a0f5faa839bab20f96a99f412c7330e812` |
| `net/minecraft/world/effect/MobEffectInstance.java` | `4999d12cd468e402506f067dbd8694b1c8125dd9131e4f8a2fcc1848f616f180` | `8976532fca6e09bfe459194e50777c5cf690c0806275e8327a8b8bca4367e34b` |
| `net/minecraft/world/effect/MobEffects.java` | `64b3b592a48eac1016c3a307b85c3e201689662dea68c80b6d642cffa35fd289` | `64b3b592a48eac1016c3a307b85c3e201689662dea68c80b6d642cffa35fd289` |
| `net/minecraft/world/item/ItemStack.java` | `2bb228453c39f9792dc1496802e46220738dbf32855442696baaa0288f791156` | `ba25d48580a3c586233956558905467dc183502efc59334090b65d46f598da59` |
| `net/minecraft/world/item/ArmorItem.java` | `f677ca2632f528b57507159946fb0ed5f447c4d1b99dbb6c8d81a16f2a1167ff` | `64ede823cd5ad91ae8dce5078234db1eaccccfc56a32f9a98f7216cc3236150f` |
| `net/minecraft/world/item/enchantment/Enchantment.java` | `9ad634e8770a61baa3f292078d90d7067f3a0e69576cae7818a6a2ae253e9a49` | `9ad634e8770a61baa3f292078d90d7067f3a0e69576cae7818a6a2ae253e9a49` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `cb4ff36f66976365247cf4fa0e052182e7896aa5c363ccc7236be91e84a99eef` | `5527514a661b14aaf8ff2a979380218c8dedd17c24b097851160be5d27fc4081` |
