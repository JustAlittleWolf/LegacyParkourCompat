package me.wolfii.legacyparkourcompat.change.v1_16_2.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaResultProjectionHook;

/**
 * Mojmap 1.16.2 {@code Entity#updateInWaterStateAndDoFluidPushing} scans and
 * applies lava after water unconditionally. Restore native projection after
 * the 1.16/1.16.1 water-positive early return.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class NativeLavaResultProjection implements LavaResultProjectionHook {
    @Override
    public boolean allowLavaResults(boolean legacyWaterPresenceProbeResult) {
        return true;
    }
}
