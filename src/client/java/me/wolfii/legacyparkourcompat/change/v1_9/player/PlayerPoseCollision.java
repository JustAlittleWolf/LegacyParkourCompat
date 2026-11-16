package me.wolfii.legacyparkourcompat.change.v1_9.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import me.wolfii.legacyparkourcompat.mixin.EntityPoseDimensionsAccessor;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Reproduces the 1.9 player pose resize and its block-only collision gate.
 *
 * <p>Minecraft 1.8, Ornithe Feather
 * {@code net.minecraft.entity.living.player.PlayerEntity.tick()} and
 * {@code PlayerEntity.getEyeHeight()}, has no tick-time pose resize. Minecraft
 * 1.9, Ornithe Feather {@code PlayerEntity.tick()} and
 * {@code PlayerEntity.updatePlayerPose()}, selects dimensions in fall-flying,
 * sleeping, sneaking, standing order; it submits an unchanged box anchored at
 * the current minimum coordinates to {@code World.getCollisions(Box)} and
 * calls {@code net.minecraft.entity.Entity.setSize()} only when no block
 * collision exists. Its {@code PlayerEntity.getEyeHeight()} checks sleeping,
 * then sneaking or a 1.65F body, then fall-flying or a 0.6F body. The
 * 1.9–1.9.4 pose selection, query, resize gate and eye-height order are
 * identical.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class PlayerPoseCollision implements PlayerDimensionsHook, PlayerPoseHook {
    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING) {
            return new EntityDimensions(vanilla.width(), 1.65F, legacyEyeHeight(player, 1.65F),
                vanilla.attachments(), vanilla.fixed());
        }
        return vanilla;
    }

    @Override
    public boolean updatePose(Player player, EntityDimensions previousDimensions, AABB previousBox,
                              float previousEyeHeight) {
        float width = 0.6F;
        float height = 1.8F;
        if (player.isFallFlying()) {
            height = 0.6F;
        } else if (player.isSleeping()) {
            width = 0.2F;
            height = 0.2F;
        } else if (player.isShiftKeyDown()) {
            height = 1.65F;
        }

        EntityPoseDimensionsAccessor dimensions = (EntityPoseDimensionsAccessor)player;
        float oldWidth = previousDimensions.width();
        float oldHeight = previousDimensions.height();

        if (width == oldWidth && height == oldHeight) {
            restorePreviousState(player, dimensions, previousDimensions, previousBox,
                legacyEyeHeight(player, oldHeight));
            return true;
        }

        AABB candidateBox = new AABB(previousBox.minX, previousBox.minY, previousBox.minZ,
            previousBox.minX + width, previousBox.minY + height, previousBox.minZ + width);
        boolean clear = MovementRuntime.withHistoricalBlockShapePlayer(player,
            () -> player.level().noBlockCollision(null, candidateBox));
        if (!clear) {
            restorePreviousState(player, dimensions, previousDimensions, previousBox,
                legacyEyeHeight(player, oldHeight));
            return true;
        }

        float eyeHeight = legacyEyeHeight(player, height);
        EntityDimensions resized = previousDimensions.scale(width / oldWidth, height / oldHeight)
            .withEyeHeight(eyeHeight);
        dimensions.legacyparkourcompat$setDimensions(resized);
        dimensions.legacyparkourcompat$setEyeHeight(eyeHeight);
        player.setBoundingBox(candidateBox);
        return true;
    }

    private static void restorePreviousState(Player player, EntityPoseDimensionsAccessor dimensions,
                                             EntityDimensions previousDimensions, AABB previousBox,
                                             float eyeHeight) {
        dimensions.legacyparkourcompat$setDimensions(previousDimensions.withEyeHeight(eyeHeight));
        player.setBoundingBox(previousBox);
        dimensions.legacyparkourcompat$setEyeHeight(eyeHeight);
    }

    private static float legacyEyeHeight(Player player, float height) {
        float eyeHeight = 1.62F;
        if (player.isSleeping()) {
            eyeHeight = 0.2F;
        } else if (player.isShiftKeyDown() || height == 1.65F) {
            eyeHeight -= 0.08F;
        } else if (player.isFallFlying() || height == 0.6F) {
            eyeHeight = 0.4F;
        }
        return eyeHeight;
    }
}
