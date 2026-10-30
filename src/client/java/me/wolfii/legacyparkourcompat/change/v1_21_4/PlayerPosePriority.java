package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPosePriorityHook;
import net.minecraft.world.entity.Pose;

/**
 * Ornithe Feather 1.14 PlayerEntity pose update and Mojmap 1.16 / 1.21.4
 * Player.updatePlayerPose select FALL_FLYING before SLEEPING. Mojmap 1.21.4
 * also selects FALL_FLYING before SWIMMING. Mojmap 1.21.5 Player.getDesiredPose
 * reverses both priorities, retained in 26.2 unobfuscated. Restore the overlapping-state choice
 * for the logical client; existing swimming-fit and chosen-pose-fit gates
 * and the final pose write stay native. Sleep entry and server pose decisions
 * are not emulated. Pre-1.14 applicability remains unbound.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PlayerPosePriority implements PlayerPosePriorityHook {
    @Override
    public Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, boolean swimming, Pose vanilla) {
        return fallFlying && (sleeping || swimming) ? Pose.FALL_FLYING : vanilla;
    }
}
