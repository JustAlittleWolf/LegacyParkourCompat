package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Historical rotation calculation for a clientbound player position correction. */
@MechanicType("player.correction.rotation")
public interface PlayerCorrectionRotationHook extends VersionedMechanic {
    /**
     * Calculates the angles applied by a correction. Relative deltas are added
     * to the pre-correction angles before the historical angle handling runs.
     */
    Angles apply(float currentYaw, float currentPitch, float packetYaw, float packetPitch,
                 boolean relativeYaw, boolean relativePitch);

    record Angles(float yRot, float xRot) {
    }
}
