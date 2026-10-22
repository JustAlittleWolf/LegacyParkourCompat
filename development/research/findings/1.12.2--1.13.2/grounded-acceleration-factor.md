# Grounded movement acceleration coefficient
1.12.2, Ornithe Feather: `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, line 1485.
1.13.2, Ornithe Feather: `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, line 1545.
Difference: In the non-fall-flying movement branch, the grounded acceleration factor changes from `0.16277136F / (s * s * s)` to `0.16277137F / (t * t * t)`. The slipperiness multiplier is computed first in both versions; the factor is used for input acceleration when `onGround` is true, while the airborne branch uses `speedInAir`.
Movement meaning: A grounded player applying movement input receives a slightly different horizontal acceleration before collision movement. Exact later position effects depend on the input, friction and collision path.
