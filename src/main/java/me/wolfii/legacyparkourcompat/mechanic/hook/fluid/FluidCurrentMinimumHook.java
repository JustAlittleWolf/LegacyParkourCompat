package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Historical minimum magnitude applied to weak fluid-current impulses. */
@FunctionalInterface
@MechanicType("entity.fluid.current.minimum")
public interface FluidCurrentMinimumHook extends VersionedMechanic {
    boolean applyMinimum(Entity entity);
}
