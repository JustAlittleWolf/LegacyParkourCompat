package me.wolfii.legacyparkourcompat.change.v1_15_2.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaResultProjectionHook;

/**
 * Resolver barrier before the accepted 1.16/1.16.1 water-first lava-scan
 * operation. Earlier selected profiles retain the native lava-result view.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeLavaResultProjection implements LavaResultProjectionHook {
    @Override
    public boolean allowLavaResults(boolean legacyWaterPresenceProbeResult) {
        return true;
    }
}
