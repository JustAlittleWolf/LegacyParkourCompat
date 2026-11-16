package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Supplies the requested-Y-aware return after a step-assist route wins. */
@MechanicType("entity.step_assist.vertical_return")
public interface StepAssistVerticalReturnHook extends VersionedMechanic {
    @FunctionalInterface
    interface VerticalCorrectionClipper {
        double clip(double requestedCorrectionY);
    }

    /** Returns the achieved route Y plus its collision-clipped vertical correction. */
    double verticalReturn(double achievedStepY, double requestedY, VerticalCorrectionClipper clipper);
}
