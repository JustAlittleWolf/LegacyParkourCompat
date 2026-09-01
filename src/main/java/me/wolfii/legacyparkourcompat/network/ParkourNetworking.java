package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;

public final class ParkourNetworking {
    private ParkourNetworking() {
    }

    public static void register() {
        PayloadTypeRegistry.clientboundConfiguration().register(ForceParkourVersionPayload.TYPE, ForceParkourVersionPayload.CODEC);
        PayloadTypeRegistry.serverboundConfiguration().register(ParkourHandshakeAckPayload.TYPE, ParkourHandshakeAckPayload.CODEC);

        ServerConfigurationNetworking.registerGlobalReceiver(ParkourHandshakeAckPayload.TYPE, (payload, context) -> {
            context.packetListener().completeTask(ParkourHandshakeTask.TYPE);
        });

        ServerConfigurationConnectionEvents.CONFIGURE.register((listener, server) -> ParkourHandshake.configure(listener));
        LegacyParkourCompat.LOGGER.debug("Registered parkour version handshake");
    }
}
