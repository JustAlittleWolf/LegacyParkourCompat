package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagHook;
import net.minecraft.world.entity.player.Player;

/**
 * Keeps the feature-absent profiles native. In 1.8.9 (Feather Gen2),
 * {@code EntityPlayerSP#onLivingUpdate} has no Elytra ability-flight branch;
 * the direct post-move flag clear appears in 1.9.1's
 * {@code EntityPlayerSP#onLivingUpdate}.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeAbilityFlightGlideFlag implements AbilityFlightGlideFlagHook {
    @Override
    public boolean shouldClearAfterTravel(Player player) {
        return false;
    }
}
