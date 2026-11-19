package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects sprint start or timer arming after the double-tap eligibility gates. */
@MechanicType("player.sprint.inputStart.doubleTapKey")
public interface DoubleTapSprintKeyHook extends VersionedMechanic {
    boolean startsWithExpiredTimer(boolean sprintKeyDown);
}
