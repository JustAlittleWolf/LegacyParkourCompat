package me.wolfii.legacyparkourcompat.change.v1_19;

import java.util.Set;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintFallFlyingGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.VehicleSprintBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_19)
public final class SprintEligibility_1_19_VehicleSprint implements VehicleSprintBehavior {
    @Override
    public boolean vehicleCanSprint(Player player, Entity vehicle, ParkourVersion selected, boolean vanilla) {
        if (!me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(vehicle, selected)) {
            return vanilla;
        }
        return player.getFoodData().getFoodLevel() > 6 || player.getAbilities().mayfly;
    }
}
