# Fall-flying precedes swimming in the historical desired pose

1.21.4, Mojmap: `net/minecraft/world/entity/player/Player.java`, `updatePlayerPose`, lines 434–461; `net/minecraft/world/entity/Entity.java`, `baseTick` and `updateSwimming`, lines 430–455 and 1306–1313; `net/minecraft/world/entity/LivingEntity.java`, `updateFallFlying`, line 2825.

1.21.5, Mojmap: `net/minecraft/world/entity/player/Player.java`, `updatePlayerPose` and `getDesiredPose`, lines 449–476; `net/minecraft/world/entity/Entity.java`, `baseTick` and `updateSwimming`, lines 438–463 and 1336–1343; `net/minecraft/world/entity/LivingEntity.java`, `updateFallFlying`, line 2815.

Difference: 1.21.4 selects `FALL_FLYING` before `SWIMMING`; 1.21.5 selects swimming first. A local player can start fall-flying while dry, enter water through a non-climbable route, and retain the fall-flying flag while the underwater sprinting gate sets the swimming flag. This path excludes sleeping, passengers, spectators and ability flight and requires the usual input, food and usable-glider gates. Both selectors retain the preliminary swimming-fit guard and the selected-pose fit/fallback.

Movement meaning: when those flags overlap and the common swimming-fit guard passes, the old selector writes `FALL_FLYING` and the new selector writes `SWIMMING`. Both poses have the same 0.6F by 0.6F dimensions and 0.4F eye height at these endpoints. This establishes a conditional pose-state difference; it does not establish a body-box or trajectory difference, sleeping reachability, or server state production.
