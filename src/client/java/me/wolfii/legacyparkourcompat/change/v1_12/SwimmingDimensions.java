package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import me.wolfii.legacyparkourcompat.mixin.EntityPoseDimensionsAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   protected void updatePlayerPose().
 *   public float getEyeHeight().
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   protected void updatePlayerPose().
 *   public float getEyeHeight().
 *   public void updateSwimming().
 * For the accepted 1.10–1.12.2 sleeping and fall-flying overlap,
 * Ornithe Feather PlayerEntity#updatePlayerPose selects 0.6F by 0.6F and
 * checks a box anchored at its old minima before Entity#setSize. In
 * 1.10–1.11 World#getCollisions(Box) uses a block-only scan with the
 * fixed X/Z bound; 1.11.1–1.12.2 uses the null-entity overload with
 * enforceWorldBorder=true. Both reach the same bound for this box.
 * The remaining dimension operations have separate evidence gaps.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-SWIM-POSE is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-SWIM-POSE
 *   Exact independent source verdict/snapshot identity for swim-dimensions
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SwimmingDimensions implements PlayerDimensionsHook, PlayerPoseHook {
    private static final float FALL_FLYING_SIZE = 0.6F;
    private static final int WORLD_LIMIT = 30_000_000;

    @Override
    public boolean updatePose(Player player, EntityDimensions previousDimensions, AABB previousBox, float previousEyeHeight) {
        float width = previousDimensions.width();
        float height = previousDimensions.height();
        if (!player.isLocalPlayer() || !player.isSleeping() || !player.isFallFlying()
            || !(width <= FALL_FLYING_SIZE)) {
            return false;
        }

        AABB box = previousBox;
        if (width != FALL_FLYING_SIZE || height != FALL_FLYING_SIZE) {
            AABB candidate = new AABB(previousBox.minX, previousBox.minY, previousBox.minZ,
                previousBox.minX + FALL_FLYING_SIZE, previousBox.minY + FALL_FLYING_SIZE, previousBox.minZ + FALL_FLYING_SIZE);
            if (!exceedsFixedCollisionBounds(candidate) && MovementRuntime.withHistoricalBlockShapePlayer(player,
                () -> player.level().noBlockCollision(null, candidate))) {
                width = FALL_FLYING_SIZE;
                height = FALL_FLYING_SIZE;
                box = candidate;
            }
        }

        // Reconcile only the dimensions and box after the native pose write.
        EntityPoseDimensionsAccessor accessor = (EntityPoseDimensionsAccessor)player;
        EntityDimensions currentDimensions = accessor.legacyparkourcompat$getDimensions();
        if (currentDimensions.width() != width || currentDimensions.height() != height) {
            accessor.legacyparkourcompat$setDimensions(new EntityDimensions(width, height,
                currentDimensions.eyeHeight(), currentDimensions.attachments(), currentDimensions.fixed()));
        }
        if (!box.equals(player.getBoundingBox())) {
            player.setBoundingBox(box);
        }
        return true;
    }

    private static boolean exceedsFixedCollisionBounds(AABB box) {
        // Each outer X or Z scan column has an interior cell along the other
        // horizontal axis and Y. The corner skips cannot hide its fixed bound.
        return Mth.floor(box.minX) - 1 < -WORLD_LIMIT || Mth.ceil(box.maxX) >= WORLD_LIMIT
            || Mth.floor(box.minZ) - 1 < -WORLD_LIMIT || Mth.ceil(box.maxZ) >= WORLD_LIMIT;
    }

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING) {
            return EntityDimensions.scalable(0.6F, 1.65F).withEyeHeight(vanilla.eyeHeight());
        }
        return vanilla;
    }
}
