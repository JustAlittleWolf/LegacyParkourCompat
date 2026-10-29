package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PartialShapeSweepHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Mojmap 1.21.2 and 1.21.3 recenter the final entity box, move it to {@code to},
 * then sweep from {@code from} by the displacement to that box's bottom center
 * using the full-half-size AABB predicate. Mojmap 1.21.4 switches back to the
 * start box and forward displacement. Source: 1.21.2 and 1.21.3
 * Entity.collidedWithShapeMovingFrom and AABB.collidedAlongVector; 1.21.4
 * Entity.collidedWithShapeMovingFrom and AABB.collidedAlongVector.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class PartialShapeSweep implements PartialShapeSweepHook {
    @Override
    public Optional<Vec3> reverseDisplacement(AABB finalEntityBox, Vec3 from, Vec3 to) {
        Vec3 center = finalEntityBox.getCenter();
        AABB recenteredAtTo = finalEntityBox.move(center.scale(-1.0)).move(to);
        return Optional.of(from.subtract(recenteredAtTo.getBottomCenter()));
    }
}
