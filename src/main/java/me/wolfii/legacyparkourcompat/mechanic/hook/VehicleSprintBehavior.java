package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/** Whether the ridden entity permits local-player sprinting. */
@FunctionalInterface
@MechanicType("player.sprint.vehicleCanSprint")
public interface VehicleSprintBehavior extends VersionedMechanic {
    boolean vehicleCanSprint(Player player, Entity vehicle, ParkourVersion selected, boolean vanilla);
}
