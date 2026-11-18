package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagEligibility;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.AbilityFlightGlideFlagHook;
import net.minecraft.world.entity.player.Player;

/**
 * Restores the early direct clear after local ability-flight movement.
 * In 1.9.1 and 1.12.2 (Ornithe Feather Gen2),
 * {@code EntityPlayerSP#onLivingUpdate} calls {@code PlayerEntity#moveRelative}
 * before its {@code setFlag(7,false)} clear. The 1.8.9 profile lacks this
 * Elytra operation; the V1_8_9 native fence keeps that profile unchanged.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class EarlyAbilityFlightGlideFlag implements AbilityFlightGlideFlagHook {
    @Override
    public boolean shouldClearAfterTravel(Player player) {
        return AbilityFlightGlideFlagEligibility.shouldClearAfterTravel(player);
    }
}
