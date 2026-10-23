package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Controls the collision-flag reset after the direct no-physics position move. */
@MechanicType("entity.no_physics_collision_reset")
public interface NoPhysicsCollisionResetHook extends VersionedMechanic {
    boolean clearCollisionFlags();
}
