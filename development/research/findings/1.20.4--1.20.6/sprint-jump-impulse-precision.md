# Sprint-jump impulse precision

1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/world/entity/Entity.java`, addDeltaMovement 3174-3176.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/Entity.java`, addDeltaMovement 3189-3191.
1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, jumpFromGround 2008-2017; getJumpPower 2000-2002.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, jumpFromGround 2069-2082.
1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/world/phys/Vec3.java`, add 88-90.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/phys/Vec3.java`, add 88-90.
1.20.4, mojmap: `1.20.4/mojmap/net/minecraft/util/Mth.java`, sin 47-53.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/util/Mth.java`, sin 48-54.

Difference: 1.20.4 multiplies float yaw trig values by 0.2F before widening; 1.20.6 multiplies widened trig values by double 0.2 before Vec3 addition.

Movement meaning: Existing nonzero-power sprinting Player ground jumps receive differently rounded X/Z impulse. The independent low-power skip requires newer input and is excluded centrally.
