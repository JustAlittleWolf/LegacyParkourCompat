package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;

/** Native double accumulator fence so the 1.21.4 float delta cannot apply to newer profiles. */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class DoubleFallDistanceAccumulator implements FallDistanceAccumulatorHook {
    @Override
    public double updatedDistance(double nativeUpdatedDistance) {
        return nativeUpdatedDistance;
    }
}
