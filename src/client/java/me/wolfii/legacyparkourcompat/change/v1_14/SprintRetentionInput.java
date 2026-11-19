package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.SprintRetentionInputHook;

/**
 * Ornithe Feather 1.12.2 LocalClientPlayerEntity#mobTick uses
 * movementForward < 0.8F for ordinary sprint retention. In 1.13, 1.13.1 and
 * 1.13.2 that same member feeds the comparison to both the ordinary and new
 * swimming sprint stop predicates.
 * Ornithe Feather 1.14 LocalClientPlayerEntity#mobTick and Yarn 1.14.1+build.10
 * ClientPlayerEntity#tickMovement keep the shared comparison. Yarn
 * 1.14.2+build.7 ClientPlayerEntity#tickMovement instead negates
 * Input#method_20622 (movementForward > 1.0E-5F).
 * Unobfuscated 26.2 LocalPlayer#shouldStopRunSprinting and
 * #shouldStopSwimSprinting negate ClientInput#hasForwardImpulse
 * (moveVector.y > 1.0E-5F). Preserve that native swim test before 1.13.
 */
@MovementChange(emulates = ParkourVersion.V1_14_1)
public final class SprintRetentionInput implements SprintRetentionInputHook {
    @Override
    public boolean supports(ParkourVersion selected, boolean swimming) {
        return !swimming || selected.newerThanOrEqual(ParkourVersion.V1_13);
    }

    @Override
    public boolean shouldStopSprinting(float storedForwardInput) {
        return storedForwardInput < 0.8F;
    }
}
