package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PassengerPositionSyncHook;

/**
 * Mojmap 1.21.2, 1.21.3, and 1.21.4 ClientPacketListener.handleEntityPositionSync
 * update the local rider and its old position/rotation inside the snap branch.
 * Mojmap 1.21.5 moves the writes after both branches and checks isInterpolating().
 * The former branch decision matters because snapping can leave interpolation active.
 * Mojmap 1.21.1 ClientPacketListener.handleTeleportEntity predates this sync
 * handler. Earlier profiles retain the native position-sync gate.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PassengerPositionSyncSnap implements PassengerPositionSyncHook {
    @Override
    public boolean shouldSyncPassenger(ParkourVersion selected, boolean snapBranch, boolean interpolatingAfterOperation) {
        return selected.olderThan(ParkourVersion.V1_21_2) ? !interpolatingAfterOperation : snapBranch;
    }
}
