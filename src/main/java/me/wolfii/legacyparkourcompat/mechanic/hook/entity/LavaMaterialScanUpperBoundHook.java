package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Exclusive maximum block coordinate used by the historical material scan. */
@MechanicType("entity.lava_material_scan_upper_bound")
public interface LavaMaterialScanUpperBoundHook extends VersionedMechanic {
    int exclusiveUpperBound(double maximum);
}
