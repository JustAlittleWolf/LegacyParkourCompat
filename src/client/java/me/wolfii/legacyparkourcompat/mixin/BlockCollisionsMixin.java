package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.PistonHeadCollisionScanner;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScanHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.LowerRowCollisionCandidateHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.PistonMovingBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Mixin(BlockCollisions.class)
abstract class BlockCollisionsMixin<T> {
    @Shadow @Final private CollisionContext context;
    @Shadow @Final private AABB box;
    @Shadow @Final private CollisionGetter collisionGetter;
    @Shadow @Final private boolean onlySuffocatingBlocks;
    @Shadow @Final private BiFunction<BlockPos.MutableBlockPos, VoxelShape, T> resultProvider;
    @Shadow private BlockGetter getChunk(int x, int z) { throw new AssertionError(); }
    @Unique private PistonHeadCollisionScanner<T> legacyparkourcompat$pistons;
    @Unique private boolean legacyparkourcompat$initialized;

    @Inject(method = "computeNext", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalPistonParts(CallbackInfoReturnable<T> cir) {
        if (!this.legacyparkourcompat$initialized) {
            this.legacyparkourcompat$initialized = true;
            var player = MovementRuntime.playerFrom(this.context);
            var hook = MovementRuntime.find(BlockCollisionScanHook.class, player);
            if (!this.onlySuffocatingBlocks && !this.context.isPlacement() && hook.isPresent()) {
                var scan = hook.get().scan(this.box);
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
        if (this.legacyparkourcompat$pistons != null && state.is(Blocks.PISTON_HEAD)) {
            return Shapes.empty();
        }
        if (!this.onlySuffocatingBlocks && !context.isPlacement() && !context.alwaysCollideWithFluid()
            && PistonMovingBlockEntityAccessor.legacyparkourcompat$noClip().get() == null
            && MovementRuntime.find(LowerRowCollisionCandidateHook.class, MovementRuntime.playerFrom(context))
                .map(hook -> hook.excludeCandidate(state, level, pos, this.box)).orElse(false)) {
            return Shapes.empty();
        }
        return original.call(context, state, level, pos);
    }
}
