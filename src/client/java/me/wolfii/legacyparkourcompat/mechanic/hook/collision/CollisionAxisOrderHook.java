package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("entity.collision.axis_order")
public interface CollisionAxisOrderHook extends VersionedMechanic {
    /** Returns whether Z must be resolved before X for these requested components. */
    boolean zBeforeX(double x, double z);
}
