package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Leaves the 1.20.5 feet-slab edge-backoff operation native.
 *
 * <p>Mojmap 1.20.5 and 1.20.6
 * {@code net.minecraft.world.entity.player.Player#maybeBackOffFromEdge},
 * {@code #isAboveGround}, and {@code #canFallAtLeast} use feet-slab probes,
 * sign-based 0.05 reductions, an absolute-value cutoff, and stop a reduction
 * pass after an axis reaches zero. Mojmap 1.20.4
 * {@code Player#maybeBackOffFromEdge} and {@code #isAboveGround} instead use a
 * full player-box probe shifted down by {@code maxUpStep()} and the earlier
 * signed component reductions. The native helper is reached from
 * {@code Entity#move} before collision clipping in all three releases.
 *
 * <p>This fence starts at the exact stable-release cutover in the source
 * comparison. Returning empty preserves native execution, while the probe and
 * collision-query hooks continue to resolve independently.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class SneakEdgeNativeFence implements SneakEdgeHook {
    @Override
    public Optional<Vec3> maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryHook collisionQuery
    ) {
        return Optional.empty();
    }
}
