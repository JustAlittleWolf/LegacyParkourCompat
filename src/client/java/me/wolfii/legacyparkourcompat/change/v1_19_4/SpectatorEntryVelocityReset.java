package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SpectatorEntryVelocityResetHook;

/**
 * Mojmap 1.19.4 ClientPacketListener.applyPlayerInfoUpdate only updates
 * PlayerInfo for UPDATE_GAME_MODE; LocalPlayer has no onGameModeChanged
 * callback. Mojmap 1.20 adds the callback, and LocalPlayer.onGameModeChanged
 * clears only Y for SPECTATOR. The packet gate and callback remain in 1.20.6
 * and 26.2. Suppress only that local velocity write; keep the packet's changed-
 * game-mode, local-player identity, and call-order gates native.
 *
 * Sources (paths relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java:
 * applyPlayerInfoUpdate; LocalPlayer has no onGameModeChanged member.
 * 1.20/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java:
 * applyPlayerInfoUpdate; net/minecraft/client/player/LocalPlayer.java:
 * onGameModeChanged.
 * 1.20.6/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java:
 * applyPlayerInfoUpdate; net/minecraft/client/player/LocalPlayer.java:
 * onGameModeChanged.
 * 26.2/unobfuscated/net/minecraft/client/multiplayer/ClientPacketListener.java:
 * applyPlayerInfoUpdate; net/minecraft/client/player/LocalPlayer.java:
 * onGameModeChanged.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class SpectatorEntryVelocityReset implements SpectatorEntryVelocityResetHook {
    @Override
    public boolean resetVelocity(ParkourVersion selected) {
        return false;
    }
}
