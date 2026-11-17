package me.wolfii.legacyparkourcompat.change.v1_9_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PoseCollisionWorldBoundsHook;

/**
 * Reaches the historical X/Z guard only for scan cells that survive the edge filters.
 * Source: Ornithe Feather 1.9.3 and 1.9.4 `World.getCollisions(Box)`,
 * `World.isChunkLoaded(BlockPos, boolean)`, and `ClientWorld.isChunkLoadedAt(int, int, boolean)`.
 */
@MovementChange(emulates = ParkourVersion.V1_9_3)
public final class PoseCollisionWorldBounds implements PoseCollisionWorldBoundsHook {
    private static final int WORLD_LIMIT = 30_000_000;

    @Override
    public boolean hasOutOfBoundsCollision(
        double minX, double minY, double minZ,
        double maxX, double maxY, double maxZ
    ) {
        int xStart = floor(minX) - 1;
        int xEnd = ceil(maxX) + 1;
        int yStart = floor(minY) - 1;
        int yEnd = ceil(maxY) + 1;
        int zStart = floor(minZ) - 1;
        int zEnd = ceil(maxZ) + 1;

        for (int x = xStart; x < xEnd; x++) {
            for (int z = zStart; z < zEnd; z++) {
                int edgeCount = (x == xStart || x == xEnd - 1 ? 1 : 0)
                    + (z == zStart || z == zEnd - 1 ? 1 : 0);
                if (edgeCount == 2) {
                    continue;
                }

                // In 1.9.3/1.9.4 the client allowEmpty=true loaded gate admits every X/Z probe.
                for (int y = yStart; y < yEnd; y++) {
                    if (edgeCount <= 0 || y != yStart && y != yEnd - 1) {
                        if (x < -WORLD_LIMIT || x >= WORLD_LIMIT || z < -WORLD_LIMIT || z >= WORLD_LIMIT) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static int floor(double value) {
        return (int) Math.floor(value);
    }

    private static int ceil(double value) {
        return (int) Math.ceil(value);
    }
}
