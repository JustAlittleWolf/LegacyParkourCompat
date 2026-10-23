package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.prediction.BlockPredictionRollbackHook;

/**
 * Applicability boundary for the independently accepted 1.21.11 rollback.
 * Earlier behavior is unproven; retain the native comparison for those profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class NativeBlockPredictionRollback implements BlockPredictionRollbackHook {
    @Override
    public boolean restoresSavedPlayerPositionAfterTeleport() {
        return false;
    }
}
