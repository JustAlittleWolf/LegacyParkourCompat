# Depth Strider water acceleration rounding

1.20.6, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2117–2128; `net/minecraft/world/item/enchantment/EnchantmentHelper.java`, `getDepthStrider(LivingEntity)`, lines 184–185.

1.21.1, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2104–2115; `net/minecraft/world/item/enchantment/Enchantments.java`, `DEPTH_STRIDER` effect, lines 346–365; `net/minecraft/world/entity/ai/attributes/Attributes.java`, `WATER_MOVEMENT_EFFICIENCY`, lines 101–103.

Difference: while local-player water travel is controlled locally, 1.20.6 reads the integer Depth Strider level, caps it at 3, optionally halves it when airborne, then applies `(getSpeed() - 0.02F) * level / 3.0F` to the horizontal input acceleration. 1.21.1 reads the float `WATER_MOVEMENT_EFFICIENCY` attribute and multiplies directly by it. Its built-in Depth Strider attribute effect adds `0.33333334F` per level, so Depth Strider II corresponds to `0.6666667F` in that attribute (the syncable attribute value is a received-state input to the local client). For that corresponding attribute state, the different float operation order produces distinct acceleration for reachable inputs: with a non-sprinting player at default speed `0.1F`, on ground in water, Depth Strider II and nonzero horizontal input, the old acceleration is `0.07333333045244217F` and the new acceleration is `0.07333333790302277F`.

Movement meaning: `LivingEntity.travel` passes this acceleration to `moveRelative`, changing the local player's resulting horizontal motion by one float ULP for the stated state. This source finding does not claim a complete trajectory or runtime parity.
