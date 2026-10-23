package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.RelativeInputYawHook;
import net.minecraft.world.entity.player.Player;

/**
 * 1.8.9 (Ornithe Feather), net.minecraft.entity.Entity#updateVelocity(float,float,float),
 * uses yaw * (float) Math.PI / 180.0F. 1.9 (Ornithe Feather), the same member,
 * uses yaw * (float) (Math.PI / 180.0). Preserve the earlier expression only
 * on the ordinary local player's non-flying water/lava path, which falls
 * through to the native relative-input vector operation in this client.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class RelativeInputYaw implements RelativeInputYawHook {
    @Override
    public float toRadians(Player player, float yawDegrees, float vanillaRadians) {
        if (!player.isLocalPlayer() || !OrdinaryPlayerContext.matches(player)
            || player.getAbilities().flying || !(player.isInWater() || player.isInLava())) {
            return vanillaRadians;
        }
        return yawDegrees * (float) Math.PI / 180.0F;
    }
}
