package me.wolfii.legacyparkourcompat.change.common;

import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeCollisionQueryBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/** Shared horizontal backoff loops used by historical sneak-edge changes. */
public final class SneakEdgeBackoff {
    private SneakEdgeBackoff() {
    }

    public static Vec3 apply(
        Player player,
        Vec3 delta,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryBehavior collisionQuery
    ) {
        double x = delta.x;
        double z = delta.z;
        double downStep = -probeDistance;
        while (x != 0.0D && noCollision(player, player.getBoundingBox().move(x, downStep, 0.0D), collisionQuery)) {
            x = reduceTowardZero(x);
        }
        while (z != 0.0D && noCollision(player, player.getBoundingBox().move(0.0D, downStep, z), collisionQuery)) {
            z = reduceTowardZero(z);
        }
        while (x != 0.0D && z != 0.0D
            && noCollision(player, player.getBoundingBox().move(x, downStep, z), collisionQuery)) {
            x = reduceTowardZero(x);
            z = reduceTowardZero(z);
        }
        return new Vec3(x, delta.y, z);
    }

    private static boolean noCollision(
        Player player,
        AABB candidate,
        @Nullable SneakEdgeCollisionQueryBehavior collisionQuery
    ) {
        return collisionQuery == null
            ? player.level().noCollision(player, candidate)
            : collisionQuery.noCollision(player, candidate);
    }

    private static double reduceTowardZero(double value) {
        if (value < 0.05D && value >= -0.05D) {
            return 0.0D;
        }
        return value > 0.0D ? value - 0.05D : value + 0.05D;
    }
}
