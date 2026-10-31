package me.wolfii.legacyparkourcompat.mechanic.network;

/** Mutable fields matching the packet coordinates and queued lerp state held by old Entity/LivingEntity. */
public interface ClientPacketPositionState {
    long legacyPacketX();

    long legacyPacketY();

    long legacyPacketZ();

    void legacyPacketCoordinates(long x, long y, long z);

    void legacyQueue(double x, double y, double z, double yRot, double xRot, int steps);

    int legacySteps();

    double legacyX();

    double legacyY();

    double legacyZ();

    double legacyYRot();

    double legacyXRot();

    void legacyDecrementSteps();

    void legacyClearInterpolation();
}
