# Player sneaking pose height
1.13.2, Ornithe Feather: `net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose`, lines 334–360. The sneaking branch proposes width `0.6F`, height `1.65F`, applying that size only when the proposed box passes `world.hasNoCollisions(null, box)`.
1.14.4, Ornithe Feather: `net/minecraft/entity/living/player/PlayerEntity.java`, pose dimensions lines 109–117 and `updatePlayerPose` lines 343–370; `net/minecraft/entity/Entity.java`, `m_84943753`, lines 2245–2266. `Pose.SNEAKING` uses dimensions `0.6F × 1.5F` and the pose update applies those dimensions.
Difference: the sneaking player body is 0.15 blocks shorter. An otherwise crouching player can occupy a 1.5–1.65-block clearance band in 1.14.4 that the 1.13.2 crouch box cannot fit. Exact block-shape admission depends on collision-source closure.
Movement meaning: narrower vertical clearance for ordinary crouch movement; endpoint-only source evidence, with exact cutover unknown.
