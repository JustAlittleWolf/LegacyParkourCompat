package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBackoff;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 *   maybeBackOffFromEdge.
 * 1.16.5/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 *   maybeBackOffFromEdge.
 * 1.16.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 *   isAboveGround.
 * 1.16.2/mojmap/net/minecraft/world/entity/LivingEntity.java initializes
 *   maxUpStep to 0.6F; SneakEdgeDistanceHook retains that probe distance.
 *
 * Mojmap 1.16.2 Player#maybeBackOffFromEdge excludes flying players and
 * admits grounded or short-fall support through Player#isAboveGround. The
 * shared backoff helper retains the X, Z, then joint loops and their
 * step-height probes.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   move.
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface.
 *   maybeBackOffFromEdge.
 *   isAboveGround.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   move.
 * 1.19.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 * 1.19.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface.
 *   maybeBackOffFromEdge.
 *   isAboveGround.
 *
 * <p>Minecraft 1.18, 1.18.1 and 1.18.2 Mojmap, Player#maybeBackOffFromEdge:
 * the eligibility gate has no requested-Y condition. Minecraft 1.19 Mojmap,
 * the same class/member, adds requestedMovement.y <= 0.0; 1.19.4 retains it.
 * The first stable release excluding ascending movement is therefore 1.19.
 *
 * <p>1.18.2 Mojmap LivingEntity#jumpFromGround writes positive Y without
 * clearing onGround. Entity#move invokes Player#maybeBackOffFromEdge before
 * collision clipping and before updating onGround, so a crouching jump can
 * still back off horizontally. Preserve delta.y and the X, Z, then joint
 * loops' asymmetric comparisons (value < 0.05 and value >= -0.05).
 *
 * <p>The existing historical handler returns from the method head before the
 * native Y gate and probe loops run. It already implements this delta while
 * composing the separately resolved probe distance and collision queries;
 * an additional native vertical-gate override would duplicate this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class SneakEdge implements SneakEdgeHook {
    @Override
    public Vec3 maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryHook collisionQuery
    ) {
        if (player.getAbilities().flying
            || (moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !stayingOnGroundSurface
            || !isAboveGround(player, probeDistance)) {
            return delta;
        }

        return SneakEdgeBackoff.apply(player, delta, probeDistance, collisionQuery);
    }

    private static boolean isAboveGround(Player player, float probeDistance) {
        return player.onGround()
            || player.fallDistance < probeDistance
                && !player.level().noCollision(
                    player,
                    player.getBoundingBox().move(0.0D, player.fallDistance - probeDistance, 0.0D)
                );
    }
}
