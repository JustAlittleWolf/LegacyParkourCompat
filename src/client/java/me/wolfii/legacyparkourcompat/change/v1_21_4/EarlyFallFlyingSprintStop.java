package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.EarlyFallFlyingSprintStopHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Minecraft 1.21.4, Mojmap, net.minecraft.client.player.LocalPlayer.aiStep:
 * immediately after Tutorial.onInput, shouldStopSprinting calls setSprinting(false).
 * LocalPlayer.shouldStopSprinting begins with isFallFlying(), without a water gate
 * or an isSprinting gate. Only this first reason is accepted by this change.
 * Minecraft 1.21.5, Mojmap, the same aiStep has no early stop after Tutorial.onInput;
 * shouldStopRunSprinting and shouldStopSwimSprinting omit the fall-flight reason.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class EarlyFallFlyingSprintStop implements EarlyFallFlyingSprintStopHook {
    @Override
    public void afterTutorialInput(LocalPlayer player) {
        if (player.isFallFlying()) {
            player.setSprinting(false);
        }
    }
}
