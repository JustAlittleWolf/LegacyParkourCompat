package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.EarlySprintStopHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Minecraft 1.21.3, Mojmap, net.minecraft.client.player.LocalPlayer.aiStep:
 * Tutorial.onInput is followed by item-input slowdown, without an early sprint stop.
 * Minecraft 1.21.4, Mojmap, the same member adds shouldStopSprinting immediately
 * after Tutorial.onInput; that predicate begins with isFallFlying().
 * This no-op shields older profiles from the newly introduced response through
 * the nearest-later change resolver.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NoEarlySprintStop implements EarlySprintStopHook {
    @Override
    public void afterTutorialInput(LocalPlayer player) {
    }
}
