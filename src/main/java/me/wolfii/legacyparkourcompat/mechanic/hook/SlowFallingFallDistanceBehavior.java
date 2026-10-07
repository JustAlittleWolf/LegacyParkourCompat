package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Fall-distance reset for Slow Falling at the start of travel. */
@FunctionalInterface
@MechanicType("player.fall_distance.slow_falling")
public interface SlowFallingFallDistanceBehavior extends VersionedMechanic {
    void beforeTravel(LivingEntity entity);
}
