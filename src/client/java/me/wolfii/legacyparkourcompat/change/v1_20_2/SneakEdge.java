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
 * Restores the full-height support probes and signed 0.05 reductions shared by
 * Minecraft 1.19 through 1.20.4.
 *
 * <p>Mojmap {@code net.minecraft.world.entity.player.Player#maybeBackOffFromEdge}
 * and {@code #isAboveGround} retain the same eligibility gates, full-box
 * downward support probe, X-then-Z-then-diagonal order, signed 0.05 reductions,
 * and vector construction in 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, and 1.20.4.
 * The gates are, in order: not flying, requested Y at most zero, SELF or PLAYER
 * mover, staying on the ground surface, then the above-ground predicate. The
 * latter is {@code onGround || (fallDistance < maxUpStep && !noCollision(box
 * moved by (0, fallDistance - maxUpStep, 0)))}. The reductions probe the full
 * box at (x, -maxUpStep, 0), then (0, -maxUpStep, z), then both offsets; each
 * component in [-0.05, 0.05) becomes zero and every other component advances
 * toward zero by 0.05. The returned vector retains the requested Y. Exact
 * method bodies differ only in field-versus-getter access at 1.19.4 and
 * 1.20.4; the audited vanilla getters return the corresponding backing fields.
 * See those releases' {@code Player.java}, {@code maybeBackOffFromEdge} and
 * {@code isAboveGround}: lines 1060–1117 (1.19), 1061–1118 (1.19.1 and 1.19.2),
 * 1050–1107 (1.19.3), 1041–1099 (1.19.4), and 1048–1106 (1.20.4).
 *
 * <p>In Mojmap 1.20.5, {@code Player#maybeBackOffFromEdge},
 * {@code #isAboveGround}, and {@code #canFallAtLeast} replace the full-box
 * probes with feet-slab probes and change the reduction/cutoff loop; see
 * {@code Player.java}, lines 1067–1125. The 1.20.5 native fence leaves that
 * later operation intact. This change composes the existing edge loops and
 * collision-query hook so separately resolved collision behavior remains
 * available.
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
        float fallDistance = (float) player.fallDistance;
        float downwardOffset = fallDistance - probeDistance;
        return player.onGround()
            || fallDistance < probeDistance
                && !player.level().noCollision(
                    player,
                    player.getBoundingBox().move(0.0, downwardOffset, 0.0)
                );
    }
}
