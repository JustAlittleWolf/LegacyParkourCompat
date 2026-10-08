package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerYawRefreshBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.Boat;

/**
 * Restores the 1.17.1 movement-facing yaw write on a passenger-list refresh.
 * Previous-yaw interpolation and head-yaw rendering fields are intentionally excluded.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class BoatPassengerYawRefresh implements BoatPassengerYawRefreshBehavior {
    @Override
    public void onPassengerRefresh(Player player, Boat boat, ParkourVersion selected) {
        if (selected == ParkourVersion.V1_17_1 && boat.hasIndirectPassenger(player)) {
            player.setYRot(boat.getYRot());
        }
    }
}
