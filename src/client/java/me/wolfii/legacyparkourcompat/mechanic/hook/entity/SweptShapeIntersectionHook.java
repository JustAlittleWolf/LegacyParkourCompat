package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

/**
 * Resolves the result of sweeping an entity box through block or fluid shape parts.
 * An empty result preserves the running game's native intersection operation.
 */
@MechanicType("entity.swept_shape_intersection")
public interface SweptShapeIntersectionHook extends VersionedMechanic {
    Optional<Boolean> collidedAlongVector(AABB movingBox, Vec3 movement, List<AABB> shapeParts);
}
