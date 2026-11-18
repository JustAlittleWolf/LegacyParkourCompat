package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagHook;
import net.minecraft.world.entity.player.Player;

/**
 * Preserves native flag behavior for the unaccepted middle profiles. This
 * V1_20_2 resolver fence covers 1.20.2 through 1.20.4; the next enabled
 * provider starts at V1_21, whose source group is 1.20.5 through 1.21.1.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class NativeAbilityFlightGlideFlag implements AbilityFlightGlideFlagHook {
    @Override
    public boolean shouldClearAfterTravel(Player player) {
        return false;
    }
}
