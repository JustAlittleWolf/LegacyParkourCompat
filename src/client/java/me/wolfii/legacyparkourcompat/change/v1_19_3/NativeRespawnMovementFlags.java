package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementFlagsHook;

/** Keeps native respawn flags from the 1.19.3 entity-data keep-bit gate onward. */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class NativeRespawnMovementFlags implements RespawnMovementFlagsHook {
    @Override
    public byte restore(byte oldSharedFlags, byte newSharedFlags) {
        return newSharedFlags;
    }
}
