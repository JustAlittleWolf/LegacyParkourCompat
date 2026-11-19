package me.wolfii.legacyparkourcompat.mechanic.collision;

import java.util.function.BooleanSupplier;
import net.minecraft.world.entity.player.Player;

/** Marks the collision checks made by the post-travel pose update. */
public final class PlayerPoseCollisionContext {
    private static final ThreadLocal<Player> PLAYER = new ThreadLocal<>();

    private PlayerPoseCollisionContext() {}

    public static boolean isCheckingPose(Player player) {
        return PLAYER.get() == player;
    }

    public static boolean withPoseCheck(Player player, BooleanSupplier check) {
        Player previous = PLAYER.get();
        PLAYER.set(player);
        try {
            return check.getAsBoolean();
        } finally {
            if (previous == null) {
                PLAYER.remove();
            } else {
                PLAYER.set(previous);
            }
        }
    }
}
