package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;

/**
 * 1.21.11 Mojmap, net.minecraft.world.entity.Entity#updateFluidHeightAndDoFluidPushing,
 * accepts any nonzero accumulated water flow before normalization. The loaded scan
 * and Player#isPushedByFluid keep the non-flying player gate. 26.1.2 unobfuscated,
 * net.minecraft.world.entity.EntityFluidInteraction#update and
 * EntityFluidInteraction.Tracker#applyCurrentTo, reject an accumulated flow whose
 * squared length is below 1.0E-5F before averaging and scaling; Player#isPushedByFluid
 * still gates the scan. The corresponding cutoff is already bypassed for shallow
 * water by the tracker context, which retains the old nonzero-flow check.
 *
 * The source scan and retained-water cutoff behavior begin in the 1.16 movement
 * implementation. The lower bound prevents applying this later operation to older
 * profiles whose water-current mechanism differs. Historical depth from the deflated
 * box is restored independently by FluidCurrentDepthChange.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowWaterCurrentCutoff implements ShallowWaterCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(ParkourVersion selected, double waterHeight) {
        return selected.newerThanOrEqual(ParkourVersion.V1_16)
            && waterHeight > 0.0
            && waterHeight < 0.4;
    }
}
