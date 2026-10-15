package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Direct local-player vertical velocity reset on spectator entry. */
@MechanicType("player.spectator.entry_velocity_reset")
public interface SpectatorEntryVelocityResetHook extends VersionedMechanic {
    boolean resetVelocity(ParkourVersion selected);
}
