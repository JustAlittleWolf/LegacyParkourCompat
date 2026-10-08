package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.Boat;

/** Restores movement-facing yaw when an existing boat passenger is re-listed. */
@FunctionalInterface
@MechanicType("player.boat_passenger_yaw_refresh")
public interface BoatPassengerYawRefreshBehavior extends VersionedMechanic {
    void onPassengerRefresh(Player player, Boat boat, ParkourVersion selected);
}
