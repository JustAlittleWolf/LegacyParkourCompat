package me.wolfii.legacyparkourcompat.mechanic.hook;

import com.google.common.collect.ImmutableList;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

/**
 * Historical collision axis order (pre-1.14 was always Y-X-Z).
 */
@MechanicType("entity.collision.axis_order")
public interface CollisionAxisOrder extends VersionedMechanic {
    ImmutableList<Direction.Axis> axisOrder(Vec3 movement, ImmutableList<Direction.Axis> vanilla);
}
