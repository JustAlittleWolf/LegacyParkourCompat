package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ForcedRotationPitchHook;

/**
 * Minecraft 1.21.6, 1.21.7 and 1.21.8 (Mojmap),
 * ClientPacketListener.handleRotatePlayer, pass the decoded pitch directly to
 * Entity.setXRot. That setter rejects nonfinite values, then applies
 * Math.clamp(pitch % 360.0F, -90.0F, 90.0F).
 *
 * <p>Minecraft 1.21.9 and 1.21.10 (Mojmap),
 * ClientPacketListener.handleRotatePlayer and PositionMoveRotation.calculateAbsolute,
 * instead clamp the absolute pitch before invoking the setter. For example,
 * decoded pitch 360.0F becomes 90.0F rather than 0.0F.
 * Minecraft 26.2 (unobfuscated), Entity.setXRot, retains the old setter arithmetic,
 * so restoring its raw input preserves the historical order and finite-value gate.
 * Relative rotation flags retain their native behavior at the dispatch site.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class ForcedRotationPitch implements ForcedRotationPitchHook {
    @Override
    public float pitchForSetter(float packetPitch, float nativePitch) {
        return packetPitch;
    }
}
