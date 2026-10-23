package me.wolfii.legacyparkourcompat.mechanic.hook.prediction;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Whether an acknowledged block prediction may supply its saved player position. */
@MechanicType("block.prediction.rollback")
public interface BlockPredictionRollbackHook extends VersionedMechanic {
    boolean restoresSavedPlayerPositionAfterTeleport();
}
