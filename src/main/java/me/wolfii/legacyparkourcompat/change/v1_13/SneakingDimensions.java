package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** The 1.13.2 player height and physical size used when modern pose selection falls back to swimming. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakingDimensions implements PlayerDimensionsBehavior, PlayerPoseBehavior {
    @Override
    public boolean updatePose(Player player) {
        Pose pose = desiredPose(player);
        EntityDimensions dimensions = player.getDimensions(pose);
        var current = player.getBoundingBox();
        if (dimensions.width() == player.getBbWidth() && dimensions.height() == player.getBbHeight()) {
            return false;
        }

        AABB requested = new AABB(
            current.minX,
            current.minY,
            current.minZ,
            current.minX + dimensions.width(),
            current.minY + dimensions.height(),
            current.minZ + dimensions.width()
        );
        if (player.level().noCollision((Entity) null, requested)) {
            Pose previous = player.getPose();
            player.setPose(pose);
            if (previous == pose) {
                player.refreshDimensions();
            }
        }
        return true;
    }

    private static Pose desiredPose(Player player) {
        if (player.isFallFlying()) {
            return Pose.FALL_FLYING;
        }
        if (player.isSleeping()) {
            return Pose.SLEEPING;
        }
        if (player.isSwimming()) {
            return Pose.SWIMMING;
        }
        if (player.isAutoSpinAttack()) {
            return Pose.SPIN_ATTACK;
        }
        return player.isShiftKeyDown() ? Pose.CROUCHING : Pose.STANDING;
    }

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
