# MD-05: Rideable-mob jump charge updates local mount movement

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice ID: stage 1.5 jump edges and riding gates
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player interaction with another entity; a locally controlled rideable jump mount
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A `LocalClientPlayerEntity.mobTick()` lines 606-617 detects jump release while riding a `JumpingMount`, sets the local `horseJumpTimer` to `-10`, and calls `startRidingJump()`; it does not call the mount's `setJumpStrength`. `startRidingJump()` sends the `RIDING_JUMP` action packet with `(int)(getRidingJumpProgress() * 100.0F)`. Source `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, SHA-256 `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053`.
- B `LocalClientPlayerEntity.mobTick()` lines 727-740 detects the same jump release, sets the timer, invokes `jumpingMount.setJumpStrength(MathHelper.floor(getRidingJumpProgress() * 100.0F))`, then calls `startRidingJump()`. The setter is a local object call before the network action. B source `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, SHA-256 `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`.
- The same local-player methods accumulate `horseJumpSize` while jump is held: values rise by `0.1F` per tick for the first nine ticks and then use `0.8F + 2.0F / (horseJumpTimer - 9) * 0.1F`. In B, the floored progress times 100 becomes the argument to the mount setter.
- In both sources, `HorseBaseEntity.setJumpStrength(int)` maps strengths below 90 to `0.4F + 0.4F * strength / 90.0F`, and strengths at least 90 to `1.0F`. During its tick, the horse multiplies this stored `jumpStrength` into vertical launch velocity (`getCustomJumpStrength() * jumpStrength`) and forward launch terms. A horse source SHA-256 `83B1F42AF08544280F5E0F381C116986C521B35E5DC80C54432BDDE283305431`; B source SHA-256 `DAE2A372ADB29488C8CECFF6A86E49C8C87CED7FDB21CC6E3A6BC49A1093B31C`.

## Source-level difference

On a rideable jump mount, B sets the local mount's jump strength from the charge before sending the jump action. A sends the charge to the server but leaves the local mount's jump-strength field untouched at that point. The mount's own tick consumes this field when calculating its launch velocity, so the locally predicted horse movement can differ until server state is applied.

## Reachability and dependencies

The gate requires riding an entity that implements `JumpingMount` and reports `canJump()`, and a jump-button falling edge. The held-input duration determines `horseJumpSize`; this is not a generic player jump change. A and B both send a charge in the riding-jump action, and this finding concerns the additional local setter call in B. It does not assert a difference in server-authoritative jump handling.

## Consequence and uncertainty

Source confirms that B updates the local mount's strength before its next movement tick and that this field scales the horse's launch velocity. A locally predicted mount jump can therefore use a different charge-derived impulse. No trajectory was tested; the extent and duration of any client/server correction are outside this slice.

## Handoff

Independent delta: 1.9.4 applies the charged jump strength to the local rideable mount before sending the jump action; 1.8.9 only sends the charge. Keep the compatibility scope limited to the mounted player's locally predicted movement.
