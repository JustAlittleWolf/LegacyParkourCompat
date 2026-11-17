# Small velocity component cutoff
1.8.9, Ornithe Feather: `net/minecraft/entity/living/LivingEntity.java`, `mobTick` lines 1406–1416.
1.9, Ornithe Feather: `net/minecraft/entity/living/LivingEntity.java`, `mobTick` lines 1653–1663.
Difference: the pre-travel cleanup sets each velocity component to zero when its absolute value is below `0.005` in 1.8.9 and below `0.003` in 1.9. The comparison is strict (`<`) on both releases and is applied independently to X, Y and Z before local AI/input movement and `moveRelative`.
Movement meaning: for a locally controlled player's component with magnitude at least `0.003` and below `0.005`, 1.8.9 clears that component while 1.9 retains it. The value can therefore contribute to the position change computed by the ensuing movement call in 1.9 but not 1.8.9. `Entity.move` then resolves the resulting requested displacement against gathered collision boxes.
