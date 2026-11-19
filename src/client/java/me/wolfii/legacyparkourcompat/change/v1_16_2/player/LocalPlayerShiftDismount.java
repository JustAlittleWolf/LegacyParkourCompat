package me.wolfii.legacyparkourcompat.change.v1_16_2.player;

import java.util.function.BooleanSupplier;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalRideables;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LocalPlayerShiftDismountHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.vehicle.boat.Boat;

/**
 * In 1.16 through 1.16.5 Mojmap Player.rideTick checks the sampled shift
 * state before running the inherited passenger tick. LocalPlayer.isShiftKeyDown
 * reads input.shiftKeyDown. The successful branch leaves the vehicle and then
 * clears the entity's shared shift flag, preserving the sampled input.
 * In 1.15.2 and 1.17 Mojmap, and 26.2 unobfuscated, Player.rideTick
 * checks the logical side first and only enters the dismount branch on the server.
 * This change restores the accepted local boat-passenger response.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class LocalPlayerShiftDismount implements LocalPlayerShiftDismountHook {
    @Override
    public boolean dismountIfRequested(LocalPlayer player, BooleanSupplier wantsToStopRiding,
                                       ParkourVersion selected) {
        if (!wantsToStopRiding.getAsBoolean() || !player.isPassenger()
            || !(player.getVehicle() instanceof Boat boat) || !HistoricalRideables.contains(boat, selected)) {
            return false;
        }
        player.stopRiding();
        player.setShiftKeyDown(false);
        return true;
    }
}
