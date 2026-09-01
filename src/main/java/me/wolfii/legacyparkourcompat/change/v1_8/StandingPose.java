package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * 1.8 sneaking does not shrink the collision box (always 1.8m). 1.9 lowered
 * sneaking to 1.65m. Eye height still drops by {@code 0.08} while sneaking.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class StandingPose implements PlayerPoseBehavior {
    @Override
    public void updatePlayerPose(Player player, VanillaCall vanilla) {
        if (player.isSpectator() || player.isSleeping()) {
            vanilla.run();
            return;
        }
        player.setPose(Pose.STANDING);
    }

    @Override
    public float eyeHeight(Player player, float vanilla) {
        if (player.isSleeping()) {
            return vanilla;
        }
        float height = 1.62F;
        if (player.isShiftKeyDown()) {
            height -= 0.08F;
        }
        return height;
    }
}
