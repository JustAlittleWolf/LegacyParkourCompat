package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackBudgetHook;

import java.util.OptionalInt;

/**
 * Mojmap 1.21.6/1.21.7/1.21.8 Entity.checkInsideBlocks(List, StepBasedCollector)
 * has no record-wide budget. Mojmap 1.21.9 and unobfuscated 26.2 initialize 16,
 * debit each component helper, and replay a stationary endpoint on exhaustion.
 * A zero debit leaves that native counter positive across all components and
 * prevents its extra stationary replay. The old per-segment DDA cap belongs to
 * BlockCallbackTraversal, independently of this record accounting operation.
 * Native move/addMovementThisTick/applyEffectsFromBlocks still preserve the old
 * full-vector position addition, to-minus-from reconstruction, 100-record
 * compression and unsplit synthesized records. The distinct Mojmap 1.21.5
 * component-record production is outside this change's applicability.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class BlockCallbackBudget implements BlockCallbackBudgetHook {
    @Override
    public OptionalInt segmentDebit() {
        return OptionalInt.of(0);
    }
}
