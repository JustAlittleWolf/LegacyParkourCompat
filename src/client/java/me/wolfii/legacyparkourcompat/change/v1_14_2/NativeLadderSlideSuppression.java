package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LadderSlideSuppressionHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Preserves the native predicate for profiles before the source-verified 1.15
 * shift-only behavior group. This resolver boundary does not claim historical
 * correctness for earlier profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class NativeLadderSlideSuppression implements LadderSlideSuppressionHook {
    @Override
    public boolean isSuppressingSlidingDownLadder(LocalPlayer player, boolean nativeValue) {
        return nativeValue;
    }
}
