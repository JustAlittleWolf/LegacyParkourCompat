# 1.17 → 1.17.1 item and modifier boundary audit

## Scope and source identity

Bounded source-only comparison of exact Mojmap decompiled trees:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap`

The 1.17 root has `.decompile-provenance.json`; the 1.17.1 root does not. The canonical directory names identify the requested release/mapping pair, and the file hashes below identify the inspected bytes. I did not independently authenticate publication inputs for the manifest-less 1.17.1 tree. No product implementation, product history, wiki or backlog was consulted. No source generation, build, test, game or runtime check was run.

| Exact relative source path | SHA-256 1.17 | SHA-256 1.17.1 |
| --- | --- | --- |
| `net/minecraft/world/item/ItemStack.java` | `C25315CDD8CB4AE565079FAE623A3331092E9705DF5299D81E8A01867CDF48B1` | `BA25D48580A3C586233956558905467DC183502EFC59334090B65D46F598DA59` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `3AF454A5BA2CCA327D2F85798C841A06ACD54E7879DDAD80BA6126FA0D026088` | `5527514A661B14AAF8FF2A979380218C8DEDD17C24B097851160BE5D27FC4081` |
| `net/minecraft/world/entity/LivingEntity.java` | `8E81E5F1317D154FA41B676721FF581BADD46296F77BCEBCADB3C63448E528ED` | `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F` |
| `net/minecraft/world/entity/Entity.java` | `81231AD8AC1B9A7A9F7EE186352C7E73C0C5211A3034C3A35C04BADC6914C8EF` | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` |
| `net/minecraft/world/entity/player/Player.java` | `9AD4C439814CAF5F51CF6DA9A86C5D22906B0A28B5B606BDEB1B770617D90516` | `724BB298499DABE489DFFD5CE7EC81C9773619A8D2C70044911EAE4C9E8FF481` |
| `net/minecraft/client/player/LocalPlayer.java` | `08DBB8615CC6C7776F35CE269223699D39F758D15786BABFE11E497421C5F4D4` | `C9A91CB6CB57806BC8D22E6E2F97DAAF21D5D2A48F34A6E2C53164C61C5812` |

## Paired movement-relevant result

### Jump Boost arithmetic changed in a local-player jump path

In 1.17, `LivingEntity.jumpFromGround()` (lines 1967–1977) keeps base jump power and Jump Boost in a `float`: it initializes `float f` from `getJumpPower()`, adds `0.1F * (amplifier + 1)` when `MobEffects.JUMP` is present, then writes `f` as the Y component through `setDeltaMovement`. In 1.17.1, this method (lines 1971–1984) obtains the effect term from the new public `getJumpBoostPower()` (lines 1971–1973), adds it to `getJumpPower()` in a `double`, and writes the double Y value. The effect product is still computed using `0.1F`, but the sum is no longer rounded back to float before the velocity write. `LivingEntity.aiStep` reaches this method at 1.17 lines 2516–2526 and 1.17.1 lines 2520–2530; `Player.jumpFromGround()` delegates to `super` at 1.17 lines 1407–1417 and 1.17.1 lines 1398–1408. This is a concrete effect-reading local-player movement delta, with precision as the changed operation. No trajectory or runtime parity is asserted.

### Movement enchantment readers and attribute providers: no difference found

- `LivingEntity#getBlockSpeedFactor`, `tryAddSoulSpeed`, `removeSoulSpeed`, `onChangedBlock`, and the Depth Strider read in `travel` have no behavioral member delta in this pair. Their movement-facing enchantment path is `LivingEntity` → `EnchantmentHelper.getEnchantmentLevel(Enchantment, LivingEntity)` → `getItemEnchantmentLevel(Enchantment, ItemStack)`. In both sources the latter checks the same stack/tag/id gates and clamps the stored `lvl` with `Mth.clamp(..., 0, 255)`. The new helper delegation in 1.17.1 preserves those expressions. The Soul Speed modifier formula/gates and the Depth Strider travel branch are unchanged.
- The `removeFrost` / `tryAddFrost` movement-speed modifier and their tick calls are unchanged between 1.17 and 1.17.1. They are present at 1.17 `LivingEntity.java:500–523, 2552–2557` and 1.17.1 `:500–523, 2556–2561`; these are the already introduced powder-snow/freeze feature paths, not a new pair delta.
- `MobEffect.java`, `MobEffects.java`, `AttributeInstance.java`, and `Attributes.java` are byte-identical across the pair. Thus speed/slowness modifier definitions and attribute operation order have no source change here.

### Enchantment and ItemStack refactor: data reads changed, movement-facing result did not

