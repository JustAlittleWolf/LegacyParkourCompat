package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedBlockHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;

/** 1.16.5–1.19.4 Mojmap LivingEntity#onSoulSpeedBlock selects boundingBox.minY - 0.5000001. */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class SoulSpeedSelector implements SoulSpeedBlockHook {
    public SoulSpeedSelector() {
    }

    @Override
    public boolean onSoulSpeedBlock(LivingEntity entity, BlockPos supportPosition) {
        // 1.16.5–1.19.4 Mojmap LivingEntity#onSoulSpeedBlock selects
        // boundingBox.minY - 0.5000001.
        BlockPos pos = BlockPos.containing(
            entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ()
        );
        return entity.level().getBlockState(pos).is(BlockTags.SOUL_SPEED_BLOCKS);
    }
}
