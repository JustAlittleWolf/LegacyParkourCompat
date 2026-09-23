package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Historical ground acceleration speed at a given block friction. */
@MechanicType("player.ground.speed")
public interface GroundSpeedBehavior extends VersionedMechanic {
    float speed(LivingEntity entity, float blockFriction, float vanilla);
}
