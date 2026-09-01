package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.ConfigurationTask;

import java.util.function.Consumer;

public record ParkourHandshakeTask(String parkourVersionId) implements ConfigurationTask {
    public static final Type TYPE = new Type(LegacyParkourCompat.MOD_ID + ":handshake");

    @Override
    public void start(Consumer<Packet<?>> connection) {
        connection.accept(ServerConfigurationNetworking.createClientboundPacket(
            new ForceParkourVersionPayload(parkourVersionId)));
    }

    @Override
    public Type type() {
        return TYPE;
    }
}
