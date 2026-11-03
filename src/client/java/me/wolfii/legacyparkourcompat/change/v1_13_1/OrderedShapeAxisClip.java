package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisClippingHook;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.OptionalDouble;

/** 1.13.1 Ornithe Feather VoxelShapes#calculateMaxOffset returns zero before folding a next shape below 1.0E-7. */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class OrderedShapeAxisClip implements AxisClippingHook {
    public OrderedShapeAxisClip() { }

    @Override
    public OptionalDouble clip(Direction.Axis axis, AABB moving, Iterable<VoxelShape> shapes, double distance,
                               CollisionAxisOrderContext.HistoricalAxisCandidates candidates) {
        if (candidates == null) return OptionalDouble.empty();
        double offset = distance;
        for (VoxelShape candidate : candidates.historicalOrder()) {
            if (Math.abs(offset) < 1.0E-7) return OptionalDouble.of(0.0);
            offset = candidate.collide(axis, moving, offset);
        }
        return OptionalDouble.of(offset);
    }
}
