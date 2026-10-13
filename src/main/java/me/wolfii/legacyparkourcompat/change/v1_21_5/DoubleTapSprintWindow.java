package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintWindowHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 649-650.
 *   Source SHA-256: 6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa.
 * 1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines 729-730.
 *   Source SHA-256: 948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class DoubleTapSprintWindow implements SprintWindowHook {
    @Override
    public int doubleTapWindow(Player player, int vanilla) {
        return 7;
    }
}
