package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.FlowScanAreaAdmissionHook;

/** Resolver boundary: profiles before the supported 1.8.9 delta retain native admission. */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NativeFlowScanAreaAdmission implements FlowScanAreaAdmissionHook {
    public NativeFlowScanAreaAdmission() {
    }

    @Override
    public boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                          int maxBlockX, int maxBlockY, int maxBlockZ) {
        return true;
    }
}
