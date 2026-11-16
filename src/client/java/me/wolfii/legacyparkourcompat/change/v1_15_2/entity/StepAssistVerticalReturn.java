package me.wolfii.legacyparkourcompat.change.v1_15_2.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepAssistVerticalReturnHook;

/**
 * Applies the requested-Y correction after a selected step route.
 *
 * <p>Mojmap {@code net.minecraft.world.entity.Entity#collide(Vec3)} in 1.14.4,
 * 1.15, 1.15.1 and 1.15.2 returns the achieved route plus the collision-clipped
 * correction {@code -routeY + requestedY}. The separately resolved clipper
 * uses the same bounding-box position and collision collection as the source.
 * This provider supplies that return wherever the selected profile has no
 * closer vertical-return implementation.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class StepAssistVerticalReturn implements StepAssistVerticalReturnHook {
    @Override
    public double verticalReturn(double achievedStepY, double requestedY, VerticalCorrectionClipper clipper) {
        return achievedStepY + clipper.clip(-achievedStepY + requestedY);
    }
}
