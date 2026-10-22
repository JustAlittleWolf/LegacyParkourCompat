# Successful bed entry centers the player in 1.14.4

1.13.2, Ornithe Feather Gen2 build 2: `net/minecraft/entity/living/player/PlayerEntity.java`, `trySleep`, lines 1228–1288, especially the chunk-gated position write at lines 1268–1280.
1.14.4, Ornithe Feather Gen2 build 2: `net/minecraft/entity/living/player/PlayerEntity.java`, `trySleep`/`startSleeping`, lines 1211–1262; `net/minecraft/entity/living/LivingEntity.java`, `startSleeping` and its position helper, lines 2717–2735.
Difference: After the client-side successful sleep path, 1.13.2 sets size to `0.2F × 0.2F`; if the bed chunk is loaded, it places the player at `x + 0.5F + facingX × 0.4F`, `y + 0.6875F`, `z + 0.5F + facingZ × 0.4F`, otherwise at the centered x/z position. 1.14.4 routes through `LivingEntity.startSleeping` and always sets the player to the centered x/z position at the same y offset, then enters the sleeping pose and clears velocity.
Movement meaning: For a loaded bed and a successful local sleep transition, the 1.13.2 player is offset by 0.4 block along the bed's facing axis; 1.14.4 centers the player. This finding covers the resulting position response, not the conditions for entering a bed.
