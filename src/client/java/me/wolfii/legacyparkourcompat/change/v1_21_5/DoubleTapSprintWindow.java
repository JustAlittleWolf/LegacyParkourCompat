package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintWindowHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
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
