package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Speed passed to moveRelative during ordinary ground travel. */
@MechanicType("player.travel.ground_acceleration")
public interface GroundAccelerationBehavior extends VersionedMechanic {
    float speed(LivingEntity entity, float blockFriction, float vanilla);
}
