package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.SprintRetentionInputHook;

/**
 * Yarn 1.14.1+build.10 ClientPlayerEntity#tickMovement stops an existing
 * sprint when movementForward < 0.8F. Yarn 1.14.2+build.7
 * ClientPlayerEntity#tickMovement instead negates Input#method_20622,
 * whose strict predicate is movementForward > 1.0E-5F. Mojmap 1.14.4
 * LocalPlayer#aiStep retains that rule at shouldStopRunSprinting; this check
 * is after input polling and does not depend on water state.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class SprintRetentionInput implements SprintRetentionInputHook {
    @Override
    public boolean shouldStopSprinting(float storedForwardInput) {
        return !(storedForwardInput > 1.0E-5F);
    }
}