package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.InputSlowdownHook;
import net.minecraft.world.entity.player.Player;

/**
 * Ornithe Feather 1.13.2 PlayerEntity#updatePlayerPose changes dimensions
 * when the selected crouch box fits, while KeyboardInput#tick slows input only
 * when the sneak key is down. Mojmap 1.14.4 Player#updatePlayerPose can select
 * Pose.SNEAKING when standing does not fit, and LocalPlayer#modifyInput applies
 * its sneaking-speed attribute to that forced pose. Unobfuscated 26.2 retains
 * the forced-pose attribute path in LocalPlayer#modifyInput. This hook keeps
 * the old key-driven slowdown operation separate from pose selection.
 *
 * The 1.8.9 and 1.13.2 Feather mappings
 * ({@code 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java#tick}
 * and
 * {@code 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java#tick})
 * multiply both keyboard axes by double {@code 0.3} when the current sneak
 * key is down, then cast to float. This remains the assigned keyboard slowdown
 * behavior through 1.13.2; flight compensation is a separate operation.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class InputSlowdownV1_13 implements InputSlowdownHook {
    @Override
    public boolean supports(ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_8)
            && selected.olderThanOrEqual(ParkourVersion.V1_13);
    }

    @Override
    public boolean samplePoseSlowdown(Player player) {
        return false;
    }

    @Override
    public boolean shouldSlowDown(Player player, boolean sampledPose) {
        return player.isShiftKeyDown();
    }

    @Override
    public float scaleImpulse(float impulse) {
        return (float)(impulse * 0.3);
    }

    @Override
    public float modifyInputFactor(float nativeFactor) {
        return 1.0F;
    }
}
