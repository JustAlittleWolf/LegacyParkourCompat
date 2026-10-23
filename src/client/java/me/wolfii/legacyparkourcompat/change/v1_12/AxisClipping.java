package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisClippingHook;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.ArrayList;
import java.util.OptionalDouble;

/**
 * Ornithe Feather 1.12.2 Box.intersectX/intersectY/intersectZ, called by
 * Entity.move, reject exact transverse contact and clip only when the moving
 * box lies wholly before/after the obstacle along the movement axis. No
 * distance or overlap epsilon is used. Ornithe Feather 1.13 VoxelShapes
 * m_84966497/m_14725303 introduce the 1.0E-7 sampling and distance tolerances;
 * 1.13.2 VoxelShape.calculateMaxDistance retains them. 26.2 unobfuscated
 * Entity.collideWithShapes delegates to Shapes.collide and VoxelShape.collideX,
 * which retain both the outer distance cutoff and voxel sampling tolerances.
 *
 * Only single-AABB shapes have a proven correspondence to the generic old
 * Box provider (including the full-cube witness). Empty shapes add no box.
 * Compound voxel decomposition is not proof of the old provider's box list:
 * internal faces can matter when starting inside a shape. Such a call keeps
 * native clipping pending independent provider/decomposition source closure.
 * This is one axis operation; axis order and collision queries resolve separately.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class AxisClipping implements AxisClippingHook {
    @Override
    public OptionalDouble clip(Direction.Axis axis, AABB moving, Iterable<VoxelShape> shapes, double distance) {
        var boxes = new ArrayList<AABB>();
        for (VoxelShape shape : shapes) {
            var parts = shape.toAabbs();
            if (parts.size() > 1) {
                return OptionalDouble.empty();
            }
            boxes.addAll(parts);
        }
        Direction.Axis transverseA = axis == Direction.Axis.X ? Direction.Axis.Y : Direction.Axis.X;
        Direction.Axis transverseB = axis == Direction.Axis.Z ? Direction.Axis.Y : Direction.Axis.Z;
        for (AABB box : boxes) {
            if (!(moving.max(transverseA) <= box.min(transverseA))
                && !(moving.min(transverseA) >= box.max(transverseA))
                && !(moving.max(transverseB) <= box.min(transverseB))
                && !(moving.min(transverseB) >= box.max(transverseB))) {
                if (distance > 0.0 && moving.max(axis) <= box.min(axis)) {
                    double gap = box.min(axis) - moving.max(axis);
                    if (gap < distance) {
                        distance = gap;
                    }
                } else if (distance < 0.0 && moving.min(axis) >= box.max(axis)) {
                    double gap = box.max(axis) - moving.min(axis);
                    if (gap > distance) {
                        distance = gap;
                    }
                }
            }
        }
        return OptionalDouble.of(distance);
    }
}
