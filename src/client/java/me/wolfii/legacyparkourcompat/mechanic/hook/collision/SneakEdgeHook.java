package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Resolves horizontal movement against the sneak-edge guard. The prevented
 * drop distance resolves independently through {@link SneakEdgeDistanceHook}.
 * An empty result preserves the native method.
 */
@FunctionalInterface
@MechanicType("player.sneak.edge")
public interface SneakEdgeHook extends VersionedMechanic {
    Optional<Vec3> maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryHook collisionQuery
    );
}
