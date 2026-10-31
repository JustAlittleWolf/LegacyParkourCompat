package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.FullCubeOverlapHook;
import net.minecraft.world.phys.AABB;

/**
 * Recreates the query-shape encoding and AND admission shared by exact 1.15,
 * 1.15.1, and 1.15.2 Mojmap CollisionGetter.getBlockCollisions and
 * Shapes.create/findBits/joinIsNotEmpty/createIndexMerger (IndirectMerger).
 * 1.16.5 Mojmap CollisionSpliterator.collisionCheck instead uses strict
 * AABB.intersects for Shapes.block(), with that AABB member supplying the
 * predicate. Selections before 1.15 retain the preceding product operation
 * through the separate V1_14_2 preservation entry, without a source-parity
 * claim for every earlier profile.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class FullCubeOverlap implements FullCubeOverlapHook {
    private static final double EPSILON = 1.0E-7;

    @Override
    public boolean overlaps(AABB queryBox, int blockX, int blockY, int blockZ) {
        int xBits = findBits(queryBox.minX, queryBox.maxX);
        int yBits = findBits(queryBox.minY, queryBox.maxY);
        int zBits = findBits(queryBox.minZ, queryBox.maxZ);
        if (xBits >= 0 && yBits >= 0 && zBits >= 0) {
            if (xBits == 0 && yBits == 0 && zBits == 0) {
                if (!queryBox.contains(0.5, 0.5, 0.5)) {
                    return false;
                }
                // Shapes.create returns the origin Shapes.block() in this branch.
                return overlaps(0.0, 1.0, blockX, blockX + 1.0)
                    && overlaps(0.0, 1.0, blockY, blockY + 1.0)
                    && overlaps(0.0, 1.0, blockZ, blockZ + 1.0);
            }

            return snappedOverlap(queryBox.minX, queryBox.maxX, xBits, blockX)
                && snappedOverlap(queryBox.minY, queryBox.maxY, yBits, blockY)
                && snappedOverlap(queryBox.minZ, queryBox.maxZ, zBits, blockZ);
        }

        // ArrayVoxelShape retains all six input endpoints. Its old join uses
        // IndirectMerger, whose 1e-7 coalescing applies independently per axis.
        return arrayAxisOverlaps(queryBox.minX, queryBox.maxX, blockX, blockX + 1.0)
            && arrayAxisOverlaps(queryBox.minY, queryBox.maxY, blockY, blockY + 1.0)
            && arrayAxisOverlaps(queryBox.minZ, queryBox.maxZ, blockZ, blockZ + 1.0);
    }

    private static int findBits(double min, double max) {
        if (!(min < -EPSILON) && !(max > 1.0 + EPSILON)) {
            for (int bits = 0; bits <= 3; bits++) {
                double scale = 1 << bits;
                double scaledMin = min * scale;
                double scaledMax = max * scale;
                boolean minAligned = Math.abs(scaledMin - Math.floor(scaledMin)) < EPSILON;
                boolean maxAligned = Math.abs(scaledMax - Math.floor(scaledMax)) < EPSILON;
                if (minAligned && maxAligned) {
                    return bits;
                }
            }
        }
        return -1;
    }

    private static boolean snappedOverlap(double min, double max, int bits, int blockMin) {
        int scale = 1 << bits;
        double snappedMin = (double) Math.round(min * scale) / scale;
        double snappedMax = (double) Math.round(max * scale) / scale;
        // The old discrete shape has no filled cells on a collapsed grid axis.
        return snappedMin < snappedMax && overlaps(snappedMin, snappedMax, blockMin, blockMin + 1.0);
    }

    private static boolean overlaps(double queryMin, double queryMax, double blockMin, double blockMax) {
        return queryMin < blockMax && queryMax > blockMin;
    }

    /** Mirrors the two-coordinate IndirectMerger used against one full-block axis. */
    private static boolean arrayAxisOverlaps(double queryMin, double queryMax, double blockMin, double blockMax) {
        if (!(queryMin < queryMax) || queryMax < blockMin - EPSILON || blockMax < queryMin - EPSILON) {
            return false;
        }
        if (queryMin == blockMin && queryMax == blockMax) {
            return true;
        }

        double[] query = {queryMin, queryMax};
        double[] block = {blockMin, blockMax};
        double[] merged = new double[4];
        int[] queryIndices = new int[4];
        int[] blockIndices = new int[4];
        int mergedSize = 0;
        int queryIndex = 0;
        int blockIndex = 0;
        double previous = Double.NaN;

        while (queryIndex < query.length || blockIndex < block.length) {
            boolean hasQuery = queryIndex < query.length;
            boolean hasBlock = blockIndex < block.length;
            boolean takeQuery = hasQuery && (!hasBlock || query[queryIndex] < block[blockIndex] + EPSILON);
            double coordinate = takeQuery ? query[queryIndex++] : block[blockIndex++];
            boolean include = (queryIndex != 0 && hasQuery || takeQuery)
                && (blockIndex != 0 && hasBlock || !takeQuery);
            if (include) {
                if (!(previous >= coordinate - EPSILON)) {
                    merged[mergedSize] = coordinate;
                    queryIndices[mergedSize] = queryIndex - 1;
                    blockIndices[mergedSize] = blockIndex - 1;
                    mergedSize++;
                    previous = coordinate;
                } else if (mergedSize != 0) {
                    queryIndices[mergedSize - 1] = queryIndex - 1;
                    blockIndices[mergedSize - 1] = blockIndex - 1;
                }
            }
        }

        for (int index = 0; index + 1 < mergedSize; index++) {
            if (queryIndices[index] == 0 && blockIndices[index] == 0
                && merged[index] < merged[index + 1]) {
                return true;
            }
        }
        return false;
    }
}
