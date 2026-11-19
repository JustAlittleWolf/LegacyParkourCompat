package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Forward-input stop reason for an existing sprint, after input polling and scaling. */
@MechanicType("player.sprint.retention_input")
public interface SprintRetentionInputHook extends VersionedMechanic {
    boolean supports(ParkourVersion selected, boolean swimming);

    boolean shouldStopSprinting(float storedForwardInput);
}
