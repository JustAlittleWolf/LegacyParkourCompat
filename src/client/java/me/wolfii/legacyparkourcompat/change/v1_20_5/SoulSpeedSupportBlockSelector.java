package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedBlockHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;

/** 1.20.6 and 1.21.1 Mojmap LivingEntity#onSoulSpeedBlock use getBlockPosBelowThatAffectsMyMovement. */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class SoulSpeedSupportBlockSelector implements SoulSpeedBlockHook {
    public SoulSpeedSupportBlockSelector() {
    }

    @Override
    public boolean onSoulSpeedBlock(LivingEntity entity, BlockPos soulSpeedSupportPosition) {
        return entity.level().getBlockState(soulSpeedSupportPosition).is(BlockTags.SOUL_SPEED_BLOCKS);
    }
}
