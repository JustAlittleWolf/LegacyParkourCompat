package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;

/**
 * Keeps closest-later resolution from applying the 1.21 accumulator change to older profiles,
 * whose source applicability is not established by this finding.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class NativeFallDistanceAccumulator implements FallDistanceAccumulatorHook {
    @Override
    public double updatedDistance(double nativeUpdatedDistance) {
        return nativeUpdatedDistance;
    }
}
