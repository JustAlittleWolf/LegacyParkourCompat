package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.EyeFluidSurfaceBoundaryHook;

/**
 * Restores the strict fluid-top test from 1.21.11 Mojmap
 * {@code Entity#updateFluidOnEyes}, where tags are included only when
 * {@code fluidTop > eyeY}. In 26.1.2 unobfuscated,
 * {@code EntityFluidInteraction#update} includes equality with
 * {@code eyeY <= fluidTop}.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class StrictEyeFluidSurfaceBoundary implements EyeFluidSurfaceBoundaryHook {
    public StrictEyeFluidSurfaceBoundary() {
    }

    @Override
    public int upperBoundaryComparison(double eyeY, double fluidTop, int vanillaComparison) {
        return eyeY == fluidTop ? 1 : vanillaComparison;
    }
}
