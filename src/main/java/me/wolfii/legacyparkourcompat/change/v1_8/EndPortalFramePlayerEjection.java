package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class EndPortalFramePlayerEjection extends me.wolfii.legacyparkourcompat.change.v1_16.SuffocationProbe {
    @Override
    protected boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        if (target == ParkourVersion.V1_8 && player.level().getBlockState(pos).is(Blocks.END_PORTAL_FRAME)) {
            return true;
        }
        return super.isSuffocating(player, target, pos);
    }
}
