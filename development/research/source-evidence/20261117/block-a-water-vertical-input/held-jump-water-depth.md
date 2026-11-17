# Held-jump vertical response in water

**1.12.2, Ornithe Feather:** `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `mobTick`, lines 1827–1847; `jumpInWater`, lines 1413–1415.
**1.13, Ornithe Feather:** `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `mobTick`, lines 1884–1907; the positive vertical liquid response, lines 1451–1453.

Difference: 1.12.2 applies `jumpInWater()` (`velocityY += 0.04F`) whenever `jumping && isInWater()`. 1.13 branches on the sampled `waterHeight`: on ground, a positive sample at or below `0.4` reaches ordinary `jump()` (only when the jump cooldown is zero); deeper water uses the `+0.04F` liquid response. A positive shallow sample while airborne also uses the liquid response. The float literal and addition are unchanged. The water-height producer is outside this finding's scope.

Movement meaning: holding jump while touching shallow water on the ground gives an ordinary ground jump in 1.13, while the 1.12.2 response is the small liquid impulse; deeper water retains the liquid impulse.

Checked follow-through: the same sampled-height gate and `+0.04F` response occur in 1.13.1 (`LivingEntity.java` lines 1903–1926, 1470–1472), 1.13.2 (same lines), 1.14 Feather (lines 2197–2220, 1771–1773), 1.14.4 Mojmap (`net/minecraft/world/entity/LivingEntity.java` lines 2201–2221, 1774–1776), and 1.15.2 Mojmap (lines 2266–2287, 1823–1825). No later difference was found in this bounded operation.
