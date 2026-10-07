package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakInputSlowdownBehavior;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** The 1.14.4 pose-aware sneak-input slowdown gate. */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class SneakInputSlowdown implements SneakInputSlowdownBehavior {
    @Override
    public boolean shouldSlowDown(Player player) {
        boolean poseSlowdown = !player.getAbilities().flying
            && !player.isSwimming()
            && canFit(player, Pose.CROUCHING)
            && (player.isShiftKeyDown() || !canFit(player, Pose.STANDING));
        boolean swimmingPoseOutsideWater = !poseSlowdown
            && player.getPose() == Pose.SWIMMING
            && !player.isInWater();
        return !player.isSpectator()
            && (player.isShiftKeyDown() || poseSlowdown || swimmingPoseOutsideWater);
    }

    private static boolean canFit(Player player, Pose pose) {
        AABB box = player.getDimensions(pose).makeBoundingBox(player.position());
        return player.level().noBlockCollision(player, box) && player.level().noEntityCollision(player, box);
    }
}
