package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/** Player pose priority when multiple movement states are active. */
@MechanicType("player.desired_pose")
public interface DesiredPoseBehavior extends VersionedMechanic {
    Pose desiredPose(Player player, Pose vanilla);
}
