package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/** The 1.13.2 player height and physical size used when modern pose selection falls back to swimming. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakingDimensions implements PlayerDimensionsBehavior {
    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING) {
            return EntityDimensions.scalable(0.6F, 1.65F).withEyeHeight(vanilla.eyeHeight());
        }
        if (pose == Pose.SWIMMING && !player.isSwimming()) {
            float height = player.isShiftKeyDown() ? 1.65F : 1.8F;
            return EntityDimensions.scalable(0.6F, height).withEyeHeight(vanilla.eyeHeight());
        }
        return vanilla;
    }
}
