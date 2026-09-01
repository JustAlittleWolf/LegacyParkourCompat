package me.wolfii.legacyparkourcompat.network;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Client to server: the forced parkour version was applied locally.
 */
public record ParkourHandshakeAckPayload(String appliedVersionId) implements CustomPacketPayload {
    public static final Type<ParkourHandshakeAckPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(LegacyParkourCompat.MOD_ID, "handshake_ack"));

    public static final StreamCodec<FriendlyByteBuf, ParkourHandshakeAckPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ParkourHandshakeAckPayload::appliedVersionId,
            ParkourHandshakeAckPayload::new);

    @Override
    public Type<ParkourHandshakeAckPayload> type() {
        return TYPE;
    }
}
