package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.OptionalInt;

/** Debit returned to the enclosing movement record; empty keeps native accounting. */
@MechanicType("block_callback.record_budget")
public interface BlockCallbackBudgetHook extends VersionedMechanic {
    OptionalInt segmentDebit();
}
