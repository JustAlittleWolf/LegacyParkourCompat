package me.wolfii.legacyparkourcompat.change.v1_11_2.movingpiston;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.movingpiston.ShortArmThresholdHook;

/** Keeps the caller's native predicate for profiles before the accepted 1.12 change. */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class NativeShortArmThreshold implements ShortArmThresholdHook {
    @Override
    public boolean isShort(float progress, boolean nativeResult) {
        return nativeResult;
    }
}
