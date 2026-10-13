package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.VelocityZeroThresholdHook;
import net.minecraft.world.entity.LivingEntity;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class VelocityZeroThreshold implements VelocityZeroThresholdHook {
    private static final double THRESHOLD = 0.005;

    @Override
    public double filterComponent(LivingEntity entity, double component) {
        return Math.abs(component) < THRESHOLD ? 0.0 : component;
    }
}
