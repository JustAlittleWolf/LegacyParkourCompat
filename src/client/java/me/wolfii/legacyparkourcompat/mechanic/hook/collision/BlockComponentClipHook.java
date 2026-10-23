package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

/** Clips one explicitly collected block component against a moving box. */
@MechanicType("block.component_clip")
public interface BlockComponentClipHook extends VersionedMechanic {
    double clip(AABB component, Direction.Axis axis, AABB moving, double distance);
}
