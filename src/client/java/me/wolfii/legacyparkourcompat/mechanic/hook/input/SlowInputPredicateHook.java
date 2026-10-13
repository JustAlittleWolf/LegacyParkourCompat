package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/**
 * Resolves the pre-poll crouch-or-crawl predicate used to slow keyboard axes.
 * The assigned producer applies only to 1.16 through 1.21.4. The consumer
 * keeps native fallback outside that range unless another resolved producer
 * covers the selected profile. Fit providers are lazy to preserve source
 * short-circuit query order; crawling remains an independent OR branch.
 */
@MechanicType("player.input.slow-predicate")
public interface SlowInputPredicateHook extends VersionedMechanic {
    boolean shouldSlow(boolean flying, boolean swimming, boolean passenger,
                       boolean shiftWasDown, boolean sleeping,
                       VanillaFn<Boolean> crouchingPoseFits, VanillaFn<Boolean> standingPoseFits,
                       VanillaFn<Boolean> visuallyCrawling);
}
