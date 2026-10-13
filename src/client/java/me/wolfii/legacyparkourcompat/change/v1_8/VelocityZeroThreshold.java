package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.VelocityZeroThresholdHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void mobTick().
 * 1.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void mobTick(): first stable 0.003 component threshold.
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void mobTick().
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class VelocityZeroThreshold implements VelocityZeroThresholdHook {
    private static final double THRESHOLD = 0.005;

    @Override
    public double filterComponent(LivingEntity entity, double component) {
        return Math.abs(component) < THRESHOLD ? 0.0 : component;
    }
}
