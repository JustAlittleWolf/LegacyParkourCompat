package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidInteractionBoxBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidInteractionFluidBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaCurrentBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
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
    @Redirect(
        method = "update(Lnet/minecraft/world/entity/Entity;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/BlockGetter;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
        )
    )
    private FluidState legacyparkourcompat$fluidInteractionState(BlockGetter level, BlockPos pos, Entity entity) {
        FluidState vanilla = level.getFluidState(pos);
        var behavior = MovementRuntime.find(FluidInteractionFluidBehavior.class, entity);
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
        var behavior = MovementRuntime.find(FluidInteractionBoxBehavior.class, entity);
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
        MovementRuntime.find(LavaCurrentBehavior.class, entity).ifPresent(behavior -> {
            if (!behavior.allowLavaCurrent(entity, true)) {
                callback.cancel();
            }
        });
    }
}
