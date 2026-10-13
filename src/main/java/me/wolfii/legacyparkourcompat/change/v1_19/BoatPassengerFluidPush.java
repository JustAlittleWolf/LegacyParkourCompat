package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerFluidPushHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 403-461.
 *   updateInWaterStateAndDoWaterCurrentPushing; lines 1055-1071.
 *   Source SHA-256: 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1822-1824.
 *   Source SHA-256: bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a.
 * 1.18.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java:
 *   isUnderWater; lines 869-871.
 *   Source SHA-256: 4e09ba747b5ee6a6a63c9663bebc96c561013501126550fe55e506b9cce33fe4.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   rideTick; lines 953-961.
 *   isEffectiveAi; lines 581-583.
 *   Source SHA-256: 36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateInWaterStateAndDoWaterCurrentPushing; lines 1069-1085.
 *   Source SHA-256: 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2497-2620.
 *   travel; lines 2042-2178.
 *   Source SHA-256: fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77.
 * 1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel; lines 1464-1494.
 *   removeVehicle; lines 1045-1048.
 *   Source SHA-256: 155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   rideTick; lines 874-882.
 *   isEffectiveAi; lines 493-495.
 *   Source SHA-256: 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateInWaterStateAndDoWaterCurrentPushing; lines 1074-1093.
 *   Source SHA-256: 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2508-2631.
 *   travel; lines 2052-2185.
 *   Source SHA-256: 2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db.
 * 1.19.3/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel; lines 1448-1478.
 *   removeVehicle; lines 1034-1037.
 *   Source SHA-256: 02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 409-467.
 *   updateInWaterStateAndDoWaterCurrentPushing; lines 1080-1096.
 *   Source SHA-256: 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid; lines 1848-1850.
 *   Source SHA-256: 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
 * 1.19.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java:
 *   isUnderWater; lines 876-878.
 *   Source SHA-256: 63fca5d8a66f5b29344af35c01debd479dd8d10d3f6bbdc4dce5c8b89235084e.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.18.2/mojmap/net/minecraft/world/entity/vehicle/Boat.java, 1.19.4/mojmap/net/minecraft/world/entity/vehicle/Boat.java. Neither a matching archived full-file hash nor an archived matching canonical source-manifest digest was resolved for them.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class BoatPassengerFluidPush implements BoatPassengerFluidPushHook {
    @Override
    public boolean skipWaterCurrent(Player player, Entity vehicle, ParkourVersion selected) {
        return !player.getAbilities().flying && vehicle instanceof AbstractBoat boat && !boat.isUnderWater()
            && me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(vehicle, selected);
    }
}
