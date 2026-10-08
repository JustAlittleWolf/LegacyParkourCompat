package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

/** Shared mechanics-neutral iteration over the block cells intersecting a player's vertical bounds. */
public final class SuffocationProbeColumn {
    private SuffocationProbeColumn() {
    }

    public static boolean any(Player player, BlockPos column, Predicate<BlockPos> predicate) {
        int minY = Mth.floor(player.getBoundingBox().minY);
        int maxY = Mth.ceil(player.getBoundingBox().maxY);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(column.getX(), minY, column.getZ());
        for (int y = minY; y < maxY; y++) {
            cursor.setY(y);
            if (predicate.test(cursor)) {
                return true;
            }
        }
        return false;
    }
}
