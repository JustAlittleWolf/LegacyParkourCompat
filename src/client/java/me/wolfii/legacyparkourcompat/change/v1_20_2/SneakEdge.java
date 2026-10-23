package me.wolfii.legacyparkourcompat.change.v1_20_2;

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
 * Restores the 1.19.4 full-height support probes and signed 0.05 reductions.
 *
 * <p>Mojmap 1.19.4 {@code net.minecraft.world.entity.player.Player#maybeBackOffFromEdge}
 * and {@code #isAboveGround} use the full player box shifted down by maxUpStep;
 * unobfuscated 26.2 {@code net.minecraft.world.entity.player.Player#maybeBackOffFromEdge},
 * {@code #isAboveGround}, and {@code #canFallAtLeast} use a feet slab instead.
 * This change composes the existing edge loops and collision-query hook so
 * separately resolved collision behavior remains available.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
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
        if (!player.getAbilities().flying
            && delta.y <= 0.0
            && (moverType == MoverType.SELF || moverType == MoverType.PLAYER)
            && stayingOnGroundSurface
            && isAboveGround(player, probeDistance)) {
            return Optional.of(SneakEdgeBackoff.apply(player, delta, probeDistance, collisionQuery));
        }

        return Optional.of(delta);
    }

    private static boolean isAboveGround(Player player, float probeDistance) {
        float fallDistance = player.fallDistance;
        float downwardOffset = fallDistance - probeDistance;
        return player.onGround()
            || fallDistance < probeDistance
                && !player.level().noCollision(
                    player,
                    player.getBoundingBox().move(0.0, downwardOffset, 0.0)
                );
    }
}
