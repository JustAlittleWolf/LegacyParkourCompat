package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowLavaCurrentCutoffHook;

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
 * 1.21.11/mojmap/net/minecraft/data/worldgen/DimensionTypes.java:
 *   bootstrap.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   updateFluidHeightAndDoFluidPushing.
 * 1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FlowingFluid.java:
 *   getNewLiquid.
 *   getFlow.
 * 1.21.11/mojmap/net/minecraft/world/level/material/FluidState.java:
 *   getHeight.
 * 1.21.11/mojmap/net/minecraft/world/level/material/LavaFluid.java:
 *   getDropOff.
 * 1.21.11/mojmap/net/minecraft/world/phys/Vec3.java:
 *   normalize.
 *   normalize.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 *   updateFluidInteraction.
 *   getFluidInteractionBox.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/EntityFluidInteraction.java:
 *   update.
 *   applyCurrentTo.
 * 26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java:
 *   isPushedByFluid.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java:
 *   getNewLiquid.
 *   getFlow.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/FluidState.java:
 *   getHeight.
 * 26.1.2/unobfuscated/net/minecraft/world/level/material/LavaFluid.java:
 *   getDropOff.
 * 26.1.2/unobfuscated/net/minecraft/world/phys/Vec3.java:
 *   normalize.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowLavaCurrentCutoff implements ShallowLavaCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(double lavaHeight) {
        return lavaHeight > 0.0 && lavaHeight < 0.4;
    }
}
