package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.CollisionShapeSourceContext;
import net.minecraft.world.level.block.Blocks;
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
 * The closed provider witness is stone: Ornithe Feather 1.12.2 StoneBlock
 * inherits Block.addCollisions, getCollisionShape and getShape, supplying one
 * FULL_BLOCK_SHAPE translated to the block position. Provider provenance is
 * captured from 26.2 BlockCollisions.computeNext's actual emitted shape.
 * Accept only that block and its exact translated unit cube. Unknown sources
 * retain native clipping for the entire axis call.
 * A single modern AABB is insufficient: 1.12.2 ChorusPlantBlock.addCollisions
 * emits core and arm boxes separately, whereas 1.13.2 PipeBlock's shape union
 * and 26.2 VoxelShape.forAllBoxes can merge them, hiding an internal face.
 * Other provider families remain open even if their shape merges to one box.
 * This is one axis operation; axis order and collision queries resolve separately.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class AxisClipping implements AxisClippingHook {
    @Override
    public OptionalDouble clip(Direction.Axis axis, AABB moving, Iterable<VoxelShape> shapes, double distance) {
        var boxes = new ArrayList<AABB>();
        for (VoxelShape shape : shapes) {
            if (shape.isEmpty()) {
                continue;
            }
            var source = CollisionShapeSourceContext.source(shape);
            if (source == null || !source.state().is(Blocks.STONE)) {
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
            boxes.add(box);
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
