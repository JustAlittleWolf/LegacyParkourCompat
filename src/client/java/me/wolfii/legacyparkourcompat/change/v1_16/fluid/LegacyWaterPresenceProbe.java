package me.wolfii.legacyparkourcompat.change.v1_16.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

/**
 * Mojmap 1.16.1 {@code Entity#updateInWaterStateAndDoWaterCurrentPushing} and
 * {@code Entity#updateFluidHeightAndDoFluidPushing}: the water pass scans the
 * deflated entity box and is skipped for a Boat passenger. Its
 * {@code LevelReader#hasChunksAt} gate rejects a box wholly outside Y [0, 256),
 * and old-world out-of-height cells read as empty. The wrapper then takes its
 * lava early return when this water-height result is positive.
 */
public final class LegacyWaterPresenceProbe {
    private LegacyWaterPresenceProbe() {
    }

    public static boolean probe(Entity entity) {
        if (entity.getVehicle() instanceof AbstractBoat) {
            return false;
        }

        AABB query = entity.getBoundingBox().deflate(0.001);
        int minX = net.minecraft.util.Mth.floor(query.minX);
        int minY = net.minecraft.util.Mth.floor(query.minY);
        int minZ = net.minecraft.util.Mth.floor(query.minZ);
        int maxX = net.minecraft.util.Mth.ceil(query.maxX);
        int maxY = net.minecraft.util.Mth.ceil(query.maxY);
        int maxZ = net.minecraft.util.Mth.ceil(query.maxZ);
        if (maxY < 0 || minY >= 256) {
            return false;
        }
        Level level = entity.level();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                if (y < 0 || y >= 256) {
                    continue;
                }
                for (int z = minZ; z < maxZ; z++) {
                    pos.set(x, y, z);
                    FluidState fluid = level.getFluidState(pos);
                    if (fluid.is(FluidTags.WATER)
                        && (double) (y + fluid.getHeight(level, pos)) >= query.minY) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
