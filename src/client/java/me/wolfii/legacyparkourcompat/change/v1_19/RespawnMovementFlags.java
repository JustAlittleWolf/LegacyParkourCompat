package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementFlagsHook;

/**
 * Restores only the old local player's sprinting, swimming and fall-flying flags.
 *
 * <p>Mojmap 1.19.2 {@code ClientPacketListener.handleRespawn} unconditionally
 * assigns the old player's entity data to its replacement. Mojmap 1.19.3
 * {@code ClientPacketListener.handleRespawn} assigns nondefault entity data only
 * when {@code ClientboundRespawnPacket.shouldKeep((byte) 2)} is true. The
 * shared flag byte is {@code Entity.DATA_SHARED_FLAGS_ID}; bits 3, 4 and 7
 * are read by {@code Entity.isSprinting}, {@code Entity.isSwimming}, and
 * {@code LivingEntity.isFallFlying} during travel. This preserves those bits
 * while retaining the replacement player's other native data.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class RespawnMovementFlags implements RespawnMovementFlagsHook {
    private static final int MOVEMENT_FLAGS = (1 << 3) | (1 << 4) | (1 << 7);

    @Override
    public byte restore(byte oldSharedFlags, byte newSharedFlags) {
        return (byte) ((newSharedFlags & ~MOVEMENT_FLAGS) | (oldSharedFlags & MOVEMENT_FLAGS));
    }
}
