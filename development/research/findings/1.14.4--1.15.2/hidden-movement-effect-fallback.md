# Hidden movement-effect restoration

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, `update`, lines 54–120.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/effect/MobEffectInstance.java`, `update`, lines 66–147.
1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `tickEffects, addEffect`, lines 605–662, 761–777.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `tickEffects, addEffect`, lines 573–630, 729–745.

Difference: If a shorter, stronger Speed or Slowness instance replaces a weaker instance with more remaining duration, 1.15.2 keeps the weaker instance and counts its remaining duration down behind the active one. When the stronger instance expires, 1.15.2 restores the weaker amplifier and refreshes the movement-speed attribute modifier immediately. 1.14.4 discards the weaker instance, so expiry removes the movement-speed modifier entirely. This changes the player's movement speed after the stronger effect ends. The same restoration also resumes Jump Boost, Slow Falling, Levitation, Dolphin’s Grace and Blindness; their existing movement readers consume the restored active instance.

Movement meaning: Restored Speed/Slowness modifiers change movement speed; restored active effects change existing jump/travel/sprint predicates. Server modifier refresh and client active-instance replacement are separate paths.
