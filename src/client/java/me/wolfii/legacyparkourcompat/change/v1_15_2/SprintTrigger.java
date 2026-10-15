package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTriggerHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 * 1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   aiStep.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SprintTrigger implements SprintTriggerHook {
    @Override
    public boolean shouldReset(Player player, boolean priorShift, boolean itemUseReset) {
        return itemUseReset;
    }
}
