package me.wolfii.legacyparkourcompat.mechanic.hook.world;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.OptionalInt;

/**
 * Supplies a historical exclusive upper bound for cells contributing to the
 * liquid-flow velocity vector. An empty result leaves the traversal's native
 * upper-bound calculation in place.
 *
 * <p>The caller owns the complete scan: its lower bound remains
 * {@code floor(bounds.minAxis)}, and its integer loop must use the half-open
 * interval {@code [lowerBound, exclusiveUpperBound)}. This hook only selects
 * which already-scanned cells may contribute to the flow vector. Material,
 * loaded-chunk, liquid-surface, flow, normalization, and impulse gates remain
 * owned by the caller. It must not control water-presence reporting.
 */
@MechanicType("world.water_flow_scan_bound")
public interface WaterFlowScanBoundHook extends VersionedMechanic {
    /**
     * @param boundsMax the maximum coordinate on the axis currently being scanned
     * @return the historical exclusive scan end, or empty to preserve the
     *         native scan end
     */
    OptionalInt exclusiveUpperBound(double boundsMax);
}
