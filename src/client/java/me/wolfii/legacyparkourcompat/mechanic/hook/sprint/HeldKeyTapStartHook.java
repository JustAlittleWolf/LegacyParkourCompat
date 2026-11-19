package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("sprint.held_key_tap_start")
public interface HeldKeyTapStartHook extends VersionedMechanic {
    boolean startOnExpiredTimer();
}
