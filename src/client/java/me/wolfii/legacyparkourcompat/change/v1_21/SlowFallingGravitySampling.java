package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SlowFallingGravitySamplingHook;

import java.util.OptionalDouble;

/**
 * Restores the pre-air-movement Slow Falling velocity sample used in 1.21.1.
 *
 * <p>In 1.21.1 Mojmap, {@code LivingEntity.travel(Vec3)} captures base gravity,
 * Slow Falling presence and the nonpositive-Y predicate before calling
 * {@code handleRelativeFrictionAndCalculateMovement(Vec3, float)} in ordinary
 * air travel; its later gravity subtraction uses those sampled values. In
 * 1.21.2 Mojmap, {@code LivingEntity.travelInAir(Vec3)} calls that movement
 * helper before {@code getEffectiveGravity()} samples Y velocity. The hook
 * returns the 1.21.1 cap selection from the caller's pre-movement samples. The
 * caller retains 1.21.2 post-movement branch selection and subtracts the sample
 * only where native control flow normally subtracts gravity. Base-gravity
 * calculation and all other travel gates remain native.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class SlowFallingGravitySampling implements SlowFallingGravitySamplingHook {
    @Override
    public OptionalDouble effectiveGravity(boolean slowFalling, double baseGravity, double sampledYVelocity) {
        return OptionalDouble.of(slowFalling && sampledYVelocity <= 0.0
                ? Math.min(baseGravity, 0.01)
                : baseGravity);
    }
}
