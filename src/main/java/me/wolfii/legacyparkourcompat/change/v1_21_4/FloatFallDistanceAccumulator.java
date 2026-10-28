package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;

/**
 * Restores the float rounding of the accumulator update while leaving its call site and gates native.
 *
 * <p>Exact-source basis: 1.21.1, 1.21.2, 1.21.3 and 1.21.4 Mojmap
 * {@code Entity.fallDistance} fields are float, and {@code Entity.checkFallDamage} subtracts the
 * cast float delta from that float accumulator. 1.21.5 changes the field to double while retaining
 * the casted delta; 26.2 unobfuscated {@code Entity.checkFallDamage} retains the double update.
 * The redirected value is the native {@code double accumulator - (double)(float)delta}; narrowing
 * it reproduces the prior float accumulator's write rounding for each ordinary downward update.
 * Other field writes and the callback/reset/effect branches remain native.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FloatFallDistanceAccumulator implements FallDistanceAccumulatorHook {
    @Override
    public double updatedDistance(double nativeUpdatedDistance) {
        return (float) nativeUpdatedDistance;
    }
}
