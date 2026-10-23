package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/** Preserves the prior V1_8 body-size and vanilla eye-height behavior. */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class Pre189SneakingDimensions implements PlayerDimensionsHook, PlayerPoseHook {
    @Override
    public boolean updatePose(Player player) {
        return false;
    }

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING) {
            return player.getDimensions(Pose.STANDING).withEyeHeight(vanilla.eyeHeight());
        }
        return vanilla;
    }
}
