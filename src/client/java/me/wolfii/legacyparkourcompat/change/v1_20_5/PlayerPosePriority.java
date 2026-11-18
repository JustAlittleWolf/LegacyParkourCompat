package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPosePriorityHook;
import net.minecraft.world.entity.Pose;

/**
 * Preserve the previously emulated sleeping/fall-flying choice for earlier profiles.
 * Ornithe Feather 1.14 PlayerEntity pose update and Mojmap 1.16 / 1.21.4
 * Player.updatePlayerPose select FALL_FLYING before SLEEPING; Mojmap 1.21.5
 * Player.getDesiredPose reverses it. Pre-1.14 applicability remains unbound.
 * The swimming overlap is supported from Mojmap 1.21 Player#updatePlayerPose;
 * this fence retains the native swimming choice for earlier selections.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class PlayerPosePriority implements PlayerPosePriorityHook {
    @Override
    public Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, boolean swimming, Pose vanilla) {
        return selected.newerThanOrEqual(ParkourVersion.V1_14) && sleeping && fallFlying ? Pose.FALL_FLYING : vanilla;
    }
}
