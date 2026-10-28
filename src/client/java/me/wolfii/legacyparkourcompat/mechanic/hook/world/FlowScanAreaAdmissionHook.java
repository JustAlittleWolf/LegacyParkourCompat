package me.wolfii.legacyparkourcompat.mechanic.hook.world;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Controls admission to the historical water flow scan. */
@MechanicType("world.flow_scan_area_admission")
public interface FlowScanAreaAdmissionHook extends VersionedMechanic {
    /**
     * Returns whether a flow scan is admitted for the integer query bounds passed to
     * the historical {@code World.isAreaLoaded} call. The minima are
     * {@code floor(bounds.minAxis)} and the maxima are {@code floor(bounds.maxAxis + 1.0)};
     * maxima therefore remain the scan's exclusive loop bounds.
     *
     * <p>For client player flow in 1.8.9, only the vertical overlap with block levels
     * {@code [0, 256)} restricts scanning; horizontal chunk coordinates do not.
     */
    boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                   int maxBlockX, int maxBlockY, int maxBlockZ);
}
