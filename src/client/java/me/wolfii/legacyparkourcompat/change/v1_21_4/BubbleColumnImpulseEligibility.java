package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BubbleColumnImpulseEligibilityHook;

/**
 * Mojmap 1.21.4 Entity.checkInsideBlocks dispatches a collided
 * BubbleColumnBlock.entityInside callback without an eligibility flag. The
 * accepted old-contact range starts at 1.21.5; keep the native flag passed by
 * the 26.2 BubbleColumnBlock.entityInside callback for profiles through 1.21.4.
 * Mojmap 1.21.10 Entity.checkInsideBlocks computes that flag and
 * BubbleColumnBlock.entityInside gates the impulse on it.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class BubbleColumnImpulseEligibility implements BubbleColumnImpulseEligibilityHook {
    @Override
    public boolean shouldApplyImpulse(boolean vanillaEligible) {
        return vanillaEligible;
    }
}
