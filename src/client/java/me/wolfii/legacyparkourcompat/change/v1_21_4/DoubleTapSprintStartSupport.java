package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintStartHook;
import net.minecraft.world.entity.player.Player;

/**
 * 1.21.4, Mojmap: net.minecraft.client.player.LocalPlayer.aiStep requires
 * onGround() || isUnderWater() for unmounted double-tap sprint start support.
 * LocalPlayer.isUnderWater returns the cached wasUnderwater eye-query result.
 * 1.21.5, Mojmap: LocalPlayer.aiStep starts its double-tap branch under
 * canStartSprinting() without the former ground/underwater support condition.
 *
 * The consumer must pass the local client player: virtual isUnderWater dispatch
 * must reach its cached-eye override, not Entity's combined underwater predicate.
 * Passenger support differs independently and retains the incoming decision.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class DoubleTapSprintStartSupport implements DoubleTapSprintStartHook {
    @Override
    public boolean allowDoubleTapStart(Player player, boolean vanilla) {
        if (player.isPassenger()) {
            return vanilla;
        }
        return vanilla && (player.onGround() || player.isUnderWater());
    }

    @Override
    public boolean allowDoubleTapTransition(Player player, boolean previousSneak, boolean previousForward) {
        if (player.isPassenger()) {
            return !previousForward;
        }
        return DoubleTapSprintStartHook.super.allowDoubleTapTransition(player, previousSneak, previousForward);
    }
}
