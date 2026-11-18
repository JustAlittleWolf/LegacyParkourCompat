package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterScanAdmissionHook;

/** Keeps the earlier water-body path before the 1.13 inset scan. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeBodyWaterScanAdmission implements BodyWaterScanAdmissionHook {
    @Override
    public boolean admitsColumn(int minY, int maxY) {
        return false;
    }
}
