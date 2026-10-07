package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/** Whether water currents should be suppressed for a player riding a boat. */
@FunctionalInterface
@MechanicType("player.boat_fluid_push")
public interface BoatPassengerFluidPushBehavior extends VersionedMechanic {
    boolean skipWaterCurrent(Player player, Entity vehicle, ParkourVersion selected);
}