`EnchantmentHelper` adds compound-tag ID/level helper methods in 1.17.1 (`:34–58`). `getItemEnchantmentLevel` now delegates ID parsing and level extraction to those helpers (`1.17:34–50; 1.17.1:62–78`); the level helper is still the same `Mth.clamp(compoundTag.getInt("lvl"), 0, 255)`. `deserializeEnchantments` / `getEnchantments` now clamp map levels (`1.17:53–68; 1.17.1:81–94`), and `runIterationOnItem` passes clamped levels (`1.17:95–103; 1.17.1:118–127`). `ItemStack` and `EnchantedBookItem` likewise route tooltip/enchantment writes through these helpers (`ItemStack.java:752–756, 805–808`; `EnchantedBookItem.java:44–66`). The movement readers listed above use `getEnchantmentLevel(Enchantment, LivingEntity)` and the already-clamped item-level reader, rather than the changed generic map/iteration paths. No movement-facing enchantment gate or level delta was found. The changed generic level normalization may affect other consumers, but those consumers were not traced as movement paths here.

### Drop methods changed, with no movement call edge found

The base `Player.drop(boolean)` implementation is removed in 1.17.1 (1.17 `Player.java:656–665`; absent in 1.17.1). `LocalPlayer.drop(boolean)` remains at 1.17 `LocalPlayer.java:282–288` and 1.17.1 `:281–286`, but the newer version removes the selected stack through the new `Inventory.removeFromSelected(boolean)` (`Inventory.java:591–594`) before sending the same player-action packet; the older implementation sent the packet before calling `removeItem` inline. `Player.drop(ItemStack, boolean)` and its underlying item-entity/drop path remain separate. These differences concern selected inventory state and packet/order behavior; no local-player movement or movement-modifier operation calls this drop path in the inspected members. They are not a movement finding.

## Other changed member reaching player movement

`Entity.updateSwimming()` adds `level.getFluidState(blockPosition).is(FluidTags.WATER)` to the non-swimming entry condition in 1.17.1 (`Entity.java:1056–1062`); the 1.17 version lacks this final conjunct (`:1056–1061`). `Entity.tick()` calls `updateSwimming()` in both versions (`:417`). `Player.updateSwimming()` delegates to `super` when not flying (`Player.java:1442–1447`), and LocalPlayer movement/jump branches read `isSwimming()` (`LocalPlayer.java:660, 718, 738`). This is a separate movement-affecting state gate from the item/equipment/effect slice, reported for the pair owner to reconcile with the fluid/tick inventory.

`Entity.load(CompoundTag)` also changes the loaded Y position write from raw NBT to `Mth.clamp(..., -2.0E7, 2.0E7)` (1.17 `Entity.java:1520–1535`, 1.17.1 `:1520–1537`). It is a generic entity position writer, but this bounded audit did not establish whether LocalPlayer reaches this load path; applicability remains open and it is unrelated to the item/modifier slice.

`Items.SMALL_DRIPLEAF` changes from the ordinary `BlockItem` factory to `new DoubleHighBlockItem(...)` in 1.17.1 (`Items.java:221–225`). The new `DoubleHighBlockItem.placeBlock` writes the above position to water or air before placing the lower block (`DoubleHighBlockItem.java:16–21`); `BlockItem.place` then calls the placed lower block's `setPlacedBy` (`BlockItem.java:73–82`). `SmallDripleafBlock.setPlacedBy` writes its upper half using `copyWaterloggedFrom` in 1.17.1 (`SmallDripleafBlock.java:65–73`), whereas 1.17 directly set the upper half and used `level.isWaterAt` for its WATERLOGGED value (`:65–77`). This is an item-use placement sequence that may affect later fluid/movement state; final-state and callback equivalence across all placement contexts were not established, so no movement finding is made from it here. `LivingEntity.addEffect(MobEffectInstance)` only becomes `final`; the added named-entity death log is server-side and neither changes the movement path in this comparison.

## Coverage boundary

Examined paired changed members in `ItemStack`, `EnchantmentHelper`, `EnchantedBookItem`, `DoubleHighBlockItem`, `Items`, `SmallDripleafBlock`, `Inventory`, `Player`, `LocalPlayer`, `LivingEntity`, and `Entity`; checked byte identity for `MobEffect`, `MobEffects`, `AttributeInstance`, and `Attributes`. The all-source scoped path census found no further changed `world/item`, `world/effect`, player, or living-entity source paths that add a movement modifier provider. No changed Soul Speed, Frost Walker, Depth Strider, speed-effect, equipment-speed, or freeze-speed formula/gate was found. This is a bounded item/effect/modifier boundary audit, not full pair coverage. Remaining work: reconcile the separately noted swim-entry delta with the pair's fluid/tick owner; close the small-dripleaf placement/final-state and callback question if item-use placement is in the authorized inventory; establish or disposition the generic NBT load applicability. Runtime parity remains untested.
