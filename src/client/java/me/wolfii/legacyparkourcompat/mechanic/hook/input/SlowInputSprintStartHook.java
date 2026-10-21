package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("player.sprint.slow-input-start")
public interface SlowInputSprintStartHook extends VersionedMechanic {
    boolean allowsSprintingWhileMovingSlowly();
}
