# Wind Burst attacker impulse depends on sweep-target damage acceptance

1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, attack 1209-1223.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/player/Player.java`, attack 1174-1184.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, doPostAttackEffects 190-221.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, doPostAttackEffects 190-221.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/item/enchantment/Enchantment.java`, doPostAttack 260-280.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/item/enchantment/Enchantment.java`, doPostAttack 260-280.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/damagesource/DamageSource.java`, getEntity 65-67.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/damagesource/DamageSource.java`, getEntity 65-67.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 1158-1193; bootstrap 227-243.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 1158-1193; bootstrap 227-243.

Difference: For a server-side player sweep attack where the target's `hurt`/`hurtServer` result is false, 1.21.4 still runs post-attack enchantment effects, while 1.21.5 skips them. If the attacking player holds a Wind Burst mace and satisfies the non-flying, fall-distance-at-least-1.5 condition, 1.21.4 therefore runs Wind Burst's explosion at the attacker and applies its direct player impulse; 1.21.5 does not. The source difference concerns the attack-effect dispatch gate. Damage calculation and outcomes are not emulated by this finding.

Movement meaning: The player must reach the sweep-attack loop, the target must reject the swept `hurt` attempt in 1.21.5, and the attacker must satisfy Wind Burst's direct-attacker predicate. The effect path then runs on the server through the attacker weapon's enchantment, `ExplodeEffect`, `ServerExplosion`, and `Entity.push`. The player impulse is suppressed when abilities-based flying is active and is scaled by explosion exposure and `EXPLOSION_KNOCKBACK_RESISTANCE`. This is separate from the primary-target hit path, whose dispatch site remains outside this bounded finding. In the stated branch, 1.21.4 can apply a Wind Burst impulse to the attacking player after a rejected sweep-target hit; 1.21.5 cannot.
