package me.wolfii.legacyparkourcompat.network;

import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

final class ServerConfigurationNetworkingCompat {
    private ServerConfigurationNetworkingCompat() {
    }

    static boolean canSendForcePacket(ServerConfigurationPacketListenerImpl listener) {
        return ServerConfigurationNetworking.canSend(listener, ForceParkourVersionPayload.TYPE);
    }
}
