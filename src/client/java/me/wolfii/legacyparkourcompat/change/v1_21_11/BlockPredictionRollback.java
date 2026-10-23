package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.prediction.BlockPredictionRollbackHook;

/**
 * 1.21.11 Mojmap: ClientPacketListener.handleMovePlayer does not record a
 * prediction sequence; BlockStatePredictionHandler.endPredictionsUpTo always
 * passes the saved player position to ClientLevel.syncBlockState. That method
 * restores the authoritative block state and snaps to the saved position only
 * when the changed state collides with the player in the same level.
 *
 * 26.1, 26.1.1 and 26.1.2 unobfuscated: the corresponding packet handler calls
 * BlockStatePredictionHandler.onTeleport, and endPredictionsUpTo supplies the
 * saved position only when lastTeleportSequence < sequence. 26.2 unobfuscated
 * retains that comparison and the ClientLevel.syncBlockState collision gates.
 * Restore only the old position-argument eligibility; retain acknowledgement
 * iteration/removal, sequence bookkeeping, block state and collision predicate.
 * Earlier applicability is unproven and retains the native comparison.
 */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class BlockPredictionRollback implements BlockPredictionRollbackHook {
    @Override
    public boolean restoresSavedPlayerPositionAfterTeleport() {
        return true;
    }
}
