package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.DesiredPoseBehavior;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/** 1.21.4 chooses fall-flying before sleeping and swimming. */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FallFlyingFirstPose implements DesiredPoseBehavior {
    @Override
    public Pose desiredPose(Player player, Pose vanilla) {
        if (player.isFallFlying()) {
            return Pose.FALL_FLYING;
        }
        if (player.isSleeping()) {
            return Pose.SLEEPING;
        }
        if (player.isSwimming()) {
            return Pose.SWIMMING;
        }
        if (player.isAutoSpinAttack()) {
            return Pose.SPIN_ATTACK;
        }
        return player.isShiftKeyDown() && !player.getAbilities().flying ? Pose.CROUCHING : Pose.STANDING;
    }
}
