package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects the requested downward displacement after a step route has won. */
@MechanicType("entity.step.downward-return")
public interface StepDescentHook extends VersionedMechanic {
    /**
     * @param nativeDescent the signed downward request produced by the active movement implementation
     * @param configuredStepHeight the entity's configured maximum upward step height
     * @return the signed downward request to pass to the existing descent collision pass
     */
    double downwardReturn(double nativeDescent, float configuredStepHeight);
}
