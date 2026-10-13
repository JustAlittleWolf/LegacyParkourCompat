# Sneaking pose height

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose`, lines 334–360.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `f_48397842` pose-dimension map, lines 109–117.

Difference: The sneaking collision height is 1.65 in 1.13.2 and 1.5 in 1.14.4, with width 0.6.

Movement meaning: The dimension provider supplies the player collision box and pose-fit checks; a 1.5-high opening fits the newer sneaking box while obstructing the older 1.65-high box. Pose selection and collision-aware resizing are separately recorded.
