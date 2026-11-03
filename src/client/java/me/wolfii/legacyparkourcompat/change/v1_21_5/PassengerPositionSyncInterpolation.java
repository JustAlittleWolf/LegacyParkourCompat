package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PassengerPositionSyncInterpolationHook;

/** Restores the 1.21.5 interpolation gate for synchronized vehicle passenger updates. */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class PassengerPositionSyncInterpolation implements PassengerPositionSyncInterpolationHook {
    /**
     * In Mojmap 1.21.5 ClientPacketListener.handleEntityPositionSync gates the local
     * rider update with isInterpolating() && hasIndirectPassenger(player). In Mojmap
     * 1.21.6 the same handler uses !isInterpolating() && hasIndirectPassenger(player).
     * The redirected operand is read from Entity.isInterpolating; inversion here lets
     * the existing 1.21.6 negation execute the 1.21.5 predicate without changing the
     * authority, indirect-passenger, direct-passenger, positionRider, or old-position
     * bookkeeping gates in the source method.
     */
    @Override
    public boolean interpolationOperand(boolean nativeValue) {
        return !nativeValue;
    }
}
