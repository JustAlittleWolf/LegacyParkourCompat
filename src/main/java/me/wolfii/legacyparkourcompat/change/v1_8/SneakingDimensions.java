package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class SneakingDimensions implements PlayerDimensionsBehavior, PlayerPoseBehavior {
    @Override
    public boolean updatePose(Player player) {
        return false;
    }

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING || pose == Pose.SWIMMING && !player.isSwimming()) {
            EntityDimensions standing = player.getDimensions(Pose.STANDING);
            return standing.withEyeHeight(vanilla.eyeHeight());
        }
        return vanilla;
    }
}
