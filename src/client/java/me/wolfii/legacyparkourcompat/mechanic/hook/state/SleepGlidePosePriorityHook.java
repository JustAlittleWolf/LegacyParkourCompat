package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Pose;

/** Desired pose when sleeping and fall-flight states coexist. */
@MechanicType("player.pose.sleep_glide_priority")
public interface SleepGlidePosePriorityHook extends VersionedMechanic {
    Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, Pose vanilla);
}
