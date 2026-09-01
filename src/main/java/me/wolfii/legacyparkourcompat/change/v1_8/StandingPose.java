package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.EyeHeightBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * 1.8 sneaking does not shrink the collision box (always {@code 0.6×1.8}).
 * 1.9 lowered sneaking to {@code 1.65}m. Eye height still drops by
 * {@code 0.08} while the sneak key is held.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class StandingPose implements PlayerPoseBehavior, EyeHeightBehavior, PlayerDimensionsBehavior {
    private static final EntityDimensions STANDING = EntityDimensions.scalable(0.6F, 1.8F).withEyeHeight(1.62F);

    @Override
    public void updatePlayerPose(Player player, VanillaCall vanilla) {
        if (player.isSpectator() || player.isSleeping()) {
            vanilla.run();
            return;
        }
        player.setPose(Pose.STANDING);
        player.refreshDimensions();
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

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.SLEEPING) {
            return vanilla;
        }
        return STANDING;
    }
}
