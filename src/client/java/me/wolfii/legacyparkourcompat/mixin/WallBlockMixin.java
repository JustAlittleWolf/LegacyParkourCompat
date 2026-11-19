package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.change.common.WallCollisionGeometry;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.LegacyFenceWallNeighborConnectionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.wall.WallPaneAttachmentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.wall.WallSoulSandConnectionHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Function;

@Mixin(WallBlock.class)
public abstract class WallBlockMixin {
    @Shadow @Final private Function<BlockState, VoxelShape> collisionShapes;

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
        var player = MovementRuntime.blockShapePlayerFrom(context);
        if (player == null) {
            return;
        }

        BlockState shapeState = state;
        if (player instanceof LocalPlayer localPlayer && MovementRuntime.appliesTo(localPlayer)) {
            var selected = MovementRuntime.profile(localPlayer).target();
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                EnumProperty<WallSide> sideProperty = WallBlock.PROPERTY_BY_DIRECTION.get(direction);
                if (shapeState.getValue(sideProperty) == WallSide.NONE) {
                    continue;
                }
                BlockState neighbor = level.getBlockState(pos.relative(direction));
                boolean suppressPane = MovementRuntime.find(WallPaneAttachmentHook.class, localPlayer)
                    .map(hook -> hook.suppressAttachment(state, neighbor, selected))
                    .orElse(false);
                if (suppressPane) {
                    shapeState = shapeState.setValue(sideProperty, WallSide.NONE);
                }
            }
            var soulSandConnection = MovementRuntime.find(WallSoulSandConnectionHook.class, localPlayer);
            if (soulSandConnection.isPresent()) {
                shapeState = soulSandConnection.get().collisionState(shapeState, level, pos, selected);
            }
        }

        BlockState collisionState = shapeState;
        VoxelShape vanillaShape = collisionState == state
            ? callback.getReturnValue()
            : this.collisionShapes.apply(collisionState);
        var neighborMaskHook = MovementRuntime.find(WallNeighborMaskHook.class, player);
        OptionalInt selectedHistoricalMask = OptionalInt.empty();
        if (neighborMaskHook.isPresent() && neighborMaskHook.get().suppliesHistoricalMask()) {
            selectedHistoricalMask = neighborMaskHook.get().selectMask(collisionState, level, pos);
            if (selectedHistoricalMask.isEmpty()) {
                callback.setReturnValue(vanillaShape);
                return;
            }
        }
        OptionalInt completeHistoricalMask = selectedHistoricalMask;
        int restoredConnections = MovementRuntime.find(LegacyFenceWallNeighborConnectionHook.class, player)
            .map(hook -> hook.restoredConnectionMask(level, pos))
            .orElse(0);

        var heightHook = MovementRuntime.find(WallCollisionHeightHook.class, collisionState.getBlock(), player);
        var historicalHeight = heightHook.map(hook -> {
            int mask = completeHistoricalMask.orElseGet(() -> WallCollisionGeometry.mask(collisionState));
            return hook.maxY(mask | restoredConnections);
        });
        VoxelShape heightAdjustedShape = historicalHeight
            .map(maxY -> WallCollisionHeightHook.applyHeight(vanillaShape, maxY))
            .orElse(vanillaShape);
        OptionalDouble historicalMaxY = historicalHeight
            .map(OptionalDouble::of)
            .orElseGet(() -> vanillaShape.isEmpty() ? OptionalDouble.empty()
                : OptionalDouble.of(vanillaShape.max(Direction.Axis.Y)));
        VoxelShape historicalShape = MovementRuntime.find(WallCollisionShapeHook.class, collisionState.getBlock(), player)
            .flatMap(hook -> hook.horizontalCollisionShape(collisionState, level, pos, heightAdjustedShape,
                historicalMaxY, restoredConnections, completeHistoricalMask))
            .orElseGet(() -> completeHistoricalMask.isPresent() ? vanillaShape : heightAdjustedShape);
        callback.setReturnValue(historicalShape);
    }
}
