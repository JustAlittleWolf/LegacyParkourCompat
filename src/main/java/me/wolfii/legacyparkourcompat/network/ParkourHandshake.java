package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

/**
 * Server-side join rules. The server rejects vanilla clients; the client never
 * disconnects itself.
 *
 * <ul>
 *     <li>Clients with this mod are always accepted (older or newer than the server, including
 *     Via-translated connections) and told to use the server parkour version.</li>
 *     <li>Clients without the mod are accepted only when their Minecraft version matches that
 *     parkour version (native or Via-translated).</li>
 * </ul>
 */
public final class ParkourHandshake {
    private ParkourHandshake() {
    }

    public static void configure(ServerConfigurationPacketListenerImpl listener) {
        ParkourVersion parkourVersion = MovementController.get().selectedVersion();
        boolean hasMod = ServerConfigurationNetworkingCompat.canSendForcePacket(listener);
        String player = playerLabel(listener);

        if (hasMod) {
            LegacyParkourCompat.LOGGER.info(
                "Accepting {} with Legacy Parkour Compat; forcing parkour version {}",
                player,
                parkourVersion.id()
            );
            listener.addTask(new ParkourHandshakeTask(parkourVersion.id()));
            return;
        }

        ParkourVersion clientVersion = ViaVersionAccess.translatedClientVersion(listener.getOwner().id())
            .orElse(MovementController.get().nativeVersion());

        if (clientVersion == parkourVersion) {
            LegacyParkourCompat.LOGGER.info(
                "Accepting {} without the mod; Minecraft version {} matches parkour version {}",
                player,
                clientVersion.id(),
                parkourVersion.id()
            );
            return;
        }

        LegacyParkourCompat.LOGGER.info(
            "Disconnecting {}: Minecraft version {} does not match parkour version {} (install Legacy Parkour Compat or join with a matching version)",
            player,
            clientVersion.id(),
            parkourVersion.id()
        );
        listener.disconnect(Component.translatable(
            "legacyparkourcompat.disconnect.version_mismatch",
            clientVersion.id(),
            parkourVersion.id()));
    }

    private static String playerLabel(ServerConfigurationPacketListenerImpl listener) {
        var owner = listener.getOwner();
        return owner.name() + " (" + owner.id() + ")";
    }
}
