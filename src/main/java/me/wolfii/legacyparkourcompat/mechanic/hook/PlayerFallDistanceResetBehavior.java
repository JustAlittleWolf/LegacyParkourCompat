package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Controls the fall-distance write inside Entity.move for player movement. */
@FunctionalInterface
@MechanicType("player.movement.fall_distance_reset")
public interface PlayerFallDistanceResetBehavior extends VersionedMechanic {
    boolean suppressMovementFallDistanceReset(ParkourVersion selected);
}
