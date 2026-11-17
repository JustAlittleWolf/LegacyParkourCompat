package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PassengerPositionSyncHook;

/**
 * Mojmap 1.21.5 ClientPacketListener.handleEntityPositionSync checks
 * Entity.isInterpolating() after either position operation before updating
 * the local rider. Mojmap 1.21.6 negates that predicate, as does 26.2's
 * unobfuscated ClientPacketListener.handleEntityPositionSync.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class PassengerPositionSyncInterpolation implements PassengerPositionSyncHook {
    @Override
    public boolean shouldSyncPassenger(ParkourVersion selected, boolean snapBranch, boolean interpolatingAfterOperation) {
        return interpolatingAfterOperation;
    }
}
