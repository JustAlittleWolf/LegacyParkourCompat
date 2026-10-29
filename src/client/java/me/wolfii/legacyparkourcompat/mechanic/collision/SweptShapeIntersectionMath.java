package me.wolfii.legacyparkourcompat.mechanic.collision;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * Full-half-size segment intersection shared by source-identical sweep predicates.
 * This matches Mojmap 1.21.2-1.21.8 AABB.collidedAlongVector; 1.21.9 and 26.2
 * shrink each half-size before the same checks.
 */
public final class SweptShapeIntersectionMath {
    private SweptShapeIntersectionMath() {}

    public static boolean collides(AABB movingBox, Vec3 movement, List<AABB> shapeParts) {
        Vec3 from = movingBox.getCenter();
        Vec3 to = from.add(movement);

        for (AABB shapePart : shapeParts) {
            AABB inflated = shapePart.inflate(
                movingBox.getXsize() * 0.5,
                movingBox.getYsize() * 0.5,
                movingBox.getZsize() * 0.5
            );
            if (inflated.contains(to) || inflated.contains(from)) {
                return true;
            }

            if (inflated.clip(from, to).isPresent()) {
                return true;
            }
        }

        return false;
    }
}
