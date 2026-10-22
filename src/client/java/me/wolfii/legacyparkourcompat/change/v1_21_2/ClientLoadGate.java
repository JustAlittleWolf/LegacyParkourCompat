package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.clientload.ClientLoadGateHook;

/**
 * Mojmap 1.21.3 LocalPlayer#tick has no load gate; Mojmap 1.21.4
 * LocalPlayer#tick introduces Player#hasClientLoaded and its timeout. The
 * absence of the newer loading feature is excluded, so older profiles keep
 * the native readiness decision rather than inheriting the timeout change.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class ClientLoadGate implements ClientLoadGateHook {
    @Override
    public boolean shouldRunTick(boolean clientLoaded, int timeoutTicks) {
        return clientLoaded;
    }
}
