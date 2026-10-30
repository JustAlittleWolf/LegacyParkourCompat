package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.callback.SweptBlockResponseHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Dispatches only the accepted historical callback-family responses. */
@Mixin(Entity.class)
abstract class EntitySweptBlockResponseMixin {
    @WrapOperation(
        method = "lambda$checkInsideBlocks$0(ILjava/util/concurrent/atomic/AtomicInteger;ZLnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lit/unimi/dsi/fastutil/longs/LongSet;ZLnet/minecraft/world/phys/AABB;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lnet/minecraft/core/BlockPos;I)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;entityInside(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/InsideBlockEffectApplier;Z)V")
    )
    private void legacyparkourcompat$historicalBlockResponse(
        BlockState state,
        Level level,
        BlockPos pos,
        Entity entity,
        InsideBlockEffectApplier effectApplier,
        boolean precise,
        Operation<Void> original
    ) {
        var response = MovementRuntime.find(SweptBlockResponseHook.class, entity);
        if (response.isEmpty() || !response.get().apply(
            state,
            level,
            pos,
            entity,
            () -> original.call(state, level, pos, entity, effectApplier, precise)
        )) {
            original.call(state, level, pos, entity, effectApplier, precise);
        }
    }

    @WrapOperation(
        method = "lambda$checkInsideBlocks$0(ILjava/util/concurrent/atomic/AtomicInteger;ZLnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lit/unimi/dsi/fastutil/longs/LongSet;ZLnet/minecraft/world/phys/AABB;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lnet/minecraft/core/BlockPos;I)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;entityInside(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/InsideBlockEffectApplier;)V")
    )
    private void legacyparkourcompat$omitHistoricalSweptFluidResponse(
        FluidState state,
        Level level,
        BlockPos pos,
        Entity entity,
        InsideBlockEffectApplier effectApplier,
        Operation<Void> original
    ) {
        boolean omit = MovementRuntime.find(SweptBlockResponseHook.class, entity)
            .map(hook -> hook.omitSweptFluidCallback(state))
            .orElse(false);
        if (!omit) {
            original.call(state, level, pos, entity, effectApplier);
        }
    }
}
