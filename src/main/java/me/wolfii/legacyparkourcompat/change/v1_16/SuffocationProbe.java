package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SuffocationProbeBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_16)
public final class SuffocationProbe implements SuffocationProbeBehavior {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        return SuffocationProbeColumn.any(player, pos, cursor -> this.isSuffocating(player, target, cursor));
    }

    protected boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        return player.level().getBlockState(pos).isSuffocating(player.level(), pos);
    }
}
