# Ground jump preserves a stronger upward velocity

1.21.1, Mojmap: `decompiled_minecraft/1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `jumpFromGround`, lines 2056–2067.
1.21.3, Mojmap: `decompiled_minecraft/1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, `jumpFromGround`, lines 2138–2149.
Difference: after the unchanged `getJumpPower()` threshold, 1.21.1 writes `jumpPower` into Y velocity. 1.21.3 writes `Math.max(jumpPower, oldY)`.
Movement meaning: on the ordinary grounded jump path admitted by `LivingEntity.aiStep`, a current upward Y velocity higher than jump power survives the 1.21.3 jump; 1.21.1 replaces it with jump power.