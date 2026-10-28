package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.ForwardSprintInputHook;

/**
 * Yarn 1.14.1+build.10 ClientPlayerEntity#tickMovement compares
 * movementForward >= 0.8F before input.tick and in both sprint-start checks.
 * Yarn 1.14.2+build.7 ClientPlayerEntity#method_20623 uses
 * Input#method_20622 (movementForward > 1.0E-5F) when isInWater(), and
 * movementForward >= 0.8F otherwise. In 1.14.2, isInWater() reads the player
 * cache refreshed by PlayerEntity#updateInWater through Entity#isInFluid(WATER, true);
 * the current LocalPlayer#isUnderWater() supplies this cached eye-water query.
 * The pre-poll snapshot and both post-poll checks remain at their original call sites.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class ForwardSprintInput implements ForwardSprintInputHook {
    @Override
    public boolean hasEnoughForwardInput(float storedForwardInput, boolean cachedEyeUnderwater) {
        return cachedEyeUnderwater ? storedForwardInput > 1.0E-5F : storedForwardInput >= 0.8F;
    }
}