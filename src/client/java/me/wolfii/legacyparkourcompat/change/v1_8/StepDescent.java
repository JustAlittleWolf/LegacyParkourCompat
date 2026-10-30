package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepDescentHook;

/**
 * Restores 1.8's configured-height descent request after the chosen step route.
 * In Ornithe Feather, 1.8 {@code net.minecraft.entity.Entity#move} assigns
 * {@code -this.stepHeight}; 1.8.1's same member assigns the chosen route's
 * clipped rise ({@code -var33} or {@code -var81}), and 1.8.2 retains that
 * route-based request. The caller preserves the source's existing collision
 * clipping after this scalar choice.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class StepDescent implements StepDescentHook {
    @Override
    public double downwardReturn(double nativeDescent, float configuredStepHeight) {
        // 1.8 Entity#move applies unary negation while stepHeight is still float,
        // then widens the resulting request when assigning it to its double dy.
        return -configuredStepHeight;
    }
}
