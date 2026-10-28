package me.wolfii.legacyparkourcompat.mechanic.hook.falldistance;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Resolves only the numeric result of an ordinary fall-distance accumulator write. */
@MechanicType("entity.fall-distance-accumulator")
public interface FallDistanceAccumulatorHook extends VersionedMechanic {
    double updatedDistance(double nativeUpdatedDistance);
}
