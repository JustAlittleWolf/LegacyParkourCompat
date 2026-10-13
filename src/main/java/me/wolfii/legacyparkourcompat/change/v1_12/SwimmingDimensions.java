package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   protected void updatePlayerPose() {; lines 291-315.
 *   public float getEyeHeight() {; lines 1756-1767.
 *   Source SHA-256: e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e.
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   protected void updatePlayerPose() {; lines 334-361.
 *   public float getEyeHeight() {; lines 1859-1870.
 *   public void updateSwimming() {; lines 1471-1477.
 *   Source SHA-256: 4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-SWIM-POSE is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-SWIM-POSE
 *   Exact independent source verdict/snapshot identity for swim-dimensions
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SwimmingDimensions implements PlayerDimensionsHook, PlayerPoseHook {
    @Override
    public boolean updatePose(Player player) {
        return false;
    }

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING) {
            return EntityDimensions.scalable(0.6F, 1.65F).withEyeHeight(vanilla.eyeHeight());
        }
        if (pose == Pose.SWIMMING) {
            return EntityDimensions.scalable(0.6F, player.isShiftKeyDown() ? 1.65F : 1.8F);
        }
        return vanilla;
    }
}
