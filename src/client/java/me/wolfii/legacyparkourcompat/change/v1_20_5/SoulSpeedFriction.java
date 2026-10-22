package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.soulspeed.SoulSpeedFrictionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 1.20.6 Mojmap LivingEntity#getBlockSpeedFactor applies the Soul Speed branch
 * before Entity#getBlockSpeedFactor; 1.21.1 adds MOVEMENT_EFFICIENCY lerping to
 * that base method. Rebuilds the 1.20.6 base operation when the Soul Speed
 * predicates fail, including the base operation's separately selected support
 * position.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class SoulSpeedFriction implements SoulSpeedFrictionHook {
    public SoulSpeedFriction() {
    }

    @Override
    public float blockSpeedFactor(
        LivingEntity entity,
        boolean onSoulSpeedBlock,
        int soulSpeedLevel,
        VanillaFn<BlockPos> baseFactorSupportPosition
    ) {
        if (onSoulSpeedBlock && soulSpeedLevel > 0) {
            return 1.0F;
        }

        BlockState state = entity.level().getBlockState(entity.blockPosition());
        float factor = state.getBlock().getSpeedFactor();
        if (!state.is(Blocks.WATER) && !state.is(Blocks.BUBBLE_COLUMN)) {
            return factor == 1.0F
                ? entity.level().getBlockState(baseFactorSupportPosition.get()).getBlock().getSpeedFactor()
                : factor;
        }
        return factor;
    }
}
