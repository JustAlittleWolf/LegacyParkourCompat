package me.wolfii.legacyparkourcompat.change.v1_12;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GroundSpeedHook;
import net.minecraft.world.entity.LivingEntity;
/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards).
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards).
 *   moveRelative.
 * 1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   moveRelative, m_70235197.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-GROUND-ACCEL is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-GROUND-ACCEL
 *   Exact independent source verdict/snapshot identity for ground-accel-float
 *   1.13.2 original Feather mapped-client JAR remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class GroundAcceleration implements GroundSpeedHook {
    @Override
    public float speed(LivingEntity entity, float blockFriction, float vanilla) {
        if (!entity.onGround()) {
            return vanilla;
        }
        float friction = blockFriction * 0.91F;
        float acceleration = 0.16277136F / (friction * friction * friction);
        return entity.getSpeed() * acceleration;
    }
}
