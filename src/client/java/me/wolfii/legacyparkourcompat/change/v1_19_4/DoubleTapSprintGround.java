package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintDoubleTapGroundHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.19.3 LocalPlayer.aiStep uses player ground, underwater, or a grounded
 * vehicle for double-tap admission. Mojmap 1.19.4 LocalPlayer.aiStep selects only
 * vehicle ground when mounted, otherwise player ground, then allows underwater.
 * The separate held-key route remains ungated by ground in both versions.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class DoubleTapSprintGround implements SprintDoubleTapGroundHook {
    @Override
    public boolean admits(ParkourVersion selected, Player player) {
        return (player.isPassenger() ? player.getVehicle().onGround() : player.onGround())
            || player.isUnderWater();
    }
}
