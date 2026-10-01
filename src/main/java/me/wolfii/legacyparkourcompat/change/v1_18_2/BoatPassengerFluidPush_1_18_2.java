package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerFluidPushBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;


@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class BoatPassengerFluidPush_1_18_2 implements BoatPassengerFluidPushBehavior {
    @Override
    public boolean skipWaterCurrent(Player player, Entity vehicle, ParkourVersion selected) {
        if (vehicle == null || player.getAbilities().flying) {
            return false;
        }
        var vehicleId = BuiltInRegistries.ENTITY_TYPE.getKey(vehicle.getType());
        return vehicle instanceof net.minecraft.world.entity.vehicle.boat.Boat
            && me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(vehicle, selected);
    }
}
