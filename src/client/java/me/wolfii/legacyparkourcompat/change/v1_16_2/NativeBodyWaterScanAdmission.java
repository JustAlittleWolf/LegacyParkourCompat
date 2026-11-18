package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterScanAdmissionHook;

/** Keeps the existing body scan admission for unreviewed releases before 1.17. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class NativeBodyWaterScanAdmission implements BodyWaterScanAdmissionHook {
    @Override
    public boolean admitsColumn(int minY, int maxY) {
        return false;
    }
}
