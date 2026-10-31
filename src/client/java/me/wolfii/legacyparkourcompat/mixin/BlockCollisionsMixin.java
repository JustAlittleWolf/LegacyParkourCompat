package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.CollisionShapeSourceContext;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.PistonHeadCollisionScanner;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScan;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScanHook;
import net.minecraft.core.Cursor3D;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.FullCubeOverlapHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.LowerRowCollisionCandidateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovingFenceEdgeScanHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.PistonMovingBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.BooleanOp;
import me.wolfii.legacyparkourcompat.mechanic.collision.ComponentCollisionShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;

@Mixin(BlockCollisions.class)
abstract class BlockCollisionsMixin<T> {
    @Shadow @Final private CollisionContext context;
    @Shadow @Final private AABB box;
    @Shadow private BlockGetter cachedBlockGetter;
    @Shadow @Final @Mutable private Cursor3D cursor;
    @Shadow @Final private CollisionGetter collisionGetter;
    @Shadow @Final private boolean onlySuffocatingBlocks;
    @Shadow @Final private BiFunction<BlockPos.MutableBlockPos, VoxelShape, T> resultProvider;
    @Shadow private BlockGetter getChunk(int x, int z) { throw new AssertionError(); }
    @Unique private PistonHeadCollisionScanner<T> legacyparkourcompat$pistons;
    @Unique private boolean legacyparkourcompat$initialized;
    @Unique private BlockCollisionScan legacyparkourcompat$scan;

    @Inject(method = "<init>(Lnet/minecraft/world/level/CollisionGetter;Lnet/minecraft/world/phys/shapes/CollisionContext;Lnet/minecraft/world/phys/AABB;ZLjava/util/function/BiFunction;)V", at = @At("RETURN"))
    private void legacyparkourcompat$historicalScanBounds(
        CollisionGetter collisionGetter, CollisionContext context, AABB box, boolean onlySuffocatingBlocks,
        BiFunction<BlockPos.MutableBlockPos, VoxelShape, T> resultProvider, CallbackInfo ci
    ) {
        if (onlySuffocatingBlocks || context.isPlacement()) {
            return;
        }
        Entity player = MovementRuntime.playerFrom(context);
        this.legacyparkourcompat$scan = MovementRuntime.find(BlockCollisionScanHook.class, player)
            .flatMap(hook -> hook.scan(box)).orElse(null);
        if (this.legacyparkourcompat$scan != null) {
            BlockCollisionScan scan = this.legacyparkourcompat$scan;
            // Cursor3D advances its X axis fastest, then Y, then Z. Map those to
            // historical Y, Z, X so BlockCollisions visits logical X/Z/Y order.
            this.cursor = new Cursor3D(scan.minY(), scan.minZ(), scan.minX(),
                scan.maxYExclusive() - 1, scan.maxZExclusive() - 1, scan.maxXExclusive() - 1);
        }
    }

