package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PositionPacketThresholdBehavior;

@MovementChange(emulates = ParkourVersion.V1_18)
public final class PositionPacketThreshold implements PositionPacketThresholdBehavior {
    @Override
    public double squaredDisplacementThreshold(double vanillaThreshold) {
        return 9.0E-4;
    }
}
