package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;

/**
 * 1.15.2 Mojmap, net.minecraft.world.entity.Entity#checkAndHandleWater, and 1.21.11
 * Mojmap, Entity#updateFluidHeightAndDoFluidPushing, admit any nonzero accumulated
 * water flow. 1.15.2 then scales the player sample by 0.014 without a minimum-impulse
 * rule; the separate FluidCurrentMinimumHook owns the later minimum-impulse behavior.
 * At both endpoints Player#isPushedByWater / Player#isPushedByFluid excludes flying
 * players. 26.1.2 unobfuscated, net.minecraft.world.entity.EntityFluidInteraction#update
 * and EntityFluidInteraction.Tracker#applyCurrentTo, reject accumulated flow whose
 * squared length is below 1.0E-5F before averaging and scaling; Player#isPushedByFluid
 * still gates the scan. The shallow-water tracker context retains the historical
 * nonzero-flow cutoff independently of the minimum-impulse operation.
 *
 * The 1.15.2 checkAndHandleWater operation is the lower bound for this retained-water
 * cutoff. Earlier sampler and LiquidBlock mechanisms are separate operations.
 * Historical depth from the deflated box is restored independently by
 * FluidCurrentDepthChange.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowWaterCurrentCutoff implements ShallowWaterCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(ParkourVersion selected, double waterHeight) {
        return selected.newerThanOrEqual(ParkourVersion.V1_15_2)
            && waterHeight > 0.0
            && waterHeight < 0.4;
    }
}
