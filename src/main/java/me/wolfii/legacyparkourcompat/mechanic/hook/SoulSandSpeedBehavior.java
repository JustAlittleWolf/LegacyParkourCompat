package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Pre-1.15 soul sand callback after collision resolution. */
@MechanicType("block.soul_sand_speed")
public interface SoulSandSpeedBehavior extends VersionedMechanic {
    float movementSpeedFactor(Entity entity, float vanilla);

    void afterCollision(Entity entity);
}
