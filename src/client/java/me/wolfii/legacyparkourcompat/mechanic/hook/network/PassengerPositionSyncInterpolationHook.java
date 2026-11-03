package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects the raw interpolation value consumed by the passenger position sync gate. */
@MechanicType("network.passenger_position_sync_interpolation")
public interface PassengerPositionSyncInterpolationHook extends VersionedMechanic {
    /**
     * Maps Entity.isInterpolating() to the operand used by the native negated gate.
     * Returning the inverse restores the 1.21.5 positive gate; returning the value
     * unchanged preserves the native 1.21.6-and-later gate.
     */
    boolean interpolationOperand(boolean nativeValue);
}
