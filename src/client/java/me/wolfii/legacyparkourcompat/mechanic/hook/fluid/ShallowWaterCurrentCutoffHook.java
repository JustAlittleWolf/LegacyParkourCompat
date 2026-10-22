package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether a water current can pass the modern weak-current cutoff. */
@FunctionalInterface
@MechanicType("entity.fluid.current.shallow_water_cutoff")
public interface ShallowWaterCurrentCutoffHook extends VersionedMechanic {
    boolean bypassWeakCurrentCutoff(double waterHeight);
}