    @Redirect(method = "computeNext", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Cursor3D;nextX()I"))
    private int legacyparkourcompat$logicalX(Cursor3D cursor) {
        return this.legacyparkourcompat$scan == null ? cursor.nextX() : cursor.nextZ();
    }

    @Redirect(method = "computeNext", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Cursor3D;nextY()I"))
    private int legacyparkourcompat$logicalY(Cursor3D cursor) {
        return this.legacyparkourcompat$scan == null ? cursor.nextY() : cursor.nextX();
    }

    @Redirect(method = "computeNext", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Cursor3D;nextZ()I"))
    private int legacyparkourcompat$logicalZ(Cursor3D cursor) {
        return this.legacyparkourcompat$scan == null ? cursor.nextZ() : cursor.nextY();
    }

    @Redirect(method = "computeNext", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Cursor3D;getNextType()I"))
    private int legacyparkourcompat$historicalScanCells(Cursor3D cursor) {
        BlockCollisionScan scan = this.legacyparkourcompat$scan;
        if (scan == null) {
            return cursor.getNextType();
        }
        return scan.includes(cursor.nextZ(), cursor.nextX(), cursor.nextY()) ? 0 : 3;
    }

    @Inject(method = "computeNext", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalPistonParts(CallbackInfoReturnable<T> cir) {
        if (!this.legacyparkourcompat$initialized) {
            this.legacyparkourcompat$initialized = true;
            var player = MovementRuntime.playerFrom(this.context);
            if (!this.onlySuffocatingBlocks && !this.context.isPlacement() && this.legacyparkourcompat$scan != null) {
                var scan = this.legacyparkourcompat$scan;
                var border = player.level().getWorldBorder();
                // Accepted witness assumes candidate cells inside the border. Outside that bounded
                // context retain native collection and the separately resolved border operation.
                if (scan.minX() >= border.getMinX() && scan.maxXExclusive() <= border.getMaxX()
                    && scan.minZ() >= border.getMinZ() && scan.maxZExclusive() <= border.getMaxZ()
                    && PistonHeadCollisionScanner.hasOnlyPistonHeads(scan, this.box, this::getChunk)) {
                    this.legacyparkourcompat$pistons = new PistonHeadCollisionScanner<>(scan, this.context,
                        this.box, this.collisionGetter, this.resultProvider, this::getChunk, player);
                }
            }
        }
        if (this.legacyparkourcompat$pistons != null && this.legacyparkourcompat$pistons.advance()) {
            cir.setReturnValue(this.legacyparkourcompat$pistons.result());
        }
    }

    @WrapOperation(method = "computeNext", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/world/phys/shapes/CollisionContext;getCollisionShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/CollisionGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape legacyparkourcompat$filterCandidate(CollisionContext context, BlockState state,
        CollisionGetter level, BlockPos pos, Operation<VoxelShape> original) {
        if (state.is(Blocks.MOVING_PISTON) && !this.onlySuffocatingBlocks
            && !context.isPlacement() && !context.alwaysCollideWithFluid()
            && MovementRuntime.playerFrom(context) instanceof LocalPlayer player
            && this.cachedBlockGetter != null) {
            var hook = MovementRuntime.find(MovingFenceEdgeScanHook.class, player);
            if (hook.isPresent()) {
                var blockEntity = this.cachedBlockGetter.getBlockEntity(pos);
                if (blockEntity != null && hook.get().omitEdge(this.box, pos, blockEntity)) {
                    return Shapes.empty();
                }
            }
        }
        if (this.legacyparkourcompat$pistons != null && state.is(Blocks.PISTON_HEAD)) {
            return Shapes.empty();
        }
        if (!this.onlySuffocatingBlocks && !context.isPlacement() && !context.alwaysCollideWithFluid()
            && PistonMovingBlockEntityAccessor.legacyparkourcompat$noClip().get() == null
            && MovementRuntime.find(LowerRowCollisionCandidateHook.class, MovementRuntime.playerFrom(context))
                .map(hook -> hook.excludeCandidate(state, level, pos, this.box)).orElse(false)) {
            return Shapes.empty();
        }
        VoxelShape shape = original.call(context, state, level, pos);
        return this.onlySuffocatingBlocks ? shape : MovementRuntime.collectMovementBlock(context, state, level, pos, shape);
    }

    @WrapOperation(method = "computeNext", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/world/phys/shapes/Shapes;joinIsNotEmpty(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/BooleanOp;)Z"))
    private boolean legacyparkourcompat$componentQuery(VoxelShape candidate, VoxelShape query,
                                                       BooleanOp operation, Operation<Boolean> original) {
        return candidate instanceof ComponentCollisionShape components
            ? components.intersectsQuery(this.box) : original.call(candidate, query, operation);
    }

    @WrapOperation(method = "computeNext", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/world/phys/AABB;intersects(DDDDDD)Z"))
    private boolean legacyparkourcompat$fullCubeOverlap(AABB query, double x, double y, double z,
        double maxX, double maxY, double maxZ, Operation<Boolean> original) {
        return MovementRuntime.find(FullCubeOverlapHook.class, MovementRuntime.playerFrom(this.context))
            .map(hook -> hook.overlaps(query, (int) x, (int) y, (int) z))
            .orElseGet(() -> original.call(query, x, y, z, maxX, maxY, maxZ));
    }

    @WrapOperation(method = "computeNext", at = @At(value = "INVOKE", target =
        "Ljava/util/function/BiFunction;apply(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object legacyparkourcompat$recordShapeSource(BiFunction<?, ?, ?> provider, Object pos,
        Object shape, Operation<Object> original, @Local BlockState state) {
        CollisionShapeSourceContext.record(
            (VoxelShape) shape, state, (BlockPos) pos);
        return original.call(provider, pos, shape);
    }

}
