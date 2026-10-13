package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.VehicleSprintHook;
import me.wolfii.legacyparkourcompat.change.common.HistoricalRideables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * In 1.19.3 Mojmap LocalPlayer#aiStep calls LocalPlayer#hasEnoughFoodToStartSprinting,
 * which accepts passengers. In 1.19.4 Mojmap LocalPlayer#aiStep calls
 * LocalPlayer#canStartSprinting, which adds LocalPlayer#vehicleCanSprint and
 * requires Entity#canSprint plus Entity#isControlledByLocalInstance. Mojmap
 * Entity#canSprint defaults to false in 1.19.4. This restores the passenger
 * predicate for the 1.19.3-era vehicle registry ids only.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class VehicleSprint implements VehicleSprintHook {
    @Override
    public boolean vehicleCanSprint(Player player, Entity vehicle, ParkourVersion selected, boolean vanilla) {
        if (!player.isPassenger() || player.getVehicle() != vehicle
            || selected.olderThan(ParkourVersion.V1_19_3)
            || !HistoricalRideables.contains(vehicle, selected)) {
            return vanilla;
        }
        return true;
    }

}
