# Sprinting doubles airborne flight acceleration

1.7.10, Ornithe Feather: `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative`, lines 1237–1250; `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1095–1112.
1.8, Ornithe Feather: `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative`, lines 1277–1293; `net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1115–1136.
Difference: For a flying player without a vehicle, 1.7.10 temporarily sets `speedInAir` to `abilities.getFlySpeed()`. 1.8 sets it to `abilities.getFlySpeed() * (isSprinting() ? 2 : 1)`. Both delegate to `LivingEntity.moveRelative` and restore the previous `speedInAir`; that method selects `speedInAir` when airborne and applies it through `updateVelocity`.
Movement meaning: A locally controlled player who is flying, sprinting, airborne, not riding, and has nonzero horizontal input receives twice the fly-speed horizontal acceleration scale in 1.8. The scale selection and its use for airborne input are source facts; accumulated position divergence is a predicted trajectory effect.
