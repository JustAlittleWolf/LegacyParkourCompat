package me.wolfii.legacyparkourcompat.mechanic.hook.shape;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Supplies the historical encoding for the bounded short-pose SELF movement
 * collision query. The runtime materializes the description using vanilla
 * voxel-shape classes; an empty result requests native fallback.
 */
@MechanicType("collision.query_shape_encoding")
public interface CollisionQueryShapeHook extends VersionedMechanic {
    Optional<Encoding> encode(AABB bounds);

    enum Kind {
        EMPTY,
        FULL_CUBE,
        DISCRETE,
        RAW
    }

    /** Exact description of Shapes.create(AABB), including cell coordinates. */
    record Encoding(Kind kind, Axis x, Axis y, Axis z) {
    }

    record Axis(double min, double max, int bits, int minCell, int maxCell) {
        public static Axis discrete(double min, double max, int bits, int minCell, int maxCell) {
            return new Axis(min, max, bits, minCell, maxCell);
        }

        public static Axis raw(double min, double max) {
            return new Axis(min, max, -1, -1, -1);
        }
    }
}
