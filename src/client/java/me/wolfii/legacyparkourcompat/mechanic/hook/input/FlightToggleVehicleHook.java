package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether the double-press flight toggle requires a jumpable vehicle. */
@MechanicType("player.flight.toggle_vehicle_gate")
public interface FlightToggleVehicleHook extends VersionedMechanic {
    boolean checksVehicle(ParkourVersion selected);
}
