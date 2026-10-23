package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.SprintRetentionInputHook;

/**
 * Ornithe Feather 1.13.2 and 1.14 LocalClientPlayerEntity#tickMovement, and
 * Yarn 1.14.1+build.10 ClientPlayerEntity#tickMovement, stop an existing sprint
 * when the post-poll/scaled movementForward is less than 0.8F.
 * Yarn 1.14.2+build.7 ClientPlayerEntity#tickMovement instead negates
 * Input#method_20622 (movementForward > 1.0E-5F); Mojmap 1.14.4
 * LocalPlayer#aiStep negates Input#hasForwardImpulse identically. Unlike the
 * separate start/pre-poll predicate, retention has no eye-water branch.
 * Unobfuscated 26.2 LocalPlayer#shouldStopRunSprinting negates
 * ClientInput#hasForwardImpulse (moveVector.y > 1.0E-5F), so that native call
 * supplies the 1.14.2+ fallback on either historical stored or modern input.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class SprintRetentionInput implements SprintRetentionInputHook {
    @Override
    public boolean shouldStopSprinting(float storedForwardInput) {
        return storedForwardInput < 0.8F;
    }
}
