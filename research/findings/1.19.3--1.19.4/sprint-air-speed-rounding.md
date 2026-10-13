# Sprint air-speed float rounding

1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2201-2248.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2152-2203.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 652-819.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 652-819.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 496-517.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 499-510; getFlyingSpeed 2112-2118.

Difference: 1.19.3 computes float 0.02F then adds 0.006F (bits 0x3cd4fdf4); 1.19.4 uses literal 0.025999999F (bits 0x3cd4fdf3). Non-sprinting remains 0.02F.

Movement meaning: Under stable ordinary airborne sprint, the acceleration coefficient differs by one float ULP; ability-flight is excluded.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.3--1.19.4/findings/F-01-sprint-air-speed.md).
