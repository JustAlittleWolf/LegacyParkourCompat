# Simultaneous sneak and jump flight arithmetic

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, lines 797–806.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `mobTick`, lines 728–743.

Difference: Under `abilities.flying && isCamera()`, 1.13.2 handles sneak and jump in separate conditionals: sneaking subtracts `flySpeed * 3.0F` from double `velocityY`, then jumping adds the same float product. 1.14.4 increments/decrements integer `j` for those inputs and applies one vector update only when `j != 0`. With both inputs held, 1.14.4 leaves vertical velocity untouched; 1.13.2 executes two double assignments. Lateral input unscaling on sneak occurs in both.

Movement meaning: Preconditions are flying ability, local camera player, simultaneous sneak+jump input.
