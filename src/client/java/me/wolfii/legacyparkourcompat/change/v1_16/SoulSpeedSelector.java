package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedBlockHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.FenceGateBlock;

/** 1.16 Mojmap LivingEntity#onSoulSpeedBlock reads getBlockStateOn(). */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class SoulSpeedSelector implements SoulSpeedBlockHook {
    public SoulSpeedSelector() {
    }

    @Override
    public boolean onSoulSpeedBlock(LivingEntity entity, BlockPos supportPosition) {
        // 1.16 Mojmap Entity#getOnPos and LivingEntity#getBlockStateOn select
        // floor(position.y - 0.2F), falling back one block only for an air cell over
        // a fence, wall, or fence gate.
        BlockPos pos = BlockPos.containing(entity.getX(), entity.getY() - 0.2F, entity.getZ());
        var state = entity.level().getBlockState(pos);
        if (state.isAir()) {
            BlockPos below = pos.below();
            var belowState = entity.level().getBlockState(below);
            if (belowState.is(BlockTags.FENCES) || belowState.is(BlockTags.WALLS)
                || belowState.getBlock() instanceof FenceGateBlock) {
                pos = below;
            }
        }
        return entity.level().getBlockState(pos).is(BlockTags.SOUL_SPEED_BLOCKS);
    }
}
