package me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Resolves the historical double-intermediate precision of current turn pitch. */
@MechanicType("entity.turn.pitch-precision")
public interface CurrentPitchHook extends VersionedMechanic {
    float transformFirstPitchArgument(float currentPitch, double pitchInput, float vanillaArgument);
}
