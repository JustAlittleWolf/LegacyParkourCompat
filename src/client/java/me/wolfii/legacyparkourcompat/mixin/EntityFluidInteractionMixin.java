package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionBoxHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionFluidHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidScanLoadedChunksHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.EyeFluidSurfaceBoundaryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeFluidSampleHook;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFluidInteraction.class)
abstract class EntityFluidInteractionMixin {
    @Definition(id = "eyeY", local = @Local(type = double.class, ordinal = 1))
    @Definition(id = "fluidBottom", local = @Local(type = double.class, ordinal = 2))
    @Expression("eyeY >= fluidBottom")
    @ModifyExpressionValue(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At("MIXINEXTRAS:EXPRESSION"),
        require = 1
    )
    private boolean legacyparkourcompat$waterEyeFluidBottom(boolean vanilla,
        @Local(argsOnly = true) Entity entity,
        @Local FluidState fluidState,
        @Local(ordinal = 1) double eyeY,
        @Local(ordinal = 2) double fluidBottom) {
        if (!fluidState.is(FluidTags.WATER)) {
            return vanilla;
        }
        return MovementRuntime.find(PlayerEyeFluidSampleHook.class, entity)
            .map(hook -> {
                double sampleY = hook.sampleY(entity, eyeY);
                return sampleY == eyeY ? vanilla : Double.compare(sampleY, fluidBottom) >= 0;
            })
            .orElse(vanilla);
    }

    @Definition(id = "eyeY", local = @Local(type = double.class, ordinal = 1))
    @Definition(id = "fluidTop", local = @Local(type = double.class, ordinal = 3))
    @Expression("eyeY <= fluidTop")
    @ModifyExpressionValue(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At("MIXINEXTRAS:EXPRESSION"),
        require = 1
    )
    private boolean legacyparkourcompat$eyeFluidUpperBoundary(boolean vanilla,
        @Local(argsOnly = true) Entity entity,
        @Local FluidState fluidState,
        @Local(ordinal = 1) double eyeY,
        @Local(ordinal = 3) double fluidTop) {
        int comparison = vanilla ? 0 : 1;
        double sampleY = eyeY;
        if (fluidState.is(FluidTags.WATER)) {
            sampleY = MovementRuntime.find(PlayerEyeFluidSampleHook.class, entity)
                .map(hook -> hook.sampleY(entity, eyeY))
                .orElse(eyeY);
            if (sampleY != eyeY) {
                comparison = Double.compare(sampleY, fluidTop);
            }
        }
        if (entity instanceof Player) {
            double eyeSampleY = sampleY;
            int upperBoundaryComparison = comparison;
            return MovementRuntime.find(EyeFluidSurfaceBoundaryHook.class, entity)
                .map(hook -> hook.upperBoundaryComparison(eyeSampleY, fluidTop, upperBoundaryComparison))
                .orElse(upperBoundaryComparison) <= 0;
        }
        return comparison <= 0;
    }

    @WrapOperation(method = "update", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/EntityFluidInteraction;hasFluidAndLoaded(Lnet/minecraft/world/level/Level;IIIIII)Z"))
    private static boolean legacyparkourcompat$fluidLoadedChunks(Level level, int x0, int y0, int z0, int x1, int y1, int z1,
        Operation<Boolean> original, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof Player player) {
            return MovementRuntime.find(FluidScanLoadedChunksHook.class, player)
                .flatMap(hook -> hook.scanAllowed(player, MovementRuntime.profile(player).target()))
                .orElseGet(() -> original.call(level, x0, y0, z0, x1, y1, z1));
        }
        return original.call(level, x0, y0, z0, x1, y1, z1);
    }

    @Redirect(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/BlockGetter;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
        )
    )
    private FluidState legacyparkourcompat$fluidInteractionState(BlockGetter level, BlockPos pos, Entity entity) {
        FluidState vanilla = level.getFluidState(pos);
        var behavior = MovementRuntime.find(FluidInteractionFluidHook.class, entity);
        return behavior.isPresent() ? behavior.get().fluidState(entity, vanilla) : vanilla;
    }

    @Redirect(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;getFluidInteractionBox()Lnet/minecraft/world/phys/AABB;"
        )
    )
    private @Nullable AABB legacyparkourcompat$fluidInteractionBox(Entity entity) {
        AABB vanilla = entity.getFluidInteractionBox();
        var behavior = MovementRuntime.find(FluidInteractionBoxHook.class, entity);
        return behavior.isPresent() ? behavior.get().interactionBox(entity, vanilla) : vanilla;
    }

    @Inject(method = "applyCurrentTo", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$applyLavaCurrent(
        TagKey<Fluid> fluid,
        Entity entity,
        double scale,
        CallbackInfo callback
    ) {
        if (!fluid.equals(FluidTags.LAVA)) {
            return;
        }
        MovementRuntime.find(LavaCurrentHook.class, entity).ifPresent(behavior -> {
            if (!behavior.allowLavaCurrent(entity, true)) {
                callback.cancel();
            }
        });
    }
}
