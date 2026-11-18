package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPosePriorityHook;
import net.minecraft.world.entity.Pose;

/**
 * Ornithe Feather 1.14 PlayerEntity pose selection and Mojmap 1.16
 * Player#updatePlayerPose select FALL_FLYING before SLEEPING. In exact Mojmap
 * 1.21, 1.21.1, 1.21.2, 1.21.3 and 1.21.4, Player#updatePlayerPose also
 * selects FALL_FLYING before SWIMMING. Entity#updateSwimming and
 * LivingEntity#updateFallFlying admit the locally controlled dry-glider
 * water-entry overlap. Mojmap 1.21.5 and 26.2 unobfuscated
 * Player#getDesiredPose select sleeping and swimming first. The native
 * swimming-fit gate, desired-pose fit and fallback still run.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PlayerPosePriority implements PlayerPosePriorityHook {
    @Override
    public Pose desiredPose(ParkourVersion selected, boolean sleeping, boolean fallFlying, boolean swimming, Pose vanilla) {
        return fallFlying && (sleeping || swimming) ? Pose.FALL_FLYING : vanilla;
    }
}
