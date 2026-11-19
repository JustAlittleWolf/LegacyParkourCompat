package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionPitchStateHook;

import java.util.Optional;

/**
 * In 1.19.3 through 1.21.1 Mojmap ClientPacketListener.handleMovePlayer adds a
 * relative pitch separately to xRotO and the current pitch. Entity.setXRot
 * stores finite input directly and retains the current value on rejection,
 * while the handler writes xRotO unconditionally. Absolute corrections assign
 * the packet pitch to both destinations under the same respective rules.
 * In 1.21.2 Mojmap and 26.2 unobfuscated the correction helper instead calls
 * Entity.setXRot, which wraps and clamps its finite input.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class PlayerCorrectionPitchState implements PlayerCorrectionPitchStateHook {
    @Override
    public Optional<PitchState> resolvedState(float currentPitch, float previousPitch, float packetPitch, boolean relativePitch) {
        float resolvedPitch = relativePitch ? currentPitch + packetPitch : packetPitch;
        float resolvedPrevious = relativePitch ? previousPitch + packetPitch : packetPitch;
        return Optional.of(new PitchState(resolvedPrevious, Float.isFinite(resolvedPitch) ? resolvedPitch : currentPitch));
    }
}
