package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WallBlock.class)
public abstract class WallBlockMixin {
    @Inject(method = "getCollisionShape", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$horizontalCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        CallbackInfoReturnable<VoxelShape> callback
    ) {
        MovementRuntime.find(WallCollisionShapeHook.class, MovementRuntime.playerFrom(context))
            .flatMap(hook -> hook.horizontalCollisionShape(state, callback.getReturnValue()))
            .ifPresent(callback::setReturnValue);
    }
}
