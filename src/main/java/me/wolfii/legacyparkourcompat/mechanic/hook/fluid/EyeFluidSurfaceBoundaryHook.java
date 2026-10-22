package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects the upper comparison used to include a player's eyes in a fluid. */
@MechanicType("fluid.eye_surface_boundary")
public interface EyeFluidSurfaceBoundaryHook extends VersionedMechanic {
    /**
     * Adjusts the comparison operand tested by the native upper-bound branch.
     *
     * @param eyeY eye sample Y coordinate used for this scan
     * @param fluidTop upper surface Y coordinate of the fluid
     * @param vanillaComparison native comparison operand for {@code eyeY <= fluidTop}
     * @return comparison operand to test at the upper boundary
     */
    int upperBoundaryComparison(double eyeY, double fluidTop, int vanillaComparison);
}
