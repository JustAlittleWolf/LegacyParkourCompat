package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LookAtHorizontalLengthHook;

/**
 * Preserves native precision before the source-proven 1.16.2 patch group.
 * 1.16.5 Mojmap Entity#lookAt and Mth#sqrt establish the later float
 * intermediate; older profiles remain on Math.sqrt(double) until their exact
 * source establishes a different behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class LookAtHorizontalLengthPreservation implements LookAtHorizontalLengthHook {
    @Override
    public double horizontalLength(double squaredHorizontalDistance) {
        return Math.sqrt(squaredHorizontalDistance);
    }
}
