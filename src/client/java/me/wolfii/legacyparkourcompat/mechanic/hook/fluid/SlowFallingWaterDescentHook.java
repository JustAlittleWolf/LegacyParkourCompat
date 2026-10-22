package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects the falling predicate used by Slow Falling's water descent adjustment. */
@MechanicType("player.travel.water.slow_falling_descent_predicate")
public interface SlowFallingWaterDescentHook extends VersionedMechanic {
    boolean isFallingAtWaterDescentCheck(
        ParkourVersion selectedVersion,
        boolean entryPredicate,
        double verticalVelocityAfterDrag
    );
}
