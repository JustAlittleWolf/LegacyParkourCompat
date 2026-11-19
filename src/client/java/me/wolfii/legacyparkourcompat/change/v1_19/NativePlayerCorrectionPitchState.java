package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionPitchStateHook;

import java.util.Optional;

/**
 * In 1.19.2 Mojmap ClientPacketListener.handleMovePlayer uses Entity.absMoveTo
 * to normalize both angle fields. PlayerCorrectionRotation already restores that
 * operation. The separate direct pitch writes begin in 1.19.3 Mojmap.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class NativePlayerCorrectionPitchState implements PlayerCorrectionPitchStateHook {
    @Override
    public Optional<PitchState> resolvedState(float currentPitch, float previousPitch, float packetPitch, boolean relativePitch) {
        return Optional.empty();
    }
}
