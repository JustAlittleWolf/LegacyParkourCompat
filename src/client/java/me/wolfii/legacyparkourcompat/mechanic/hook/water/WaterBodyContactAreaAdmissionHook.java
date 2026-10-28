package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Controls admission to the body-contact water scan. */
@MechanicType("water.body_contact_area_admission")
public interface WaterBodyContactAreaAdmissionHook extends VersionedMechanic {
    /** Bounds use floor minima and exclusive upper ends. */
    boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                   int maxBlockX, int maxBlockY, int maxBlockZ);
}
