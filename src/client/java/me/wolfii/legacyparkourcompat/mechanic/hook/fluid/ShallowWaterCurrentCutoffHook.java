package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether a shallow water current can pass the weak-current cutoff. */
@FunctionalInterface
@MechanicType("entity.fluid.current.shallow_water_cutoff")
public interface ShallowWaterCurrentCutoffHook extends VersionedMechanic {
    boolean bypassWeakCurrentCutoff(ParkourVersion selected, double waterHeight);
}
