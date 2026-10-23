package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/** Adjusts only the empty-shape solver result; nonempty solvers remain native. */
@MechanicType("collision.empty_shape_movement")
public interface EmptyShapeMovementHook extends VersionedMechanic {
    Optional<Vec3> resolve(Entity entity, Vec3 requested, AABB boundingBox);
}
