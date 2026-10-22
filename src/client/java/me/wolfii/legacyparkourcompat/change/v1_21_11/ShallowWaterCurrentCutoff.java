package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;

/**
 * 1.8.9 Ornithe-Feather, net.minecraft.world.World#applyLiquidDrag, admits a
 * nonzero accumulated current when Entity#hasLiquidCollision is true, then normalizes
 * and scales it; PlayerEntity#hasLiquidCollision excludes flying players. 1.13.2
 * Ornithe-Feather, net.minecraft.entity.Entity#m_69693160, also admits any nonzero
 * accumulated current and applies the player sample without the later minimum-impulse
 * rule, with PlayerEntity#hasLiquidCollision excluding flying players. 1.15.2 Mojmap,
 * Entity#checkAndHandleWater, and 1.21.11 Mojmap,
 * Entity#updateFluidHeightAndDoFluidPushing, retain nonzero admission; their player
 * gates are Player#isPushedByWater and Player#isPushedByFluid, respectively.
 * The separate FluidCurrentMinimumHook owns the later minimum-impulse behavior.
 * 26.1.2 unobfuscated, net.minecraft.world.entity.EntityFluidInteraction#update and
 * EntityFluidInteraction.Tracker#applyCurrentTo, reject accumulated flow whose
 * squared length is below 1.0E-5F before averaging and scaling; Player#isPushedByFluid
 * still gates the scan. The shallow-water tracker context retains the historical
 * nonzero-flow cutoff independently of the minimum-impulse operation.
 *
 * The cutoff and minimum impulse are independent operations. Historical depth from
 * the deflated box is restored independently by FluidCurrentDepthChange.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowWaterCurrentCutoff implements ShallowWaterCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(double waterHeight) {
        return waterHeight > 0.0 && waterHeight < 0.4;
    }
}
