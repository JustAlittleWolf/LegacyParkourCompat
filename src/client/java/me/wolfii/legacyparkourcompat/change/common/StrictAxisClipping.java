package me.wolfii.legacyparkourcompat.change.common;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.function.DoubleUnaryOperator;
import me.wolfii.legacyparkourcompat.mechanic.CollisionShapeSourceContext;
import me.wolfii.legacyparkourcompat.mechanic.collision.ComponentCollisionShape;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Strict box clipping from Ornithe Feather 1.12.2 Box.intersectX/intersectY/intersectZ,
 * called by Entity.move. Retains exact transverse-contact rejection and gap math.
 * Ornithe Feather 1.13 VoxelShapes.m_84966497/m_14725303 introduce tolerances;
 * 1.13.2 VoxelShape.calculateMaxDistance and 26.2 unobfuscated VoxelShape.collideX
 * retain them. Provider eligibility belongs to the independently resolved Change.
 *
 * A modern AABB alone is insufficient: 1.12.2 ChorusPlantBlock.addCollisions emits
 * core/arm boxes separately, whereas 1.13.2 PipeBlock and 26.2 VoxelShape.forAllBoxes
 * may merge their union and hide an internal face. Unknown provenance or anything
 * other than an eligible provider's exact translated unit cube or an explicitly
 * collected component wrapper retains native clipping for the entire axis.
 * Axis order and collision queries resolve separately.
 */
public final class StrictAxisClipping {
    private StrictAxisClipping() {}

    public static OptionalDouble clip(Direction.Axis axis, AABB moving, Iterable<VoxelShape> shapes, double distance,
        Predicate<BlockState> eligibleProvider) {
        var clippers = new ArrayList<DoubleUnaryOperator>();
        for (VoxelShape shape : shapes) {
            if (shape.isEmpty()) {
                continue;
            }
            if (shape instanceof ComponentCollisionShape components) {
                clippers.add(amount -> components.collide(axis, moving, amount));
                continue;
            }
            var source = CollisionShapeSourceContext.source(shape);
            if (source == null || !eligibleProvider.test(source.state())) {
                return OptionalDouble.empty();
            }
            var parts = shape.toAabbs();
            var pos = source.pos();
            if (parts.size() != 1) {
                return OptionalDouble.empty();
            }
            AABB box = parts.getFirst();
            if (box.minX != pos.getX() || box.minY != pos.getY() || box.minZ != pos.getZ()
                || box.maxX != pos.getX() + 1.0 || box.maxY != pos.getY() + 1.0 || box.maxZ != pos.getZ() + 1.0) {
                return OptionalDouble.empty();
            }
            clippers.add(amount -> clipBox(box, axis, moving, amount));
        }
        for (DoubleUnaryOperator clipper : clippers) {
            distance = clipper.applyAsDouble(distance);
        }
        return OptionalDouble.of(distance);
    }

    /** Strict single-component math; caller retains authoritative provider order. */
    public static double clipBox(AABB box, Direction.Axis axis, AABB moving, double distance) {
        Direction.Axis transverseA = axis == Direction.Axis.X ? Direction.Axis.Y : Direction.Axis.X;
        Direction.Axis transverseB = axis == Direction.Axis.Z ? Direction.Axis.Y : Direction.Axis.Z;
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
        return distance;
    }
}
