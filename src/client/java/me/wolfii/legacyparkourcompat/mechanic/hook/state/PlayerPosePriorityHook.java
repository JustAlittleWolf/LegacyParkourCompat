package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Pose;

/** Desired-pose precedence before the native fit checks and fallback. */
@MechanicType("player.pose.priority")
public interface PlayerPosePriorityHook extends VersionedMechanic {
    Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, boolean swimming, Pose vanilla);
}
