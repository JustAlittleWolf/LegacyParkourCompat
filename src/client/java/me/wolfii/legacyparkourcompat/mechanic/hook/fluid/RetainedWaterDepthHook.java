package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;

/** One scalar update after the original native water refresh has completed. */
@MechanicType("fluid.retained_water_depth")
public interface RetainedWaterDepthHook extends VersionedMechanic {
    /**
     * The generic client integration owns a per-player double initialized to 0.0.
     * Supply the raw bounding box used by the refresh, before any deflation,
     * the previous retained value, and the native WATER depth after the refresh.
     * Store the returned value once at that refresh phase, before AI jump selection.
     * This hook must not change the native water flag, fluid map or fluid pushing.
     */
    double afterWaterRefresh(ParkourVersion selectedVersion, boolean passenger,
                             AABB rawBox, double previousDepth, double nativeWaterDepth);
}
