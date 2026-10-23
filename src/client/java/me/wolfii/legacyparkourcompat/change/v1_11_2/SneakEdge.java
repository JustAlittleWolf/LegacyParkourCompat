package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBackoff;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.11/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(MoverType, double, double, double) applies the grounded sneaking
 *   player gate only to SELF/PLAYER movement and probes at -stepHeight.
 * 1.11.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(MoverType, double, double, double) retains that gate and offset.
 * The accepted comparison establishes the 1.11.0 through 1.11.2 boundary.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class SneakEdge implements SneakEdgeHook {
    @Override
    public Optional<Vec3> maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryHook collisionQuery
    ) {
        if ((moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !player.onGround()
            || !player.isShiftKeyDown()) {
            return Optional.of(delta);
        }

        return Optional.of(SneakEdgeBackoff.apply(player, delta, probeDistance, collisionQuery));
    }
}
