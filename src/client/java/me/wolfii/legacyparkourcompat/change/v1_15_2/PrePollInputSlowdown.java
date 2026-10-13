package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.input.HistoricalInputSlowdown;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.InputSlowdownHook;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Minecraft 1.14.4 and 1.15 (Mojmap), LocalPlayer.aiStep and
 * KeyboardInput.tick: 1.15 consumes only the pre-poll isMovingSlowly sample,
 * removing the keyboard's current sneak-input and spectator alternatives.
 * Minecraft 1.15.1 and 1.15.2 (Mojmap) retain these operations unchanged.
 * Item slowdown remains 0.2F in LocalPlayer.aiStep, followed by movement-copy
 * damping of 0.98F in LivingEntity.aiStep in all three 1.15 releases.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class PrePollInputSlowdown implements InputSlowdownHook {
    @Override
    public boolean supports(ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_15)
            && selected.olderThanOrEqual(ParkourVersion.V1_15_2);
    }

    /**
     * Minecraft 1.14.4 (Mojmap), LocalPlayer.isVisuallySneaking, compared with
     * 1.15, 1.15.1 and 1.15.2 (Mojmap), LocalPlayer.isCrouching and
     * isMovingSlowly: add the sleeping gate only to failed standing fit.
     * Entity.isVisuallyCrawling still means swimming pose outside body water.
     * Evaluate before polling so isShiftKeyDown reads the previous input.
     */
    @Override
    public boolean samplePoseSlowdown(Player player) {
        boolean crouching = !player.getAbilities().flying && !player.isSwimming()
            && HistoricalInputSlowdown.fits(player, 1.5F)
            && (player.isShiftKeyDown()
                || !player.isSleeping() && !HistoricalInputSlowdown.fits(player, 1.8F));
        return crouching || player.getPose() == Pose.SWIMMING && !player.isInWater();
    }

    @Override
    public boolean shouldSlowDown(Player player, boolean sampledPose) {
        return sampledPose;
    }

    /** KeyboardInput.tick in Mojmap 1.14.4, 1.15, 1.15.1 and 1.15.2. */
    @Override
    public float scaleImpulse(float impulse) {
        return (float)(impulse * 0.3);
    }

}
