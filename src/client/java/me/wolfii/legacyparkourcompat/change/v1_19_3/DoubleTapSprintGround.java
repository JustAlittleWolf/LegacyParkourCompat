package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintDoubleTapGroundHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.19.2 net.minecraft.client.player.LocalPlayer.aiStep gates double-tap
 * sprint with player ground or underwater state. Mojmap 1.19.3
 * net.minecraft.client.player.LocalPlayer.aiStep adds the short-circuited
 * passenger-and-grounded-vehicle term. The held sprint-key path remains separate
 * and ungated by ground in both versions.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class DoubleTapSprintGround implements SprintDoubleTapGroundHook {
    @Override
    public boolean admits(Player player) {
        return player.onGround() || player.isUnderWater()
            || player.isPassenger() && player.getVehicle().onGround();
    }
}
