package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Fall-distance reset for Levitation before air travel writes vertical velocity. */
@FunctionalInterface
@MechanicType("player.fall_distance.levitation")
public interface LevitationFallDistanceBehavior extends VersionedMechanic {
    void beforeAirTravel(LivingEntity entity);
}
