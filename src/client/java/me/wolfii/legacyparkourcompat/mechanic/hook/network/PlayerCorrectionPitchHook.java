package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Pitch bound applied before correction-relative velocity rotation. */
@MechanicType("player.correction.pitch_bound")
public interface PlayerCorrectionPitchHook extends VersionedMechanic {
    float pitch(ParkourVersion selected, float resolvedPitch, float vanilla);
}
