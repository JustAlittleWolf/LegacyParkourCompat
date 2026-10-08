package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SuffocationProbeBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ShulkerBoxBlock;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SuffocationProbe implements SuffocationProbeBehavior {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        return SuffocationProbeColumn.any(player, pos, cursor -> this.isSuffocating(player, target, cursor));
    }

    private boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        var blockState = player.level().getBlockState(pos);
        // This bounded finding proves the behavior at 1.15.2; the first changed release is unknown.
        if (target == ParkourVersion.V1_15_2
            && blockState.getBlock() instanceof ShulkerBoxBlock) {
            return true;
        }
        return player.level().getBlockState(pos).isSuffocating(player.level(), pos);
    }
}
