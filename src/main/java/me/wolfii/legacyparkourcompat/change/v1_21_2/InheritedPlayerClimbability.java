package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerClimbabilityFlyingGateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Keeps the inherited climbability predicate when the newer player-level
 * flying veto would otherwise return early.
 *
 * <p>In 1.21.3 Mojmap, {@code Player} inherits {@code LivingEntity.onClimbable}
 * without the new flying early return. In 1.21.4 Mojmap,
 * {@code Player.onClimbable} returns false for {@code abilities.flying} and
 * delegates to {@code LivingEntity.onClimbable} otherwise. The caller must
 * bypass only that new veto and continue evaluating the inherited predicate.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class InheritedPlayerClimbability implements PlayerClimbabilityFlyingGateHook {
    @Override
    public boolean shouldApplyFlyingVeto(Player player, boolean vanillaFlying) {
        return false;
    }
}
