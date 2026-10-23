package me.wolfii.legacyparkourcompat.change.lava;

import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LavaMaterialScanUpperBoundHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

/**
 * Shared query for the old immediate lava test. The independent scan-bound
 * hook keeps the 1.8 and 1.9 arithmetic differences separately resolvable.
 *
 * <p>Source: Minecraft 1.8.9 (Ornithe Feather Gen2 build 2),
 * {@code Entity.isInLava}, {@code Box.grown}, and {@code World.containsMaterial};
 * 1.9.4 (same mapping), {@code World.containsMaterial}; and 1.13.2 (same
 * mapping), {@code Entity.isInLava}, {@code Box.contract}, and
 * {@code World.containsMaterial}. The negative float literals are widened to
 * double only after float conversion, as in the old method call.
 */
public final class LavaMaterialQuery {
    private LavaMaterialQuery() {
    }

    public static boolean isInLava(Entity entity, LavaMaterialScanUpperBoundHook scanUpperBound) {
        AABB bounds = entity.getBoundingBox().inflate((double) -0.1F, (double) -0.4F, (double) -0.1F);
        int minX = Mth.floor(bounds.minX);
        int maxX = scanUpperBound.exclusiveUpperBound(bounds.maxX);
        int minY = Mth.floor(bounds.minY);
        int maxY = scanUpperBound.exclusiveUpperBound(bounds.maxY);
        int minZ = Mth.floor(bounds.minZ);
        int maxZ = scanUpperBound.exclusiveUpperBound(bounds.maxZ);
        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    if (entity.level().getBlockState(position.set(x, y, z)).getFluidState().is(FluidTags.LAVA)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
