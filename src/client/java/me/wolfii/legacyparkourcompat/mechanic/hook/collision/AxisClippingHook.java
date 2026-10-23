package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.OptionalDouble;

/** Resolves one movement axis against supplied shapes; empty retains native clipping. */
@MechanicType("collision.axis_clipping")
public interface AxisClippingHook extends VersionedMechanic {
    OptionalDouble clip(Direction.Axis axis, AABB moving, Iterable<VoxelShape> shapes, double distance);
}
