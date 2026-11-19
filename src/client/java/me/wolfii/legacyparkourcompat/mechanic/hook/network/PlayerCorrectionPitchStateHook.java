package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.Optional;

/** Resolves the pitch fields written by a direct local-player position correction. */
@MechanicType("player.correction.pitch_state")
public interface PlayerCorrectionPitchStateHook extends VersionedMechanic {
    Optional<PitchState> resolvedState(float currentPitch, float previousPitch, float packetPitch, boolean relativePitch);

    record PitchState(float previousPitch, float currentPitch) {}
}
