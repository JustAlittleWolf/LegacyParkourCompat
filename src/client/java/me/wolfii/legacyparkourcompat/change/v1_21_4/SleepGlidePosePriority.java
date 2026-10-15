package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SleepGlidePosePriorityHook;
import net.minecraft.world.entity.Pose;

/**
 * Mojmap 1.16 and 1.21.4 Player.updatePlayerPose select FALL_FLYING before
 * SLEEPING. Mojmap 1.21.5 Player.getDesiredPose reverses that priority,
 * retained in 26.2 unobfuscated. Restore only the overlapping-state choice
 * for the logical client; existing swimming-fit and chosen-pose-fit gates
 * and the final pose write stay native. Sleep entry and server pose decisions
 * are not emulated. Pre-1.16 applicability remains unbound.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class SleepGlidePosePriority implements SleepGlidePosePriorityHook {
    @Override
    public Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, Pose vanilla) {
        return selected.newerThanOrEqual(ParkourVersion.V1_16) && sleeping && fallFlying ? Pose.FALL_FLYING : vanilla;
    }
}
