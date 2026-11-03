# Slow Falling gravity samples velocity after air movement

1.21.1, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2091–2097 and air-travel branch lines 2195–2204; `handleRelativeFrictionAndCalculateMovement(Vec3,float)`, lines 2252–2256.
1.21.2, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `getEffectiveGravity()`, lines 2173–2175; `travelInAir(Vec3)`, lines 2191–2201; `handleRelativeFrictionAndCalculateMovement(Vec3,float)`, lines 2351–2355.

Difference: In 1.21.1, `travel` reads whether Y velocity is nonpositive and applies the Slow Falling cap before air movement; that sampled gravity is subtracted after the movement helper. In 1.21.2, the helper moves the entity before `getEffectiveGravity()` samples Y velocity for the subtraction. The base `getGravity()` value and cap expression `Math.min(getGravity(), 0.01)` are the same; the changed operation is when the sign predicate is read.

Movement meaning: During ordinary local-player air travel with Slow Falling and downward velocity, an unsuppressed bed or slime landing can reverse Y velocity in the helper's `move(SELF, ...)` (`Entity.move`, 1.21.1 lines 657–660; 1.21.2 lines 682–686; `BedBlock`/`SlimeBlock.updateEntityAfterFallOn` in 1.21.1 and `updateEntityMovementAfterFallOn` in 1.21.2). The 1.21.1 tick retains its pre-move nonpositive sample and subtracts capped gravity; 1.21.2 samples the positive bounced velocity and subtracts full gravity. The resulting Y velocity differs after travel. A normal floor collision that leaves Y at zero still takes the capped branch in both versions.

This is a gravity-sampling timing finding. It does not claim a change to the base gravity value or to separate fall-distance accumulation, reset, or landing effects; `Entity.move` invokes fall-damage bookkeeping before the post-move block bounce callback.
