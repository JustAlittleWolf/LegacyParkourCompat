package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void tick(); lines 183-290.
 *   Source SHA-256: e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88.
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   protected void updatePlayerPose() {; lines 285-309.
 *   Source SHA-256: d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85.
 * Bounded source decisions:
 *   Review 67030ba73b0428d3c8c71d7a4375d57661efa16e; accepted, bounded.
 *   Reviewed snapshot c942939e8755d67ca1e3445dfd7680e7d0b68b41; SHA-256 c92149bb776ae1e8a91bc7e2cbb85ca47ada9f55b46779a7daebdd80def4f7dd.
 *   Review 67030ba73b0428d3c8c71d7a4375d57661efa16e; accepted, bounded.
 *   Reviewed snapshot c942939e8755d67ca1e3445dfd7680e7d0b68b41; SHA-256 c92149bb776ae1e8a91bc7e2cbb85ca47ada9f55b46779a7daebdd80def4f7dd.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-STATE-03-sneak-collision-height is unknown within (1.8.9, 1.9.4]
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class SneakingDimensions implements PlayerDimensionsHook, PlayerPoseHook {
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
