package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SlowInputPredicateHook;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;

/**
 * Emulates through 1.21.4. 1.20.2 LocalPlayer.aiStep adds the passenger guard to the ordinary
 * crouching branch while retaining prior-shift and pose-fit logic. The
 * isMovingSlowly crawl alternative remains unchanged (1.20.2 Mojmap
 * LocalPlayer.aiStep and Entity.isVisuallyCrawling).
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class SlowInputPredicate implements SlowInputPredicateHook {
    @Override
    public boolean shouldSlow(boolean flying, boolean swimming, boolean passenger,
                              boolean shiftWasDown, boolean sleeping,
                              VanillaFn<Boolean> crouchingPoseFits, VanillaFn<Boolean> standingPoseFits,
                              VanillaFn<Boolean> visuallyCrawling) {
        boolean crouching = !flying && !swimming && !passenger && crouchingPoseFits.get()
            && (shiftWasDown || !sleeping && !standingPoseFits.get());
        return crouching || visuallyCrawling.get();
    }
}
