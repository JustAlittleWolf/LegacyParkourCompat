package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintKeyStartHook;
import net.minecraft.world.entity.player.Player;

/**
 * 1.12.2 Feather LocalClientPlayerEntity.mobTick requires ground for the
 * forward-tap branch and permits held-key start without a water predicate.
 * 1.13 Feather LocalClientPlayerEntity.mobTick adds eye-water support and
 * excludes shallow water from the held-key branch. Both recheck sprint state
 * immediately after the forward-tap branch, before considering the held key.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SprintInputStart implements ShallowWaterSprintStartHook, DoubleTapSprintStartHook, SprintKeyStartHook {
    @Override
    public boolean allowShallowWaterStart(Player player, boolean vanilla) {
        return vanilla;
    }

    @Override
    public boolean allowDoubleTapStart(Player player, boolean vanilla) {
        return vanilla && (player.isSwimming() || player.onGround());
    }

    @Override
    public boolean allowDoubleTapTransition(Player player, boolean previousSneak, boolean previousForward) {
        return player.isSwimming() ? !previousForward
            : DoubleTapSprintStartHook.super.allowDoubleTapTransition(player, previousSneak, previousForward);
    }

    @Override
    public boolean allowSprintKeyStart(Player player, boolean vanilla) {
        return vanilla && !player.isSprinting();
    }
}
