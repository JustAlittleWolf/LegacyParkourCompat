# Soul Speed friction override persists through 1.20.6

1.15.2, Mojmap: `net/minecraft/world/entity/LivingEntity.java` and `net/minecraft/world/item/enchantment/Enchantments.java` have no Soul Speed friction hook or Soul Speed entry.
1.16, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `onSoulSpeedBlock` and `getBlockSpeedFactor`, lines 424â€“430; `net/minecraft/world/entity/Entity.java`, `move`, lines 561â€“562.
1.16.1, Mojmap: `LivingEntity.java`, same members, lines 424â€“430; `Entity.java`, same movement consumer, lines 561â€“562.
1.16.5, Mojmap: `LivingEntity.java`, same members, lines 425â€“431; `Entity.java`, `move`, lines 576â€“577.
1.17 and 1.17.1, Mojmap: `LivingEntity.java`, same members, lines 458â€“464; `Entity.java`, `move`, lines 630â€“631.
1.18.1 and 1.18.2, Mojmap: `LivingEntity.java`, same members, lines 456â€“462 and 460â€“466 respectively; `Entity.java`, `move`, lines 643â€“644 and 653â€“654 respectively.
1.19.2 and 1.19.3, Mojmap: `LivingEntity.java`, same members, lines 462â€“468 and 461â€“467 respectively; `Entity.java`, `move`, lines 658â€“659 in both releases.
1.19.4, Mojmap: `LivingEntity.java`, same members, lines 456â€“462; `Entity.java`, `move`, lines 660â€“661.
1.20.1 and 1.20.4, Mojmap: `LivingEntity.java`, same members, lines 471â€“477; `Entity.java`, `move`, lines 701â€“702 in 1.20.1 and 704â€“705 in 1.20.4.
1.20.6, Mojmap: `LivingEntity.java`, same members, lines 489â€“495; `Entity.java`, `move`, lines 705â€“706.
1.21, Mojmap: `LivingEntity.java`, `getBlockSpeedFactor`, lines 485â€“488; Soul Speed `MOVEMENT_EFFICIENCY` effect is registered in `Enchantments.java`, lines 476â€“522, and reads through the movement-efficiency attribute.

Difference: In every checked release from 1.16 through 1.20.6, `getBlockSpeedFactor` first calls `onSoulSpeedBlock()`, then conditionally reads the Soul Speed enchantment level, and returns `1.0F` only when both predicates are true; otherwise it delegates to the native block-speed factor. Java `&&` short-circuits in that order. `Entity.move` multiplies horizontal delta movement by this returned float. The local player inherits this client movement path. At 1.21 the movement-factor operation instead reads `MOVEMENT_EFFICIENCY` and lerps the block factor toward `1.0F`; server-side attribute production is contextual only.

Movement meaning: With Soul Speed on the player and a matching vanilla block at the selected support position, the checked 1.16â€“1.20.6 local-player path cancels block friction by returning exactly `1.0F`; otherwise it preserves the native factor. The 1.20.6-to-1.21 friction-operation transition remains supported. The sampled earlier releases establish the old formula at those versions, not every unexamined patch boundary.
