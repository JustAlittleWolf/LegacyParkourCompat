package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FenceCollisionStateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.pane.PaneWallAttachmentHook;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

/** Client-player dispatch for query-time fence collision connections. */
@Mixin(CrossCollisionBlock.class)
abstract class CrossCollisionBlockMixin {
    @Shadow @Final private Function<BlockState, VoxelShape> collisionShapes;

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$resolveFenceCollision(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context,
        CallbackInfoReturnable<VoxelShape> callback
    ) {
        Entity player = MovementRuntime.playerFrom(context);
        if (state.getBlock() instanceof FenceBlock) {
            MovementRuntime.find(FenceCollisionStateHook.class, player)
                .flatMap(hook -> hook.collisionState(state, level, pos))
                .ifPresent(queryState -> callback.setReturnValue(this.collisionShapes.apply(queryState)));
            return;
        }
        if (!(state.getBlock() instanceof IronBarsBlock)
            || !(player instanceof LocalPlayer localPlayer)
            || !MovementRuntime.appliesTo(localPlayer)) {
            return;
        }

        ParkourVersion selected = MovementRuntime.profile(localPlayer).target();
        BlockState queryState = state;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            var property = CrossCollisionBlock.PROPERTY_BY_DIRECTION.get(direction);
            if (!queryState.getValue(property)) {
                continue;
            }
            BlockState neighbor = level.getBlockState(pos.relative(direction));
            boolean suppressWall = MovementRuntime.find(PaneWallAttachmentHook.class, localPlayer)
                .map(hook -> hook.suppressAttachment(neighbor, selected))
                .orElse(false);
            if (suppressWall) {
                queryState = queryState.setValue(property, false);
            }
        }
        if (queryState != state) {
            callback.setReturnValue(this.collisionShapes.apply(queryState));
        }
    }
}
