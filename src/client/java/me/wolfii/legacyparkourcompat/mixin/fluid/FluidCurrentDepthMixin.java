package me.wolfii.legacyparkourcompat.mixin.fluid;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidCurrentDepthOriginHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidCurrentSurfaceHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.level.material.Fluid;
import java.util.OptionalDouble;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityFluidInteraction.class)
public abstract class FluidCurrentDepthMixin {
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
    private AABB legacyparkourcompat$captureRawBox(AABB original,
        @Share("currentDepthRawBox") LocalRef<AABB> rawBox) {
        rawBox.set(original);
        return original;
    }

    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/material/FluidState;getHeight(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"))
    private float legacyparkourcompat$captureHeight(float original,
        @Share("currentDepthFluidHeight") LocalFloatRef fluidHeight) {
        fluidHeight.set(original);
        return original;
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D"), index = 0)
    private double legacyparkourcompat$currentDepth(double original,
        @Local(argsOnly = true) Entity entity,
        @Local(ordinal = 1) Fluid fluid,
        @Local BlockPos.MutableBlockPos mutablePos,
        @Local(ordinal = 3) double nativeFluidTop,
        @Share("currentDepthRawBox") LocalRef<AABB> rawBox,
        @Share("currentDepthFluidHeight") LocalFloatRef fluidHeight) {
        if (!MovementRuntime.appliesTo(entity)) {
            return original;
        }
        ParkourVersion target = MovementRuntime.profile(entity).target();
        OptionalDouble surface = MovementRuntime.find(FluidCurrentSurfaceHook.class, entity)
            .map(hook -> hook.currentSurface(target, fluid, mutablePos.getY(), fluidHeight.get()))
            .orElseGet(OptionalDouble::empty);
        OptionalDouble origin = MovementRuntime.find(FluidCurrentDepthOriginHook.class, entity)
            .map(hook -> hook.currentDepthOrigin(target, fluid, rawBox.get()))
            .orElseGet(OptionalDouble::empty);
        if (surface.isEmpty() && origin.isEmpty()) {
            return original;
        }
        // Only the first Math.max argument changes. Its running maximum and all
        // native admission, eye checks and current accumulation remain in place.
        return surface.orElse(nativeFluidTop) - origin.orElse(rawBox.get().minY);
    }
}
