package me.wolfii.legacyparkourcompat.mechanic.network;

/** Recreates the byte angle representation used by historical absolute entity packets. */
public final class LegacyPacketRotation {
    private LegacyPacketRotation() {
    }

    /**
     * Current absolute position-sync packets carry raw floats; historical teleport packets packed
     * the angle to a signed byte and unpacked it again before interpolation. 1.15.2, 1.16.1, and
     * 1.19 through 1.19.3 Mojmap {@code ClientboundTeleportEntityPacket(Entity)} and
     * {@code ClientPacketListener.handleTeleportEntity} use the same cast and unpack expression.
     * Current 26.2
     * {@code ClientboundEntityPositionSyncPacket} keeps raw floats in {@code PositionMoveRotation}.
     */
    public static float unpackAbsoluteAngle(float angle) {
        byte packed = (byte)(angle * 256.0F / 360.0F);
        return packed * 360 / 256.0F;
    }
}
