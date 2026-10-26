package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.RelativeInputYawHook;
import net.minecraft.world.entity.player.Player;

/** Leaves the yaw conversion native for 1.8 through 1.8.8, outside the accepted source boundary. */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NativeRelativeInputYaw implements RelativeInputYawHook {
    @Override
    public double toRadians(Player player, float yawDegrees, double vanillaRadians) {
        return vanillaRadians;
    }
}
