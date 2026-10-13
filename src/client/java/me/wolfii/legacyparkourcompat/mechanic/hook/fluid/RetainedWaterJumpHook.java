package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selection of the missing water-jump branch only; false preserves native selection. */
@MechanicType("fluid.retained_water_jump")
public interface RetainedWaterJumpHook extends VersionedMechanic {
    /**
     * Evaluate at native jump selection after input sampling and immobility handling.
     * A true result selects the original jumpInLiquid(WATER) operation exactly once,
     * instead of the remaining native jump branches, then continues native travel.
     * There is deliberately no jump-delay argument: this branch neither requires
     * zero delay nor sets or resets it. Retain native delay decrement and the
     * non-jumping reset. The native affected-by-fluids gate remains at the call site.
     */
    boolean selectsWaterJump(ParkourVersion selectedVersion, boolean passenger,
                             boolean inWater, boolean inLava, boolean jumping,
                             boolean onGround, double retainedWaterDepth);
}
