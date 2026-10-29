package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.LegacyFenceWallNeighborConnectionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
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
import java.util.OptionalInt;

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
        var neighborMaskHook = MovementRuntime.find(WallNeighborMaskHook.class, player);
        OptionalInt selectedHistoricalMask = OptionalInt.empty();
        if (neighborMaskHook.isPresent() && neighborMaskHook.get().suppliesHistoricalMask()) {
            selectedHistoricalMask = neighborMaskHook.get().selectMask(state, level, pos);
            if (selectedHistoricalMask.isEmpty()) {
                callback.setReturnValue(vanillaShape);
                return;
            }
        }
        OptionalInt completeHistoricalMask = selectedHistoricalMask;

        var heightHook = MovementRuntime.find(WallCollisionHeightHook.class, state.getBlock(), player);
        VoxelShape heightAdjustedShape = heightHook
            .map(hook -> hook.applyHeight(vanillaShape, state))
            .orElse(vanillaShape);
        OptionalDouble historicalMaxY = heightHook
            .map(hook -> OptionalDouble.of(hook.maxY(state)))
            .orElseGet(() -> vanillaShape.isEmpty() ? OptionalDouble.empty()
                : OptionalDouble.of(vanillaShape.max(Direction.Axis.Y)));
        int restoredConnections = MovementRuntime.find(LegacyFenceWallNeighborConnectionHook.class, player)
            .map(hook -> hook.restoredConnectionMask(level, pos))
            .orElse(0);
        VoxelShape historicalShape = MovementRuntime.find(WallCollisionShapeHook.class, state.getBlock(), player)
            .flatMap(hook -> hook.horizontalCollisionShape(state, level, pos, heightAdjustedShape,
                historicalMaxY, restoredConnections, completeHistoricalMask))
            .orElseGet(() -> completeHistoricalMask.isPresent() ? vanillaShape : heightAdjustedShape);
        callback.setReturnValue(historicalShape);
    }
}
