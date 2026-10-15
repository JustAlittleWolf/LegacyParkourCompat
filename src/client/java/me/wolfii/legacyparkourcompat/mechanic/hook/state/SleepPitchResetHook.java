package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Post-travel sleeping pitch write inside the living tick. */
@MechanicType("player.sleep.post_travel_pitch_reset")
public interface SleepPitchResetHook extends VersionedMechanic {
    boolean resetsPitch(ParkourVersion selected);
}
