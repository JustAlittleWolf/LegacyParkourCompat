package me.wolfii.legacyparkourcompat.mechanic.hook.clientload;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** The local-player tick readiness decision, after advancing its load timer. */
@MechanicType("player.client_load_gate")
public interface ClientLoadGateHook extends VersionedMechanic {
    boolean shouldRunTick(boolean clientLoaded, int timeoutTicks);
}
