package me.wolfii.legacyparkourcompat;

import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.network.ForceParkourVersionPayload;
import me.wolfii.legacyparkourcompat.network.ForcedVersionNotifier;
import me.wolfii.legacyparkourcompat.network.ParkourHandshakeAckPayload;
import me.wolfii.legacyparkourcompat.network.ViaVersionAccess;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.network.chat.Component;

public class LegacyParkourCompatClient implements ClientModInitializer {
    private static boolean serverHasMod;

    @Override
    public void onInitializeClient() {
        ClientConfigurationNetworking.registerGlobalReceiver(ForceParkourVersionPayload.TYPE, (payload, context) -> {
            applyForcedServerVersion(payload.versionId());
            ClientConfigurationNetworking.send(new ParkourHandshakeAckPayload(payload.versionId()));
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!serverHasMod) {
                applyViaFabricServerVersion();
            }
            ForcedVersionNotifier.flush(client);
        });
        ClientConfigurationConnectionEvents.COMPLETE.register((handler, client) -> {
            if (!serverHasMod) {
                applyViaFabricServerVersion();
            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> resetJoinState());
        ClientConfigurationConnectionEvents.DISCONNECT.register((handler, client) -> resetJoinState());
    }

    private static void applyForcedServerVersion(String versionId) {
        serverHasMod = true;
        ParkourVersion required = ParkourVersion.of(versionId);
        ParkourVersion previous = MovementController.get().selectedVersion();
        MovementController.get().select(required);
        if (previous != required) {
            ForcedVersionNotifier.queue(Component.translatable(
                    "legacyparkourcompat.message.forced",
                    displayName(required)));
        }
    }

    /**
     * ViaFabric/ViaFabricPlus is translating this connection. Match parkour to the
     * remote server's Minecraft version automatically. This is not a server force:
     * the user can still change the version, because that server does not have this mod.
     */
    private static void applyViaFabricServerVersion() {
        ViaVersionAccess.clientTranslatedServerVersion().ifPresent(version -> {
            MovementController.get().select(version);
            LegacyParkourCompat.LOGGER.debug("ViaFabric in use; parkour version set to {}", version);
        });
    }

    private static void resetJoinState() {
        serverHasMod = false;
        ForcedVersionNotifier.clear();
    }

    private static String displayName(ParkourVersion version) {
        if (!version.isCurrent()) {
            return version.id();
        }
        return net.fabricmc.loader.api.FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("current");
    }
}
