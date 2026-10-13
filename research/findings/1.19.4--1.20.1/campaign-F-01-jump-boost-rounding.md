

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, getJumpPower 1947-1953; aiStep 2512-2537.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, getJumpPower 1969-1975; aiStep 2512-2537.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, jumpFromGround 1429-1437.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/player/Player.java`, jumpFromGround 1435-1443.

Difference: 1.19.4 adds the float block-dependent power and double effect bonus as double before assigning the upward velocity. 1.20.1 adds two float values inside float-returning getJumpPower, rounding the sum to float before it is widened for velocity assignment. With Jump Boost active, the source permits a different upward delta from this rounding.

Movement meaning: Local keyboard jump input reaches LocalPlayer#aiStep and delegates through Player and LivingEntity. LivingEntity#aiStep invokes jumpFromGround when jump state is set, the ground/low-fluid gates pass, and noJumpDelay == 0; the Player override delegates to the changed superclass. getJumpBoostPower reads MobEffects.JUMP and its amplifier. Effect application and block jump-factor sources remain in INV-MODIFIERS / INV-WORLD-MOVEMENT. The paired source proves a float-versus-double sum on a reachable player ground-jump path. No every-input quantification or measured trajectory is claimed. Later travel, collision and client/server correction can affect final position.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-01-jump-boost-rounding.md).
