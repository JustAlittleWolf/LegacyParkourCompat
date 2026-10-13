package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidCurrentDepthOriginHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidCurrentSurfaceHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import java.util.OptionalDouble;

/**
 * 1.21.11 mojmap, net.minecraft.world.entity.Entity#updateFluidHeightAndDoFluidPushing:
 * adds the int scan Y to the float FluidState#getHeight before widening to double,
 * then subtracts getBoundingBox().deflate(0.001).minY before the running Math.max.
 * 26.1 unobfuscated, net.minecraft.world.entity.EntityFluidInteraction#update:
 * widens block Y before adding the float height and subtracts the raw box minimum.
 * The same native arithmetic remains in 26.2 unobfuscated in that member.
 *
 * Water lower bound: 1.13 ornithe-feather, net.minecraft.entity.Entity#m_69693160,
 * called by Entity#checkWaterState, uses int Y + float FluidState#getHeight and
 * getShape().contract(0.001).minY. Lava lower bound: 1.16 mojmap,
 * net.minecraft.world.entity.Entity#updateFluidHeightAndDoFluidPushing, called by
 * Entity#updateInWaterStateAndDoFluidPushing with FluidTags.LAVA, uses the same
 * arithmetic. Older water/lava models are outside these two operations.
 *
 * These independently registered operations correct only numeric depth for cells
 * admitted by the native scan. Admission and eyesInside remain native. Corrected
 * tracker height can also affect EntityFluidInteraction#isInFluid; this does not
 * establish complete historical contact or current parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class FluidCurrentDepthChange implements FluidCurrentSurfaceHook, FluidCurrentDepthOriginHook {
    @Override
    public OptionalDouble currentSurface(ParkourVersion selected, Fluid fluid, int blockY, float fluidHeight) {
        return applies(selected, fluid) ? OptionalDouble.of((double) (blockY + fluidHeight)) : OptionalDouble.empty();
    }

    @Override
    public OptionalDouble currentDepthOrigin(ParkourVersion selected, Fluid fluid, AABB rawBoundingBox) {
        return applies(selected, fluid) ? OptionalDouble.of(rawBoundingBox.deflate(0.001).minY) : OptionalDouble.empty();
    }

    private static boolean applies(ParkourVersion selected, Fluid fluid) {
        return ((fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER)
            && selected.newerThanOrEqual(ParkourVersion.V1_13))
            || ((fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA)
            && selected.newerThanOrEqual(ParkourVersion.V1_16));
    }
}
