package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPosePriorityHook;
import net.minecraft.world.entity.Pose;

/**
 * Ornithe Feather 1.13 through 1.13.2 PlayerEntity#updatePlayerPose uses direct
 * resizing, while Ornithe Feather 1.14 introduces the distinct desired-pose
 * selection and fit checks used by the later priority change. Retain the
 * native 26.2 unobfuscated Player#getDesiredPose result here so the existing
 * historical resize can run with its own conditions.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class PlayerPosePriority implements PlayerPosePriorityHook {
    @Override
    public Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, boolean swimming, Pose vanilla) {
        return vanilla;
    }
}
