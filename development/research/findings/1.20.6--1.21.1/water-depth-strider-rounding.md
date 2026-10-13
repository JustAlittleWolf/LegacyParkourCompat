# water-depth-strider-rounding: Depth Strider III water speed factor changes by one float ulp

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2104-2129.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, travel 2091-2112; createLivingAttributes 321-322.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, getDepthStrider 184-186.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, class declaration / field initialization 74-79.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 344-368.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/LevelBasedValue.java`, perLevel 30-32.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java`, class declaration / field initialization 101-103.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/ItemStack.java`, forEachModifier 915-923.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, forEachModifier 311-316.

Difference: Under the same reachable setup—player in water, affected by fluids, unable to stand on the fluid, on ground, sprinting, wearing Depth Strider III, and without Dolphin's Grace—1.20.6 starts `$$5` at `0.9F` and uses integer enchantment factor `3.0F`, then performs multiply and divide by `3.0F`. 1.21.1's registered level-3 attribute contribution evaluates to the bounded water-efficiency value `1.0F`, and its formula multiplies by that value without the final division. These algebraically equivalent expressions do not produce the same binary32 result: Java float evaluation gives 1.20.6 `$$5 = 0.5460000038146973` and 1.21.1 `$$5 = 0.546000063419342` after this adjustment. The change is in the horizontal water-speed multiplier; the later movement and drag order is otherwise unchanged in this branch.

Movement meaning: The local player enters `LivingEntity#travel` through `LocalPlayer#aiStep`, `Player#travel`, and `LivingEntity#aiStep`; the water branch is guarded by `isInWater()`, `isAffectedByFluids()`, and `!canStandOnFluid($$3)`. 1.20.6 obtains the equipped enchantment level through `EnchantmentHelper`; 1.21.1 declares and applies the attribute modifier for eligible foot armor. 1.21.1's attribute is included in every LivingEntity attribute supplier and syncable. The vanilla Depth Strider maximum is 3 on both sides. The source proves a one-float-ulp difference in the multiplier under the stated preconditions. It predicts a corresponding small difference in horizontal velocity after the water drag multiplication; no observed position trace or gameplay impact is claimed. This endpoint pair does not establish the first release containing the change. Other water-branch dependencies and all remaining pair coverage continue in the run ledger.
