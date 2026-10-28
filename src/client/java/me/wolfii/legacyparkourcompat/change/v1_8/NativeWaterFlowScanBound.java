package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WaterFlowScanBoundHook;

import java.util.OptionalInt;

/** Keeps the native scan bound for 1.8–1.8.8 profiles. */
@MovementChange(emulates = ParkourVersion.V1_8_5)
public final class NativeWaterFlowScanBound implements WaterFlowScanBoundHook {
    public NativeWaterFlowScanBound() {
    }

    @Override
    public OptionalInt exclusiveUpperBound(double boundsMax) {
        return OptionalInt.empty();
    }
}
