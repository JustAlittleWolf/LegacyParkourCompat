package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/**
 * Resolves the transformation applied to piston-limited movement while an entity
 * has a pending stuck-speed multiplier. The call site supplies the vector after
 * {@code Entity.limitPistonMovement} and invokes this only for piston movement
 * inside the native nonzero-multiplier gate. Other mover types keep their native
 * transformation.
 */
@MechanicType("entity.piston-stuck-speed")
public interface PistonStuckSpeedHook extends VersionedMechanic {
    Vec3 transformPistonMovement(Vec3 limitedMovement, Vec3 stuckSpeedMultiplier);
}
