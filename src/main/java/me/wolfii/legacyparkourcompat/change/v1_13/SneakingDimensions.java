package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * The 1.13.2 player height and physical size used when modern pose selection falls back to swimming.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   updatePlayerPose; lines [[334, 360]].
 *   Source SHA-256: 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
 * 1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   updatePlayerPose; lines [[343, 370], [109, 117]].
 *   Source SHA-256: 2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/d138264d37d9992dbab8bc72b8929978a908d246c2585e55f86a0fe1929a69a2.md; accepted, bounded.
 *   Reviewed snapshot 448934e826fb41266dc79a313ef1187899d76382:workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakingDimensions implements PlayerDimensionsHook, PlayerPoseHook {
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
        if (player.level().noBlockCollision(null, requested)) {
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
