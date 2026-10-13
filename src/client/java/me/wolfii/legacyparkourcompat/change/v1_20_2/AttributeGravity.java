package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GravityHook;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 *   aiStep.
 *   isFallFlying.
 *   getFluidFallingAdjustedMovement.
 * 1.20.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel.
 *   tryToStartFallFlying.
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   getDefaultGravity.
 *   getDefaultGravity.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 *   getDefaultGravity.
 *   createLivingAttributes.
 *   getFluidFallingAdjustedMovement.
 *   travel.
 *   getDefaultGravity.
 *   createLivingAttributes.
 * 1.20.6/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java:
 *   class declaration / field initialization.
 *   class declaration / field initialization.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   createAttributes.
 *   class declaration / field initialization.
 *   tryToStartFallFlying.
 *   createAttributes.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.20.6/mojmap/net/minecraft/world/entity/Entity.java. Archived source identity correspondence remains unresolved for them.
 *   Eligible vanilla 1.20.4 Player producer for noGravity in fall-flight; non-default newer generic GRAVITY attribute is excluded and source acceptance does not establish producer applicability.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class AttributeGravity implements GravityHook {
    @Override
    public double gravity(Entity entity, double vanilla) {
        return entity.isNoGravity() ? 0.0 : 0.08;
    }
}
