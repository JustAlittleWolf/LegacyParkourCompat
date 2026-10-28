package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/**
 * Forward-input eligibility for the prior sprint snapshot and current sprint start checks.
 * The caller supplies the stored input axis at the call site, including historical
 * input scaling, and the local player's cached eye-water query result. Yarn 1.14.2+build.7
 * names this cached query isInWater(); unobfuscated 26.2 LocalPlayer#isUnderWater()
 * exposes the corresponding cached eye-water state at the modern call site.
 */
@MechanicType("player.sprint.forward_input")
public interface ForwardSprintInputHook extends VersionedMechanic {
    boolean hasEnoughForwardInput(float storedForwardInput, boolean cachedEyeUnderwater);
}
