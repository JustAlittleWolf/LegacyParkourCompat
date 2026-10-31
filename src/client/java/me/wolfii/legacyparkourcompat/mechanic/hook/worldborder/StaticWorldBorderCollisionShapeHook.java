package me.wolfii.legacyparkourcompat.mechanic.hook.worldborder;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Historical collision geometry for a static world-border extent. */
@MechanicType("world_border.static_collision_shape")
public interface StaticWorldBorderCollisionShapeHook extends VersionedMechanic {
    /** Moving extents and unsupported profiles retain the native shape. */
    Optional<VoxelShape> collisionShape(double centerX, double centerZ, double size, int absoluteMaxSize);
}
