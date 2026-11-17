package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PoseCollisionWorldBoundsHook;

/**
 * The 1.9 query has an X/Z guard, but its preceding loaded-position gate rejects out-of-bounds probes.
 * This preserves the existing native block-query result for 1.9–1.9.2 pose resizing.
 * Source: Ornithe Feather 1.9 `World.getCollisions(Box)` and `World.isChunkLoaded(BlockPos, boolean)`;
 * the identical loaded-gate ordering is present in 1.9.1 and 1.9.2.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class PoseCollisionWorldBounds implements PoseCollisionWorldBoundsHook {
    @Override
    public boolean hasOutOfBoundsCollision(
        double minX, double minY, double minZ,
        double maxX, double maxY, double maxZ
    ) {
        return false;
    }
}
