# Ground acceleration operation order

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1537–1553.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative, m_70235197`, lines 1841–1845, 1961–1962.

Difference: 1.13.2 computes `t = slipperiness * 0.91F`, then `u = 0.16277137F / (t * t * t)`, then multiplies `getSpeed() * u`. 1.14.4's helper returns `getSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness))` when grounded. The algebraic forms are close, but constants and float operation order differ.

Movement meaning: Ordinary ground/air travel samples the block below the entity shape for slipperiness and uses onGround to select the helper result.
