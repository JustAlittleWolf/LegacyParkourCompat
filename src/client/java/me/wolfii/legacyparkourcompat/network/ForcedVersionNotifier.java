package me.wolfii.legacyparkourcompat.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Client-local chat notice that the server forced a parkour version.
 * The text is created on the client; the server never sends this message.
 */
public final class ForcedVersionNotifier {
    private static volatile Component pending;

    private ForcedVersionNotifier() {
    }

    public static void queue(Component message) {
        pending = message;
    }

    public static void clear() {
        pending = null;
    }

    public static void flush(Minecraft client) {
        Component message = pending;
        pending = null;
        if (message == null) {
            return;
        }
        client.execute(() -> client.gui.hud.getChat().addClientSystemMessage(message));
    }
}
