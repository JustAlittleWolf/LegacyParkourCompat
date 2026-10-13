package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.VehicleSprintHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 687-716.
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 772-785.
 *   Source SHA-256: 36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   isSprinting; lines 1984-1990.
 *   Source SHA-256: 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   stopRiding; lines 2715-2720.
 *   aiStep; lines 2566-2571.
 *   Source SHA-256: fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77.
 * 1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   removeVehicle; lines 1045-1048.
 *   Source SHA-256: 155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 719-729.
 *   aiStep; lines 693-719.
 *   removeVehicle; lines 172-176.
 *   Source SHA-256: 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   removeVehicle; lines 1784-1790.
 *   Source SHA-256: 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2566-2571.
 *   setSprinting; lines 1964-1974.
 *   getFrictionInfluencedSpeed; lines 2246-2248.
 *   Source SHA-256: 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 685-708.
 *   aiStep; lines 645-800.
 *   aiStep; lines 685-701.
 *   removeVehicle; lines 172-176.
 *   Source SHA-256: 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   canSprint; lines 3163-3165.
 *   canSprint; lines 3163-3165.
 *   removeVehicle; lines 1798-1804.
 *   Source SHA-256: 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getFrictionInfluencedSpeed; lines 2197-2199.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
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
