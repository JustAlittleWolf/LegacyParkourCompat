package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.EntityInsideShapeHook;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
abstract class BlockStateBaseEntityInsideShapeMixin {
    @Inject(
        method = "getEntityInsideCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("HEAD"), cancellable = true
    )
    private void legacyparkourcompat$useHistoricalEntityInsideShape(
        BlockGetter level, BlockPos pos, Entity entity, CallbackInfoReturnable<VoxelShape> cir
    ) {
        if (entity != Minecraft.getInstance().player) {
            return;
        }

        BlockBehaviour.BlockStateBase state = (BlockBehaviour.BlockStateBase)(Object)this;
        MovementRuntime.find(EntityInsideShapeHook.class, state.getBlock(), entity)
            .flatMap(hook -> hook.entityInsideShape(state, level, pos, entity))
            .ifPresent(cir::setReturnValue);
    }
}
