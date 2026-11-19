package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.FullCubeOverlapHook;
import net.minecraft.world.phys.AABB;

/**
 * Ornithe Feather 1.10 and 1.12.2 {@code Block.addCollision} admit translated
 * boxes with the strict comparisons in {@code Box.intersects}. This includes
 * the full collision cube from 1.10 {@code FarmlandBlock.getCollisionShape}.
 * Ornithe Feather 1.13 {@code WorldView.getBlockCollisions} instead uses
 * {@code VoxelShapes.overlap} with {@code BooleanOp.AND} for full cubes.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class FullCubeOverlap implements FullCubeOverlapHook {
    @Override
    public boolean overlaps(AABB query, int blockX, int blockY, int blockZ) {
        return query.intersects(blockX, blockY, blockZ, blockX + 1.0, blockY + 1.0, blockZ + 1.0);
    }
}
