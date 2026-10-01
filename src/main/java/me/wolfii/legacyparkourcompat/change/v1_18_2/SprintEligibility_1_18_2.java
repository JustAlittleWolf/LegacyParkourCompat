package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Set;

@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class SprintEligibility_1_18_2 implements SprintingBehavior {
    private static final Set<String> HISTORICAL_RIDEABLES = Set.of(
        "minecraft:boat",
        "minecraft:minecart",
        "minecraft:chest_minecart",
        "minecraft:furnace_minecart",
        "minecraft:tnt_minecart",
        "minecraft:hopper_minecart",
        "minecraft:spawner_minecart",
        "minecraft:command_block_minecart",
        "minecraft:pig",
        "minecraft:strider",
        "minecraft:horse",
        "minecraft:donkey",
        "minecraft:mule",
        "minecraft:skeleton_horse",
        "minecraft:zombie_horse",
        "minecraft:llama",
        "minecraft:trader_llama"
    );

    @Override
    public boolean fallFlyingForSprintGate(Player player, boolean vanilla) {
        return false;
    }

    @Override
    public boolean vehicleCanSprint(Player player, Entity vehicle, boolean vanilla) {
        var vehicleId = BuiltInRegistries.ENTITY_TYPE.getKey(vehicle.getType());
        if (vehicleId == null || !HISTORICAL_RIDEABLES.contains(vehicleId.toString())) {
            return vanilla;
        }
        return player.getFoodData().getFoodLevel() > 6 || player.getAbilities().mayfly;
    }
}
