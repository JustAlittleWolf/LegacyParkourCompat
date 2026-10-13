package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/**
 * Forward-input eligibility for the prior sprint snapshot and current sprint start checks.
 * The caller supplies the stored input axis at the call site, including historical
 * input scaling, and the local player's cached eye-water query result.
 */
@MechanicType("player.sprint.forward_input")
public interface ForwardSprintInputHook extends VersionedMechanic {
    boolean hasEnoughForwardInput(float storedForwardInput, boolean cachedEyeUnderwater);
}
