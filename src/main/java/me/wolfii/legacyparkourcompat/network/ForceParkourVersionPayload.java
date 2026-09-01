package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Server to client: use this parkour version for the connection.
 */
public record ForceParkourVersionPayload(String versionId) implements CustomPacketPayload {
    public static final Type<ForceParkourVersionPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(LegacyParkourCompat.MOD_ID, "force_parkour_version"));

    public static final StreamCodec<FriendlyByteBuf, ForceParkourVersionPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ForceParkourVersionPayload::versionId,
            ForceParkourVersionPayload::new);

    @Override
    public Type<ForceParkourVersionPayload> type() {
        return TYPE;
    }
}
