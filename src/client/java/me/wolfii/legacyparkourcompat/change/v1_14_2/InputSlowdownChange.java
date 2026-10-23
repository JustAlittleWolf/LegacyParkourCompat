package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.input.HistoricalInputSlowdown;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.InputSlowdownHook;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Minecraft 1.14.2 (Yarn 1.14.2+build.7-v2), ClientPlayerEntity
 * .isInSneakingPose and .tickMovement: excludes swimming before testing
 * crouch fit and previous sneak intent. Minecraft 1.14.1
 * (Yarn 1.14.1+build.10-v2), same members, lacks this exclusion.
 * Entity.shouldLeaveSwimmingPose retains the dry swimming-pose branch.
 * Continuity: Minecraft 1.14.3 (Yarn 1.14.3+build.13-v2), ClientPlayerEntity.isInSneakingPose
 * and .tickMovement; Minecraft 1.14.4 (Mojmap), LocalPlayer
 * .isVisuallySneaking and .aiStep.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class InputSlowdownChange implements InputSlowdownHook {
    @Override
    public boolean samplePoseSlowdown(Player player) {
        boolean sneakingPose = !player.getAbilities().flying && !player.isSwimming()
            && HistoricalInputSlowdown.fits(player, 1.5F)
            && (player.isShiftKeyDown() || !HistoricalInputSlowdown.fits(player, 1.8F));
        return sneakingPose || player.getPose() == Pose.SWIMMING && !player.isInWater();
    }

    /**
     * Minecraft 1.14.1 (Yarn 1.14.1+build.10-v2) and 1.14.2
     * (Yarn 1.14.2+build.7-v2), KeyboardInput.tick: spectator is excluded,
     * and current sneak intent is combined with the retained pre-poll sample.
     * Minecraft 1.14.3 (Yarn 1.14.3+build.13-v2), KeyboardInput.tick, and
     * 1.14.4 (Mojmap), KeyboardInput.tick, retain this combination. In
     * 1.15, 1.15.1 and 1.15.2 (Mojmap), KeyboardInput.tick instead uses
     * only LocalPlayer.aiStep's pre-poll isMovingSlowly sample. Thus the
     * first shift press of an ordinary grounded player slows here, while
     * the release transition slows in both forms because sampledPose still
     * observes the previous shift state.
     */
    @Override
    public boolean shouldSlowDown(Player player, boolean sampledPose) {
        return !player.isSpectator() && (player.isShiftKeyDown() || sampledPose);
    }

    /** Both exact KeyboardInput.tick sources multiply in double then cast to float. */
    @Override
    public float scaleImpulse(float impulse) {
        return (float)(impulse * 0.3);
    }
}
