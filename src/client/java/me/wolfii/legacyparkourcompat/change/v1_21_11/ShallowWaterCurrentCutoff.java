package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.21.11 Entity.updateFluidHeightAndDoFluidPushing admits accumulated current
 * only when its length is positive. The scoped cutoff bypass retains this
 * zero/cancellation exclusion before the modern tracker averages the current.
 * 26.1/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java,
 * Tracker.applyCurrentTo, already has the squared-length cutoff found in
 * 26.1.2. The first changed stable release is 26.1.
 * This cutoff operation does not restore historical float-top rounding or
 * overlap depth relative to the deflated box; those are separate operations.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateInWaterStateAndDoWaterCurrentPushing.
 *   updateFluidHeightAndDoFluidPushing.
 * 1.21.11/mojmap/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java:
 *   getFlow.
 *   getFlow.
 *   getNewLiquid.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java:
 *   getFlow.
 * 1.21.11/mojmap/net/minecraft/world/level/material/WaterFluid.java:
 *   getDropOff.
 * 1.21.11/mojmap/net/minecraft/world/phys/Vec3.java:
 *   normalize.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   updateFluidInteraction.
 *   getFluidInteractionBox.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java:
 *   update.
 *   applyCurrentTo.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityType.java:
 *   class declaration / field initialization.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java:
 *   getNewLiquid.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowWaterCurrentCutoff implements ShallowWaterCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(double waterHeight) {
        return waterHeight > 0.0 && waterHeight < 0.4;
    }
}
