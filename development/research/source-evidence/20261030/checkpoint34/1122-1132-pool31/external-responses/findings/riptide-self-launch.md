# Riptide trident self-launch

1.12.2, Feather Gen2 build 2: `net/minecraft/item/TridentItem.java` is absent from the exact source tree and source manifest; no trident item exists in this release.
1.13.2, Feather Gen2 build 2: `net/minecraft/item/TridentItem.java`, `startUsing`, lines 114–127, gates use on durability and Riptide's water-or-rain requirement; `stopUsing`, lines 56–109, handles release; `net/minecraft/entity/living/LivingEntity.java`, `stopUsingItem`, lines 2288–2291, dispatches to the active stack.
Difference: after at least 10 use ticks, `stopUsing` checks the Riptide enchantment and water-or-rain condition, computes a yaw/pitch-directed vector using float intermediates and a float square-root result, and adds it to the player velocity. When the player is on ground, it also calls `move(MoverType.SELF, 0.0, 1.1999999F, 0.0)`.
Movement meaning: in 1.13.2, releasing a charged Riptide trident directly launches the player; 1.12.2 has no corresponding item or operation. This is a 1.13.2 feature addition, not a behavior required from a 1.12.2 profile.
