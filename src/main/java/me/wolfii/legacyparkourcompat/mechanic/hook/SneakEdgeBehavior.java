package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/** Resolves horizontal movement against the sneak-edge guard.
 * The prevented drop distance resolves independently through {@link SneakEdgeDistanceBehavior}. */
@FunctionalInterface
@MechanicType("player.sneak.edge")
public interface SneakEdgeBehavior extends VersionedMechanic {
    Vec3 maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryBehavior collisionQuery
    );
}
