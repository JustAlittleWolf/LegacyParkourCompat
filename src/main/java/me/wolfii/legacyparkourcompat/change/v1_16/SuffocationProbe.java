package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SuffocationProbeBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_16)
public class SuffocationProbe implements SuffocationProbeBehavior {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        int minY = Mth.floor(player.getBoundingBox().minY);
        int maxY = Mth.ceil(player.getBoundingBox().maxY);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(pos.getX(), minY, pos.getZ());
        for (int y = minY; y < maxY; y++) {
            cursor.setY(y);
            if (this.isSuffocating(player, target, cursor)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        return player.level().getBlockState(pos).isSuffocating(player.level(), pos);
    }
}
