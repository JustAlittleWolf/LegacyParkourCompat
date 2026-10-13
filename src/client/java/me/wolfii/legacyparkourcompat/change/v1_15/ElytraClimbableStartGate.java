package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalElytra;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingClimbableGateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.15 and 1.15.1 LocalPlayer.aiStep omit the local onLadder gate;
 * Mojmap 1.15.2 LocalPlayer.aiStep adds it before tryToStartFallFlying.
 * This independently resolved predicate ends at V1_15. Descent, local flag
 * mutation and same-tick creative-flight-toggle behavior changed in 1.15
 * and retain their separate boundaries. Before Elytra introduction and for
 * custom modern gliders/components, keep the native predicate.
 */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class ElytraClimbableStartGate implements FallFlyingClimbableGateHook {
    @Override
    public boolean isOnClimbableForFallFlyingStart(Player player, ParkourVersion selected, boolean vanilla) {
        return HistoricalElytra.applies(player, selected) ? false : vanilla;
    }
}
