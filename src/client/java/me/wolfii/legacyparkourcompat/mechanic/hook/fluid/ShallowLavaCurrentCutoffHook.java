package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether lava current bypasses the modern accumulated-current cutoff. */
@FunctionalInterface
@MechanicType("entity.fluid.lava.current.shallow_cutoff")
public interface ShallowLavaCurrentCutoffHook extends VersionedMechanic {
    boolean bypassWeakCurrentCutoff(double lavaHeight);
}
