package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Restores the tick stage and block selection of historical soul sand slowdown. */
@MechanicType("block.soul_sand_speed")
public interface SoulSandSpeedBehavior extends VersionedMechanic {
    float movementSpeedFactor(Entity entity, float vanilla);

    void baseTick(Entity entity);
}
