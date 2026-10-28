package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactAreaAdmissionHook;

/**
 * Keeps body-contact admission native outside the bounded 1.8.9 integration.
 * This is a scope fence, not a historical-equivalence claim for earlier profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NativeWaterBodyContactAreaAdmission implements WaterBodyContactAreaAdmissionHook {
    public NativeWaterBodyContactAreaAdmission() {
    }

    @Override
    public boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                          int maxBlockX, int maxBlockY, int maxBlockZ) {
        // The accepted 1.8.9 World.applyLiquidDrag/World.isAreaLoaded client
        // gate is installed only at V1_8_9; earlier profile coverage stays native.
        return true;
    }
}
