package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SuffocationProbeBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class EndPortalFramePlayerEjection implements SuffocationProbeBehavior {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        // The 1.8 probe floors getShape().minY + 0.5 and tests that cell plus the cell above.
        if (target == ParkourVersion.V1_8) {
            double sampleY = player.getBoundingBox().minY + 0.5D;
            BlockPos samplePos = BlockPos.containing(pos.getX(), sampleY, pos.getZ());
            if (this.isEndPortalFrame(player, samplePos) || this.isEndPortalFrame(player, samplePos.above())) {
                return true;
            }
        }
        return SuffocationProbeColumn.any(player, pos, cursor ->
            player.level().getBlockState(cursor).isSuffocating(player.level(), cursor));
    }

    private boolean isEndPortalFrame(Player player, BlockPos pos) {
        return player.level().getBlockState(pos).is(Blocks.END_PORTAL_FRAME);
    }
}
