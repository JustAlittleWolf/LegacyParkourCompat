package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/**
 * Minecraft 1.8.9, Ornithe Feather Gen2 build 2,
 * PlayerEntity.getEyeHeight starts at 1.62F and subtracts 0.08F while sneaking;
 * PlayerEntity.tick has no sneaking size write.
 * Minecraft 1.9.4, Ornithe Feather Gen2 build 2, PlayerEntity.updatePlayerPose
 * adds the collision-gated 1.65F crouch body; PlayerEntity.getEyeHeight also
 * subtracts 0.08F for sneaking. Sleep and modern special poses stay native.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-STATE-03-sneak-collision-height is unknown within (1.8.9, 1.9.4]
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class SneakingDimensions implements PlayerDimensionsHook, PlayerPoseHook {
    @Override
    public boolean updatePose(Player player, EntityDimensions previousDimensions, AABB previousBox, float previousEyeHeight) {
        return false;
    }

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose == Pose.CROUCHING && OrdinaryPlayerContext.matches(player)) {
            EntityDimensions standing = player.getDimensions(Pose.STANDING);
            return standing.withEyeHeight(1.62F - 0.08F);
        }
        return vanilla;
    }
}
