package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

/** Shared only by releases with the same retained-depth jump gate. */
public final class WaterDepthJumpGate {
    private WaterDepthJumpGate() {
    }

    public static boolean shouldJump(Player player, double retainedDepth, boolean jumpInput) {
        return jumpInput
            && !player.onGround()
            && retainedDepth > 0.4
            && !player.isInWater()
            && !player.isInLava()
            && !player.isPassenger()
            && Mth.floor(player.getBoundingBox().deflate(0.001).minY) >= 256;
    }
}
