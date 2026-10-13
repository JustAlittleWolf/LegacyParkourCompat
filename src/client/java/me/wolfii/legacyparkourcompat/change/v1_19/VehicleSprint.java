package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.VehicleSprintHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   isSprinting.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   stopRiding.
 *   aiStep.
 * 1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   removeVehicle.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 *   removeVehicle.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   removeVehicle.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 *   setSprinting.
 *   getFrictionInfluencedSpeed.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 *   aiStep.
 *   removeVehicle.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   canSprint.
 *   canSprint.
 *   removeVehicle.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getFrictionInfluencedSpeed.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class VehicleSprint implements VehicleSprintHook {
    @Override
    public boolean vehicleCanSprint(Player player, Entity vehicle, ParkourVersion selected, boolean vanilla) {
        if (!me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(vehicle, selected)) {
            return vanilla;
        }
        return player.getFoodData().getFoodLevel() > 6 || player.getAbilities().mayfly;
    }
}
