package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalDouble;

@Mixin(WallBlock.class)
public abstract class WallBlockMixin {
    @Inject(
        method = "getCollisionShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void legacyparkourcompat$horizontalCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        CallbackInfoReturnable<VoxelShape> callback
    ) {
        var player = MovementRuntime.playerFrom(context);
        if (player == null) {
            return;
        }

        VoxelShape vanillaShape = callback.getReturnValue();
        var heightHook = MovementRuntime.find(WallCollisionHeightHook.class, state.getBlock(), player);
        VoxelShape heightAdjustedShape = heightHook
            .map(hook -> hook.applyHeight(vanillaShape))
            .orElse(vanillaShape);
        OptionalDouble historicalMaxY = heightHook
            .map(hook -> OptionalDouble.of(hook.maxY()))
            .orElseGet(() -> vanillaShape.isEmpty() ? OptionalDouble.empty()
                : OptionalDouble.of(vanillaShape.max(Direction.Axis.Y)));
        VoxelShape historicalShape = MovementRuntime.find(WallCollisionShapeHook.class, state.getBlock(), player)
            .flatMap(hook -> hook.horizontalCollisionShape(state, heightAdjustedShape, historicalMaxY))
            .orElse(heightAdjustedShape);
        callback.setReturnValue(historicalShape);
    }
}
