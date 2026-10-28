package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.OptionalInt;

/** Supplies an exclusive upper bound for the body-contact water scan. */
@MechanicType("water.body_contact_scan_bound")
public interface WaterBodyContactScanBoundHook extends VersionedMechanic {
    /** Returns the selected historical upper bound, or empty to keep native ceil behavior. */
    OptionalInt exclusiveUpperBound(double boundsMax);
}
