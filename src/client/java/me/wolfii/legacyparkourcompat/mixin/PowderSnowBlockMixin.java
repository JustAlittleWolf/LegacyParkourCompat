package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.PowderSnowInsideCollisionShapeHook;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
abstract class PowderSnowBlockMixin {
    @Inject(method = "getEntityInsideCollisionShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$restoreFullInsideShape(
        BlockState state, BlockGetter level, BlockPos pos, Entity entity,
        CallbackInfoReturnable<VoxelShape> cir
    ) {
        if (entity != Minecraft.getInstance().player) {
            return;
        }
        MovementRuntime.find(PowderSnowInsideCollisionShapeHook.class, entity)
            .ifPresent(change -> cir.setReturnValue(change.entityInsideCollisionShape(
                MovementRuntime.profile(entity).target(), cir.getReturnValue())));
    }
}
