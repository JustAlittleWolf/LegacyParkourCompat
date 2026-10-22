package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BubbleColumnImpulseEligibilityHook;

/**
 * Mojmap 1.21.5 through 1.21.9 Entity.checkInsideBlocks dispatches the
 * intersecting callback to BubbleColumnBlock.entityInside without an
 * eligibility flag. Mojmap 1.21.10 Entity.checkInsideBlocks passes a
 * moved-far-or-final-box-overlaps-cell flag, and BubbleColumnBlock.entityInside
 * gates its response on it. Preserve the earlier eligibility only for an
 * already-dispatched contact; contact and overhead classification stay native.
 */
@MovementChange(emulates = ParkourVersion.V1_21_9)
public final class BubbleColumnImpulseEligibility implements BubbleColumnImpulseEligibilityHook {
    @Override
    public boolean shouldApplyImpulse(boolean vanillaEligible) {
        return true;
    }
}
