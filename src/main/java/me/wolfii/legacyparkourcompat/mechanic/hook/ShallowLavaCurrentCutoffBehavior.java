package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether shallow lava current bypasses the accumulated-current cutoff. */
@FunctionalInterface
@MechanicType("entity.fluid.lava.current.shallow_cutoff")
public interface ShallowLavaCurrentCutoffBehavior extends VersionedMechanic {
    boolean bypassWeakCurrentCutoff(double lavaHeight);
}
