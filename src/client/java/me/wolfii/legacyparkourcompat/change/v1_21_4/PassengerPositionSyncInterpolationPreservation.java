package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PassengerPositionSyncInterpolationHook;

/** Keeps unproven older profiles on the native interpolation predicate. */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PassengerPositionSyncInterpolationPreservation implements PassengerPositionSyncInterpolationHook {
    /**
     * The 1.21.5 Mojmap ClientPacketListener.handleEntityPositionSync branch uses
     * isInterpolating(), while 1.21.6 Mojmap ClientPacketListener.handleEntityPositionSync
     * uses !isInterpolating(); 1.21.6 Mojmap Entity.isInterpolating supplies that
     * predicate. No earlier cutover is established, so preserve the native operand
     * for selections through 1.21.4 instead of projecting the 1.21.5 behavior back.
     */
    @Override
    public boolean interpolationOperand(boolean nativeValue) {
        return nativeValue;
    }
}
