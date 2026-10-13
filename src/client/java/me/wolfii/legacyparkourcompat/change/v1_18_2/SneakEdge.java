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
