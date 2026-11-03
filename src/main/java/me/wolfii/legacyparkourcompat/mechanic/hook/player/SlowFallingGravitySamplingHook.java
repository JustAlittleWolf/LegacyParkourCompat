package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.OptionalDouble;

/**
 * Selects the gravity used by ordinary air travel from the Y velocity sampled
 * before its SELF movement operation.
 *
 * <p>On the historical path, the caller samples base gravity, Slow Falling
 * presence and Y velocity before SELF movement. It must still follow native
 * post-movement control flow and subtract this sample only in the branch that
 * normally subtracts gravity. Base-gravity calculation, branch selection and
 * all other outcomes stay native. Call only on ordinary air travel after
 * Levitation, unloaded-chunk and other native early exits have been handled.
 * An empty result requests the native effective-gravity value instead.
 */
@MechanicType("player.travel.slow_falling_gravity_sampling")
public interface SlowFallingGravitySamplingHook extends VersionedMechanic {
    /**
     * Returns the selected historical gravity, or empty to retain the native
     * effective-gravity result at the subtraction site.
     */
    OptionalDouble effectiveGravity(boolean slowFalling, double baseGravity, double sampledYVelocity);
}
