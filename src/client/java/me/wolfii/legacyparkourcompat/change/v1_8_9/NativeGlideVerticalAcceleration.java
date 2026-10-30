package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GlideVerticalAccelerationHook;

/** Elytra did not exist in 1.8; retain native acceleration for this modern-only feature. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeGlideVerticalAcceleration implements GlideVerticalAccelerationHook {
    @Override
    public double verticalAcceleration(ParkourVersion selected, double liftForce, double gravity,
                                       boolean slowFalling, double vanilla) {
        return vanilla;
    }
}
