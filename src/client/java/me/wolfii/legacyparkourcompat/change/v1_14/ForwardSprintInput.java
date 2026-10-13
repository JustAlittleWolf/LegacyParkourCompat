package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.ForwardSprintInputHook;

/**
 * 1.14.1, Yarn 1.14.1+build.10: net.minecraft.client.network.ClientPlayerEntity
 * tickMovement compares input.movementForward with 0.8F both before input.tick
 * and at current sprint start checks after item scaling.
 * 1.14.2, Yarn 1.14.2+build.7: the same class's method_20623 instead selects
 * Input.method_20622 (> 1.0E-5F) for the cached local eye-water result, or
 * compares the promoted forward float with the double 0.8 otherwise.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class ForwardSprintInput implements ForwardSprintInputHook {
    @Override
    public boolean hasEnoughForwardInput(float storedForwardInput, boolean cachedEyeUnderwater) {
        return storedForwardInput >= 0.8F;
    }
}
