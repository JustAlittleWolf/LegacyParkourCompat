package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.client.player.LocalPlayer;
import org.jspecify.annotations.Nullable;

/** Local-player correction scope shared by the packet writer and value resolver. */
public final class PlayerCorrectionContext {
    private static final ThreadLocal<LocalPlayer> PLAYER = new ThreadLocal<>();

    private PlayerCorrectionContext() {}

    public static @Nullable LocalPlayer player() {
        return PLAYER.get();
    }

    public static void set(@Nullable LocalPlayer player) {
        if (player == null) {
            PLAYER.remove();
        } else {
            PLAYER.set(player);
        }
    }
}
