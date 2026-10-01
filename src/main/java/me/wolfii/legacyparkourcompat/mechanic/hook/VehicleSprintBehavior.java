package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

@MechanicType("player.sprint.vehicleCanSprint")
public interface VehicleSprintBehavior extends VersionedMechanic {
    default boolean vehicleCanSprint(Player player, Entity vehicle, me.wolfii.legacyparkourcompat.api.ParkourVersion selected, boolean vanilla) {
        return vanilla;
    }
}
