package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

/** Shared block scan for historical immediate and movement-cached lava presence. */
public final class LavaPresenceScan {
    private LavaPresenceScan() {
    }

    /**
     * Scans a half-open block range in X/Y/Z order. {@code max*Exclusive} is
     * already resolved by the caller so older profiles can preserve their
     * distinct upper-bound rounding.
     */
    public static boolean contains(
        Entity entity,
        int minX,
        int minY,
        int minZ,
        int maxXExclusive,
        int maxYExclusive,
        int maxZExclusive,
        boolean requireLoaded
    ) {
        if (minX >= maxXExclusive || minY >= maxYExclusive || minZ >= maxZExclusive) {
            return false;
        }

        BlockPos min = new BlockPos(minX, minY, minZ);
        BlockPos max = new BlockPos(maxXExclusive - 1, maxYExclusive - 1, maxZExclusive - 1);
        if (requireLoaded && !entity.level().hasChunksAt(min, max)) {
            return false;
        }

        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();
        for (int x = minX; x < maxXExclusive; x++) {
            for (int y = minY; y < maxYExclusive; y++) {
                for (int z = minZ; z < maxZExclusive; z++) {
                    if (entity.level().getBlockState(position.set(x, y, z))
                        .getFluidState().is(FluidTags.LAVA)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 1.14.x collision checks floor(min) through floor(max), inclusive, after
     * the 0.001 inset and only when the entire block range is loaded.
     */
    public static boolean containsInclusive(Entity entity, AABB box, boolean requireLoaded) {
        int minX = Mth.floor(box.minX);
        int minY = Mth.floor(box.minY);
        int minZ = Mth.floor(box.minZ);
        int maxX = Mth.floor(box.maxX);
        int maxY = Mth.floor(box.maxY);
        int maxZ = Mth.floor(box.maxZ);
        return contains(entity, minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1, requireLoaded);
    }
}
