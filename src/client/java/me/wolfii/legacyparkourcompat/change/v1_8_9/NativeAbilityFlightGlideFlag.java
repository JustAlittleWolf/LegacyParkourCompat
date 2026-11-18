package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagHook;
import net.minecraft.world.entity.player.Player;

/**
 * Keeps the feature-absent profiles native. In 1.8.9 (Ornithe Feather Gen2),
 * {@code LocalClientPlayerEntity#mobTick} has no Elytra or fall-flying path.
 * The direct post-move clear appears in 1.9.1 (Feather Gen2), where
 * {@code PlayerEntity#moveRelative} clears flag 7 after inherited movement.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeAbilityFlightGlideFlag implements AbilityFlightGlideFlagHook {
    @Override
    public boolean shouldClearAfterTravel(Player player) {
        return false;
    }
}
