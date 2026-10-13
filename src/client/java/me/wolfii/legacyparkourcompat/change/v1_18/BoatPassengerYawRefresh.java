package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerYawRefreshHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.Boat;

/**
 * Restores the pre-1.18.2 movement-facing yaw write on a passenger-list refresh.
 * Previous-yaw interpolation and head-yaw rendering fields are intentionally excluded.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   startRiding sets player yaw from the boat after a successful mount.
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   startRiding has no boat yaw write; this response starts with the 1.9 profile.
 * 1.18/mojmap/net/minecraft/client/player/LocalPlayer.java and
 * 1.18.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   startRiding still writes the boat yaw on every successful direct mount.
 * 1.18.2/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java:
 *   handleSetEntityPassengersPacket writes boat yaw only for a newly listed player.
 * The first changed stable release is 1.18.2. V1_18 includes 1.18.1.
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   heal.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   startRiding.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class BoatPassengerYawRefresh implements BoatPassengerYawRefreshHook {
    @Override
    public void onPassengerRefresh(Player player, Boat boat, ParkourVersion selected) {
        if (selected.newerThanOrEqual(ParkourVersion.V1_9) && boat.hasIndirectPassenger(player)) {
            player.setYRot(boat.getYRot());
        }
    }
}
