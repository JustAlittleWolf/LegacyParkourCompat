# shallow lava uses a different vertical adjustment

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1913–1926.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1959–1978.

Difference: The branch before movement is shared in outline: add relative input and move once. When 1.16.5's lava height is at or below the eye-height-derived threshold, it uses vertical multiplier `0.8F` instead of 1.15.2's `0.5` and applies the falling-adjustment helper before common gravity. The helper subtracts `d / 16.0` from y unless gravity is disabled or sprinting; those latter cases return the input vector unchanged. At greater lava height 1.16.5 follows the 1.15.2-style `scale(0.5)` path, preserving the branch distinction.

Movement meaning: For a non-flying Player in lava, 1.16.5's `Player.isAffectedByFluids()` returns true and `LivingEntity.canStandOnFluid()` returns false.
