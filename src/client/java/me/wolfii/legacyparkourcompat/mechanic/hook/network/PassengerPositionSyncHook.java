package me.wolfii.legacyparkourcompat.mechanic.hook.network;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects the position-sync gate before vanilla checks the local player's passenger membership. */
@MechanicType("network.passenger_position_sync")
public interface PassengerPositionSyncHook extends VersionedMechanic {
    boolean shouldSyncPassenger(ParkourVersion selected, boolean snapBranch, boolean interpolatingAfterOperation);
}
