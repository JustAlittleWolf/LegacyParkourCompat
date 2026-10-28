package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisClippingHook;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Iterator;
import java.util.OptionalDouble;

/**
 * Clips the one aggregate created by {@link BlockCollisionCollection} with
 * the existing per-axis operation. Calls outside that query keep native math.
 *
 * <p>Source: Minecraft 1.13.0 ({@code 1.13/ornithe-feather}),
 * {@code Entity.move} lines 578-598 clips its single joined collision shape
 * once in Y/X/Z order; {@code VoxelShapes.calculateMaxOffset} applies the
 * per-axis box offset. Compared against Minecraft 26.2
 * ({@code 26.2/unobfuscated}), {@code Entity.collideWithShapes} and
 * {@code VoxelShape.collide}; accepted slab-union evidence closes this
 * operation only for exact grid-aligned 19-key slab aggregates.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class AxisClipping implements AxisClippingHook {
    @Override
    public OptionalDouble clip(Direction.Axis axis, AABB moving, Iterable<VoxelShape> shapes, double distance) {
        VoxelShape aggregate = CollisionAxisOrderContext.collectedBlockShape();
        if (aggregate == null) {
            return OptionalDouble.empty();
        }
        Iterator<VoxelShape> iterator = shapes.iterator();
        if (!iterator.hasNext() || iterator.next() != aggregate || iterator.hasNext()) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(aggregate.collide(axis, moving, distance));
    }
}
