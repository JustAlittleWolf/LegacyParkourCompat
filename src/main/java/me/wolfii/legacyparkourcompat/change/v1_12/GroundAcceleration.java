package me.wolfii.legacyparkourcompat.change.v1_12;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GroundSpeedHook;
import net.minecraft.world.entity.LivingEntity;
/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards) {; lines 1425-1599.
 *   Source SHA-256: 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards) {; lines 1478-1669.
 *   moveRelative; lines [[1537, 1553]].
 *   Source SHA-256: bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
 * 1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   moveRelative, m_70235197; lines [[1841, 1845], [1961, 1962]].
 *   Source SHA-256: 2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-GROUND-ACCEL is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-GROUND-ACCEL
 *   Exact independent source verdict/snapshot identity for ground-accel-float
 *   1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.
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
