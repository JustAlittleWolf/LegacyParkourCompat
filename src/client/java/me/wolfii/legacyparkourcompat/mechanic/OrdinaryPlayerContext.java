package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/** Ordinary player states supported by historical two-axis movement operations. */
public final class OrdinaryPlayerContext {
    private OrdinaryPlayerContext() {
    }

    public static boolean matches(Entity entity) {
        return entity instanceof Player player
            && (player.getPose() == Pose.STANDING || player.getPose() == Pose.CROUCHING)
            && !player.isSleeping() && !player.isSwimming() && !player.isFallFlying()
            && !player.isAutoSpinAttack() && !player.isPassenger();
    }

    /** Historical 1.8.9 heading movement only ran for the locally controlled player outside fluids. */
    public static boolean matchesHeadingVelocity(Entity entity) {
        return matches(entity)
            && entity instanceof Player player
            && player.isLocalPlayer()
            && ((!player.isInWater() && !player.isInLava()) || player.getAbilities().flying);
    }
}
