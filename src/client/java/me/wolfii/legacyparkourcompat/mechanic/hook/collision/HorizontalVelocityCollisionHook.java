package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import java.util.Optional;

/** Predicate used only for clearing horizontal velocity, independently of collision flags. */
@MechanicType("collision.horizontal_velocity_predicate")
public interface HorizontalVelocityCollisionHook extends VersionedMechanic {
    Optional<Boolean> shouldClear(double requested, double resolved);
}
