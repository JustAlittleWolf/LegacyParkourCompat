package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SlowInputPredicateHook;

/**
 * Emulates through 1.20.1. 1.16 LocalPlayer.aiStep computes crouching from
 * flight, swimming, pose-fit, prior shift input and the standing-fit fallback.
 * This branch has no passenger guard. isMovingSlowly then checks the unchanged
 * dry visual-crawl predicate (1.16 Mojmap LocalPlayer.aiStep and
 * Entity.isVisuallyCrawling).
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class SlowInputPredicate implements SlowInputPredicateHook {
    @Override
    public boolean shouldSlow(boolean flying, boolean swimming, boolean passenger,
                              boolean shiftWasDown, boolean sleeping,
                              VanillaFn<Boolean> crouchingPoseFits, VanillaFn<Boolean> standingPoseFits,
                              VanillaFn<Boolean> visuallyCrawling) {
        boolean crouching = !flying && !swimming && crouchingPoseFits.get()
            && (shiftWasDown || !sleeping && !standingPoseFits.get());
        return crouching || visuallyCrawling.get();
    }
}
