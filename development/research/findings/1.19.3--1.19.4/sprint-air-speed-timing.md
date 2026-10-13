# Sprint air-speed lookup timing

1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2201-2248.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, handleRelativeFrictionAndCalculateMovement 2152-2203.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 652-819.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, aiStep 652-819.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 496-517.
1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 499-510; getFlyingSpeed 2112-2118.

Difference: 1.19.3 travel reads flyingSpeed stored only after the preceding travel; 1.19.4 getFlyingSpeed reads the current sprint flag during this travel.

Movement meaning: Sprint transitions affect old cached air acceleration one travel call later; arithmetic-value difference is separately recorded.
