package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ForcedRotationPitchHook;

/**
 * Bounds the verified 1.21.6-1.21.8 forced-pitch delta. Earlier profiles keep
 * their existing native response; this is not a claim of historical parity.
 *
 * <p>Evidence for the bounded delta: Minecraft 1.21.8 (Mojmap),
 * ClientPacketListener.handleRotatePlayer and Entity.setXRot, versus
 * Minecraft 1.21.10 (Mojmap), ClientPacketListener.handleRotatePlayer and
 * PositionMoveRotation.calculateAbsolute. The dispatch site supplies the native
 * Minecraft 26.2 (unobfuscated) ClientPacketListener.handleRotatePlayer setter input.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class NativeForcedRotationPitch implements ForcedRotationPitchHook {
    @Override
    public float pitchForSetter(float packetPitch, float nativePitch) {
        return nativePitch;
    }
}
