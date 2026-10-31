package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LookAtHorizontalLengthHook;

/**
 * Restores the float intermediate used for look-at pitch in 1.16.5.
 *
 * <p>1.16.5 Mojmap {@code net.minecraft.world.entity.Entity#lookAt} calls
 * {@code net.minecraft.util.Mth#sqrt(double)}, whose float result is widened
 * back to double. 1.17/1.17.1 Mojmap {@code Entity#lookAt} uses
 * {@code Math.sqrt(double)} directly and retains double precision.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class LookAtHorizontalLength implements LookAtHorizontalLengthHook {
    @Override
    public double horizontalLength(double squaredHorizontalDistance) {
        return (double) (float) Math.sqrt(squaredHorizontalDistance);
    }
}
