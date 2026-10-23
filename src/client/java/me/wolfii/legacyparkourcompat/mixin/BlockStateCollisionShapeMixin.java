package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fence.FenceGlassCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.MovingPistonCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.PistonMovingBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Dispatches player-specific historical shapes after the block's own virtual shape implementation.
 * BlockCollisions uses this player-context overload, which bypasses the two-argument cached shape and
 * preserves subclass overrides before the historical shape is selected.
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
abstract class BlockStateCollisionShapeMixin {
    @Inject(
        method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void legacyparkourcompat$applyPlayerCollisionShape(
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        CallbackInfoReturnable<VoxelShape> cir
    ) {
        var player = MovementRuntime.playerFrom(context);
        if (player == null) {
            return;
        }

        BlockState state = (BlockState)(Object)this;
        MovementRuntime.find(BlockCollisionShapeHook.class, state.getBlock(), player)
            .filter(shape -> shape.supports(MovementRuntime.profile(player).target()))
            .flatMap(shape -> shape.collisionShape(state, level, pos, context))
            .ifPresent(cir::setReturnValue);

        MovementRuntime.find(FenceGlassCollisionHook.class, player)
            .flatMap(hook -> hook.collisionShape(state, level, pos, cir.getReturnValue()))
            .ifPresent(cir::setReturnValue);

        // Assembly resolves independently of the head provider. Apply it last so
        // the older swept-base assembly is not replaced by a later head delta.
        // An active direct piston response remains outside this ordinary query.
        if (state.is(Blocks.MOVING_PISTON)
            && PistonMovingBlockEntityAccessor.legacyparkourcompat$noClip().get() == null) {
            MovementRuntime.find(MovingPistonCollisionShapeHook.class, player).ifPresent(hook -> {
                if (level.getBlockEntity(pos) instanceof PistonMovingBlockEntity piston) {
                    var access = (PistonMovingBlockEntityAccessor) piston;
                    hook.collisionShape(piston.getMovedState(), piston.getDirection(),
                        piston.isExtending(), piston.isSourcePiston(), access.legacyparkourcompat$progress(),
                        access.legacyparkourcompat$previousProgress()).ifPresent(cir::setReturnValue);
                }
            });
        }
    }
}
