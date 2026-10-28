package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/**
 * Decides whether lava-derived movement results are exposed for the current
 * historical base-tick water decision.
 *
 * <p>The input is the already-computed result of the exact legacy WATER
 * presence probe. Callers must not substitute current {@code Entity.isInWater()}
 * or modern fluid-tracker state. For the 1.16/1.16.1 client Player path, compute
 * the probe at the matching baseTick phase (including the historical Boat
 * exclusion) and retain its result until the next baseTick. This gate may be
 * applied to both public LAVA depth exposure and LAVA current application.
 * It does not perform the probe or own its lifetime.
 *
 * <p>Evaluating this projection must not mutate eye-fluid trackers, the
 * tracker's internal fluid-presence value used by the separate
 * {@code Entity.isInLava()} predicate, or WATER depth.
 */
@MechanicType("fluid.lava_result_projection")
public interface LavaResultProjectionHook extends VersionedMechanic {
    /**
     * Returns whether a lava-derived result is allowed through the projection.
     *
     * @param legacyWaterPresenceProbeResult exact old WATER presence-probe
     *                                      Boolean latched for this baseTick;
     *                                      this is not modern water state
     * @return {@code false} when the legacy probe was positive, otherwise
     *         {@code true}
     */
    boolean allowLavaResults(boolean legacyWaterPresenceProbeResult);
}
