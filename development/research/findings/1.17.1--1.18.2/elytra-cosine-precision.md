# Fall-flying coefficient changes from float-table to double cosine

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 2074–2090.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 2080–2099.

Difference: 1.17.1 LivingEntity#travel lines 2074-2090cos` into a float before using it in the fall-flying lift/pull equations. 1.18.2 LivingEntity#travel lines 2080-2099cos` and retains the double result in those equations. The branch is guarded by `isFallFlying()` in both versions.

Movement meaning: LocalPlayer#aiStep 1.17.1 lines 747-751 / 1.18.2 lines 740-744 reads jump input and, when the player is airborne, not already fall-flying, not ability-flying, not a passenger and not on a climbable, checks the chest-slot item and `ElytraItem.isFlyEnabled`.
