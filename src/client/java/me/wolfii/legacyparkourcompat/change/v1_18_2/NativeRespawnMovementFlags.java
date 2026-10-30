package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementFlagsHook;

/** Keeps native shared movement flags for selections before the accepted 1.19 boundary. */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class NativeRespawnMovementFlags implements RespawnMovementFlagsHook {
    @Override
    public byte restore(byte oldSharedFlags, byte newSharedFlags) {
        return newSharedFlags;
    }
}
