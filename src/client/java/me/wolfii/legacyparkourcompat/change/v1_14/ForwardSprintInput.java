package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.ForwardSprintInputHook;

/**
 * Ornithe Feather 1.13.2 and 1.14 LocalClientPlayerEntity#tickMovement and
 * Yarn 1.14.1+build.10 ClientPlayerEntity#tickMovement compare stored forward
 * input with 0.8F before input.tick and in current sprint-start checks.
 * Yarn 1.14.2+build.7 ClientPlayerEntity#method_20623 instead selects
 * Input#method_20622 (> 1.0E-5F) for the cached local eye-water result, or
 * compares the promoted forward float with the double 0.8 otherwise.
 * Sprint retention is a separate operation with its own applicability boundary.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class ForwardSprintInput implements ForwardSprintInputHook {
    @Override
    public boolean hasEnoughForwardInput(float storedForwardInput, boolean cachedEyeUnderwater) {
        return storedForwardInput >= 0.8F;
    }
}
