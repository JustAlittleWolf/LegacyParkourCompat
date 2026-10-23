package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.EmptyShapeMovementHook;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/**
 * 1.17.1 Mojmap Entity.collide includes the border unless it overlaps the
 * deflated player box. Entity.collideBoundingBoxHeuristically chooses
 * collideBoundingBoxLegacy with at least two nonzero axes. Shapes.collide
 * (Axis,AABB,Stream,double) snaps abs(axis) < 1.0E-7 before its first shape,
 * even when that only shape is the distant border.
 * 1.18 and 1.18.1 Mojmap Entity.collideBoundingBox omit the distant border;
 * Entity.collideWithShapes returns an empty-list request untouched. 1.18.2
 * Mojmap Entity.collideBoundingBox/collideWithShapes retain that behavior.
 * Only the empty-list difference is implemented here, not a replacement solver.
 * The product also recognizes its sole admitted distant-border shape as the
 * otherwise-empty native list, using the original admission and shape rather
 * than sampling moving-border geometry again. Admission is established by the
 * existing deflated-current-box gate. Retain zero input axes, including -0.0.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class EmptyShapeTinyAxisMovement implements EmptyShapeMovementHook {
    @Override
    public Optional<Vec3> resolve(Vec3 requested) {
        int nonzeroAxes = (requested.x != 0.0 ? 1 : 0)
            + (requested.y != 0.0 ? 1 : 0) + (requested.z != 0.0 ? 1 : 0);
        if (nonzeroAxes < 2) {
            return Optional.empty();
        }
        return Optional.of(new Vec3(snap(requested.x), snap(requested.y), snap(requested.z)));
    }

    private static double snap(double value) {
        return value != 0.0 && Math.abs(value) < 1.0E-7 ? 0.0 : value;
    }
}
