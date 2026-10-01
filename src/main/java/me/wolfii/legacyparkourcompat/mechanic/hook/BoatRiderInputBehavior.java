package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Local-player input deltas applied by the current boat control routine. */
@MechanicType("player.boat_rider_input")
public interface BoatRiderInputBehavior extends VersionedMechanic {
    float sideOnlyAcceleration(float vanilla, boolean left, boolean right, boolean forward, boolean backward);

    float sneakingAcceleration(float vanilla, boolean sneaking);
}
