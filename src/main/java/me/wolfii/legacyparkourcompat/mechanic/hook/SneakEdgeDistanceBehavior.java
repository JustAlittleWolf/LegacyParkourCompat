package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/**
 * How far sneaking will prevent a drop ({@code Player#maybeBackOffFromEdge}
 * comparing against {@code maxUpStep}). 1b before 1.11, {@code 0.6b} after.
 *
 * <p>Kept separate from {@link SneakEdgeBehavior} so the 1.16.2 step-down
 * algorithm can resolve independently.
 */
@MechanicType("player.sneak.edge.distance")
public interface SneakEdgeDistanceBehavior extends VersionedMechanic {
    float edgeFallDistance(Player player, float vanillaMaxUpStep);
}
