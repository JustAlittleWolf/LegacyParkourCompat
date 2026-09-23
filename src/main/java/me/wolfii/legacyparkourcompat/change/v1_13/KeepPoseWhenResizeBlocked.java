package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** Through 1.13, a blocked player resize kept the previous hitbox. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class KeepPoseWhenResizeBlocked implements PlayerPoseBehavior {
    @Override
    public void updatePlayerPose(Player player, VanillaCall vanilla) {
        Pose desired;
        if (player.isFallFlying()) {
            desired = Pose.FALL_FLYING;
        } else if (player.isSleeping()) {
            desired = Pose.SLEEPING;
        } else if (player.isSwimming()) {
            desired = Pose.SWIMMING;
        } else if (player.isAutoSpinAttack()) {
            desired = Pose.SPIN_ATTACK;
        } else if (player.isShiftKeyDown()) {
            desired = Pose.CROUCHING;
        } else {
            desired = Pose.STANDING;
        }

        EntityDimensions dimensions = player.getDimensions(desired);
        if (dimensions.width() == player.getBbWidth() && dimensions.height() == player.getBbHeight()) {
            return;
        }
        AABB box = player.getBoundingBox();
        AABB proposed = new AABB(
            box.minX, box.minY, box.minZ,
            box.minX + dimensions.width(), box.minY + dimensions.height(), box.minZ + dimensions.width()
        );
        if (player.level().noCollision(null, proposed)) {
            player.setPose(desired);
            player.refreshDimensions();
        }
    }
}
