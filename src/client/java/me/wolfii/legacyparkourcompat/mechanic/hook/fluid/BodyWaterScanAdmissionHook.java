package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Admission for a separately projected body-water column when native padding rejects it. */
@MechanicType("fluid.body-water-scan-admission")
public interface BodyWaterScanAdmissionHook extends VersionedMechanic {
    /** Both Y coordinates are included in the sampled column. */
    boolean admitsColumn(int minY, int maxY);
}
