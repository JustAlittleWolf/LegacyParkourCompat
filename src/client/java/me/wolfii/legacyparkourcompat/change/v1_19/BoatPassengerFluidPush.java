package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerFluidPushHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.player.Player;

/**
 * Water-current updates are skipped while riding any era-existing boat through
 * 1.19.2, including chest boats and submerged boats. The modern AbstractBoat
 * type includes historical Boat subclasses such as ChestBoat.
 * 1.19/mojmap/net/minecraft/world/entity/Entity.java: updateInWaterStateAndDoWaterCurrentPushing.
 * 1.19.1/mojmap/net/minecraft/world/entity/Entity.java: updateInWaterStateAndDoWaterCurrentPushing.
 * 1.19/mojmap/net/minecraft/world/entity/vehicle/ChestBoat.java: class declaration extends Boat.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   updateInWaterStateAndDoWaterCurrentPushing.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 * 1.18.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java:
 *   isUnderWater.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   rideTick.
 *   isEffectiveAi.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateInWaterStateAndDoWaterCurrentPushing.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 *   travel.
 * 1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel.
 *   removeVehicle.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   rideTick.
 *   isEffectiveAi.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateInWaterStateAndDoWaterCurrentPushing.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 *   travel.
 * 1.19.3/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel.
 *   removeVehicle.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   updateInWaterStateAndDoWaterCurrentPushing.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 * 1.19.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java:
 *   isUnderWater.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.18.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java, 1.19.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java. Archived source identity correspondence remains unresolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class BoatPassengerFluidPush implements BoatPassengerFluidPushHook {
    @Override
    public boolean skipWaterCurrent(Player player, Entity vehicle, ParkourVersion selected) {
        if (vehicle == null || player.getAbilities().flying) {
            return false;
        }
        return vehicle instanceof AbstractBoat
            && me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(vehicle, selected);
    }
}
