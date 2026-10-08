
# F-01: Jump Boost is summed into ground-jump power at float precision

- Older version A: Minecraft 1.19.4
- Newer version B: Minecraft 1.20.1
- Mechanic / coverage slice IDs: ground jump; S3-01, S1-01, S1-02
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.19.4, 1.20.1]
- Runtime validation: not performed

## Paired evidence

- A: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java; getJumpPower/getJumpBoostPower lines 1947-1953 and jumpFromGround lines 1955-1965; SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165. The float base and double boost are added into a double local before setDeltaMovement.
- B: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java; getJumpPower/getJumpBoostPower lines 1969-1975 and jumpFromGround lines 1977-1986; SHA-256 decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f. getJumpPower returns float and evaluates 0.42F * getBlockJumpFactor() + getJumpBoostPower() before setDeltaMovement widens it.
- Player reachability: Player#jumpFromGround lines 1429-1437 in A and 1435-1443 in B both call super.jumpFromGround(); hashes 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2 / 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
- Caller: LivingEntity#aiStep A lines 2512-2537 and B lines 2531-2556 call jumpFromGround under corresponding grounded or liquid-jump gates.

## Source-level difference

A adds the float block-dependent power and double effect bonus as double before assigning the upward velocity. B adds two float values inside float-returning getJumpPower, rounding the sum to float before it is widened for velocity assignment. With Jump Boost active, the source permits a different upward delta from this rounding.

## Reachability and dependencies

Local keyboard jump input reaches LocalPlayer#aiStep and delegates through Player and LivingEntity. LivingEntity#aiStep invokes jumpFromGround when jump state is set, the ground/low-fluid gates pass, and noJumpDelay == 0; the Player override delegates to the changed superclass. getJumpBoostPower reads MobEffects.JUMP and its amplifier. Effect application and block jump-factor sources remain in INV-MODIFIERS / INV-WORLD-MOVEMENT.

## Consequence and uncertainty

The paired source proves a float-versus-double sum on a reachable player ground-jump path. No every-input quantification or measured trajectory is claimed. Later travel, collision and client/server correction can affect final position.

## Handoff

Preserve B's float sum only for versions selecting this delta and A's separate double bonus for A-era movement. First changed release within the interval is unknown.
