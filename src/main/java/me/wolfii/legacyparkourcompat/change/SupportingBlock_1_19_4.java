package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SupportingBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Pre-1.20 supporting-block lookup (MC-262325): ice, slime, and similar block
 * effects use the block under the entity's centre, not the collided edge.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class SupportingBlock_1_19_4 implements SupportingBlockBehavior {
    @Override
    public BlockPos getOnPos(Entity entity, float offset, VanillaFn<BlockPos> vanilla) {
        int x = Mth.floor(entity.getX());
        int y = Mth.floor(entity.getY() - offset);
        int z = Mth.floor(entity.getZ());
        BlockPos pos = new BlockPos(x, y, z);
        if (entity.level().getBlockState(pos).isAir()) {
            BlockPos below = pos.below();
            BlockState belowState = entity.level().getBlockState(below);
            if (belowState.is(BlockTags.FENCES) || belowState.is(BlockTags.WALLS) || belowState.getBlock() instanceof FenceGateBlock) {
                return below;
            }
        }
        return pos;
    }

    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ());
    }
}
