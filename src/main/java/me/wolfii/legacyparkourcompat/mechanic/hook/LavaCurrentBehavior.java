package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Whether vanilla lava flow may push this entity. */
@FunctionalInterface
@MechanicType("entity.fluid.lava.current")
public interface LavaCurrentBehavior extends VersionedMechanic {
    boolean allowLavaCurrent(Entity entity, boolean vanilla);
}
