package me.wolfii.legacyparkourcompat.change.v1_13.player.pose;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import me.wolfii.legacyparkourcompat.mixin.EntityPoseDimensionsAccessor;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * 1.13 ordinary player pose choice and collision-aware direct resize.
 * The selector is text-identical in 1.13, 1.13.1 and 1.13.2 Ornithe Feather
 * PlayerEntity.updatePlayerPose (including isDoingSpinAttack/isSneaking); the
 * current adapter is 26.2 unobfuscated Player.updatePlayerPose, Player.isAutoSpinAttack
 * and Entity.isShiftKeyDown (which reads the same shared flag as 1.13 Entity.isSneaking).
 * The post-native adapter uses the requested pose to exclude modern-only states but still
 * applies the historical fit when the native selector retains an entity/border fallback pose.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class PlayerPoseUpdate implements PlayerPoseHook {
    @Override
    public boolean updatePose(Player player, EntityDimensions previousDimensions, AABB previousBox, float previousEyeHeight) {
        // 1.13/Ornithe Feather PlayerEntity.updatePlayerPose (333-359) compares against the dimensions and box
        // present before its collision check, then calls Entity.setSize (285-300) only on success.
        // 26.2/unobfuscated Player.updatePlayerPose (343-355) calls Entity.setPose, which reaches
        // Entity.refreshDimensions (3393-3400) before this TAIL hook; use the HEAD snapshot so a rejected
        // historical fit retains the pre-native dimensions and box.
        // 26.2/unobfuscated Pose.java lists states absent from the 1.13 pose operation; retain
        // native behavior for them. The mixin excludes a desired SWIMMING pose when the player is
        // crawling, so a CROUCHING fallback caused only by modern entity/border checks can still be
        // resized according to the old block-only fit operation.
        Pose currentPose = player.getPose();
        if (currentPose != Pose.STANDING && currentPose != Pose.FALL_FLYING
            && currentPose != Pose.SLEEPING && currentPose != Pose.SWIMMING
            && currentPose != Pose.SPIN_ATTACK && currentPose != Pose.CROUCHING) {
            return false;
        }
        if (currentPose == Pose.SWIMMING && (player.isSleeping() || !player.isSwimming())) {
            return false;
        }

        boolean historicalSwim = player.isSwimming();
        boolean spinAttack = player.isAutoSpinAttack();
        boolean fallFlying = player.isFallFlying();
        boolean sleeping = player.isSleeping();
        boolean modernCrawl = currentPose == Pose.SWIMMING
            && !historicalSwim && !spinAttack && !fallFlying && !sleeping;

        float width;
        float height;
        if (fallFlying) {
            width = 0.6F;
            height = 0.6F;
        } else if (sleeping) {
            width = 0.2F;
            height = 0.2F;
        } else if (historicalSwim || spinAttack) {
            width = 0.6F;
            height = 0.6F;
        } else if (player.isShiftKeyDown()) {
            width = 0.6F;
            // Compose with the existing V1_13 PlayerDimensionsHook, which supplies
            // the historical crouch height while preserving the native attachments.
            EntityDimensions crouching = player.getDimensions(Pose.CROUCHING);
            height = crouching.height();
        } else {
            width = 0.6F;
            height = 1.8F;
        }

        EntityPoseDimensionsAccessor dimensions = (EntityPoseDimensionsAccessor)player;
        EntityDimensions oldDimensions = previousDimensions;
        float oldWidth = oldDimensions.width();
        float nextEyeHeight = legacyEyeHeight(player, height);

        if (width == oldWidth && height == oldDimensions.height()) {
            // 1.13 PlayerEntity.updatePlayerPose does not call setSize when the target dimensions
            // match the pre-update size. Preserve a modern crawl result if that no-op coincides
            // with a native SWIMMING fallback; otherwise undo the native resize to preserve old state.
            if (modernCrawl) {
                return false;
            }
            restorePreviousState(player, dimensions, previousDimensions, previousBox, previousEyeHeight,
                legacyEyeHeight(player, previousDimensions.height()));
            return true;
        }

        // 1.13, 1.13.1 and 1.13.2/Ornithe Feather PlayerEntity.updatePlayerPose builds from the current
        // box minimum and calls WorldView.hasNoCollisions(null, box). In 26.2/unobfuscated
        // Player.canPlayerFitWithinBlocksAndEntitiesWhen instead calls CollisionGetter.noCollision(this, deflatedBox),
        // which adds entity and border checks.
        // CollisionGetter.noCollision(null, box) still asks for entity collisions, so use its block-only primitive
        // to preserve the old null-source gate and the exact, undeflated candidate dimensions.
        AABB candidate = new AABB(previousBox.minX, previousBox.minY, previousBox.minZ,
            previousBox.minX + width, previousBox.minY + height, previousBox.minZ + width);
        if (!player.level().noBlockCollision(null, candidate)) {
            // 1.13 PlayerEntity.updatePlayerPose leaves the pre-call size and box untouched when
            // the block-only fit fails. Keep a native SWIMMING fallback only when it represents
            // modern crawling rather than a historical swimming/spin/fall-flying/sleep pose.
            if (modernCrawl) {
                return false;
            }
            restorePreviousState(player, dimensions, previousDimensions, previousBox, previousEyeHeight,
                legacyEyeHeight(player, previousDimensions.height()));
            return true;
        }

        // 1.13/Ornithe Feather PlayerEntity.getEyeHeight derives the eye height from
        // pose predicates after setSize; 26.2/unobfuscated Entity stores dimensions and eyeHeight separately.
        EntityDimensions scaled = oldDimensions.scale(width / oldWidth, height / oldDimensions.height());
        EntityDimensions newDimensions = new EntityDimensions(width, height, nextEyeHeight,
            scaled.attachments(), scaled.fixed());
        dimensions.legacyparkourcompat$setDimensions(newDimensions);
        dimensions.legacyparkourcompat$setEyeHeight(newDimensions.eyeHeight());

        // 1.13, 1.13.1 and 1.13.2/Ornithe Feather Entity.setSize centers the box on x/z when shrinking width;
        // otherwise it extends the current box from its minimum corner. 26.2/unobfuscated Entity.setBoundingBox
        // writes the adapted box directly. The historical width-expansion move is server-only in Entity.setSize;
        // MovementRuntime gates this hook to client-side players, so that move does not run here.
        if (width < oldWidth) {
            double halfWidth = width / 2.0;
            player.setBoundingBox(new AABB(player.getX() - halfWidth, player.getY(), player.getZ() - halfWidth,
                player.getX() + halfWidth, player.getY() + height, player.getZ() + halfWidth));
        } else {
            player.setBoundingBox(new AABB(previousBox.minX, previousBox.minY, previousBox.minZ,
                previousBox.minX + width, previousBox.minY + height, previousBox.minZ + width));
        }
        return true;
    }

    private static void restorePreviousState(Player player, EntityPoseDimensionsAccessor dimensions,
                                             EntityDimensions previousDimensions, AABB previousBox,
                                             float previousEyeHeight, float legacyEyeHeight) {
        dimensions.legacyparkourcompat$setDimensions(previousDimensions.withEyeHeight(legacyEyeHeight));
        player.setBoundingBox(previousBox);
        if (previousEyeHeight != legacyEyeHeight || player.getEyeHeight() != legacyEyeHeight) {
            dimensions.legacyparkourcompat$setEyeHeight(legacyEyeHeight);
        }
    }

    private static float legacyEyeHeight(Player player, float height) {
        float eyeHeight = 1.62F;
        if (player.isSleeping()) {
            eyeHeight = 0.2F;
        } else if (player.isSwimming() || player.isFallFlying() || height == 0.6F) {
            eyeHeight = 0.4F;
        } else if (player.isShiftKeyDown() || height == 1.65F) {
            eyeHeight -= 0.08F;
        }
        return eyeHeight;
    }
}
