package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mixin.accessor.ServerCommonPacketListenerAccessor;
import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Configuration and play listeners share one connection. Only a confirmed mod
 * handshake grants permission; reconnecting cannot inherit another session's
 * permission. Weak keys release closed connections without retaining players.
 */
public final class ServerMovementPolicy {
    private static final Map<Connection, Confirmation> CONNECTIONS = new WeakHashMap<>();

    private record Confirmation(ParkourVersion required, boolean acknowledged) {
    }

    private ServerMovementPolicy() {
    }

    public static synchronized void configure(ServerCommonPacketListenerImpl listener,
                                              ParkourVersion required, boolean hasMod) {
        Connection connection = connection(listener);
        CONNECTIONS.remove(connection);
        if (hasMod) {
            CONNECTIONS.put(connection, new Confirmation(required, false));
        }
    }

    public static synchronized @Nullable String expectedVersion(ServerCommonPacketListenerImpl listener) {
        Confirmation confirmation = CONNECTIONS.get(connection(listener));
        return confirmation == null ? null : confirmation.required().id();
    }

    public static synchronized boolean acknowledge(ServerCommonPacketListenerImpl listener, String appliedVersionId) {
        Connection connection = connection(listener);
        Confirmation confirmation = CONNECTIONS.get(connection);
        if (confirmation == null || confirmation.acknowledged()
            || !confirmation.required().id().equals(appliedVersionId)) {
            return false;
        }
        CONNECTIONS.put(connection, new Confirmation(confirmation.required(), true));
        return true;
    }

    public static synchronized boolean relaxPlayerMovement(ServerCommonPacketListenerImpl listener) {
        Confirmation confirmation = CONNECTIONS.get(connection(listener));
        return confirmation != null && confirmation.acknowledged()
            && !confirmation.required().isCurrent()
            && confirmation.required() != MovementController.get().nativeVersion();
    }

    private static Connection connection(ServerCommonPacketListenerImpl listener) {
        return ((ServerCommonPacketListenerAccessor) listener).legacyparkourcompat$connection();
    }
}
