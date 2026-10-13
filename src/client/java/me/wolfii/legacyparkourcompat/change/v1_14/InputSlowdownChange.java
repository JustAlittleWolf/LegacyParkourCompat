package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.input.HistoricalInputSlowdown;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.InputSlowdownHook;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Minecraft 1.14.1 (Yarn 1.14.1+build.10-v2), ClientPlayerEntity
 * .isInSneakingPose and .tickMovement: sample crouch eligibility before
 * Input.tick. Minecraft 1.14.2 (Yarn 1.14.2+build.7-v2), same members:
 * adds the swimming exclusion. Entity.shouldLeaveSwimmingPose is the
 * unchanged dry swimming-pose branch. No riding or spectator gate belongs
 * in this pre-poll pose predicate. Continuity: Minecraft 1.14
 * (Ornithe Feather), LocalClientPlayerEntity.m_63723874 and .mobTick.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class InputSlowdownChange implements InputSlowdownHook {
    @Override
    public boolean samplePoseSlowdown(Player player) {
        boolean sneakingPose = !player.getAbilities().flying
            && HistoricalInputSlowdown.fits(player, 1.5F)
            && (player.isShiftKeyDown() || !HistoricalInputSlowdown.fits(player, 1.8F));
        return sneakingPose || player.getPose() == Pose.SWIMMING && !player.isInWater();
    }

    /**
     * Minecraft 1.14.1 (Yarn 1.14.1+build.10-v2) and 1.14.2
     * (Yarn 1.14.2+build.7-v2), KeyboardInput.tick: spectator is excluded,
     * and current sneak intent is combined with the retained pre-poll sample.
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
