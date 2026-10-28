package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/**
 * Selects whether the player-level flying veto in {@code Player.onClimbable}
 * should take its early-false branch.
 *
 * <p>The caller substitutes this result only for that added gate. When it is
 * false, the inherited {@code LivingEntity.onClimbable} predicate must still be
 * evaluated. An unresolved hook keeps the source method's native gate. Because
 * the audited override is on shared {@code Player}, the historical dispatch
 * must be limited to the local logical-client player; server-side and remote
 * player instances keep native behavior.
 */
@MechanicType("player.climbability.flying-gate")
public interface PlayerClimbabilityFlyingGateHook extends VersionedMechanic {
    boolean shouldApplyFlyingVeto(Player player, boolean vanillaFlying);
}
