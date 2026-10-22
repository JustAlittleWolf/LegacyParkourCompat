package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.EyeFluidSurfaceBoundaryHook;

/**
 * Keeps native comparison behavior before the cached eye-fluid scan exists.
 * 1.15.2 Mojmap {@code Entity#isUnderLiquid} uses a separate
 * {@code fluidHeight + 0.11111111F} threshold; 1.16.5 Mojmap
 * {@code Entity#updateFluidOnEyes} introduces the cached strict surface test.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeEyeFluidSurfaceBoundary implements EyeFluidSurfaceBoundaryHook {
    public NativeEyeFluidSurfaceBoundary() {
    }

    @Override
    public int upperBoundaryComparison(double eyeY, double fluidTop, int vanillaComparison) {
        return vanillaComparison;
    }
}
