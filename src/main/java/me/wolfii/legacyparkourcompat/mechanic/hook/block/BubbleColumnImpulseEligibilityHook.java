package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/**
 * Selects whether an already-dispatched bubble-column contact may apply its
 * native impulse. The caller remains responsible for client-player dispatch
 * and for preserving vanilla contact and column classification.
 */
@MechanicType("block.bubble_column.impulse_eligibility")
public interface BubbleColumnImpulseEligibilityHook extends VersionedMechanic {
    /**
     * Adjusts only the native impulse eligibility flag for the existing
     * {@code BubbleColumnBlock.entityInside} callback.
     *
     * <p>In Mojmap 1.21.5 through 1.21.9, {@code Entity.checkInsideBlocks}
     * dispatches the callback for an intersecting entity and
     * {@code BubbleColumnBlock.entityInside} applies its response. In Mojmap
     * 1.21.10, the same path passes a moved-far-or-cell-overlap flag to
     * {@code BubbleColumnBlock.entityInside}, which can now reject a short
     * swept contact. This hook changes only that eligibility decision; it
     * does not create contacts or change the impulse or above-column
     * calculations.
     */
    boolean shouldApplyImpulse(boolean vanillaEligible);
}
