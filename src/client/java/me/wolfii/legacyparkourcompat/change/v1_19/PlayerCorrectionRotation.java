package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionRotationHook;
import net.minecraft.util.Mth;

/**
 * Restores the rotation calculation used through the 1.19.2 correction endpoint.
 *
 * <p>In Mojmap 1.19.2, {@code ClientPacketListener.handleMovePlayer} applies
 * the packet's relative {@code Y_ROT}/{@code X_ROT} deltas before calling
 * {@code Entity.absMoveTo}; that method wraps yaw and clamps then wraps pitch,
 * and copies both resulting angles to {@code Entity.yRotO}/{@code Entity.xRotO}. Mojmap
 * 1.19.3's {@code ClientPacketListener.handleMovePlayer} instead applies
 * relative values through {@code Entity.setYRot}/{@code Entity.setXRot} and
 * adjusts {@code Entity.yRotO}/{@code Entity.xRotO} by the raw deltas.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class PlayerCorrectionRotation implements PlayerCorrectionRotationHook {
    @Override
    public Angles apply(float currentYaw, float currentPitch, float packetYaw, float packetPitch,
                        boolean relativeYaw, boolean relativePitch) {
        float yaw = relativeYaw ? currentYaw + packetYaw : packetYaw;
        float pitch = relativePitch ? currentPitch + packetPitch : packetPitch;

        float wrappedYaw = yaw % 360.0F;
        float clampedPitch = Mth.clamp(pitch, -90.0F, 90.0F);
        float wrappedPitch = clampedPitch % 360.0F;
        return new Angles(wrappedYaw, wrappedPitch);
    }
}
