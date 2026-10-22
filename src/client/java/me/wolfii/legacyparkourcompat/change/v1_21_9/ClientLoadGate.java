package me.wolfii.legacyparkourcompat.change.v1_21_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.clientload.ClientLoadGateHook;

/**
 * Mojmap 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9 and 1.21.10:
 * LocalPlayer#tick advances Player#tickClientLoadTimeout before checking
 * Player#hasClientLoaded (readiness or timeout <= 0). Player initializes the
 * timer to 60 per instance; ClientPacketListener#handleRespawn creates a new
 * local player, and its level-ready notification sets that player's readiness.
 * Mojmap 1.21.11 LocalPlayer#tick instead uses the timeout-free
 * ClientPacketListener#hasClientLoaded. Published 26.2 retains that call.
 * The identical historical predicate needs only the latest boundary; the
 * 1.21.2 change prevents propagation to profiles before its introduction.
 */
@MovementChange(emulates = ParkourVersion.V1_21_9)
public final class ClientLoadGate implements ClientLoadGateHook {
    @Override
    public boolean shouldRunTick(boolean clientLoaded, int timeoutTicks) {
        return clientLoaded || timeoutTicks <= 0;
    }
}
