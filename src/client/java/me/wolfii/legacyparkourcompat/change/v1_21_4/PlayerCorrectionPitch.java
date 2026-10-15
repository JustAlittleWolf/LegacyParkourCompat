package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PlayerCorrectionPitchHook;

/**
 * Mojmap 1.19.3 ClientPacketListener.handleMovePlayer directly sets finite
 * resolved pitch, bypassing the older absolute-correction absMoveTo clamp.
 * Mojmap 1.21.2 and 1.21.4 PositionMoveRotation.calculateAbsolute retain the
 * raw float sum. Mojmap 1.21.5 adds Mth.clamp(sum, -90F, 90F), still present
 * in 26.2 unobfuscated. Replace only that clamp inside the local player's
 * correction scope, before ROTATE_DELTA consumes the resolved pitch. The
 * native current/old position, yaw, relative flags and velocity math remain.
 * Earlier correction normalization needs separate evidence and stays native.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PlayerCorrectionPitch implements PlayerCorrectionPitchHook {
    @Override
    public float pitch(ParkourVersion selected, float resolvedPitch, float vanilla) {
        return selected.newerThanOrEqual(ParkourVersion.V1_19_3) ? resolvedPitch : vanilla;
    }
}
