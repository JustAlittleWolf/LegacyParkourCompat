package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether a shallow water current can pass the weak-current cutoff. */
@FunctionalInterface
@MechanicType("entity.fluid.current.shallow_water_cutoff")
public interface ShallowWaterCurrentCutoffBehavior extends VersionedMechanic {
    boolean bypassWeakCurrentCutoff(double waterHeight);
}
