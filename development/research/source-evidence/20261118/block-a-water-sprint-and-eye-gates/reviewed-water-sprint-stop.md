# Water sprint cancellation predicates

1.12.2, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()`, lines 743-745.
1.13, ornithe-feather: `1.13/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()`, lines 748-758.
1.13.1, ornithe-feather: `1.13.1/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()`, lines 750-760.
1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick()`, lines 750-760.

Difference: In 1.12.2, sprint cancellation checks only forward input below `0.8F`, horizontal collision, or insufficient food (unless flight is available). Starting with stable 1.13, the non-swimming branch adds `isInWater() && !isSubmergedInWater()` to that stop condition. The swimming branch instead stops when `(!onGround && !sneaking && (forward < 0.8F || !foodEligible)) || !isInWater()`; Java's `&&` precedence makes the airborne/sneaking/threshold group bind before the final `||`. Thus a swimming player is not cancelled merely for horizontal collision or being surface-wet while still in water. The complete cancellation expression is identical in 1.13, 1.13.1, and 1.13.2.

The predicates distinguish body-water contact from eye submersion. `Entity.checkWaterState()` refreshes `inWater` from the entity's water volume before the LivingEntity tick calls `mobTick()`. `Entity.isSubmergedInWater()` is `submergedInWater && isInWater()`; the submerged flag is refreshed from `isSubmergedIn(FluidTags.WATER)`, which samples fluid at `y + getEyeHeight()` and checks its height. `PlayerEntity.tick()` also refreshes its client-visible submersion field before its superclass tick. Consequently, a non-swimming player whose body contacts water while the eyes are above water cancels sprint; a swimming player may remain sprinting in that surface-water state if the swimming-specific stop gates are false.

Movement meaning: The change is the local sprint-stop decision, not water state itself or sprint-start eligibility. Preserve the separate pre-existing `isSwimming()` branch: applying the ordinary surface-water stop to it would cancel sprint while it remains in water, contrary to the source.
