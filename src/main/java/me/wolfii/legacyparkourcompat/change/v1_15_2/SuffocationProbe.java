package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ShulkerBoxBlock;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SuffocationProbe extends me.wolfii.legacyparkourcompat.change.v1_16.SuffocationProbe {
    @Override
    protected boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        var blockState = player.level().getBlockState(pos);
        // This bounded finding proves the behavior at 1.15.2; the first changed release is unknown.
        if (target == ParkourVersion.V1_15_2
            && blockState.getBlock() instanceof ShulkerBoxBlock
        ) {
            return true;
        }
        return super.isSuffocating(player, target, pos);
    }
}
