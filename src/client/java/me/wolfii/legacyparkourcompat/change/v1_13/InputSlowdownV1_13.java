package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.InputSlowdownHook;
import net.minecraft.world.entity.player.Player;

/**
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
}
