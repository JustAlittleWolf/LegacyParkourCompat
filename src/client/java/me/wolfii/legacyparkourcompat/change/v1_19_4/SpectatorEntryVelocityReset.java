package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SpectatorEntryVelocityResetHook;

/**
 * Mojmap 1.19.4 ClientPacketListener game-mode update does not invoke a local
 * velocity-reset callback. Mojmap 1.20 LocalPlayer.onGameModeChanged adds
 * setDeltaMovement(current.with(Y, 0.0)) for spectator entry, retained in
 * 26.2 unobfuscated. Suppress only this new local velocity write; the packet's
 * identity/new-mode checks, actual game mode and server behavior stay native.
 * Earlier than 1.16 source scope remains unbound.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class SpectatorEntryVelocityReset implements SpectatorEntryVelocityResetHook {
    @Override
    public boolean resetVelocity(ParkourVersion selected) {
        return selected.olderThan(ParkourVersion.V1_16);
    }
}
