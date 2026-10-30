package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GlideVerticalAccelerationHook;

/**
 * Ornithe Feather 1.12.2 LivingEntity.moveRelative adds -0.08 + m * 0.06,
 * where m is the float-rounded lift factor promoted to double. Ornithe Feather
 * 1.13, 1.13.1 and 1.13.2 LivingEntity.moveRelative instead group the term as
 * d * (-1.0 + n * 0.75). The latter grouping remains in 26.2 unobfuscated
 * LivingEntity.updateFallFlyingMovement. Leave later velocity corrections intact.
 * Slow Falling is modern-only before 1.13 and keeps the existing native term.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class GlideVerticalAcceleration implements GlideVerticalAccelerationHook {
    @Override
    public double verticalAcceleration(ParkourVersion selected, double liftForce, double gravity,
                                       boolean slowFalling, double vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9) || slowFalling || gravity != 0.08
            || liftForce != (double) (float) liftForce) {
            return vanilla;
        }
        return -0.08 + liftForce * 0.06;
    }
}
