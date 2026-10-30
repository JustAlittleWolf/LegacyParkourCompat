package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPosePriorityHook;
import net.minecraft.world.entity.Pose;

/**
 * Preserve the previously emulated sleeping/fall-flying choice for earlier profiles.
 * Ornithe Feather 1.14 PlayerEntity pose update and Mojmap 1.16 / 1.21.4
 * Player.updatePlayerPose select FALL_FLYING before SLEEPING; Mojmap 1.21.5
 * Player.getDesiredPose reverses it. Pre-1.14 applicability remains unbound.
 * This lower fence leaves earlier swimming overlap behavior unchanged until its
 * historical applicability is established independently.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class PlayerPosePriority implements PlayerPosePriorityHook {
    @Override
    public Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, boolean swimming, Pose vanilla) {
        return selected.newerThanOrEqual(ParkourVersion.V1_14) && sleeping && fallFlying ? Pose.FALL_FLYING : vanilla;
    }
}
