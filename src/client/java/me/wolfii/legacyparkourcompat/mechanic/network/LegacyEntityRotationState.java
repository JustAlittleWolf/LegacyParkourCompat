package me.wolfii.legacyparkourcompat.mechanic.network;

/** Narrow access to old lerp's direct rotation writes and final setRot call. */
public interface LegacyEntityRotationState {
    void legacySetRotations(float yRot, float xRot);

    void legacyFinishRotations(float yRot, float xRot);
}
