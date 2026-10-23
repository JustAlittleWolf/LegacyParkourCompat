package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.TrapdoorClimbingHook;

/**
 * Keeps native 26.2 trapdoor climbing for profiles before the first accepted
 * source comparison, 1.8.9.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class TrapdoorClimbingNativeFence implements TrapdoorClimbingHook {
    public TrapdoorClimbingNativeFence() {
    }

    @Override
    public boolean canClimbTrapdoor(boolean vanillaResult) {
        return vanillaResult;
    }
}
