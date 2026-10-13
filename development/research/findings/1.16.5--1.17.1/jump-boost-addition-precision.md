# Jump Boost ground-jump vertical impulse uses different addition precision

1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower`, lines 1878–1896.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `getJumpPower`, lines 1967–1985.

Difference: In 1.16.5, `getJumpPower()` returns a float. `jumpFromGround()` stores it in float `f` and, when `MobEffects.JUMP` is active, adds `0.1F * (amplifier + 1)` to that float before passing `f` to `setDeltaMovement(double, double, double)`. The compound assignment rounds the sum to float before widening. In 1.17.1, `getJumpPower()` still returns the same float, but `jumpFromGround()` stores `getJumpPower() + getJumpBoostPower()` in double `d`. The helper's active-effect expression uses the same float literal and integer amplifier, then returns the value as double. The float base and float-valued boost are therefore widened and added as doubles. With Jump Boost I (`amplifier == 0`) and block jump factor `1.0F`, 1.16.5 writes vertical velocity `0.5199999809265137`; 1.17.1 writes `0.5199999883770943`. Both retain the same x/z components, set `hasImpulse`, and apply the same sprint-only horizontal impulse afterward. `Entity.setDeltaMovement` stores the supplied vector directly in both versions.

Movement meaning: The S1-01/S1-02 input and tick path reaches the local player's `LivingEntity.aiStep`.
