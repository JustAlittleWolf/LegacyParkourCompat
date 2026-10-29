package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Resolves the displacement used to sweep the final entity box through a partial block shape. */
@MechanicType("entity.partial_shape_sweep")
public interface PartialShapeSweepHook extends VersionedMechanic {
    Optional<Vec3> reverseDisplacement(AABB finalEntityBox, Vec3 from, Vec3 to);
}
