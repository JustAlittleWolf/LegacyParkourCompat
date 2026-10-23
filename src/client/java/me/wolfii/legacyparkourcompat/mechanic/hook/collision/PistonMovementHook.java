package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Controls the entire pre-collision piston input limiter, including its zero-result return. */
@FunctionalInterface
@MechanicType("player.piston.movement")
public interface PistonMovementHook extends VersionedMechanic {
    boolean limitPistonMovement();
}
