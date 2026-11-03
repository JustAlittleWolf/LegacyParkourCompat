package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SlowFallingGravitySamplingHook;

import java.util.OptionalDouble;

/**
 * Keeps profiles before the accepted 1.21 source boundary on native gravity
 * sampling while the same independently resolved operation is present.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class SlowFallingGravitySamplingFence implements SlowFallingGravitySamplingHook {
    @Override
    public OptionalDouble effectiveGravity(boolean slowFalling, double baseGravity, double sampledYVelocity) {
        return OptionalDouble.empty();
    }
}
