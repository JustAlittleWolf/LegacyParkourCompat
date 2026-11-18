package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionPitchStateHook;

import java.util.Optional;

/**
 * In 1.8 through 1.8.9 Ornithe Feather, ClientPlayNetworkHandler#handlePlayerMove
 * adds the current pitch for a relative pitch correction. Entity#updatePositionAndAngles
 * copies the resolved pitch into lastPitch and Entity#setRotation stores its remainder
 * modulo 360 in pitch. The handler then replies with the stored pitch.
 * In 1.9 Ornithe Feather, Entity#updatePositionAndAngles instead clamps pitch before
 * both writes. In 26.2 unobfuscated, PositionMoveRotation#calculateAbsolute and
 * Entity#setXRot both clamp, before ClientPacketListener#handleMovePlayer replies.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class PlayerCorrectionPitch implements PlayerCorrectionPitchStateHook {
    @Override
    public Optional<PitchState> resolvedState(float currentPitch, float packetPitch, boolean relativePitch) {
        if (!Float.isFinite(currentPitch) || !Float.isFinite(packetPitch)) {
            return Optional.empty();
        }
        float resolvedPitch = relativePitch ? packetPitch + currentPitch : packetPitch;
        if (!Float.isFinite(resolvedPitch)) {
            return Optional.empty();
        }
        return Optional.of(new PitchState(resolvedPitch, resolvedPitch % 360.0F));
    }
}
