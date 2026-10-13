# F-26.2-SLIME-LANDING-RESTITUTION: Low-speed slime landings stop bouncing

- **26.1.2**, unobfuscated: `net/minecraft/world/entity/Entity.java`; lines 781–785.
- **26.2**, unobfuscated: `net/minecraft/world/entity/Entity.java`; lines 784–786.

## Difference

For an unsuppressed downward LocalPlayer collision on an existing slime block where `0 < -currentMovement.y < getEffectiveGravity()`, 26.1.2 dispatches to the slime callback, which changes the negative Y velocity to its positive inverse for a living entity. 26.2's restitution threshold selects zero and the vertical restitution formula writes Y velocity zero.

The low-speed threshold itself guarantees this 26.2 result; it does not depend on the initial entity restitution value.

The 26.2 threshold is the exact method value `getEffectiveGravity()`, not an assumed constant.

The precondition excludes bounce suppression (shift held) and a suppression-tagged block; the cited slime registration and tag resource establish that ordinary slime is not tag-suppressed.

## Movement meaning

In both versions `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player`; `Player` extends `Avatar`, and `Avatar` extends `LivingEntity`.

The paired `canSimulateMovement()` override in `Player.java` returns `!this.level().isClientSide() || this.isLocalPlayer()`, so the local client player enters the guarded movement response.

Normal player travel delegates through `LivingEntity`, whose movement path calls `Entity.move`; the broader player tick/travel call graph remains tracked in the open run slices.

Bounce suppression reads the player's shift state in 26.2 `Entity.isSuppressingBounce()`.

The movement result is the player entity's Y velocity, consumed by subsequent player travel/ticks.

This finding concerns client-side source behavior for the local player; it makes no claim about server synchronization or damage.

The source proves that this bounded low-speed landing writes positive Y velocity in 26.1.2 and zero Y velocity in 26.2 under the stated conditions.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-SLIME-LANDING-RESTITUTION.md).
