package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Block speed multiplier applied after collision resolution. */
@FunctionalInterface
@MechanicType("entity.movement.block_speed_factor")
public interface BlockSpeedFactorBehavior extends VersionedMechanic {
    float movementSpeedFactor(Entity entity, float vanilla);
}
