package me.wolfii.legacyparkourcompat.mechanic;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import java.util.function.Predicate;

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
        return contains(entity, minX, minY, minZ, maxXExclusive, maxYExclusive, maxZExclusive,
            requireLoaded, state -> state.getFluidState().is(FluidTags.LAVA));
    }

    private static boolean contains(
        Entity entity,
        int minX,
        int minY,
        int minZ,
        int maxXExclusive,
        int maxYExclusive,
        int maxZExclusive,
        boolean requireLoaded,
        Predicate<BlockState> matches
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
                    if (matches.test(entity.level().getBlockState(position.set(x, y, z)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Mojmap 1.15.2 Entity.checkInsideBlocks floors the inset endpoints separately
     * and passes them unchanged to LevelReader.hasChunksAt. That gate checks
     * overlap with [0, 256) and tests every horizontal chunk. Level.getBlockState
     * returns air outside that height. The 26.2 horizontal overload preserves
     * the same chunk test without applying modern dimension height bounds.
     */
    public static boolean containsHistoricalCallbackCell(Entity entity, Predicate<BlockState> matches) {
        AABB box = entity.getBoundingBox();
        int minX = Mth.floor(box.minX + 0.001);
        int minY = Mth.floor(box.minY + 0.001);
        int minZ = Mth.floor(box.minZ + 0.001);
        int maxX = Mth.floor(box.maxX - 0.001);
        int maxY = Mth.floor(box.maxY - 0.001);
        int maxZ = Mth.floor(box.maxZ - 0.001);
        if (maxY < 0 || minY >= 256 || !entity.level().hasChunksAt(minX, minZ, maxX, maxZ)) {
            return false;
        }
        return contains(entity, minX, Math.max(minY, 0), minZ, maxX + 1, Math.min(maxY + 1, 256),
            maxZ + 1, false, matches);
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
