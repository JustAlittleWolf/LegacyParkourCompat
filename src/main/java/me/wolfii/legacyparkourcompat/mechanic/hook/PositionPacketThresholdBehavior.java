package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Supplies the squared displacement threshold used for client position packets. */
@FunctionalInterface
@MechanicType("client.position_packet.displacement_threshold")
public interface PositionPacketThresholdBehavior extends VersionedMechanic {
    double squaredDisplacementThreshold(double vanillaThreshold);
}
