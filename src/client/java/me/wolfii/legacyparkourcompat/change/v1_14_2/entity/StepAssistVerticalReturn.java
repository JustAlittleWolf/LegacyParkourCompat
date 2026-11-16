package me.wolfii.legacyparkourcompat.change.v1_14_2.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepAssistVerticalReturnHook;

/**
 * Applies the 1.14 requested-Y correction after a selected step route.
 *
 * <p>In 1.13.2 Ornithe Feather {@code net.minecraft.entity.Entity.move}, the
 * selected route settles by the negative achieved rise ({@code -u} or
 * {@code -aa}), then collision-clips that request. In 1.14.4 Ornithe Feather
 * {@code net.minecraft.entity.Entity.m_72373519}, the return adds a clipped
 * correction of {@code -routeY + requestedY} to the achieved route. The same
 * formula persists across stable 1.14.0–1.14.4; later profiles resolve their
 * own step-up providers.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class StepAssistVerticalReturn implements StepAssistVerticalReturnHook {
    @Override
    public double verticalReturn(double achievedStepY, double requestedY, VerticalCorrectionClipper clipper) {
        return achievedStepY + clipper.clip(-achievedStepY + requestedY);
    }
}
