package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Supplies the setter input for a decoded absolute forced rotation's pitch. */
@MechanicType("player.forced_rotation_pitch")
public interface ForcedRotationPitchHook extends VersionedMechanic {
    float pitchForSetter(float packetPitch, float nativePitch);
}
