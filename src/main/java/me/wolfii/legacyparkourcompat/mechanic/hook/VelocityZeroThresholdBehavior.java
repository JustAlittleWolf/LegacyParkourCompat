package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
@MechanicType("player.velocity.zero_threshold")
public interface VelocityZeroThresholdBehavior extends VersionedMechanic {
    double filterComponent(LivingEntity entity, double component);
}
