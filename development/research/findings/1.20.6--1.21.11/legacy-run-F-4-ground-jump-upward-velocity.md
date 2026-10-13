# F-4: Ground jump preserves a stronger upward velocity in 1.21.11

1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, jumpFromGround 2069-2080; aiStep 2656.
1.21.11, mojmap: `1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, jumpFromGround 2269-2281.

Difference: When `jumpFromGround()` is reached with a positive current `deltaMovement.y` greater than `getJumpPower()`, 1.20.6 replaces that value with the jump power. 1.21.11 keeps the current larger value through `Math.max`. When current vertical velocity is no greater than jump power, both write jump power. The sprint-jump horizontal impulse expressions are the same in the inspected method; this finding isolates only the vertical write.

Movement meaning: The local-player input jump path reaches `LivingEntity.aiStep()` and its `jumpFromGround()` call when the ground/fluid jump guards pass. Current vertical velocity may be locally calculated, collision-produced, or synchronized; its upstream producers are outside this slice and remain in stage 4/7 dependency closure. The computed jump power and its attribute, effect and block-factor inputs remain in stage 2/5/6 closure. The source proves a different vertical velocity assignment under the stated precondition. It predicts a different subsequent vertical trajectory while the 1.21.11 player carries the larger value. The source does not identify the first release where the max operation appeared.

Original evidence: [archived report](../../archive/legacy-workflows/movement-discovery/runs/1.20.6--1.21.11/findings/F-4-ground-jump-upward-velocity.md).
