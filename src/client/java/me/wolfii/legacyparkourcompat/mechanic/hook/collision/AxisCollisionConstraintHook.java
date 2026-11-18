package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

/** Clips one original axis request against an independently admitted box constraint. */
@MechanicType("collision.axis_constraint")
public interface AxisCollisionConstraintHook extends VersionedMechanic {
    double clip(Direction.Axis axis, AABB moving, AABB constraint, double distance);
}
