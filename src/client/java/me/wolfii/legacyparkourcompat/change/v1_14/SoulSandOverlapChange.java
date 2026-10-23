package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockInsideResponseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockSpeedFactorHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * The per-overlapping-cell X/Z multiplication by 0.4 persists through 1.14.4.
 * 1.14 ornithe-feather and 1.14.1, 1.14.2, 1.14.3 yarn
 * SoulSandBlock#onEntityCollision retain that writer. The 1.14.2 alias boundary
 * does not change either overlap response or suppression of the newer support
 * speed factor. 1.15 Mojmap SoulsandBlock removes the overlap writer and
 * Entity#getBlockSpeedFactor supplies the newer response; 1.15.1 and 1.15.2
 * retain both changes.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   move calls slime stepOn before checkInsideBlocks, whose soul-sand callback
 *   applies one X/Z multiplication per overlapping cell.
 * 1.14.4/mojmap/net/minecraft/world/level/block/SoulsandBlock.java:
 *   entityInside.
 * 1.14.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkInsideBlocks invokes the block callback in X/Y/Z cell order.
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move, getBlockSpeedFactor.
 * 1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java:
 *   class/shape initialization.
 * 26.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   applyEffectsFromBlocks(List) invokes checkInsideBlocks with the step-based
 *   collector; checkInsideBlocks calls BlockStateBase.entityInside per contact.
 * 26.2/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java:
 *   BlockStateBase.entityInside forwards to the block callback.
 * The client movement callback traversal uses an immediate effect applier, so
 * this return injection multiplies velocity once for each Soul Sand contact in
 * the original traversal order. The deferred applyEffectsFromBlocks collector
 * is suppressed for profiles using that traversal.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class SoulSandOverlapChange implements BlockSpeedFactorHook, BlockInsideResponseHook {
    @Override
    public float movementSpeedFactor(Entity entity, float vanilla) {
        BlockState state = entity.level().getBlockState(entity.blockPosition());
        if (state.is(Blocks.SOUL_SAND)) {
            return 1.0F;
        }
        if (state.is(Blocks.WATER) || state.is(Blocks.BUBBLE_COLUMN) || state.getBlock().getSpeedFactor() != 1.0F) {
            return vanilla;
        }

        BlockPos below = ((EntityInvoker)entity).legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();
        return entity.level().getBlockState(below).is(Blocks.SOUL_SAND) ? 1.0F : vanilla;
    }

    @Override
    public void afterInside(Entity entity, BlockState state) {
        if (state.is(Blocks.SOUL_SAND)) {
            Vec3 movement = entity.getDeltaMovement();
            entity.setDeltaMovement(movement.multiply(0.4, 1.0, 0.4));
        }
    }
}
