package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.chat.Component;

public final class ParkourNetworking {
    private ParkourNetworking() {
    }

    public static void register() {
        PayloadTypeRegistry.clientboundConfiguration().register(ForceParkourVersionPayload.TYPE, ForceParkourVersionPayload.CODEC);
        PayloadTypeRegistry.serverboundConfiguration().register(ParkourHandshakeAckPayload.TYPE, ParkourHandshakeAckPayload.CODEC);

        ServerConfigurationNetworking.registerGlobalReceiver(ParkourHandshakeAckPayload.TYPE, (payload, context) -> {
            String expected = ServerMovementPolicy.expectedVersion(context.packetListener());
            String player = context.packetListener().getOwner().id().toString();
            if (!ServerMovementPolicy.acknowledge(context.packetListener(), payload.appliedVersionId())) {
                LegacyParkourCompat.LOGGER.warn(
                    "Client {} acknowledged parkour version '{}' but this server is using '{}'",
                    player,
                    payload.appliedVersionId(),
                    expected
                );
                context.packetListener().disconnect(Component.translatable(
                    "legacyparkourcompat.disconnect.handshake_mismatch", payload.appliedVersionId(), expected));
                return;
            } else {
                LegacyParkourCompat.LOGGER.debug(
                    "Client {} acknowledged parkour version {}",
                    player,
                    payload.appliedVersionId()
                );
            }
            context.packetListener().completeTask(ParkourHandshakeTask.TYPE);
        });

        ServerConfigurationConnectionEvents.CONFIGURE.register((listener, server) -> ParkourHandshake.configure(listener));
        LegacyParkourCompat.LOGGER.debug("Registered parkour version handshake");
    }
}
