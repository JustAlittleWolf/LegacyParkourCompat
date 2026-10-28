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
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeSubmersionHook;
import net.minecraft.util.Mth;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFluidInteraction.class)
abstract class EntityFluidInteractionMixin {
    @WrapOperation(method = "update", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/world/level/material/FluidState;getFlow(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 legacyparkourcompat$localPlayerFluidCurrent(FluidState state, BlockGetter level, BlockPos pos,
                                                             Operation<Vec3> original, Entity entity,
                                                             boolean ignoreCurrent) {
        return MovementRuntime.withLocalPlayerFluidCurrent(entity,
            () -> original.call(state, level, pos));
    }

    @Redirect(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeY()D")
    )
    private double legacyparkourcompat$eyeFluidSampleY(Entity entity) {
        double eyeY = entity.getEyeY();
        return MovementRuntime.find(PlayerEyeFluidSampleHook.class, entity)
            .map(hook -> hook.sampleY(entity, eyeY))
            .orElse(eyeY);
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
        @Local(ordinal = 1) double eyeY,
        @Local(ordinal = 3) double fluidTop,
        @Local FluidState fluidState,
        @Local BlockPos.MutableBlockPos mutablePos) {
        if (entity instanceof Player
            && mutablePos.getX() == entity.getBlockX()
            && mutablePos.getY() == Mth.floor(eyeY)
            && mutablePos.getZ() == entity.getBlockZ()) {
            // This expression executes inside the scanner's existing tracker/tag and loaded-scan gates.
            // Restrict the historical predicate to the actual sampled eye block, not body fluid cells.
            var historical = MovementRuntime.find(PlayerEyeSubmersionHook.class, entity)
                .flatMap(hook -> hook.isSubmerged(
                    fluidState,
                    true,
                    entity.getVehicle() instanceof Boat,
                    eyeY,
                    mutablePos.getY()
                ));
            if (historical.isPresent()) {
                return historical.get();
            }
        }
        int comparison = Double.compare(eyeY, fluidTop);
        if (entity instanceof Player) {
            return MovementRuntime.find(EyeFluidSurfaceBoundaryHook.class, entity)
                .map(hook -> hook.upperBoundaryComparison(eyeY, fluidTop, comparison))
                .orElse(comparison) <= 0;
        }
        return vanilla;
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
