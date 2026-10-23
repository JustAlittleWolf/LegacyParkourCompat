package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Forward-input stop reason for an existing running sprint, after input polling/scaling. */
@MechanicType("player.sprint.retention_input")
public interface SprintRetentionInputHook extends VersionedMechanic {
    boolean shouldStopSprinting(float storedForwardInput);
}
