package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovingFenceEdgeScanHook;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.phys.AABB;

/**
 * Ornithe Feather 1.13 WorldView.getBlockCollisions retains the 1.12.2 World
 * upper-Y-only pruning. In 1.13.1 and 1.13.2 WorldView.getBlockCollisions, every
 * pair of scan-envelope faces is rejected before chunk, state or shape access.
 * 26.2 unobfuscated BlockCollisions.computeNext instead admits MOVING_PISTON
 * at a cursor edge. Restore the old omission for resident carried era-existing
 * fences, using the historical floor/ceil envelope rather than the modern
 * epsilon-expanded cursor's faces. The predicate precedes shape evaluation,
 * so piston direction, progress and the query's intersection do not change it.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class MovingFenceEdgeScan implements MovingFenceEdgeScanHook {
    @Override
    public boolean omitEdge(AABB query, BlockPos pos, BlockEntity blockEntity) {
        if (!(blockEntity instanceof PistonMovingBlockEntity moving) || moving.isSourcePiston()) {
            return false;
        }
        var moved = moving.getMovedState();
        if (!(moved.is(Blocks.OAK_FENCE) || moved.is(Blocks.SPRUCE_FENCE) || moved.is(Blocks.BIRCH_FENCE)
            || moved.is(Blocks.JUNGLE_FENCE) || moved.is(Blocks.ACACIA_FENCE)
            || moved.is(Blocks.DARK_OAK_FENCE) || moved.is(Blocks.NETHER_BRICK_FENCE))) {
            return false;
        }

        int minX = Mth.floor(query.minX) - 1;
        int maxX = Mth.ceil(query.maxX);
        int minY = Mth.floor(query.minY) - 1;
        int maxY = Mth.ceil(query.maxY);
        int minZ = Mth.floor(query.minZ) - 1;
        int maxZ = Mth.ceil(query.maxZ);
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (x < minX || x > maxX || y < minY || y > maxY || z < minZ || z > maxZ) {
            return false;
        }
        boolean xFace = x == minX || x == maxX;
        boolean yFace = y == minY || y == maxY;
        boolean zFace = z == minZ || z == maxZ;
        return (xFace && yFace) || (yFace && zFace) || (zFace && xFace);
    }
}
