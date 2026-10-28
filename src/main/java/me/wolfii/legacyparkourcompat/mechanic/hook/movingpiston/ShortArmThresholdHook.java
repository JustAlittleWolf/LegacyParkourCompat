package me.wolfii.legacyparkourcompat.mechanic.hook.movingpiston;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Resolves only the source-head short-arm threshold predicate for a moving piston. */
@MechanicType("moving_piston.short_arm_threshold")
public interface ShortArmThresholdHook extends VersionedMechanic {
    /**
     * Returns the short-arm threshold result for the moving block entity's raw progress.
     * {@code nativeResult} is the caller's existing/native predicate result and is used
     * unchanged by versions without an accepted historical threshold.
     */
    boolean isShort(float progress, boolean nativeResult);
}
