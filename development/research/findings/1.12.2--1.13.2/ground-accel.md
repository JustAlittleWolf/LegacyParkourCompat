# F-GROUND-ACCEL — 1.13.2 changes the grounded acceleration scalar

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: - 1.12.2 enters the ordinary land/air branch after its locally-controlled/logical-side, not-water, not-lava, and not-fall-flying guards.

When `onGround`, it reads the block slipperiness, multiplies by `0.91F`, and computes `0.16277136F / (s * s * s)`; it then multiplies by `getSpeed()` and passes that value to `updateVelocity`.

When not grounded, the branch uses `speedInAir` instead.

- 1.13.2 has the corresponding guards and ordinary branch.

When `onGround`, it reads the block slipperiness through `getSlipperiness()`, multiplies by `0.91F`, and computes `0.16277137F / (t * t * t)` before multiplying by `getSpeed()` and calling `updateVelocity`; the non-ground branch still uses `speedInAir`.

- `BLOCK-FRICTION` establishes the shared 0.6F default for corresponding 1.12.2-era blocks, with matching 0.98F ice/packed-ice and 0.8F slime values.

For the shared default friction, evaluating the source-ordered IEEE-754 binary32 operations gives 1.12.2 ground multiplier `0x3f7ffffd` and 1.13.2 `0x3f7ffffe`, one representable float apart.

This is a static evaluation of the cited expressions, not a measured movement trajectory.

No claim is made that every friction denominator produces a distinct quotient.

Movement meaning: - The local player path copies sampled input into movement fields in `LocalClientPlayerEntity.serverTickAi()` (1.12.2 lines 631-642 / 1.13.2 635-646). `LivingEntity.mobTick()` invokes the local player's virtual `serverTickAi()` and later calls `moveRelative()` (1.12.2 call sites lines 1822 and 1847; 1.13.2 lines 1898 and 1926).

In `moveRelative`, both sides allow the locally controlled player into this branch; the grounded condition selects the changed scalar. `PlayerEntity.getSpeed()` supplies the movement-speed attribute (1.12.2 line 1408 / 1.13.2 line 1489); its normal player base is 0.1F on both sides.

- Finding-specific dependencies closed: source branch and player call path above; `BLOCK-FRICTION` proves the shared default coefficient and all historical explicit slipperiness values.

The scalar supplied to `updateVelocity` differs for the concrete shared default-friction case.
