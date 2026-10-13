# F-26.2-SLIME-LANDING-RESTITUTION: Low-speed slime landings stop bouncing

26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java`, move 781-785.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/world/entity/Entity.java`, move 784-786; restituteMovementAfterCollisions 802-843.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, class declaration / field initialization 108.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, class declaration / field initialization 108.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, class declaration / field initialization 125; canSimulateMovement 1269-1271.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, class declaration / field initialization 126; canSimulateMovement 1290-1292.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/block/SlimeBlock.java`, updateEntityMovementAfterFallOn 32-46.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/world/level/block/Blocks.java`, class declaration / field initialization 3221-3223.
26.2, unobfuscated: `26.2/unobfuscated/net/minecraft/world/level/block/Blocks.java`, class declaration / field initialization 2976-2980.
26.1.2, unobfuscated: `26.1.2/unobfuscated/net/minecraft/client/player/AbstractClientPlayer.java`, class declaration / field initialization 19.

Difference: For an unsuppressed downward LocalPlayer collision on an existing slime block where `0 < -currentMovement.y < getEffectiveGravity()`, 26.1.2 dispatches to the slime callback, which changes the negative Y velocity to its positive inverse for a living entity. 26.2's restitution threshold selects zero and the vertical restitution formula writes Y velocity zero. The low-speed threshold itself guarantees this 26.2 result; it does not depend on the initial entity restitution value. The 26.2 threshold is the exact method value `getEffectiveGravity()`, not an assumed constant. The precondition excludes bounce suppression (shift held) and a suppression-tagged block; the cited slime registration and tag resource establish that ordinary slime is not tag-suppressed.

Movement meaning: For the reviewed carpet-to-existing-slime witness, a 1/16-high carpet ledge and unsuppressed non-flying local Player reach incoming Y about -0.0784 and clipped displacement -0.0625 at default gravity 0.08. 26.1.2 slime reverses the negative Player Y velocity; 26.2's low-speed restitution gate writes zero. Default slime is absent from the suppression tag. Nonzero clipped displacement is allowed; no zero-contact assumption or runtime result is implied.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-SLIME-LANDING-RESTITUTION.md).
