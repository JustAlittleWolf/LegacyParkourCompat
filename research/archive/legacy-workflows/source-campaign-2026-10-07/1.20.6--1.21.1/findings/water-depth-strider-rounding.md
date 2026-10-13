# water-depth-strider-rounding: Depth Strider III water speed factor changes by one float ulp

- Older version A: 1.20.6
- Newer version B: 1.21.1
- Mechanic / coverage slice IDs: L5, E2
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.20.6, 1.21.1]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact: `ready/1.20.6/mojmap`; source `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity#travel(Vec3)`, lines 2104-2129, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. The affected water branch reads `EnchantmentHelper.getDepthStrider(this)` at 2117, caps it at 3 at 2118-2120, halves it in air at 2122-2124, then updates `$$5` with `(0.54600006F - $$5) * $$7 / 3.0F` at 2126-2128.
- B manifest artifact: `ready/1.21.1/mojmap`; source `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity#travel(Vec3)`, lines 2091-2112, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`. The corresponding branch reads `(float)this.getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY)` at 2104, halves it in air at 2105-2107, then updates `$$5` with `(0.54600006F - $$5) * $$7` at 2109-2111.
- A producer: `net/minecraft/world/item/enchantment/EnchantmentHelper.java`, `getDepthStrider(LivingEntity)`, lines 184-186, SHA-256 `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6`; it returns the equipped Depth Strider level. `net/minecraft/world/item/enchantment/Enchantments.java`, lines 74-79, SHA-256 `26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381`, defines max level 3 on foot armor.
- B producer/default: `net/minecraft/world/item/enchantment/Enchantments.java`, lines 344-368, source SHA-256 `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266`; Depth Strider is max level 3 on foot armor and adds `WATER_MOVEMENT_EFFICIENCY` using `LevelBasedValue.perLevel(0.33333334F)` with `ADD_VALUE`. `net/minecraft/world/entity/ai/attributes/Attributes.java`, lines 101-103, source SHA-256 `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c`, gives that attribute default 0 and range 0..1. `LivingEntity#createLivingAttributes()` includes it at lines 321-322 (same B `LivingEntity.java` hash above). `ItemStack#forEachModifier(EquipmentSlot, BiConsumer)` lines 915-923 (SHA-256 `15094cc5a115bbbc81c550fafcfba51451fcc7afed2ad374ca1029ba1d1db687`) delegates enchantment modifiers to `EnchantmentHelper#forEachModifier(ItemStack, EquipmentSlot, BiConsumer)` lines 311-316 (SHA-256 `9e2f9acd5bc6a8f3b5292d720db81464954d41c9afb635f1a7c6cb31d5a2bf84`); `LivingEntity` applies/removes those equipment modifiers in its equipment-change path at lines 2488-2502 (B `LivingEntity.java` hash above). `LevelBasedValue.Linear#calculate(int)` lines 126-138 computes `base + perLevelAboveFirst * (level - 1)` and yields 1.0F at level 3 for this registered amount.

## Source-level difference

Under the same reachable setup—player in water, affected by fluids, unable to stand on the fluid, on ground, sprinting, wearing Depth Strider III, and without Dolphin's Grace—A starts `$$5` at `0.9F` and uses integer enchantment factor `3.0F`, then performs multiply and divide by `3.0F`. B's registered level-3 attribute contribution evaluates to the bounded water-efficiency value `1.0F`, and its formula multiplies by that value without the final division. These algebraically equivalent expressions do not produce the same binary32 result: Java float evaluation gives A `$$5 = 0.5460000038146973` and B `$$5 = 0.546000063419342` after this adjustment. The change is in the horizontal water-speed multiplier; the later movement and drag order is otherwise unchanged in this branch.

## Reachability and dependencies

The local player enters `LivingEntity#travel` through `LocalPlayer#aiStep`, `Player#travel`, and `LivingEntity#aiStep`; the water branch is guarded by `isInWater()`, `isAffectedByFluids()`, and `!canStandOnFluid($$3)`. A obtains the equipped enchantment level through `EnchantmentHelper`; B declares and applies the attribute modifier for eligible foot armor. B's attribute is included in every LivingEntity attribute supplier and syncable. The vanilla Depth Strider maximum is 3 on both sides. The affected state is the horizontal velocity after `setDeltaMovement($$8.multiply($$5, 0.8F, $$5))`; a trajectory difference is a source-derived consequence, not runtime-observed here.

## Consequence and uncertainty

The source proves a one-float-ulp difference in the multiplier under the stated preconditions. It predicts a corresponding small difference in horizontal velocity after the water drag multiplication; no observed position trace or gameplay impact is claimed. This endpoint pair does not establish the first release containing the change. Other water-branch dependencies and all remaining pair coverage continue in the run ledger.

## Handoff

Independent delta: preserve the operation order difference when emulating this release transition. No related finding IDs. Applicability is limited to the reachable Depth Strider III water branch preconditions above; first changed release remains unknown within `(1.20.6, 1.21.1]`.