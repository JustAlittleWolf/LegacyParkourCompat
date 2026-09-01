package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

/**
 * Server-side join rules. The server rejects vanilla clients; the client never
 * disconnects itself.
 *
 * <ul>
 *     <li>Clients with this mod are accepted and told to use the server parkour version.</li>
 *     <li>Vanilla clients are accepted only when their Minecraft version matches that parkour version.</li>
 *     <li>ViaVersion on the server can make a client's protocol differ from native; those clients are
 *     still accepted when that protocol maps to the server parkour version.</li>
 * </ul>
 */
public final class ParkourHandshake {
    private ParkourHandshake() {
    }

    public static void configure(ServerConfigurationPacketListenerImpl listener) {
        ParkourVersion parkourVersion = MovementController.get().selectedVersion();
        boolean hasMod = ServerConfigurationNetworkingCompat.canSendForcePacket(listener);

        if (hasMod) {
            listener.addTask(new ParkourHandshakeTask(parkourVersion.id()));
            return;
        }

        ParkourVersion clientVersion = ViaVersionAccess.translatedClientVersion(listener.getOwner().id())
                .orElse(MovementController.get().nativeVersion());

        if (clientVersion == parkourVersion) {
            return;
        }

        listener.disconnect(Component.translatable(
                "legacyparkourcompat.disconnect.version_mismatch",
                displayName(clientVersion),
                displayName(parkourVersion)));
    }

    static String displayName(ParkourVersion version) {
        if (!version.isCurrent()) {
            return version.id();
        }
        return FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("current");
    }
}
