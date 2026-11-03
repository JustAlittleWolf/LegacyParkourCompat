package me.wolfii.legacyparkourcompat.change.v1_16_2.shape;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.shape.CollisionQueryShapeHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Recreates the 1.16 query-shape encoding through 1.16.5. In 1.16.5 (Mojang
 * mappings), Shapes.create(AABB) and findBits retain raw endpoints when the
 * floor-based grid check fails; in 1.17.1 (Mojang mappings), Shapes.create(AABB)
 * and findBits use nearest-integer rounding. CollisionSpliterator's constructor
 * creates the query shape before collisionCheck admits provider shapes with
 * BooleanOp.AND. The accepted short-pose witness is bounded to that SELF query.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class LegacyCollisionQueryShape implements CollisionQueryShapeHook {
    private static final double MIN_BOUND = -1.0E-7;
    private static final double MAX_BOUND = 1.0000001;
    private static final double GRID_TOLERANCE = 1.0E-7;

    @Override
    public Optional<Encoding> encode(AABB bounds) {
        if (!finite(bounds.minX, bounds.maxX, bounds.minY, bounds.maxY, bounds.minZ, bounds.maxZ)
            || bounds.minX > bounds.maxX || bounds.minY > bounds.maxY || bounds.minZ > bounds.maxZ) {
            return Optional.empty();
        }

        int xBits = findBits(bounds.minX, bounds.maxX);
        int yBits = findBits(bounds.minY, bounds.maxY);
        int zBits = findBits(bounds.minZ, bounds.maxZ);
        if (xBits < 0 || yBits < 0 || zBits < 0) {
            return Optional.of(new Encoding(Kind.RAW,
                Axis.raw(bounds.minX, bounds.maxX),
                Axis.raw(bounds.minY, bounds.maxY),
                Axis.raw(bounds.minZ, bounds.maxZ)));
        }

        if (xBits == 0 && yBits == 0 && zBits == 0) {
            Kind kind = bounds.contains(0.5, 0.5, 0.5) ? Kind.FULL_CUBE : Kind.EMPTY;
            return Optional.of(new Encoding(kind,
                Axis.discrete(bounds.minX, bounds.maxX, 0, 0, 1),
                Axis.discrete(bounds.minY, bounds.maxY, 0, 0, 1),
                Axis.discrete(bounds.minZ, bounds.maxZ, 0, 0, 1)));
        }

        return Optional.of(new Encoding(Kind.DISCRETE,
            discreteAxis(bounds.minX, bounds.maxX, xBits),
            discreteAxis(bounds.minY, bounds.maxY, yBits),
            discreteAxis(bounds.minZ, bounds.maxZ, zBits)));
    }

    private static Axis discreteAxis(double min, double max, int bits) {
        int size = 1 << bits;
        int minCell = (int)Math.round(min * size);
        int maxCell = (int)Math.round(max * size);
        return Axis.discrete(min, max, bits, minCell, maxCell);
    }

    private static int findBits(double min, double max) {
        if (!(min < MIN_BOUND) && !(max > MAX_BOUND)) {
            for (int bits = 0; bits <= 3; bits++) {
                double scale = 1 << bits;
                double scaledMin = min * scale;
                double scaledMax = max * scale;
                boolean minOnGrid = Math.abs(scaledMin - Math.floor(scaledMin)) < GRID_TOLERANCE;
                boolean maxOnGrid = Math.abs(scaledMax - Math.floor(scaledMax)) < GRID_TOLERANCE;
                if (minOnGrid && maxOnGrid) {
                    return bits;
                }
            }
        }
        return -1;
    }

    private static boolean finite(double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        return Double.isFinite(minX) && Double.isFinite(maxX)
            && Double.isFinite(minY) && Double.isFinite(maxY)
            && Double.isFinite(minZ) && Double.isFinite(maxZ);
    }
}
